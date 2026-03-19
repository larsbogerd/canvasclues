#!/bin/bash

echo -n "Hello $USER, welcome to the guestbook! Choose an option (start|stop|eraseDB|startWithErease): "

read option

if [ "$option" == "start" ]; then
  sudo ./start.sh
 elif [ "$option" == "stop" ]; then
   read BACKEND_PID FRONTEND_PID < .pids
  sudo ./stop.sh "$BACKEND_PID" "$FRONTEND_PID"
 elif [ "$option" == "eraseDB" ]; then
  sudo ./erase.sh
  echo "Starting containers..."
  docker compose up -d
 elif [ "$option" == "startWithErease" ]; then
  sudo ./erase.sh
  sudo ./start.sh
 else
  echo "Sorry, I didn't understand that."
fi
