#!/bin/bash
echo "Stopping containers..."
docker compose down
echo "Removing existing database files..."
rm -rf ./sqldata
echo "Wiped database!"