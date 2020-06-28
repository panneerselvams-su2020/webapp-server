#!/bin/bash

# This script is executed before copying the resource


export app_root=/home/ubuntu/webapp-server
if [ -d "$app_root" ];then
    rm -rf /home/ubuntu/webapp-server
    mkdir -p /home/ubuntu/webapp-server
else
    mkdir -p /home/ubuntu/webapp-server
fi