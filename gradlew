#!/usr/bin/env bash
# Proxy script: forward gradle wrapper calls to the Android app container wrapper.
# This allows CI that runs ./gradlew at repo root to work.
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WRAPPER="${ROOT_DIR}/fitbuddy_mobile_app/gradlew"

if [[ ! -x "${WRAPPER}" ]]; then
  echo "Error: Gradle wrapper not found at ${WRAPPER}" >&2
  exit 127
fi

exec "${WRAPPER}" "$@"
