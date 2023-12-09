#!/bin/sh

echo "Importing dev-realm.."

/opt/jboss/keycloak/bin/standalone.sh -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=/tmp/realm-export.json -Dkeycloak.migration.strategy=OVERWRITE_EXISTING