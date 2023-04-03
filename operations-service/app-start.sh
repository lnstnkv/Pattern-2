#!/bin/bash

cp -r ./../nest-shared ./

docker network create mongors-network

docker-compose up -d --build

export MSYS_NO_PATHCONV=0

sleep 10

docker exec operations-service-web-1 node /home/node/project/dist/project/src/main

sleep 3

echo "Operations-service is started"