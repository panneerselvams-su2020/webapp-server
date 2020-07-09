#!/bin/bash

# This script is executed after copying the resource

# #copy config file to path
# sudo cp /home/ubuntu/cloudwatch-config.json /opt/aws/amazon-cloudwatch-agent/etc/


sudo systemctl start tomcat.service
