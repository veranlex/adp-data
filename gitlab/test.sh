#!/bin/bash

set -e
set -x

gradle -PNEXUS_USER=$NEXUS_USER -PNEXUS_PASS=$NEXUS_PASS \
  -PNEXUS_URL=$NEXUS_URL \
  -PNEXUS_SNAPSHOT_URL=$NEXUS_SNAPSHOT_URL \
  -PNEXUS_RELEASE_URL=$NEXUS_RELEASE_URL \
  test
