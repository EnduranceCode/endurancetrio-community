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
   1. [Quick Start](#quick-start)
   2. [Technology Stack](./docs/development.md#technology-stack)
   3. [API Key Management](./docs/development.md#api-key-management)
   4. [Database](./docs/development.md#database)
   5. [Installation](./docs/development.md#installation)
   6. [Code & Naming Conventions](./docs/development.md#code--naming-conventions)
   7. [Programmatic Version Management](./docs/development.md#programmatic-version-management)
   8. [Integrations](#integrations)
4. [Deployment](#deployment)
   1. [Container Architecture](./docs/deployment.md#container-architecture)
   2. [Server Setup](./docs/deployment.md#server-setup)
   3. [Reverse Proxy Setup](./docs/deployment.md#reverse-proxy-setup)
   4. [SSL Certificate](./docs/deployment.md#ssl-certificate)
5. [License](#license)

## Introduction

**EnduranceTrio** is a web platform and REST API for endurance sports data and resources.

The project is centered on triathlon and related multisport disciplines, with an initial focus on
Portugal. Its main long-term objective is to build a structured and reliable dataset of Portuguese
triathlon events, results, athletes, and related content, starting from the earliest years of the
sport and expanding over time toward broader historical coverage and more current competition data.

The platform combines a public-facing web application with a programmatic REST API. It is built on
a **Java 21** and **Spring Boot 4** foundation and is designed to support both archival use cases,
such as preserving and organizing historical race information, and operational use cases, such as
telemetry ingestion and route management for live event scenarios.

### Development Team

**EnduranceTrio** is a personal project created and maintained by **Ricardo do Canto**.

[![GitHub](https://img.shields.io/badge/GitHub-EnduranceCode-black?logo=github&logoColor=white)](https://github.com/EnduranceCode)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Ricardo_do_Canto-0A66C2?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ricardodocanto/)

## Platform Features

The platform is developed as a public-facing endurance sports website backed by a structured data
model and supported by a dedicated API surface for tracker-domain functionality.

### Core Functionality

**EnduranceTrio** currently provides two complementary platform surfaces:

- **Web Application**: A browser-based interface for exploring endurance sports data and related
  resources. The primary product direction is a structured database of Portuguese triathlon and
  related multisport events, results, athletes, and connected content. Development begins with a
  strong historical foundation and progressively expands toward more recent competitions and more
  timely race data as the underlying data model and ingestion workflows mature.

- **REST API**: A secure and documented API surface that currently exposes the platform's tracker
  domain. It provides scalable support for **IoT telemetry ingestion**, retrieval of device
  telemetry history, and **GeoJSON route management**, enabling live tracking and route-oriented
  integrations for endurance sports use cases.

Together, these two interfaces support the broader platform goal: combining structured endurance
sports data, historical preservation, analytical potential, and practical event technology in a
single evolving system.

### API Endpoints

The currently available API endpoints belong to the tracker domain and are summarized below.

| Method | Endpoint                                         | Description                                                         | Authentication     |
|--------|--------------------------------------------------|---------------------------------------------------------------------|--------------------|
| `GET`  | `/tracker/v1/devices`                            | Get last known telemetry for all existing devices                   | API Key Required   |
| `POST` | `/tracker/v1/devices`                            | Submit a device telemetry data point                                | API Key Required   |
| `GET`  | `/tracker/v1/devices/{device}/telemetry`         | Get historical telemetry for a device (supports pagination)         | API Key Required   |
|        |                                                  |                                                                     |                    |
| `GET`  | `/tracker/v1/routes`                             | Get all route configurations                                        | API Key Required   |
| `POST` | `/tracker/v1/routes`                             | Create a route configuration                                        | API Key Required   |
| `PUT`  | `/tracker/v1/routes/{id}`                        | Update a route configuration                                        | API Key Required   |
| `GET`  | `/tracker/v1/routes/{id}`                        | Find route configuration by id                                      | API Key Required   |
| `GET`  | `/tracker/v1/routes/{id}/metrics`                | Retrieves the GeoJSON definition for a specific route               | API Key Required   |

For detailed request and response schemas, examples, and error handling, see:

- [Tracker Domain](docs/api-endpoints-tracker.md)

### Swagger UI & API Documentation

The **EnduranceTrio** API provides interactive documentation through **Swagger UI**, with
separate documentation groups for different environments. This graphical interface allows you to:

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
5. **Run**: Use `./launch-app.sh` or the `EnduranceTrioApplication` IntelliJ run configuration,
   which triggers `GenerateFrontendAssets` before startup so browser source maps are available.
   For live frontend rebuilds, also run `FrontendAssetsWatch` in a separate IntelliJ tab.

Frontend assets are generated into
`endurancetrio-app/target/generated-resources/frontend/static/` during Maven builds and into
`endurancetrio-app/target/classes/static/` when `FrontendAssetsWatch` is running.

- Maven package builds use the production Webpack mode: minified assets, no source maps, raster
  image optimization, and runtime assets packaged under `BOOT-INF/classes/static/`.
- `EnduranceTrioApplication` runs `GenerateFrontendAssets` before startup so frontend debugging
  works out of the box.
- `FrontendAssetsWatch` is a separate npm run config for continuous frontend rebuilds — run it
  in a second IntelliJ tab alongside `EnduranceTrioApplication` when editing SCSS or JS.
- Icons use a CSS mask system with SVGs embedded as data URIs. Source SVGs and config
  live in the webpack directory (`icons.config.json`, `src/icons/`). Prebuild hooks
  regenerate icon CSS automatically.

> See the [full Development Guide](./docs/development.md) for comprehensive instructions.

### Integrations

**EnduranceTrio** can integrate independently developed domain codebases via `git merge`,
allowing external modules to evolve in their own repositories while their code is periodically
incorporated into this project.

The current integration is the **Tracker domain** (IoT telemetry ingestion and GeoJSON route
management), developed in its own
[`endurancetrio-tracker`](https://github.com/EnduranceCode/endurancetrio-tracker) repository.
Its API surface is documented in [`docs/api-endpoints-tracker.md`](./docs/api-endpoints-tracker.md).

See [`docs/tracker-integration.md`](./docs/tracker-integration.md) for the full integration
workflow and conflict resolution rules.

## Deployment

For deployment instructions (container architecture, server and reverse proxy setup,
SSL certificate configuration, etc.), please refer to the [Deployment Guide](./docs/deployment.md).

## License

This project is licensed under the [Functional Source License](https://fsl.software/), Version 1.1,
ALv2 Future License. See the [`LICENSE.md`](./LICENSE.md) file for details.
