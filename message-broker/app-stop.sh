#!/bin/bash


docker-compose -f ./kafka-docker-compose.yml down

docker-compose -f ./kafka-ui-docker-compose.yml down