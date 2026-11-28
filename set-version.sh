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

REMOTE="origin"
VERSION=""
DO_COMMIT=false
DO_TAG=false
DO_PUSH=false
DRY_RUN=false
PUSH_ONLY=false
TAG_NAME=""

print_usage() {
    echo "Usage: $0 <version> [--commit] [--tag] [--push] [--push-only] [--dry-run] [--remote <name>] [--tag-name <name>]"
    echo
    echo "Updates the project version across all Maven modules (POM files)"
    echo "and the frontend package.json and package-lock.json files."
    echo
    echo "The -SNAPSHOT suffix (if present) is stripped for package.json"
    echo "since npm does not follow Maven snapshot conventions."
    echo
    echo "Options:"
    echo "  --commit     Also create a git commit with the version change"
    echo "  --tag        Also create the annotated tag community-v<version> locally"
    echo "               (requires --commit and a non-SNAPSHOT version)"
    echo "  --push       Push the current branch to <remote>, then push the tag"
    echo "               community-v<version> if it exists locally and its commit is"
    echo "               reachable from the branch"
    echo "  --push-only  Skip version update/commit/tag creation; only push the branch"
    echo "               and the tag (if present) to <remote>. Useful for publishing a"
    echo "               previously created tag to a fork (e.g. community) after it has"
    echo "               been synced with origin."
    echo "  --dry-run    Print the planned actions without executing them"
    echo "  --remote     Remote to use for fetch/push (default: origin)"
    echo "  --tag-name   Override the tag name (default: community-v<version>)"
    echo
    echo "Examples:"
    echo "  $0 0.5.0"
    echo "  $0 0.5.0-SNAPSHOT"
    echo "  $0 0.5.0 --commit"
    echo "  $0 0.5.0 --commit --tag"
    echo "  $0 0.5.0 --commit --tag --push"
    echo "  $0 0.5.0 --commit --tag --push --dry-run"
    echo "  $0 0.5.0 --push-only --remote community"
    echo "  $0 --push-only --tag-name community-v0.5.0 --remote community"
    exit 1
}

LAST_STEP=""
on_exit() {
    local rc=$?
    if [ $rc -ne 0 ] && [ -n "$LAST_STEP" ]; then
        echo >&2
        echo -e "[\e[31mFAIL\e[0m] Failed during: ${LAST_STEP}" >&2
        echo "       Review the output above for details." >&2
    fi
}
trap on_exit EXIT

while [ $# -gt 0 ]; do
    case "$1" in
        --commit)
            DO_COMMIT=true
            shift
            ;;
        --tag)
            DO_TAG=true
            shift
            ;;
        --push)
            DO_PUSH=true
            shift
            ;;
        --push-only)
            PUSH_ONLY=true
            DO_PUSH=true
            shift
            ;;
        --dry-run)
            DRY_RUN=true
            shift
            ;;
        --remote)
            if [ $# -lt 2 ]; then
                echo "Error: --remote requires a value." >&2
                print_usage
            fi
            REMOTE="$2"
            shift 2
            ;;
        --remote=*)
            REMOTE="${1#--remote=}"
            shift
            ;;
        --tag-name)
            if [ $# -lt 2 ]; then
                echo "Error: --tag-name requires a value." >&2
                print_usage
            fi
            TAG_NAME="$2"
            shift 2
            ;;
        --tag-name=*)
            TAG_NAME="${1#--tag-name=}"
            shift
            ;;
        -*)
            echo "Unknown option: ${1}" >&2
            print_usage
            ;;
        *)
            if [ -n "$VERSION" ]; then
                echo "Only one version number can be provided." >&2
                print_usage
            fi
            VERSION="$1"
            shift
            ;;
    esac
done

if [ -z "$VERSION" ] && [ -z "$TAG_NAME" ] && [ "$PUSH_ONLY" = true ]; then
    echo "Error: --push-only requires either a version or --tag-name." >&2
    print_usage
fi

if [ -z "$REMOTE" ]; then
    echo "Error: remote name cannot be empty." >&2
    exit 1
fi

if [ -n "$TAG_NAME" ]; then
    TAG="$TAG_NAME"
elif [ -n "$VERSION" ]; then
    TAG="community-v${VERSION}"
else
    TAG=""
fi

if [ "$PUSH_ONLY" != true ] && [ -z "$VERSION" ]; then
    print_usage
fi

if [ "$DO_TAG" = true ] && [ -z "$VERSION" ]; then
    echo "Error: --tag requires a version number." >&2
    exit 1
fi

if [ "$DO_TAG" = true ]; then
    if [ "$DO_COMMIT" != true ]; then
        echo "Error: --tag requires --commit so that the tag points at the version commit." >&2
        exit 1
    fi

    if [[ "$VERSION" == *-SNAPSHOT ]]; then
        echo "Error: --tag cannot be used with a -SNAPSHOT version, because tags represent released state only." >&2
        exit 1
    fi
fi

# Pre-flight safety checks
LAST_STEP="pre-flight safety checks"
if ! git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
    echo "Error: not inside a git work tree." >&2
    exit 1
fi

if [ "$PUSH_ONLY" != true ]; then
    if [ -n "$(git status --porcelain --untracked-files=no)" ]; then
        echo "Error: working tree has uncommitted tracked changes. Commit or stash them first." >&2
        git status --short >&2
        exit 1
    fi
fi

UNTRACKED="$(git status --porcelain --untracked-files=all | grep '^??' || true)"
if [ -n "$UNTRACKED" ]; then
    echo -e "[\e[34mINFO\e[0m] Untracked files present (ignored for safety):"
    echo "$UNTRACKED" >&2
fi

BRANCH=""
if [ "$DO_PUSH" = true ]; then
    BRANCH="$(git branch --show-current)"
    if [ -z "$BRANCH" ]; then
        echo "Error: cannot push from a detached HEAD." >&2
        exit 1
    fi
fi

# Fetch from remote for up-to-date checks
LAST_STEP="fetching from ${REMOTE}"
if ! git fetch --tags "$REMOTE" 2>&1; then
    echo -e "[\e[33mWARN\e[0m] Could not fetch tags from ${REMOTE}; remote checks may be stale." >&2
fi
git fetch "$REMOTE" 2>&1 || echo -e "[\e[33mWARN\e[0m] Could not fetch branches from ${REMOTE}; remote checks may be stale." >&2

# Tag existence checks (after fetch)
if [ "$DO_TAG" = true ]; then
    LAST_STEP="checking tag existence"
    if git rev-parse -q --verify "refs/tags/${TAG}" > /dev/null 2>&1; then
        echo "Error: tag ${TAG} already exists locally." >&2
        exit 1
    fi

    REMOTE_TAG="$(git ls-remote --tags "$REMOTE" "refs/tags/${TAG}")"
    if [ -n "$REMOTE_TAG" ]; then
        echo "Error: tag ${TAG} already exists on ${REMOTE}." >&2
        exit 1
    fi
fi

# Branch divergence check
if [ "$DO_PUSH" = true ]; then
    LAST_STEP="checking branch divergence"
    if git rev-parse -q --verify "${REMOTE}/${BRANCH}" > /dev/null 2>&1; then
        if ! git merge-base --is-ancestor "${REMOTE}/${BRANCH}" HEAD; then
            echo "Error: local ${BRANCH} has diverged from ${REMOTE}/${BRANCH}. Pull or rebase first." >&2
            exit 1
        fi
    fi
fi

# Dry run: print plan and exit without mutating anything
if [ "$DRY_RUN" = true ]; then
    echo
    echo "Dry run - no changes will be made."
    echo
    echo "Planned actions:"
    if [ "$PUSH_ONLY" = true ]; then
        echo "  1. (no version update, commit, or tag creation)"
        if [ "$DO_PUSH" = true ]; then
            echo "  2. git push ${REMOTE} ${BRANCH}"
            if [ -n "$TAG" ]; then
                echo "  3. git push ${REMOTE} ${TAG} (only if the tag exists locally and its commit is an ancestor of ${BRANCH})"
            fi
        fi
    else
        echo "  1. ./mvnw versions:set -DnewVersion=${VERSION}"
        echo "  2. ./mvnw versions:commit"
        echo "  3. Update ${PACKAGE_JSON} version -> ${VERSION%-SNAPSHOT}"
        echo "  4. npm --prefix ${WEBPACK_DIR} install --package-lock-only"
        if [ "$DO_COMMIT" = true ]; then
            echo "  5. git add -u && git commit -m \"Set version to ${VERSION}\" (only if staged changes exist)"
        fi
        if [ "$DO_TAG" = true ]; then
            echo "  6. git tag -a ${TAG} -m \"v${VERSION}\""
        fi
        if [ "$DO_PUSH" = true ]; then
            echo "  7. git push ${REMOTE} ${BRANCH}"
            if [ "$DO_TAG" = true ] || git rev-parse -q --verify "refs/tags/${TAG}" > /dev/null 2>&1; then
                echo "  8. git push ${REMOTE} ${TAG} (only if the tag commit is an ancestor of ${BRANCH})"
            fi
        fi
    fi
    exit 0
fi

if [ "$PUSH_ONLY" != true ]; then
    # Remove -SNAPSHOT suffix for package.json
    STRIPPED_VERSION="${VERSION%-SNAPSHOT}"

    LAST_STEP="updating Maven POMs"
    echo "------------------------------------------------------------------------------------------------------------------------"
    echo -e "[\e[34mINFO\e[0m] Setting version to ${VERSION}..."
    echo "------------------------------------------------------------------------------------------------------------------------"
    echo

    ./mvnw versions:set -DnewVersion="${VERSION}" -q
    ./mvnw versions:commit -q

    echo -e "[\e[32mOK\e[0m] Updated POM files to version ${VERSION}"

    LAST_STEP="updating package.json"
    jq --arg v "$STRIPPED_VERSION" '.version = $v' "$PACKAGE_JSON" > "${PACKAGE_JSON}.tmp" && mv "${PACKAGE_JSON}.tmp" "$PACKAGE_JSON"
    echo -e "[\e[32mOK\e[0m] Updated ${PACKAGE_JSON} to version ${STRIPPED_VERSION}"

    LAST_STEP="syncing package-lock.json"
    npm --prefix "${WEBPACK_DIR}" install --package-lock-only --silent
    echo -e "[\e[32mOK\e[0m] Synced ${WEBPACK_DIR}/package-lock.json"

    echo
    echo "------------------------------------------------------------------------------------------------------------------------"
    echo -e "[\e[34mINFO\e[0m] Version ${VERSION} has been applied."
    echo "------------------------------------------------------------------------------------------------------------------------"

    echo
    echo "Changed files:"
    git diff --name-only
fi

# Commit
if [ "$PUSH_ONLY" != true ]; then
    if [ "$DO_COMMIT" = true ]; then
        LAST_STEP="creating commit"
        git add -u
        if git diff --cached --quiet; then
            echo
            echo -e "[\e[34mINFO\e[0m] No staged changes; skipping commit."
        else
            git commit -m "Set version to ${VERSION}"
            echo -e "[\e[32mOK\e[0m] Created commit for version ${VERSION}"
        fi
    else
        echo
        echo "To commit these changes, run:"
        echo "  git add -u && git commit -m \"Set version to ${VERSION}\""
    fi
fi

# Tag creation (local only)
if [ "$PUSH_ONLY" != true ] && [ "$DO_TAG" = true ]; then
    LAST_STEP="creating tag"
    git tag -a "$TAG" -m "v${VERSION}"
    echo -e "[\e[32mOK\e[0m] Created annotated tag ${TAG} (local only)"
fi

# Push
if [ "$DO_PUSH" = true ]; then
    LAST_STEP="pushing branch"
    BRANCH="$(git branch --show-current)"
    git push "$REMOTE" "$BRANCH"
    echo -e "[\e[32mOK\e[0m] Pushed branch ${BRANCH} to ${REMOTE}"

    LAST_STEP="pushing tag"
    if git rev-parse -q --verify "refs/tags/${TAG}" > /dev/null 2>&1; then
        TAG_COMMIT="$(git rev-parse "${TAG}^{commit}")"
        BRANCH_COMMIT="$(git rev-parse "${BRANCH}")"
        if git merge-base --is-ancestor "$TAG_COMMIT" "$BRANCH_COMMIT"; then
            git push "$REMOTE" "$TAG"
            echo -e "[\e[32mOK\e[0m] Pushed tag ${TAG} to ${REMOTE}"
        else
            echo -e "[\e[33mWARN\e[0m] Tag ${TAG} points at a commit that is not an ancestor of ${BRANCH}; skipping tag push."
        fi
    else
        echo -e "[\e[34mINFO\e[0m] No local tag ${TAG}; skipped tag push."
    fi
fi

# Warnings / follow-up hints
if [ "$PUSH_ONLY" != true ]; then
    if [ "$DO_COMMIT" = true ] && [ "$DO_PUSH" != true ]; then
        if UPSTREAM="$(git rev-parse --abbrev-ref --symbolic-full-name '@{u}' 2> /dev/null)"; then
            if [ "$(git rev-list --count "${UPSTREAM}..HEAD")" -gt 0 ]; then
                echo
                echo -e "[\e[33mWARN\e[0m] The version commit is not on ${UPSTREAM} yet."
                echo "       To publish it, run: git push (or re-run with --push)"
            fi
        fi
    fi

    if [ "$DO_TAG" = true ] && [ "$DO_PUSH" != true ]; then
        echo
        echo -e "[\e[34mINFO\e[0m] Tag ${TAG} was created locally only."
        echo "       To publish it, run: git push ${REMOTE} ${TAG} (or re-run with --push)"
    fi
fi

LAST_STEP=""
exit 0