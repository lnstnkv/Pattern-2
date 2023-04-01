#!/bin/bash

sed -i 's/\r$//' rs-init.sh
sed -i 's/\r$//' user-init.sh

cp -r ./../nest-shared ./

docker-compose up -d --build

export MSYS_NO_PATHCONV=0

sleep 15

docker exec mongo1 bash /scripts/rs-init.sh

echo "wait for user creation"

sleep 30

docker exec mongo1 bash /scripts/user-init.sh

sleep 10

docker exec core-web-1 node /home/node/project/dist/project/src/main

sleep 3

echo "Core-service is started"