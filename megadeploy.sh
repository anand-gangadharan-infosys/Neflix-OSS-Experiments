#!/bin/bash

function build(){
  gradle -p services/com/anand/pin clean build
  gradle -p services/com/anand/cache clean build
}

function start(){
  build
  java -jar infrastructure/eureka/build/libs/eureka-0.1.0.jar>>logs/eureka.log &
  sleep 11
  java -jar services/com/anand/pin/build/libs/pin-0.1.0.jar>>logs/pin.log &
  java -jar services/com/anand/cache/build/libs/cache-0.1.0.jar>>logs/cache.log &
  echo 'Service Started'
}

function stop(){
  kill -9 `lsof -i tcp:8080|sort|uniq|awk {'print $2;'}` 2>>/dev/null
  kill -9 `lsof -i tcp:8081|sort|uniq|awk {'print $2;'}` 2>>/dev/null
  kill -9 `lsof -i tcp:8761|sort|uniq|awk {'print $2;'}` 2>>/dev/null
  echo 'Service Stoped'
}


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
