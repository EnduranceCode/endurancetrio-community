# EnduranceTrio

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.0-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-336791?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)

## Table of Contents

1. [Introduction](#introduction)
2. [Platform Features](#platform-features)
   1. [Core Functionality](#core-functionality)
   2. [API Endpoints](#api-endpoints)
   3. [Swagger UI & API Documentation](#swagger-ui--api-documentation)
3. [Development](#development)
   1. [Technology Stack](./docs/development.md#technology-stack)
   2. [API Key Management](./docs/development.md#api-key-management)
   3. [Database](./docs/development.md#database)
   4. [Installation](./docs/development.md#installation)
   5. [Code & Naming Conventions](./docs/development.md#code--naming-conventions)
   6. [Programmatic Version Management](./docs/development.md#programmatic-version-management)
4. [Deployment](#deployment)
   1. [Container Architecture](./docs/deployment.md#container-architecture)
   2. [Server Setup](./docs/deployment.md#server-setup)
   3. [Reverse Proxy Setup](./docs/deployment.md#reverse-proxy-setup)
   4. [SSL Certificate](./docs/deployment.md#ssl-certificate)
5. [License](#license)

## Introduction

**EnduranceTrio** is the central hub for endurance sports data and resources.

Designed as a comprehensive ecosystem focusing on triathlon and its core disciplines, as well as all
related multisports, the platform combines a historical data archive with modern real-time
capabilities. The project is built on a robust **Java 21** and **Spring Boot 4** foundation, serving
as both a public-facing web application and a programmatic REST API.

### Development Team

This project was created by **Ricardo do Canto**, who is the lead developer and maintainer.

[![GitHub](https://img.shields.io/badge/GitHub-EnduranceCode-black?logo=github&logoColor=white)](https://github.com/EnduranceCode)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Ricardo_do_Canto-0A66C2?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ricardodocanto/)

## Platform Features

### Core Functionality

**EnduranceTrio** is designed to serve athletes, coaches, and endurance sports enthusiasts through
two primary interfaces:

- **Web Application**: A user-friendly interface for browsing race results, currently focused
  on the history of Triathlon in Portugal. It provides athlete analytics and access to a curated
  database of endurance sports resources.

- **REST API**: A secure and well-documented programmatic gateway to the platform. It offers a
  specialized subset of the web application's resources, featuring a scalable solution
  for **IoT telemetry ingestion** and **GeoJSON route management** for race courses.

### API Endpoints

The following table summarizes the available endpoints.

| Method | Endpoint                                         | Description                                                         | Authentication     |
|--------|--------------------------------------------------|---------------------------------------------------------------------|--------------------|
| `GET`  | `/tracker/v1/devices`                            | Get last known telemetry for all existing devices                   | API Key Required   |
| `POST` | `/tracker/v1/devices`                            | Submit a device telemetry data point                                | API Key Required   |
| `GET`  | `/tracker/v1/devices/{device}/telemetry`         | Get historical telemetry for a device (supports pagination)         | API Key Required   |
|        |                                                  |                                                                     |                    |
| `GET`  | `/tracker/v1/routes`                             | Get all route configurations                                        | API Key Required   |
| `POST` | `/tracker/v1/routes`                             | Submit a route configuration                                        | API Key Required   |
| `GET`  | `/tracker/v1/routes/({id}`                       | Find route configuration by id                                      | API Key Required   |
| `GET`  | `/tracker/v1/routes/({id}/metrics`               | Retrieves the GeoJSON definition for a specific route               | API Key Required   |

For comprehensive documentation including request/response schemas, examples, and error handling,
see the following documents:

- [Tracker Domain](docs/api-endpoints-tracker.md)

### Swagger UI & API Documentation

The **EnduranceTrio** API provides interactive documentation through **Swagger UI**, with
separate documentation groups for different environments. This graphic interface allows you to:

- Explore all available endpoints.
- View request/response models (e.g., `DeviceTelemetryDTO`).
- Test API calls directly from your browser.

#### Environment-Specific Access

The Swagger UI endpoint varies by environment and deployment configuration:

| Environment           | Default URL                             | Note                        |
|-----------------------|-----------------------------------------|-----------------------------|
| **Local Development** | `http://localhost:8080/swagger-ui.html` | Direct application access   |
| **Public**            | `openapi` subdomain                     | Configured via Apache proxy |

#### Selecting the Correct Environment Group

When you access Swagger UI, **you must select the appropriate documentation group**
for your environment:

1. **Look for the dropdown menu** in the top-right corner of the Swagger UI page (labeled with
   the current group name)
2. **Choose the correct group** based on your environment:
    - **`Tracker Domain (LOCAL)`** - For local development (uses paths with `/api` prefix)
    - **`Tracker Domain (PROD)`** - For production use (uses paths without `/api` prefix)

> **Important:**
> When accessing Swagger UI through the **openapi subdomain** in production, the gateway strips
> the `/api` prefix from paths. The documentation reflects the client-facing URLs, not the internal
> application paths.

**Why this matters:**
- Different groups uses different server URLs and path structures
- Selecting the wrong group may show incorrect endpoint URLs
- The authentication setup is shared between groups

#### Authentication Guide

The **EnduranceTrio** API requires **Dual-Header Authentication**. To test protected endpoints
in Swagger UI, follow these steps:

1. Click the **Authorize** button at the top right of the page.
2. You will see a modal with two separate sections. You must configure **both** to successfully
   make requests:

| Field Label      | Corresponding Header | Value Format                             | Action                          |
|------------------|----------------------|------------------------------------------|---------------------------------|
| **Account Name** | `ET-Owner`           | Your account identifier (e.g., `system`) | Enter ID & click **Authorize**  |
| **API Key**      | `Authorization`      | `Bearer <your_api_key>`                  | Enter Key & click **Authorize** |

> **Important:**
> You must click the **Authorize** button for *each* field independently. Ensure both padlocks
> appear "locked" (closed) before closing the modal and testing endpoints.

## Development

For detailed development documentation (API key management, database configuration, installation
instructions, coding conventions, etc.), please refer
to the [Development Guide](./docs/development.md).

### Quick Start

1. **Prerequisites**: Java 21, Maven, PostgreSQL
2. **Clone**: `git clone git@github.com:EnduranceCode/endurancetrio-community.git`
3. **Configure**: Set up `application-secrets.yaml` from template
4. **Build**: `./mvnw clean install` (frontend assets are generated automatically in production mode)
5. **Run**: Use `./launch-app.sh` or the provided IntelliJ run configuration, which triggers the
   shared `GenerateFrontendAssetsDev` run configuration before startup so browser source maps are
   available

Frontend assets are generated into
`endurancetrio-app/target/generated-resources/frontend/static/`.

- Maven package builds use the production Webpack mode: minified assets, no source maps, raster
  image optimization, and runtime assets packaged under `BOOT-INF/classes/static/`.
- `EnduranceTrioApplication` uses `GenerateFrontendAssetsDev` before startup so frontend debugging
  works out of the box.
- `EnduranceTrioApplicationWithFrontendWatch` starts both `EnduranceTrioApplication` and
  `FrontendAssetsWatch` for live frontend rebuilds.

> See the [full Development Guide](./docs/development.md) for comprehensive instructions.

### Tracker Domain Development Model

The Tracker functionality (IoT telemetry ingestion and GeoJSON route management) is developed
in its own [`endurancetrio-tracker`](https://github.com/EnduranceCode/endurancetrio-tracker)
repository and incorporated into this project via `git merge`.

This dual-repo approach allows the Tracker to evolve independently while its code is periodically
merged back into this repository. Issues discovered in Tracker-domain code during integration
are filed in the tracker repository.

See [`docs/development.md`](./docs/development.md#tracker-domain-development) for the full merge
workflow and operational instructions.

## Deployment

For deployment instructions (container architecture, server and reverse proxy setup,
SSL certificate configuration, etc.), please refer to the [Deployment Guide](./docs/deployment.md).

## License

This project is licensed under the [Functional Source License](https://fsl.software/), Version 1.1,
ALv2 Future License. See the [`LICENSE.md`](./LICENSE.md) file for details.
