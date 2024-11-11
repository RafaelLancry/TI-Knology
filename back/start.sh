#!/bin/sh

while ! nc -z db 3306; do
  sleep 10
done

java -jar app.jar