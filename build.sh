#!/bin/bash

mvn package -DskipTests

# is root ?
#if [ "$(id -u)" == "0" ];then
#   echo "Please don't run as root"
#   exit 0
#fi

