#!/bin/bash

set -o allexport
. ./.env
set +o allexport

mongo <<EOF
use coredb
if(!(db.getUser('coreuser'))){
  db.createUser( {
    user: 'coreuser',
    pwd: 'corepwd',
    roles: [ { role: 'readWrite', db: 'coredb'
    } ]
  } );
  print('Database user was created successfully');
} else{
  print('Database user already exists');
}
EOF