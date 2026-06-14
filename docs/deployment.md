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
of the folder `/opt/endurancetrio-community/logs/`. This will be achieved with the execution of the
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

> **Note:** Re-initialisation only happens if the `/var/lib/postgresql/data` directory inside the
> container is empty. Once the bind mount at `/opt/endurancetrio-community/db/` has data, the
> init script is not executed again.

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

Then, execute the expand command including only the missing domains/subdomains:

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

> **Note:** If already-covered domains are included in the command, Certbot usually handles them
> safely. However, limiting the `-d` list to only missing domains/subdomains reduces unnecessary
> issuance attempts.

> **Placeholder Definition**
>
> + **{EXISTING_DOMAIN}** : The existing domain (or subdomain) that already has a SSL certificate
> + **{SECOND_LEVEL_DOMAIN_SLD}** : The [Second-level domain](https://en.wikipedia.org/wiki/Second-level_domain) (e.g., 'example' in example.com)
> + **{TOP_LEVEL_DOMAIN_TLD}**    : The [TLD](https://en.wikipedia.org/wiki/Top-level_domain) (e.g., 'com', 'org', 'net')

Otherwise, to create a separate certificate for the new domain (or subdomain), execute the following
command:

```shell
sudo certbot --apache -d {DOMAIN} -d www.{DOMAIN} -d api.{DOMAIN} -d openapi.{DOMAIN} -d docs.{DOMAIN} -d stg.{DOMAIN}
```

> **Placeholder Definition**
>
> + **{DOMAIN}** : The domain (or subdomain) of the new SSL certificate

### Post-Certbot verification

[Certbot](https://certbot.eff.org/instructions?ws=apache&os=ubuntufocal) will create a file,
at `/etc/apache2/sites-available/` named `{SECOND_LEVEL_DOMAIN_SLD}-le-ssl.conf` and it's necessary
to check if the `RequestHeader` directives are set correctly. Execute the below command to be able
to edit the referred file with the [nano text editor](https://www.nano-editor.org/).

```shell
sudo nano {SECOND_LEVEL_DOMAIN_SLD}-le-ssl.conf
```

Ensure that the `RequestHeader` directives matches the content of the following snippet:

```text
RequestHeader set X-Forwarded-Proto "https"
RequestHeader set X-Forwarded-Port "443"
```

Ensure that the WWW to non-WWW redirection is set using https instead of http, as shown
in the following snippet:

```text
Redirect permanent / https://{SECOND_LEVEL_DOMAIN_SLD}.{TOP_LEVEL_DOMAIN_TLD}/
```

Check if it's necessary any further modifications, implement it if required and when finished, save
the file with the command `CTRL + O` and then exit the text editor  with the command `CTRL + X`.

Validate the Apache Server configuration and, if everything is correct, restart the
[Apache Server](https://httpd.apache.org/) to apply the updated configuration, executing the below
commands:

```shell
sudo apachectl configtest
sudo systemctl restart apache2
```

[SSL Labs Server Test](https://www.ssllabs.com/ssltest/) can be used to verify the certificate’s
grade and obtain detailed information about it, from the perspective of an external service.

To test if the [Certbot](https://certbot.eff.org/instructions?ws=apache&os=ubuntufocal) renewal
script includes the new domain (or subdomain), execute the following command:

```shell
sudo certbot renew --dry-run
```

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
