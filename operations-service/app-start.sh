#!/bin/bash

cp -r ./../nest-shared ./

docker-compose up -d --build

sleep 10

docker exec operations-service-web-1 node /home/node/project/dist/project/src/main

sleep 3

echo "Operations-service is started"