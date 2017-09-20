#!/bin/bash


if [ "$(id -u)" == "0" ];then
   echo "Please don't run as root"
   exit 0
fi


echo "start" 
