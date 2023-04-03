#!/bin/bash

docker-compose -f ./kafka-docker-compose.yml up -d

sleep 3

echo "Message-broker is started"