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

set -euo pipefail

WEBPACK_DIR="endurancetrio-app/src/main/resources/webpack"
PACKAGE_JSON="${WEBPACK_DIR}/package.json"

print_usage() {
    echo "Usage: $0 <version> [--commit]"
    echo
    echo "Updates the project version across all Maven modules (POM files)"
    echo "and the frontend package.json and package-lock.json files."
    echo
    echo "The -SNAPSHOT suffix (if present) is stripped for package.json"
    echo "since npm does not follow Maven snapshot conventions."
    echo
    echo "Options:"
    echo "  --commit    Also create a git commit with the version change"
    echo
    echo "Examples:"
    echo "  $0 0.5.0"
    echo "  $0 0.5.0-SNAPSHOT"
    echo "  $0 0.5.0 --commit"
    exit 1
}

if [ -z "${1:-}" ] || [[ "${1:-}" == "--commit" ]]; then
    print_usage
fi

VERSION="$1"
DO_COMMIT=false
if [ "${2:-}" == "--commit" ]; then
    DO_COMMIT=true
elif [ -n "${2:-}" ]; then
    print_usage
fi

# Remove -SNAPSHOT suffix for package.json
STRIPPED_VERSION="${VERSION%-SNAPSHOT}"

echo "------------------------------------------------------------------------------------------------------------------------"
echo -e "[\e[34mINFO\e[0m] Setting version to ${VERSION}..."
echo "------------------------------------------------------------------------------------------------------------------------"
echo

# Update Maven POMs
./mvnw versions:set -DnewVersion="${VERSION}" -q
./mvnw versions:commit -q

echo -e "[\e[32mOK\e[0m] Updated POM files to version ${VERSION}"

# Update package.json
jq --arg v "$STRIPPED_VERSION" '.version = $v' "$PACKAGE_JSON" > "${PACKAGE_JSON}.tmp" && mv "${PACKAGE_JSON}.tmp" "$PACKAGE_JSON"
echo -e "[\e[32mOK\e[0m] Updated ${PACKAGE_JSON} to version ${STRIPPED_VERSION}"

# Sync package-lock.json
npm --prefix "${WEBPACK_DIR}" install --package-lock-only --silent
echo -e "[\e[32mOK\e[0m] Synced ${WEBPACK_DIR}/package-lock.json"

echo
echo "------------------------------------------------------------------------------------------------------------------------"
echo -e "[\e[34mINFO\e[0m] Version ${VERSION} has been applied."
echo "------------------------------------------------------------------------------------------------------------------------"

echo
echo "Changed files:"
git diff --name-only

if [ "$DO_COMMIT" = true ]; then
    git add -u
    git commit -m "Set version to ${VERSION}"
    echo -e "[\e[32mOK\e[0m] Created commit for version ${VERSION}"
else
    echo
    echo "To commit these changes, run:"
    echo "  git add -u && git commit -m \"Set version to ${VERSION}\""
fi
