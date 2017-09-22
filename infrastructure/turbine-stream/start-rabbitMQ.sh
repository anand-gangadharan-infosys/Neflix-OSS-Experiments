#!/bin/bash

brew services start rabbitmq

/usr/local/Cellar/rabbitmq/3.6.12/sbin/rabbitmqctl add_user test test
/usr/local/Cellar/rabbitmq/3.6.12/sbin/rabbitmqctl set_user_tags test administrator
/usr/local/Cellar/rabbitmq/3.6.12/sbin/rabbitmqctl set_permissions -p / test ".*" ".*" ".*"

