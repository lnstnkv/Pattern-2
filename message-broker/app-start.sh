#!/bin/bash

docker network create mongors-network

docker-compose -f ./kafka-docker-compose.yml up -d

sleep 3

echo "Message-broker is started"