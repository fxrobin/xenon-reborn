#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64

echo "Building ..."

gradle clean desktop:dist


echo "Running ..."
java -jar desktop/build/libs/desktop-1.0.jar

