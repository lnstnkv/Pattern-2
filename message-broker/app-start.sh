#!/bin/bash

docker-compose -f ./kafka-docker-compose.yml up -d

docker-compose -f ./kafka-ui-docker-compose.yml up -d

sleep 3

echo "Message-broker is started"