#!/bin/bash
# This script is executed before copying the resource to the instance
export app_root=/home/ubuntu/webapp
if [ -d "$app_root" ];then
    rm -rf /home/ubuntu/webapp
    mkdir -p /home/ubuntu/webapp
else
    mkdir -p /home/ubuntu/webapp
fi