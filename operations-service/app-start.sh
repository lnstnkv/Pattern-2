#!/bin/bash

cp -r ./../nest-shared ./

docker-compose up -d --build

sleep 3

echo "Operations-service is started"