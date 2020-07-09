#!/bin/bash

# This script is executed after copying the resource

# #copy config file to path
# sudo cp /home/ubuntu/cloudwatch-config.json /opt/aws/amazon-cloudwatch-agent/etc/

sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -m ec2 -a stop

#starting cloudwatch agent
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/opt/CloudWatchConfig.json \
    -s

cd /home/ubuntu/webapp
chmod +x webapp-server-0.0.1-SNAPSHOT.jar
sudo systemctl daemon-reload
sudo systemctl enable webapp.service
sudo systemctl start webapp.service


