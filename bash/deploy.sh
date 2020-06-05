#!/bin/bash
export JAVA_HOME=/opt/app/jdk1.8.0_65
PROJECT_NAME="member"
base_dir="/opt/hjm/aha-${PROJECT_NAME}-server"
tar_file="aha-${PROJECT_NAME}-server.jar"

cd ${base_dir}

ENV=$1

./run.sh stop

sleep 1

./run.sh start ${ENV}

