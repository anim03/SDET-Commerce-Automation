#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

set -a
source "$SCRIPT_DIR/.env"
set +a

cd "$SCRIPT_DIR"

echo "======================================"
echo "SDET Commerce API Automation"
echo "======================================"
echo "Environment : ${TEST_ENV:-local}"
echo "Base URL    : ${BASE_URL:-configured-by-environment}"
echo "======================================"

mvn clean test