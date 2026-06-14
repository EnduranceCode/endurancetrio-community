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

set -e

psql -v ON_ERROR_STOP=1 \
     -v stg_user="$STG_DB_USERNAME" \
     -v stg_pass="$STG_DB_SECRET" \
     -v prg_user="$PRD_DB_USERNAME" \
     -v prg_pass="$PRD_DB_SECRET" \
     --username "$POSTGRES_USER" <<-'EOSQL'
    -- Staging database
    CREATE DATABASE stg_endurancetrio_community;
    CREATE USER :"stg_user" WITH PASSWORD :'stg_pass';
    GRANT ALL PRIVILEGES ON DATABASE stg_endurancetrio_community TO :"stg_user";

    -- Production database
    CREATE DATABASE prd_endurancetrio_community;
    CREATE USER :"prg_user" WITH PASSWORD :'prg_pass';
    GRANT ALL PRIVILEGES ON DATABASE prd_endurancetrio_community TO :"prg_user";
EOSQL

# Grant schema-level permissions (required for Flyway and JPA to create/alter objects)
for db in stg_endurancetrio_community prd_endurancetrio_community; do
    case "$db" in
        stg_endurancetrio_community)
            db_user="${STG_DB_USERNAME}"
            ;;
        prd_endurancetrio_community)
            db_user="${PRD_DB_USERNAME}"
            ;;
    esac
    psql -v ON_ERROR_STOP=1 \
         -v db_user="$db_user" \
         --username "$POSTGRES_USER" --dbname "$db" <<-'EOSQL'
        GRANT ALL ON SCHEMA public TO :"db_user";
        GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO :"db_user";
        GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO :"db_user";
        ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO :"db_user";
        ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO :"db_user";
EOSQL
done
