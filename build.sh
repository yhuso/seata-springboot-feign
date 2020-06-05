#!/bin/bash

#发包命令 ./build.sh.sh dev|test
#106.75.110.211 dev

PROJECT_NAME="member"
tar_file="aha-${PROJECT_NAME}-server.jar"

case $1 in
local|dev|test|test2|prod|test4|test5|test6)
    echo $"[$1] constant building ..."
    gradle clean build -xtest
    ;;
*)
    echo $"Usage: $0 {local|dev|test|test2|estest|prod|test4}"
    ;;
esac

echo $"copy ${tar_file} to [$1] constant ..."
	scp ./build/libs/${tar_file} hjmrunning@$1:/opt/hjm/aha-${PROJECT_NAME}-server
	scp ./bash/deploy.sh ./bash/run.sh hjmrunning@$1:/opt/hjm/aha-${PROJECT_NAME}-server
	ssh -t -p 22 hjmrunning@$1 "/opt/hjm/aha-${PROJECT_NAME}-server/deploy.sh "$1
