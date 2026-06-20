#!/bin/bash
#
# Copyright (c) 2021-2026 Ricardo do Canto
#
# This file is part of the EnduranceTrio project.
#
# Licensed under the Functional Software License (FSL), Version 1.1, ALv2 Future License
# (the "License");
#
# You may not use this file except in compliance with the License. You may obtain a copy
# of the License at https://fsl.software/
#
# THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
# PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
#
# IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
# SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
# EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
#

# PostgreSQL initialisation script
# =================================
# This script is run by the PostgreSQL container on first startup.
# It creates the databases and users required by the EnduranceTrio application.
#
# Environment variables set in docker-compose.yaml:
#   - POSTGRES_USER / POSTGRES_PASSWORD (superuser)
#   - PRD_DB_USERNAME / PRD_DB_SECRET
#   - STG_DB_USERNAME / STG_DB_SECRET

set -euo pipefail

required_vars="STG_DB_USERNAME STG_DB_SECRET PRD_DB_USERNAME PRD_DB_SECRET POSTGRES_USER"
for var in $required_vars; do
    if [ -z "${!var:-}" ]; then
        echo "Error: $var is not set or is empty" >&2
        exit 1
    fi
done

psql -v ON_ERROR_STOP=1 \
     -v stg_user="$STG_DB_USERNAME" \
     -v stg_pass="$STG_DB_SECRET" \
     -v prd_user="$PRD_DB_USERNAME" \
     -v prd_pass="$PRD_DB_SECRET" \
     --username "$POSTGRES_USER" <<-'EOSQL'
    -- Staging database
    SELECT 'CREATE DATABASE stg_endurancetrio_community'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stg_endurancetrio_community')\gexec
    SELECT format('CREATE USER %I WITH PASSWORD %L', :'stg_user', :'stg_pass')
    WHERE NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = :'stg_user')\gexec
    GRANT ALL PRIVILEGES ON DATABASE stg_endurancetrio_community TO :"stg_user";

    -- Production database
    SELECT 'CREATE DATABASE prd_endurancetrio_community'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'prd_endurancetrio_community')\gexec
    SELECT format('CREATE USER %I WITH PASSWORD %L', :'prd_user', :'prd_pass')
    WHERE NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = :'prd_user')\gexec
    GRANT ALL PRIVILEGES ON DATABASE prd_endurancetrio_community TO :"prd_user";
EOSQL
