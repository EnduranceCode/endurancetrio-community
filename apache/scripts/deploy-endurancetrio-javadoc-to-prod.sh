#!/bin/bash
#
# Copyright (c) 2011-2026 Ricardo do Canto
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

# This script automates the deployment of the EnduranceTrio JavaDoc website to the production
# environment.
#
# It is designed to be run on the production server after staging files have been prepared
# and uploaded. The script uses environment variables defined on `.env-prod-endurancetrio-javadoc`
# file.
#
# The script performs the following steps:
#   1. Loads environment variables from the `.env-prod-endurancetrio-javadoc` file.
#   2. Validates the presence of required environment variables.
#   3. Creates a timestamped backup of the current production files.
#   4. Deploys files from the staging folder to the production folder.
#   5. Cleans up the staging folder after deployment.
#   6. Logs progress and errors for traceability.
#
# Ensure the `.env-prod-endurancetrio-javadoc` file contains the variables:
#   - WEB_ROOT
#   - DEPLOY_STAGING_FOLDER
#   - BACKUP_FOLDER
#   - FILE_OWNER
#
# Usage:
#   1. Copy this file to the home folder of the server with the command:
#        wget -P ~/ https://raw.githubusercontent.com/EnduranceCode/endurancetrio-community/refs/heads/master/apache/scripts/deploy-endurancetrio-javadoc-to-prod.sh
#   2. Copy the companion .env-prod-endurancetrio-javadoc file to the home folder:
#        wget -P ~/ https://raw.githubusercontent.com/EnduranceCode/endurancetrio-community/refs/heads/master/apache/scripts/.env-prod-endurancetrio-javadoc
#   3. Ensure the script is executable with the command:
#        chmod +x ~/deploy-endurancetrio-javadoc-to-prod.sh
#   4. Edit ~/.env-prod-endurancetrio-javadoc and set the correct values
#   5. Execute the script with the command:
#        ./deploy-endurancetrio-javadoc-to-prod.sh

# Exit on error
set -e

# Load environment variables from the .env-prod-endurancetrio-javadoc file
set -a
source .env-prod-endurancetrio-javadoc
set +a

TIMESTAMP=$(date +%Y%m%d-%H%M%S)

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Validating required environment variables..."
MISSING=""
[ -z "${WEB_ROOT}" ] && MISSING="${MISSING} WEB_ROOT"
[ -z "${DEPLOY_STAGING_FOLDER}" ] && MISSING="${MISSING} DEPLOY_STAGING_FOLDER"
[ -z "${BACKUP_FOLDER}" ] && MISSING="${MISSING} BACKUP_FOLDER"
[ -z "${FILE_OWNER}" ] && MISSING="${MISSING} FILE_OWNER"
if [ -n "${MISSING}" ]; then
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [ERROR] : Missing required environment variables:${MISSING}"
  exit 1
fi

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Verifying source files..."
if [ ! -d "${DEPLOY_STAGING_FOLDER}" ]; then
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [ERROR] : Staging folder missing"
  exit 1
fi

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Checking if the folder ${WEB_ROOT} exists..."
if [ ! -d "${WEB_ROOT}" ]; then
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [ERROR] : The folder ${WEB_ROOT} does not exist"
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Create the folder ${WEB_ROOT} and re-run the script"
  exit 1
fi

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Creating backup..."
mkdir -p "${BACKUP_FOLDER}"
tar -czf "${BACKUP_FOLDER}/endurancetrio-javadoc-backup-${TIMESTAMP}.tar.gz" -C "${WEB_ROOT}" .

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Deploying files to production..."
if [ ! -w "${WEB_ROOT}" ]; then
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [WARN] : Attempting sudo for deployment..."
  echo
  sudo rsync -av --delete "${DEPLOY_STAGING_FOLDER}/" "${WEB_ROOT}/"
  echo
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Setting ownership on ${WEB_ROOT} to ${FILE_OWNER}..."
  sudo chown -R "${FILE_OWNER}" "${WEB_ROOT}"
else
  echo
  rsync -av --delete "${DEPLOY_STAGING_FOLDER}/" "${WEB_ROOT}/"
  echo
fi

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Verifying deployment..."
diff -rq "${DEPLOY_STAGING_FOLDER}" "${WEB_ROOT}" > /dev/null && \
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Deployment verified successfully" || \
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] [WARN] : Deployment differences detected"
  
echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Cleaning up..."
rm -rf "${DEPLOY_STAGING_FOLDER:?}/"

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : Deployment complete! Files are now live in ${WEB_ROOT}"

echo "[$(date '+%Y-%m-%d %H:%M:%S')] [INFO] : To rollback: tar -xzf ${BACKUP_FOLDER}/endurancetrio-javadoc-backup-${TIMESTAMP}.tar.gz -C ${WEB_ROOT}"
