#! /usr/bin/env bash

docker stop keycloak &&
docker rm keycloak &&
docker stop postgres &&
docker rm postgres
