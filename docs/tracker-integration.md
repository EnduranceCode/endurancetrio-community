# Tracker Integration Guide

This document describes how the
[**EnduranceTrio Tracker**](https://github.com/EnduranceCode/endurancetrio-tracker) repository
is integrated into the **EnduranceTrio Community** project. For an overview of the integrations
approach, see the [main README.md](../README.md#integrations).

The Tracker API surface is documented in
[`docs/api-endpoints-tracker.md`](./api-endpoints-tracker.md).

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Integration Workflow](#integration-workflow)
3. [Issue Tracking](#issue-tracking)

## Architecture Overview

The Tracker domain is developed in its own
[`endurancetrio-tracker`](https://github.com/EnduranceCode/endurancetrio-tracker) repository and
incorporated into this project via `git merge`.

Code flows in one direction only:

    Tracker → Community

When merging **EnduranceTrio Tracker** into **EnduranceTrio Community**:

- Upstream code is never modified downstream
- Changes flow only via controlled merges of released versions
- DO NOT automatically bump the Community version
- Version bump depends on the actual impact of the merged code

### Table-Domain Ownership

Database tables are strictly owned by the domain that created them:

- **Tracker** migrations target only Tracker-domain tables
- **Community** migrations must never create, alter, or reference Tracker-domain tables

This isolation prevents migration interference between the two domains and is enforced
at integration time.

## Integration Workflow

To incorporate a new Tracker release into Community:

### Step 1 – Add Tracker as a remote

```shell
git remote add tracker git@github.com:EnduranceCode/endurancetrio-tracker.git
```

This only needs to be done once. After that, you can fetch and merge from the `tracker` remote
as needed.

### Step 2 – Create an integration branch

```shell
git checkout master
git pull
git checkout -b integration/tracker-vX.Y.Z
```

### Step 3 – Fetch the Tracker remote and tags

```shell
git fetch tracker --tags
```

### Step 4 – Merge the specific release tag

```shell
git merge tracker-vX.Y.Z --no-commit --no-ff
```

> **Important:** Always merge from a release tag, never from `tracker/tracker` directly.
> The `--no-commit` and `--no-ff` flags allow you to review the merge before committing,
> which is crucial for resolving conflicts and ensuring the merge is clean.

### Step 5 – Resolve conflicts and implement adaptations

When conflicts arise during a Tracker merge, the Community infrastructure takes precedence.
Follow these rules case-by-case:

| File / Directory                                              | Rule                                                                                                                                                                                                                                                                |
|---------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `pom.xml`                                                     | Dependency additions from Tracker must be reconciled with the multi-module structure                                                                                                                                                                                |
| `endurancetrio-app/src/main/resources/application*.yaml`      | Tracker may add entries; host app's config structure takes precedence                                                                                                                                                                                               |
| `endurancetrio-app/.../config/AppSecurityConfig.java`         | Security filter chains, CORS, and permit rules are owned by the Community app                                                                                                                                                                                       |
| `endurancetrio-data/src/main/resources/db/migration/**/*.sql` | Tracker migrations only touch Tracker-domain tables; Community migrations never touch them. The application is already configured with `out-of-order: true`, so timestamp-based (`VYYYYMMDDHHMM`) migrations from Tracker are applied safely regardless of ordering |
| `.github/workflows/`                                          | CI/CD pipeline definition is owned by the Community project                                                                                                                                                                                                         |
| `.gitignore`                                                  | Community `.gitignore` covers frontend, IDE, and OS artifacts; use the Community version                                                                                                                                                                            |
| `Dockerfile`, `docker/`, `launch-app.sh`, `docs/`             | Tracker had its own versions; use Community versions                                                                                                                                                                                                                |

Allowed adaptations:

- Glue code and integration adjustments
- Configuration changes specific to the Community context

NOT allowed:

- Rewriting upstream Tracker logic
- Modifying Tracker's package structure

### Step 6 – Validate

```shell
./mvnw clean install
```

This runs the full test suite and ensures the build passes.

### Step 7 – Commit the merge

Add all the changes from the merge, as well as any eventual adaptations, to the staging area:

```shell
git add -A
```

Before committing, review the staged changes to ensure only intended modifications are included:

```shell
git diff --staged
```

After reviewing the staged changes, and if there's no correction to be made, commit with a clear
message referencing the merged Tracker version:

```shell
git commit -m "Merge tracker-vX.Y.Z"
```

### Step 8 – Create a pull request and merge to master

Push the integration branch:

```shell
git push --set-upstream origin integration/tracker-vX.Y.Z
```

Then go to GitHub and create a pull request for review. Once approved, merge it into master with the
following commit message format:

```shell
Integrate Tracker vX.Y.Z
```

### Step 9 – Delete the integration branch

```shell
git branch -d integration/tracker-vX.Y.Z
```

## Issue Tracking

Issues discovered in Tracker-domain code during integration should be filed in the
[`endurancetrio-tracker`](https://github.com/EnduranceCode/endurancetrio-tracker) issue tracker.
