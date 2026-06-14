#!/bin/sh
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

# Entrypoint script: /usr/local/bin/entrypoint.sh

# These following variables are read directly from the container's environment (set by ENV):
#   - APP_USER
#   - APP_HOME

# 1. Read PUID and PGID from the environment, defaulting to the build-time user's ID (e.g., 1000)
HOST_UID=${PUID:-$(id -u "$APP_USER")}
HOST_GID=${PGID:-$(id -g "$APP_USER")}

echo "Starting with UID: $HOST_UID, GID: $HOST_GID"

# 2. Check if the current user/group IDs need changing
if [ "$(id -u "$APP_USER")" != "$HOST_UID" ] || [ "$(id -g "$APP_USER")" != "$HOST_GID" ]; then
    # Modify the internal user/group to match the host UID/GID
    usermod -o -u "$HOST_UID" "$APP_USER"
    groupmod -o -g "$HOST_GID" "$APP_USER"
fi

# 3. Fix permissions on the entire volume mount point
# IMPORTANT: This step changes the ownership on the HOST file system.
chown -R "$HOST_UID":"$HOST_GID" "$APP_HOME"

# 4. Configure JVM heap via environment variables (overridable per container)
JAVA_XMS=${JAVA_XMS:-768m}
JAVA_XMX=${JAVA_XMX:-1024m}

# 5. Set up GC logging to a file in the logs directory with rotation
GC_LOG_FILE="${APP_HOME}/logs/gc.log"
GC_LOG_OPTS="-Xlog:gc*:file=${GC_LOG_FILE}::filecount=5,filesize=10M"

# 6. Drop privileges and execute the main Java application
exec su-exec "$APP_USER" java \
    -Xms"${JAVA_XMS}" \
    -Xmx"${JAVA_XMX}" \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=200 \
    -XX:+ExitOnOutOfMemoryError \
    "${GC_LOG_OPTS}" \
    "org.springframework.boot.loader.launch.JarLauncher"
