#!/usr/local/bin/bash

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
  startApp 'infrastructure/gateway' 'gateway' '0.1.0'
  startApp 'infrastructure/turbine-stream' 'turbine-stream' '0.1.0'
  startApp 'infrastructure/hystrix-monitoring' 'hystrix-monitoring' '0.1.0'
  startApp 'services/com/anand/pin' 'pin' '0.1.0'
  startApp 'services/com/anand/cache' 'cache' '0.1.0'
  echo 'Service Started'sort
}

function stop(){
  killprocesslisteningon 8080 'pin-0.1.0.jar'
  killprocesslisteningon 8081 'cache-0.1.0.jar'
  killprocesslisteningon 8082 'hystrix-monitoring-0.1.0.jar'
  killprocesslisteningon 8083 'turbine-stream-0.1.0.jar'
  killprocesslisteningon 8765 'gateway-0.1.0.jar'
  killprocesslisteningon 8761 'eureka-0.1.0.jar'
  echo 'Service Stoped'
}
#------------------------------------------------------------------------------#
##Start of Script
#------------------------------------------------------------------------------#
#==============================================================================#
#initializing project locations
declare -A PROJECT_LOCATION_MAP
PROJECT_LOCATION_MAP[eureka]=infrastructure/eureka
PROJECT_LOCATION_MAP[gateway]=infrastructure/gateway
PROJECT_LOCATION_MAP[turbine-stream]=infrastructure/turbine-stream
PROJECT_LOCATION_MAP[hystrix-monitoring]=infrastructure/hystrix-monitoring
PROJECT_LOCATION_MAP[pin]=services/com/anand/pin
PROJECT_LOCATION_MAP[cache]=services/com/anand/cache

#initializing Application ports
declare -A PORT_MAP
PORT_MAP[pin]=8080
PORT_MAP[cache]=8081
PORT_MAP[hystrix-monitoring]=8082
PORT_MAP[turbine-stream]=8083
PORT_MAP[gateway]=8765
PORT_MAP[eureka]=8761

#initializing Application jars
declare -A JARS_MAP
JARS_MAP[pin]=pin-0.1.0.jar
JARS_MAP[cache]=cache-0.1.0.jar
JARS_MAP[hystrix-monitoring]=hystrix-monitoring-0.1.0.jar
JARS_MAP[turbine-stream]=turbine-stream-0.1.0.jar
JARS_MAP[gateway]=gateway-0.1.0.jar
JARS_MAP[eureka]=eureka-0.1.0.jar

#Iterating values of an associative array
# for i in "${!JARS_MAP[@]}"
# do
#   echo "key  : $i"
#   echo "value: ${JARS_MAP[$i]}"
# done

function inArray() {
  local e
  for e in "${@:2}"; do
    [[ "$e" == "$1" ]] && return 0;
  done
  return 1
}

case $1 in
start)
  if [ $2 = all ]; then
    start
  else
    inArray $2 "${!PROJECT_LOCATION_MAP[@]}"
    if [ $? -eq "1" ]; then
      echo "Application $2 Not found"
      exit 1
    fi
    echo "starting project $2"
    startApp ${PROJECT_LOCATION_MAP[$2]} $2 '0.1.0'
    echo 'Service Started'
  fi
  ;;
stop)
  if [ $2 = all ]; then
    stop
  else
    inArray $2 "${!PROJECT_LOCATION_MAP[@]}"
    if [ $? -eq "1" ]; then
      echo "Application $2 Not found"
      exit 1
    fi
    echo "stopping project $2"
    killprocesslisteningon ${PORT_MAP[$2]} ${JARS_MAP[$2]}
    echo 'Service Stoped'
  fi
  ;;
restart)
  if [ $2 = all ]; then
    stop
    start
  else
    inArray $2 "${!PROJECT_LOCATION_MAP[@]}"
    if [ $? -eq "1" ]; then
      echo "Application $2 Not found"
      exit 1
    fi
    echo "restarting project $2"
    killprocesslisteningon ${PORT_MAP[$2]} ${JARS_MAP[$2]}
    echo 'Service Stoped'
    startApp ${PROJECT_LOCATION_MAP[$2]} $2 '0.1.0'
    echo 'Service Restarted'
  fi
  ;;
build)
  if [ $2 = all ]; then
    for i in "${!PROJECT_LOCATION_MAP[@]}"
    do
      echo "building project $i"
      buildApp ${PROJECT_LOCATION_MAP[$i]}
    done
  else
    inArray $2 "${!PROJECT_LOCATION_MAP[@]}"
    if [ $? -eq "1" ]; then
      echo "Application $2 Not found"
      exit 1
    fi
    echo "building project $2"
    buildApp ${PROJECT_LOCATION_MAP[$2]}
  fi
  ;;
*)
  echo $"Usage: $0 {start|stop|restart|build}{all|target_application_name}"
  ;;
esac

#curl -X PUT http://localhost:8761/eureka/apps/pin/ip-10-155-213-74.eu-west-1.compute.internal%3Apin%3A8080/status?value=OUT_OF_SERVICE
