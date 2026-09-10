#!/bin/bash

set -a
source "$(dirname "$0")/.env"
set +a

cd "$(dirname "$0")"

echo "Starting SDET Commerce backend..."
./mvnw spring-boot:run
