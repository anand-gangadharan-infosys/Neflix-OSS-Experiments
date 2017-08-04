#!/bin/bash

function buildApp(){
  project=$1
  gradle -p $project clean build
}
function startApp(){
  project=$1
  app=$2
  version=$3
  buildApp  $project
  java -jar $project/build/libs/$app-$version.jar >> logs/$app.log 2>>logs/$app.log &
}
function killprocesslisteningon(){
  port=$1
  jar=$2

  echo Find pids listening on $port

  # For debug list the processes listening
  lsof -i tcp:$port|awk 'NR >= 2{ print "Killing process "$1" with PID "$2 }'
  #Kill it
  kill -9 `lsof -i tcp:$port| awk 'NR >= 2 {print $2}' | sort -n|uniq` 2>>/dev/null

  echo 'Checking for java process with jar '$jar

  # Just check if any process with the jar in any inconsistent state
  ps -ax|grep java|grep $jar|awk '{Killing java process found at PID $1}'
  #Kill it
  kill -9 `ps -ax|grep java|grep $jar|awk '{print $1}' | sort -n|uniq` 2>>/dev/null

}

function start(){
  source infrastructure/ev-deploy-descriptor.sh
  startApp 'infrastructure/eureka' 'eureka' '0.1.0'
  sleep 11
  startApp 'services/com/anand/pin' 'pin' '0.1.0'
  startApp 'services/com/anand/cache' 'cache' '0.1.0'
  echo 'Service Started'sort
}

function stop(){
  killprocesslisteningon 8080 'pin-0.1.0.jar'
  killprocesslisteningon 8081 'cache-0.1.0.jar'
  killprocesslisteningon 8761 'eureka-0.1.0.jar'
  echo 'Service Stoped'
}
#------------------------------------------------------------------------------#
##Start of Script
#------------------------------------------------------------------------------#
#==============================================================================#

case $1 in
start)
  start
  ;;
stop)
  stop
  ;;
restart)
  stop
  start
  ;;
*)
  echo $"Usage: $0 {start|stop|restart}"
  ;;
esac

#curl -X PUT http://localhost:8761/eureka/apps/pin/ip-10-155-213-80.eu-west-1.compute.internal%3A8080/status?value=OUT_OF_SERVICE
