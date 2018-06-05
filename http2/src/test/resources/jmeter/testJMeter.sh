#!/usr/bin/env bash
set -e

JMETER_VERSION=$1
JMETER_PATH=${project.basedir}/.jmeter/$JMETER_VERSION
JMETER_TEST_PATH=${project.basedir}/target/jmeter-test

pwd
APLN_JAR=$(ls $JMETER_TEST_PATH/lib/ | grep alpn-boot)
pwd
JARS=$(ls $JMETER_TEST_PATH/lib/ | grep -v alpn-boot)
pwd
cd $JMETER_TEST_PATH/lib/
pwd
mkdir -p $JMETER_PATH/lib/ext/ && cp -f $JARS $JMETER_PATH/lib/ext/
pwd
cd $JMETER_TEST_PATH
pwd
bzt -o modules.jmeter.path=$JMETER_PATH -o modules.jmeter.version=$JMETER_VERSION testJMeter.yaml || ERROR=$?
pwd
cd $JMETER_PATH/lib/ext/
pwd
rm JARS
pwd
exit $ERROR
pwd

