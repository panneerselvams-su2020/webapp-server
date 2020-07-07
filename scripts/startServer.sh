#!/bin/bash

# This script is executed after copying the resource

cd /home/ubuntu/webapp/
java -jar webapp-server-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &

#starting cloudwatch agent
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/opt/cloudwatch-config.json \
    -s