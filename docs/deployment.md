# Deployment Guide

This document covers the deployment workflow for the **EnduranceTrio** project. It describes how
to set up and run the application on a Linux server using Docker Compose and an Apache reverse
proxy, with both a **staging** and a **production** environment on the same server.

Source code is maintained in the personal repository
`https://github.com/EnduranceCode/endurancetrio-community`. Public releases and Docker image
publishing are executed from the organization repository
`https://github.com/endurancetrio/endurancetrio-community`.

For an overview of the project, see the [main README.md](../README.md).

## Table of Contents

1. [Server Requirements](#server-requirements)
2. [Container Architecture](#container-architecture)
3. [Prerequisites](#prerequisites)
4. [Server Setup](#server-setup)
5. [PostgreSQL Setup](#postgresql-setup)
6. [Application Deployment](#application-deployment)
7. [Reverse Proxy Setup](#reverse-proxy-setup)
8. [SSL Certificate](#ssl-certificate)
9. [JavaDoc Deployment](#javadoc-deployment)
10. [Monitoring](#monitoring)

## Server Requirements

The application is deployed on a single Linux server running Docker, Docker Compose, and Apache.

### Minimum (2 vCPU / 4 GB memory)

| Component      | Estimated memory |
|----------------|------------------|
| Alpine OS      | ~100 MB          |
| PostgreSQL     | ~300 MB          |
| Staging JVM    | ~1 GB            |
| Production JVM | ~1.5 GB          |
| **Total**      | **~2.9 GB**      |

This configuration is viable for low traffic. The staging container can be stopped when not in
use to free resources (see [Taking staging offline](#taking-staging-offline)).

### Recommended (4 vCPU / 8 GB memory)

| Component      | Estimated memory |
|----------------|------------------|
| Alpine OS      | ~100 MB          |
| PostgreSQL     | ~400 MB          |
| Staging JVM    | ~1.5 GB          |
| Production JVM | ~2.5 GB          |
| **Total**      | **~4.5 GB**      |

Provides comfortable headroom for concurrent environments, higher traffic, and GC spikes. The
larger heap also improves application performance under load.

> **Upgrade path:** When moving to a 4 vCPU / 8 GB server, increase the `STG_JAVA_XMS`, `STG_JAVA_XMX`,
> `PRD_JAVA_XMS`, and `PRD_JAVA_XMX` values in the `.env` file
> (e.g., production: 1024m/2048m, staging: 768m/1024m).

## Container Architecture

The deployment uses three Docker containers:

| Service                | Container name                | Spring profile | Internal port |
|------------------------|-------------------------------|----------------|---------------|
| PostgreSQL 18          | `endurancetrio-postgres`      | —              | 5432          |
| Staging application    | `stg-endurancetrio-community` | `dev`          | 8080          |
| Production application | `prd-endurancetrio-community` | `prod`         | 8080          |

Both application containers use the same internal port (8080). This is safe because each Docker
container has its own network namespace — `localhost:8080` inside the production container reaches
the production application, and `localhost:8080` inside the staging container reaches the staging
application. The external ports on the host are differentiated by the Apache reverse proxy
configuration.

Key design points:

- The PostgreSQL container hosts two databases: `stg_endurancetrio_community` (staging)
  and `prd_endurancetrio_community` (production).
- Both application containers connect to the same PostgreSQL instance using different credentials
  and different database URLs.
- The database files are stored on a bind mount at `/opt/endurancetrio-community/db/` so data
  persists across container redeployments.
- Logs are stored on the host filesystem under `/opt/endurancetrio-community/logs/stg/` and
  `/opt/endurancetrio-community/logs/prd/`.

## Prerequisites

### On the server

- [Docker](https://docs.docker.com/engine/install/) and
  [Docker Compose](https://docs.docker.com/compose/install/)
- [Apache HTTP Server](https://httpd.apache.org/) with `proxy`, `proxy_http`, `headers`,
  and `rewrite` modules enabled
- This guide assumes that the Apache Server web root folder is configured as described
  [here](https://github.com/EnduranceCode/server-setup-guide/blob/master/02-01-apache-server-installation.md)
- A registered domain with DNS records pointing to the server's IP address for each subdomain

The [optional cron-based metrics collection](#optional-cron-based-metrics-collection) requires
[`jq`](https://jqlang.org/) to be installed. It can be installed with the following command:

```shell
sudo apt install jq
```

### On the development machine

- [Maven](https://maven.apache.org/) or the Maven Wrapper (`./mvnw`) for building the JavaDoc site
- [rsync](https://rsync.samba.org/) for uploading the JavaDoc to the server

## Server Setup

To create the necessary folders for the app installation, execute the following command
in the deployment server:

```shell
sudo mkdir -p /opt/endurancetrio-community/db
sudo mkdir -p /opt/endurancetrio-community/logs/prd
sudo mkdir -p /opt/endurancetrio-community/logs/stg
sudo mkdir -p /opt/endurancetrio-community/scripts
```

We will create a user to manage the **EnduranceTrio** application and set it as the owner
of the folder `/opt/endurancetrio-community/`. This will be achieved with the execution of the
following commands:

```shell
sudo useradd -r -s /usr/sbin/nologin endurancetrio
sudo chown -R endurancetrio:endurancetrio /opt/endurancetrio-community/
```

To confirm that the folder `/opt/endurancetrio-community/` has the correct ownership,
check the output of the following command:

```shell
ls -lag /opt/endurancetrio-community/
```

The folder `/opt/endurancetrio-community` will store the files necessary to deploy the application
with Docker Compose. To download, from this repository to the server, the `docker-compose.yaml` file
and the template for the `.env` file, as well as the PostgreSQL container initialization script,
execute the following commands:

```shell
cd /opt/endurancetrio-community/
sudo wget https://raw.githubusercontent.com/EnduranceCode/endurancetrio-community/refs/heads/master/docker/deployment/docker-compose.yaml
sudo wget https://raw.githubusercontent.com/EnduranceCode/endurancetrio-community/refs/heads/master/docker/deployment/.env-template
sudo wget -P scripts/ https://raw.githubusercontent.com/EnduranceCode/endurancetrio-community/refs/heads/master/docker/deployment/scripts/init-db.sh
```

To confirm that the files were downloaded, check the output of the following command:

```shell
ls -lag && ls -lag scripts/
```

Check the content of the `docker-compose.yaml` file with the below command, and if necessary, use
the [nano text editor](https://www.nano-editor.org/) to introduce the necessary adaptations.

```shell
cat docker-compose.yaml
```

Create a `.env` file in the deployment folder, based on the provided `.env-template`, using
the following command:

```shell
sudo mv .env-template .env
```

The `.env` file manages environment-specific configurations and secrets.

**Key Environment Variables:**

| Variable                     | Description                                                                          | Required    |
|------------------------------|--------------------------------------------------------------------------------------|-------------|
| `STG_VERSION`                | The Docker image tag to deploy for staging (e.g., `sha-abc1234-staging-fix-login`)   | Yes         |
| `PRD_VERSION`                | The Docker image tag to deploy for production (e.g., `1.0.0`)                        | Yes         |
| `PUID`                       | User ID under which the container process runs                                       | Yes         |
| `PGID`                       | Group ID under which the container process runs                                      | Yes         |
| `STG_ENDURANCETRIO_EXT_PORT` | Host port for staging app (e.g., `8081`)                                             | Yes         |
| `PRD_ENDURANCETRIO_EXT_PORT` | Host port for production app (e.g., `8080`)                                          | Yes         |
| `STG_JAVA_XMS`               | Initial JVM heap size for staging (e.g., `512m`)                                     | Optional    |
| `STG_JAVA_XMX`               | Maximum JVM heap size for staging (e.g., `768m`)                                     | Optional    |
| `PRD_JAVA_XMS`               | Initial JVM heap size for production (e.g., `768m`)                                  | Optional    |
| `PRD_JAVA_XMX`               | Maximum JVM heap size for production (e.g., `1024m`)                                 | Optional    |
| `POSTGRES_EXT_PORT`          | Host loopback port mapped to PostgreSQL for SSH-tunneled admin access (e.g., `5432`) | Optional    |
| `DB_ROOT_PASSWORD`           | Password for the `postgres` superuser (temporary environment variable export)        | Conditional |
| `STG_DB_URL`                 | Staging datasource URL                                                               | Yes         |
| `STG_DB_USERNAME`            | Staging database user name                                                           | Yes         |
| `STG_DB_SECRET`              | Staging database password                                                            | Yes         |
| `PRD_DB_URL`                 | Production datasource URL                                                            | Yes         |
| `PRD_DB_USERNAME`            | Production database user name                                                        | Yes         |
| `PRD_DB_SECRET`              | Production database password                                                         | Yes         |
| `STG_CORS_ALLOWED_ORIGINS`   | Allowed CORS origins for staging (e.g., `https://stg.example.com`)                   | Yes         |
| `PRD_CORS_ALLOWED_ORIGINS`   | Allowed CORS origins for production (e.g., `https://example.com`)                    | Yes         |
| `KOFI_USER_ID`               | Ko-fi user ID for the donation button                                                | Optional    |
| `FACEBOOK_PAGE_ID`           | Facebook Page ID for Open Graph meta tags                                            | Optional    |
| `TWITTER_SITE`               | Twitter/X site handle for Twitter Card meta tags                                     | Optional    |
| `GOOGLE_ADSENSE_ID`          | Google AdSense publisher ID for site verification                                    | Optional    |
| `KIT_API_KEY`                | Kit.com API key. Obtain from Kit.com → Settings → Developer Settings                 | Optional    |
| `KIT_FORM_ID_EN`             | Kit.com form ID for the English newsletter subscription form                         | Optional    |
| `KIT_FORM_ID_PT`             | Kit.com form ID for the Portuguese newsletter subscription form                      | Optional    |
| `FIRST_OWNER`                | Name for the initial account initialization                                          | Conditional |
| `FIRST_HASH`                 | Bcrypt hash for the initial account initialization (temporary export)                | Conditional |

`Conditional` means the variable is required only for specific workflows:

- `DB_ROOT_PASSWORD`: required when PostgreSQL initializes from an empty data directory
  (first deployment or volume recreation)
- `FIRST_OWNER` and `FIRST_HASH`: required only when bootstrapping the first tracker account

The user ID of the created `endurancetrio` user is obtained with the following command:

```shell
id -u endurancetrio
```

The group ID of the created `endurancetrio` user is obtained with the following command:

```shell
id -g endurancetrio
```

Open the `.env` file with the [nano text editor](https://www.nano-editor.org/)
and set the values for required and persistent environment variables. For variables documented as
temporary environment variable exports, provide them only during the relevant initialization flow.
After setting the persistent values, save the file with the command `CTRL + O` and then close the
editor with the command `CTRL + X`.

Given the sensitive nature of the variable `FIRST_HASH`, its value should be provided as a temporary
environment variable rather than persisted in the `.env` file to reduce attack surface.

For the same reason, `DB_ROOT_PASSWORD` can also be provided as a temporary environment variable
instead of being persisted in the `.env` file. This is safe as long as the value is stored in a
password manager and can be provided again whenever PostgreSQL is initialized from an empty data
directory (first deployment or volume recreation).

> **Security Note**
>
> Refer to the [API Key Management](./development.md#api-key-management) section for details
> on generating the `FIRST_HASH`.

## PostgreSQL Setup

The PostgreSQL container is automatically initialized on first startup by the `init-db.sh`
script mounted at `/docker-entrypoint-initdb.d/`. This script:

1. Creates the `prd_endurancetrio_community` database
2. Creates the `stg_endurancetrio_community` database
3. Creates the staging and production database users with their respective passwords
4. Grants all privileges on each database to its corresponding user
5. Grants schema-level permissions for Flyway and JPA

The `init-db.sh` script reads the `STG_DB_USERNAME`, `STG_DB_SECRET`, `PRD_DB_USERNAME` 
and `PRD_DB_SECRET` variables from the container environment (passed via docker-compose).

> **Note:** Re-initialisation only happens if the PostgreSQL data directory inside the container is
> empty. The bind mount at `/opt/endurancetrio-community/db/` is mounted at `/var/lib/postgresql`
> inside the container. PostgreSQL 18+ stores data in a major-version-specific subdirectory
> (e.g., `/var/lib/postgresql/data/18/`). The init script is not executed again once data exists.

### Upgrading from a previous PostgreSQL deployment

If the PostgreSQL container fails to start with an error about incompatible data directory format
(PostgreSQL 18+ image with data from an older deployment), migrate the existing data to the new
directory layout:

```shell
docker compose -p endurancetrio-community down postgres

# Move existing data into a version-specific subdirectory
cd /opt/endurancetrio-community/db
sudo mkdir -p data/18
sudo mv $(ls -A | grep -v data) data/18/
```

Then restart the container with the updated configuration:

```shell
docker compose -p endurancetrio-community up -d postgres
```

> **Note:** For a fresh deployment (no data to preserve), clear the `db/` directory before starting:
>
> ```shell
> sudo rm -rf /opt/endurancetrio-community/db/*
> ```

The PostgreSQL service is mapped to the host loopback interface only (`127.0.0.1`) using
`POSTGRES_EXT_PORT` (default `5432`). This allows local tools on the server, and remote tools via
SSH tunnel, to connect without exposing PostgreSQL to the public internet.

### DBeaver access via SSH tunnel

When using DBeaver from your local machine, configure an SSH tunnel to the server and connect
through loopback:

- **SSH Host**: your deployment server
- **SSH User**: your server user
- **Database Host**: `127.0.0.1`
- **Database Port**: value configured in `POSTGRES_EXT_PORT` (or `5432` if unset)
- **Database Name**: `stg_endurancetrio_community` or `prd_endurancetrio_community`
- **Username/Password**: the corresponding database user credentials from `.env`

## Application Deployment

### 1. Start the database first

```shell
docker compose -p endurancetrio-community up -d postgres
```

The output of the above command should show that PostgreSQL container was deployed with success.
For a second confirmation, check the output of the following command:

```shell
docker ps
```

Check the PostgreSQL logs to confirm that the health check has passed:

```shell
docker logs endurancetrio-postgres
```

### 2. Deploy the staging application

After confirming that the database is up and healthy, deploy the staging application
with the following command:

```shell
docker compose -p endurancetrio-community up -d stg-endurancetrio-community
```

The output of the above command should show that the staging **EnduranceTrio** application was
deployed with success. For a second confirmation, follow the instructions presented in the below
[verification](#verification) section.

### 3. Deploy the production application

After confirming that the database is up and healthy, deploy the production application
with the following command:

```shell
docker compose -p endurancetrio-community up -d prd-endurancetrio-community
```

The output of the above command should show that the **production** EnduranceTrio application was
deployed with success. For a second confirmation, follow the instructions presented in the below
[verification](#verification) section.

### 4. Deploy both at once

After confirming that the database is up and healthy, deploy the staging and the production
application with the following command:

```shell
docker compose -p endurancetrio-community up -d
```

The output of the above command should show that the staging and production **EnduranceTrio**
applications were deployed with success. For a second confirmation, follow the instructions
presented in the below [verification](#verification) section.

### Updating a specific environment

To deploy a new version, update the relevant variable in `.env` and restart the service:

**Staging:**

```shell
# Edit .env, update STG_VERSION
docker compose -p endurancetrio-community up -d stg-endurancetrio-community
```

**Production:**

```shell
# Edit .env, update PRD_VERSION
docker compose -p endurancetrio-community up -d prd-endurancetrio-community
```

### Verification

After deployment, verify that the containers are running:

```shell
docker ps
```

Check the application logs:

```shell
docker logs stg-endurancetrio-community
docker logs prd-endurancetrio-community
```

Test the health endpoints:

```shell
curl -f http://localhost:<STG_ENDURANCETRIO_EXT_PORT>/actuator/health
curl -f http://localhost:<PRD_ENDURANCETRIO_EXT_PORT>/actuator/health
```

### Taking staging offline

When the staging environment is not needed, stop its container. Users visiting the staging
subdomain will see a static "offline" page served by Apache (see
[Staging offline fallback](#staging-offline-fallback)).

```shell
docker compose -p endurancetrio-community stop stg-endurancetrio-community
```

To bring it back:

```shell
docker compose -p endurancetrio-community up -d stg-endurancetrio-community
```

## Reverse Proxy Setup

### Enable Apache modules

To ensure that the necessary [Apache Server](https://httpd.apache.org/) modules for reverse
proxying are enabled, execute the following commands:

```shell
sudo a2enmod proxy
sudo a2enmod proxy_http
sudo a2enmod headers
sudo a2enmod rewrite
sudo systemctl reload apache2
```

### Create DNS records

After creating the necessary [DNS Records](https://docs.digitalocean.com/products/networking/dns/),
create an Apache Virtual Host configuration file. This repository includes a template that sets
the following redirects:

| Domain and Subdomains                                      | Target                                                         | Purpose                    |
|------------------------------------------------------------|----------------------------------------------------------------|----------------------------|
| `www.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}`     | `localhost:<PRD_ENDURANCETRIO_EXT_PORT>/`                      | Redirects to naked domain  |
| `{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}`         | `localhost:<PRD_ENDURANCETRIO_EXT_PORT>/`                      | Production web application |
| `api.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}`     | `localhost:<PRD_ENDURANCETRIO_EXT_PORT>/api/`                  | Production API             |
| `docs.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}`    | `/srv/www/endurancetrio-docs`                                  | JavaDoc static website     |
| `openapi.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}` | `localhost:<PRD_ENDURANCETRIO_EXT_PORT>/swagger-ui/index.html` | Swagger UI                 |
| `stg.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}`     | `localhost:<STG_ENDURANCETRIO_EXT_PORT>/`                      | Staging environment        |

### Configure Apache virtual hosts

To use the [provided template](../apache/vhost-template.conf), execute the following command:

```shell
sudo wget -P /etc/apache2/sites-available/ \
  https://raw.githubusercontent.com/EnduranceCode/endurancetrio-community/refs/heads/master/apache/vhost-template.conf
```

Then, replace the placeholder in the below command as appropriate and execute it to rename the file.

```shell
sudo mv /etc/apache2/sites-available/vhost-template.conf \
        /etc/apache2/sites-available/{SECOND_LEVEL_DOMAIN_SLD}.conf
```

> **Placeholder Definition**
>
> + **{SECOND_LEVEL_DOMAIN_SLD}** : The [Second-level domain](https://en.wikipedia.org/wiki/Second-level_domain) (e.g., 'example' in example.com)

Customize the Virtual Host configuration file downloaded with the previous command, using
[nano text editor](https://www.nano-editor.org/), and replace the included placeholders
as described in the following table:

| Placeholder                  | Description                                                |
|------------------------------|------------------------------------------------------------|
| <SECOND_LEVEL_DOMAIN_SLD>    | Domain name (e.g., 'example' in example.com)               |
| <TOP_LEVEL_DOMAIN_TLD>       | Top-level domain (e.g., 'com', 'org', 'net')               |
| <SERVER_ADMIN_EMAIL>         | Administrator email for server notifications               |
| <STG_ENDURANCETRIO_EXT_PORT> | External port where the staging app runs (e.g., 8081)      |
| <PRD_ENDURANCETRIO_EXT_PORT> | External port where the production app runs (e.g., 8080)   |

#### Staging Basic Authentication

The staging environment is protected with Apache HTTP Basic Authentication. Credentials are stored
in `/etc/apache2/.htpasswd-staging` and managed with the `htpasswd` utility.

If `htpasswd` is not available on the server, install it with the following command:

```shell
sudo apt update && sudo apt install apache2-utils
```

Create the first user with the following command. The `-c` flag creates the file and should be
used only when the file does not exist yet. The command will prompt you to enter and confirm the
password.

```shell
sudo htpasswd -c /etc/apache2/.htpasswd-staging {USERNAME}
```

> **Placeholder Definition**
>
> + **{USERNAME}** : The username to be granted access to the staging environment

To add additional users without overwriting the file, execute the following command (without `-c`):

```shell
sudo htpasswd /etc/apache2/.htpasswd-staging {USERNAME}
```

To update the password of an existing user, execute the same command with the existing username:

```shell
sudo htpasswd /etc/apache2/.htpasswd-staging {USERNAME}
```

To remove a user and revoke access:

```shell
sudo htpasswd -D /etc/apache2/.htpasswd-staging {USERNAME}
```

Secure the file:

```shell
sudo chown www-data:www-data /etc/apache2/.htpasswd-staging
sudo chmod 640 /etc/apache2/.htpasswd-staging
```

> **Security Note**
>
> Use strong, unique passwords for all staging users and keep them in a password manager. Since
> Basic Authentication sends credentials in every request, ensure SSL/TLS is enabled as described
> in the [SSL Certificate](#ssl-certificate) section.

#### Staging offline fallback

Create the static page that will be shown when the staging container is stopped:

```shell
sudo mkdir -p /srv/www/endurancetrio
sudo wget -P /srv/www/endurancetrio \
  https://raw.githubusercontent.com/EnduranceCode/endurancetrio-community/refs/heads/master/apache/index.html
```

To take staging offline:

```shell
docker compose -p endurancetrio-community stop stg-endurancetrio-community
```

Apache will automatically serve the static offline page (502 Bad Gateway when the container 
is stopped, 503 Service Unavailable during a restart). To bring it back:

```shell
docker compose -p endurancetrio-community up -d stg-endurancetrio-community
```

#### JavaDoc document root

Prepare the directory for the JavaDoc static site (content uploaded later):

```shell
sudo mkdir -p /srv/www/endurancetrio-docs
```

### Activate the configuration

```shell
sudo a2ensite {SECOND_LEVEL_DOMAIN_SLD}.conf
sudo apachectl configtest
sudo systemctl reload apache2
```

> **Placeholder Definition**
>
> + **{SECOND_LEVEL_DOMAIN_SLD}** : The [*Second-level domain*](https://en.wikipedia.org/wiki/Second-level_domain) (e.g., 'example' in example.com)

If the domain of the Virtual Host created with the previous procedure has already its DNS Records
pointing to the server's IP address, enter the below URL into a browser’s address bar to test
the new local Virtual Host.

```text
http://{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}
```

> **Placeholder Definition**
>
> + **{SECOND_LEVEL_DOMAIN_SLD}** : The [*Second-level domain*](https://en.wikipedia.org/wiki/Second-level_domain) (e.g., 'example' in example.com)
> + **{TOP_LEVEL_DOMAIN_TLD}**    : The [TLD](https://en.wikipedia.org/wiki/Top-level_domain) (e.g., 'com', 'org', 'net')

## SSL Certificate

If [Certbot](https://certbot.eff.org/instructions?ws=apache&os=ubuntufocal) isn't yet installed
on you server, install it and set the SSL certificate for the **EnduranceTrio** application
instance domain (or a subdomain) following the [instructions available here](https://github.com/EnduranceCode/server-setup-guide/blob/master/03-01-apache-server-management.md#312-apache--secure-apache-with-lets-encrypt).
If you already have SSL Certificates installed on your server with
[Certbot](https://certbot.eff.org/instructions?ws=apache&os=ubuntufocal), you can expand it
to include the new domain, or you can create a separate certificate for the new domain.

Before expanding an existing certificate, check which domains/subdomains are already covered by
the certificate lineage:

```shell
sudo certbot certificates
```

In the output, locate the certificate name that matches `{EXISTING_DOMAIN}` and review the
`Domains:` list.

Then, execute the expand command:

```shell
sudo certbot --apache --cert-name {EXISTING_DOMAIN} \
  --expand \
  -d {EXISTING_DOMAIN} \
  -d {SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
  -d www.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
  -d api.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
  -d openapi.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
  -d docs.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
  -d stg.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}
```

**Important:** The `--expand` flag **replaces** the entire domain list on the certificate. You
MUST include ALL domains and subdomains (both existing and new) in the command. It is not
possible to expand the certificate by providing only the new subdomains.

> **Placeholder Definition**
>
> + **{EXISTING_DOMAIN}** : The existing domain (or subdomain) that already has a SSL certificate
> + **{SECOND_LEVEL_DOMAIN_SLD}** : The [Second-level domain](https://en.wikipedia.org/wiki/Second-level_domain) (e.g., 'example' in example.com)
> + **{TOP_LEVEL_DOMAIN_TLD}**    : The [TLD](https://en.wikipedia.org/wiki/Top-level_domain) (e.g., 'com', 'org', 'net')

> **Warning — Certbot ServerAlias behavior**
>
> Certbot's `--apache` plugin may add new subdomains as `ServerAlias` to an existing SSL
> VirtualHost instead of creating a dedicated `<VirtualHost *:443>` block. This happens because
> Certbot reuses existing SSL VirtualHosts when it finds a new subdomain that wasn't there
> before. If the subdomain needs its own configuration (e.g., staging with Basic Auth and
> reverse proxy), it MUST have a dedicated VirtualHost block. The
> [Post-Certbot verification](#post-certbot-verification) section covers how to detect and
> fix this.

Otherwise, to create a separate certificate for the new domain (or subdomain), execute the following
command:

```shell
sudo certbot --apache -d {DOMAIN} -d www.{DOMAIN} -d api.{DOMAIN} -d openapi.{DOMAIN} -d docs.{DOMAIN} -d stg.{DOMAIN}
```

> **Placeholder Definition**
>
> + **{DOMAIN}** : The domain (or subdomain) of the new SSL certificate

### Post-Certbot verification

[Certbot](https://certbot.eff.org/instructions?ws=apache&os=ubuntufocal) will modify the HTTP
configuration file (`{SECOND_LEVEL_DOMAIN_SLD}.conf`) and create/update the SSL configuration file
(`{SECOND_LEVEL_DOMAIN_SLD}-le-ssl.conf`). A thorough verification is required to ensure the SSL
VirtualHosts are correctly set up.

#### 1. Check the VirtualHost structure

Run the following command to list all VirtualHosts and their ServerName/ServerAlias assignments:

```shell
sudo apachectl -S
```

In the output, verify that each subdomain has its **own** entry on port 443 with the correct
`ServerName`. If a subdomain appears only as a `ServerAlias` of another VirtualHost (e.g.,
`stg.example.com` listed under `example.com:443` instead of having its own line),
it needs a dedicated VirtualHost block (see step 2).

> **Why this matters:** When a subdomain is only a `ServerAlias`, it inherits the entire
> configuration of the parent VirtualHost. For example, `stg.example.com` would serve the same
> content as `example.com` instead of being proxied to the staging application.

#### 2. Verify and fix the SSL configuration

Open the SSL configuration file:

```shell
sudo nano {SECOND_LEVEL_DOMAIN_SLD}-le-ssl.conf
```

Ensure that the VirtualHost blocks are properly separated and in the same order as in the
`{SECOND_LEVEL_DOMAIN_SLD}.conf` file.

**For each VirtualHost block, verify the following:**

##### VirtualHost port

Check that each `<VirtualHost>` directive uses port **443**, not port 80:

```apache
<VirtualHost *:443>        # correct
# <VirtualHost *:80>       # wrong — port 80 requests are handled by {SECOND_LEVEL_DOMAIN_SLD}.conf
```

Certbot may copy the HTTP VirtualHost template and leave the port unchanged, or you may be working
from a block that was pasted from the HTTP config without updating the port.

##### WWW redirect

Ensure the WWW to non-WWW redirect uses HTTPS instead of HTTP:

```text
Redirect permanent / https://{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}/
```

##### All proxied subdomains (main domain, API, OpenAPI, staging)

Check that `ProxyPass` and `ProxyPassReverse` are present and point to the correct port
(refer to the [DNS records table](#create-dns-records) for target port and path):

```text
ProxyPass / http://localhost:<PORT>/
ProxyPassReverse / http://localhost:<PORT>/
```

Check that the `RequestHeader` directives use HTTPS values:

```text
RequestHeader set X-Forwarded-Proto "https"
RequestHeader set X-Forwarded-Port "443"
```

##### Staging subdomain specifically

In addition to the items above, verify that the following configuration is present:

- Basic Authentication (`AuthType Basic`, `AuthName`, `AuthUserFile`, `Require valid-user`)
- `X-Robots-Tag "noindex, nofollow"`
- Offline fallback (`ProxyErrorOverride On`, `ErrorDocument`, `Alias /index.html`)
- The `ProxyPass /index.html !` exclusion rule

##### VirtualHost structure

A correctly configured SSL VirtualHost block for the `stg` subdomain looks like this:

```apache
<IfModule mod_ssl.c>
<VirtualHost *:443>
    ServerName stg.<SECOND_LEVEL_DOMAIN_SLD>.<TOP_LEVEL_DOMAIN_TLD>

    ServerAdmin <SERVER_ADMIN_EMAIL>

    ProxyRequests Off
    ProxyPreserveHost On

    # Connection Settings
    KeepAlive On
    KeepAliveTimeout 5
    MaxKeepAliveRequests 100
    Timeout 300

    # Apache HTTP Basic Authentication
    <Location />
        AuthType Basic
        AuthName "Staging Environment — Authorised Access Only"
        AuthUserFile /etc/apache2/.htpasswd-staging
        Require valid-user
    </Location>

    <Location /index.html>
        Require all granted
    </Location>

    Header always set X-Robots-Tag "noindex, nofollow"

    # Offline fallback page
    ProxyErrorOverride On
    ErrorDocument 502 /index.html
    ErrorDocument 503 /index.html
    Alias /index.html /srv/www/endurancetrio/index.html
    <Directory /srv/www/endurancetrio>
        Require all granted
    </Directory>

    ProxyPass /index.html !
    ProxyPass / http://localhost:<STG_ENDURANCETRIO_EXT_PORT>/
    ProxyPassReverse / http://localhost:<STG_ENDURANCETRIO_EXT_PORT>/

    RequestHeader set X-Forwarded-Proto "https"
    RequestHeader set X-Forwarded-Port "443"

    ErrorLog ${APACHE_LOG_DIR}/stg_<SECOND_LEVEL_DOMAIN_SLD>_error.log
    CustomLog ${APACHE_LOG_DIR}/stg_<SECOND_LEVEL_DOMAIN_SLD>_access.log combined

    SSLCertificateFile /etc/letsencrypt/live/{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}/fullchain.pem
    SSLCertificateKeyFile /etc/letsencrypt/live/{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}/privkey.pem
    Include /etc/letsencrypt/options-ssl-apache.conf
</VirtualHost>
</IfModule>
```

##### If a subdomain was added as ServerAlias

If step 1 revealed that a subdomain is listed as `ServerAlias` instead of having its own
`<VirtualHost *:443>` block:

1. Copy the entire main VirtualHost block and paste it as a new VirtualHost
2. Change the `ServerName` to the subdomain (e.g., `stg.example.com`)
3. Remove the `ServerAlias` line from the original VirtualHost
4. Adjust the configuration inside the new VirtualHost for the subdomain's purpose (e.g., add proxy
   configuration for staging)
5. Ensure `RequestHeader` values are set to `https`/`443`
6. Ensure the `SSLCertificateFile` and `SSLCertificateKeyFile` paths are correct

#### 3. Check the HTTP configuration

After Certbot modifies the HTTP config file (`{SECOND_LEVEL_DOMAIN_SLD}.conf`), open it:

```shell
sudo nano {SECOND_LEVEL_DOMAIN_SLD}.conf
```

Within the file, to improve the readability, start by setting a proper indentation on the lines
added by **Certbot**.

For each static VirtualHost that now has an HTTPS redirect, delete the following sections/lines:

```apache
DocumentRoot /srv/www/{SECOND_LEVEL_DOMAIN_SLD}

# Directory configuration
<Directory /srv/www/{SECOND_LEVEL_DOMAIN_SLD}>
    Options -Indexes +FollowSymLinks -MultiViews
    # Change to AllowOverride All if runtime configuration via .htaccess is necessary
    AllowOverride None
    Require all granted

    # Security headers
    <IfModule mod_headers.c>
        Header always set X-Content-Type-Options "nosniff"
        Header always set X-Frame-Options "SAMEORIGIN"
    </IfModule>
</Directory>

CustomLog ${APACHE_LOG_DIR}/{SECOND_LEVEL_DOMAIN_SLD}_access.log combined
```

Also, within the same file, and on the Reverse Proxy section of the Virtual Host, delete the
following sections/lines:

```apache
ProxyRequests Off

# Connection Settings
KeepAlive On
KeepAliveTimeout 5
MaxKeepAliveRequests 100
Timeout 300

# Forwarding configuration
ProxyPreserveHost On
ProxyPass / http://localhost:{APP_PORT}/
ProxyPassReverse / http://localhost:{APP_PORT}/

# Headers configuration
RequestHeader set X-Forwarded-Proto "http"
RequestHeader set X-Forwarded-Port "80"

CustomLog ${APACHE_LOG_DIR}/<PREFIX>_access.log combined
```

Example of a **clean** redirect:

```apache
RewriteEngine on
RewriteCond %{SERVER_NAME} =stg.<SECOND_LEVEL_DOMAIN_SLD>.<TOP_LEVEL_DOMAIN_TLD>
RewriteRule ^ https://%{SERVER_NAME}%{REQUEST_URI} [END,NE,R=permanent]
```

Example of a **broken** redirect (remove the `[OR]` line):

```apache
RewriteEngine on
RewriteCond %{SERVER_NAME} =stg.<SECOND_LEVEL_DOMAIN_SLD>.<TOP_LEVEL_DOMAIN_TLD> [OR]
RewriteCond %{SERVER_NAME} =<SECOND_LEVEL_DOMAIN_SLD>.<TOP_LEVEL_DOMAIN_TLD>
RewriteRule ^ https://%{SERVER_NAME}%{REQUEST_URI} [END,NE,R=permanent]
```

After cleanup, all HTTP VirtualHosts with SSL redirects should look like this:

```apache
<VirtualHost *:80>
    ServerName stg.<SECOND_LEVEL_DOMAIN_SLD>.<TOP_LEVEL_DOMAIN_TLD>

    ServerAdmin <SERVER_ADMIN_EMAIL>

    RewriteEngine on
    RewriteCond %{SERVER_NAME} =stg.<SECOND_LEVEL_DOMAIN_SLD>.<TOP_LEVEL_DOMAIN_TLD>
    RewriteRule ^ https://%{SERVER_NAME}%{REQUEST_URI} [END,NE,R=permanent]

    ErrorLog ${APACHE_LOG_DIR}/stg_<SECOND_LEVEL_DOMAIN_SLD>_error.log
</VirtualHost>
```

All proxy, auth, and security configuration has moved to the SSL VirtualHost.

#### 4. Validate and reload

```shell
sudo apachectl configtest
sudo systemctl restart apache2
```

[SSL Labs Server Test](https://www.ssllabs.com/ssltest/) can be used to verify the certificate's
grade and obtain detailed information about it, from the perspective of an external service.

To test if the [Certbot](https://certbot.eff.org/instructions?ws=apache&os=ubuntufocal) renewal
script includes the new domain (or subdomain), execute the following command:

```shell
sudo certbot renew --dry-run
```

### Adding a new subdomain to an existing deployment

When adding a new subdomain (e.g., `stg.example.com`) to a server that already has working
Apache VirtualHosts and an SSL certificate:

1. Add the new `<VirtualHost *:80>` block to `{SECOND_LEVEL_DOMAIN_SLD}.conf` (the HTTP config)
   by copying the relevant block from the [provided template](../apache/vhost-template.conf)
   and replacing the placeholders

2. Run Certbot to expand the existing certificate to include the new subdomain.
   **Important:** The `--expand` flag replaces the entire domain list — you MUST include ALL
   domains (existing and new):

   ```shell
   sudo certbot --apache --cert-name {EXISTING_DOMAIN} \
     --expand \
     -d {EXISTING_DOMAIN} \
     -d www.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
     -d api.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
     -d openapi.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
     -d docs.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD} \
     -d stg.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}
   ```

3. Follow the [Post-Certbot verification](#post-certbot-verification) checklist to:
   - Verify the subdomain has its own `<VirtualHost *:443>` block (not just a `ServerAlias`)
   - Verify `ProxyPass`/`ProxyPassReverse` are present in the SSL VirtualHost
   - Verify custom configuration (Basic Auth, offline fallback, etc.) is present
   - Remove any stale `[OR]` conditions from redirect rules in the HTTP config

### Troubleshooting

#### Subdomain shows content from a different domain on HTTPS

**Symptom:** Visiting `https://stg.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}`
shows the content from `https://{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}` instead of
the expected staging page. The browser URL still shows the staging subdomain.

**Diagnosis:** Run `sudo apachectl -S` and check the output. If the subdomain appears on port 443
only as a `ServerAlias` of another VirtualHost (e.g., `stg.example.com` listed under
`example.com:443`), there is no dedicated VirtualHost handling requests for it. Apache falls back
to the first matching VirtualHost, which serves the wrong content.

**Cause:** During `certbot --expand`, Certbot's `--apache` plugin added the new subdomain as a
`ServerAlias` to an existing SSL VirtualHost instead of creating a dedicated one.

**Fix:**
1. Open the SSL config file: `sudo nano {SECOND_LEVEL_DOMAIN_SLD}-le-ssl.conf`
2. Create a new `<VirtualHost *:443>` block for the subdomain (see the reference template in
   the [Post-Certbot verification](#2-verify-and-fix-the-ssl-configuration) section)
3. Remove the `ServerAlias` line from the original VirtualHost
4. Run `sudo apachectl configtest && sudo systemctl restart apache2`

#### Staging shows the Apache offline page when the container is running

**Symptom:** The staging offline fallback page is displayed even though the staging container is up.

**Diagnosis:** Verify the container is running (`docker ps`), then check if
`ProxyPass / http://localhost:<STG_ENDURANCETRIO_EXT_PORT>/` exists in the staging SSL VirtualHost.
If missing, Certbot may have stripped it during the expand process.

**Fix:** Add the missing `ProxyPass` and `ProxyPassReverse` directives to the staging
`<VirtualHost *:443>` block in the SSL config file.

## JavaDoc Deployment

### Generate the JavaDoc site

On your development machine, from the project root:

```shell
./mvnw javadoc:aggregate
```

The static HTML site is generated at `target/reports/apidocs/`.

### Upload to the server

```shell
rsync -avz --delete target/reports/apidocs/ <user>@<server>:/srv/www/endurancetrio-docs/
```

The `--delete` flag ensures removed classes are reflected in the deployed site.

### Update an existing deployment

Repeat both steps:

```shell
./mvnw javadoc:aggregate
rsync -avz --delete target/reports/apidocs/ <user>@<server>:/srv/www/endurancetrio-docs/
```

The docs are now accessible at `https://docs.{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}/`.

## Monitoring

### JVM GC logs

GC logging is enabled by default in the entrypoint script. Logs are written to:

- Staging: `/opt/endurancetrio-community/logs/stg/gc.log`
- Production: `/opt/endurancetrio-community/logs/prd/gc.log`

Each file is rotated: up to 5 files of 10 MB each.

### Real-time monitoring with htop

Use `htop` to check system CPU and memory usage in real time:

```shell
htop
```

This shows overall resource consumption but does not provide JVM-specific insight (heap breakdown,
GC pauses, etc.). For that, examine the GC logs.

### Optional: cron-based metrics collection

For historical trending of JVM heap usage, you can set up a cron job that periodically
collects metrics from Spring Boot Actuator:

```shell
sudo tee /etc/cron.d/endurancetrio-metrics << 'EOF'
*/5 * * * * endurancetrio \
  curl -s http://localhost:<PRD_ENDURANCETRIO_EXT_PORT>/actuator/metrics/jvm.memory.used \
  | jq '{time: now, used: .measurements[0].value}' \
  >> /opt/endurancetrio-community/logs/prd/metrics.jsonl
EOF
```

Repeat for the staging port with a different output file.
