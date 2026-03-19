#!/bin/bash
echo "Starting containers..."
docker compose up -d

echo "starting backend"
nohup ./mvnw spring-boot:run > backend.log 2>&1 &
BACKEND_PID=$!
echo $BACKEND_PID > backend.pid
echo "Backend started in background (PID: $BACKEND_PID). Logs in backend.log"

cd src/front-end
echo "installing required frontend packages"
npm install
echo "starting frontend"
nohup npm run dev > frontend.log 2>&1 &
FRONTEND_PID=$!
echo $FRONTEND_PID > frontend.pid
echo "Frontend started in background (PID: $FRONTEND_PID). Logs in frontend.log"
echo "$BACKEND_PID $FRONTEND_PID" > ../../.pids
echo "pid numbers saved in a file"