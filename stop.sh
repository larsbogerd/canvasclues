#!/bin/bash

BACKEND_PID=$1
FRONTEND_PID=$2

echo "stopping backend"
sudo kill $BACKEND_PID
echo "stopped backend"

echo "stopping frontend"
sudo kill $FRONTEND_PID
docker compose down
echo "stopped frontend"