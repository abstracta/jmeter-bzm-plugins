#!/usr/bin/env bash
set -e

JMETER_VERSION=$1
JMETER_PATH=${project.basedir}/.jmeter/$JMETER_VERSION
JMETER_TEST_PATH=${project.basedir}/target/jmeter-test

PWD
APLN_JAR=$(ls $JMETER_TEST_PATH/lib/ | grep alpn-boot)
PWD
JARS=$(ls $JMETER_TEST_PATH/lib/ | grep -v alpn-boot)
PWD
cd $JMETER_TEST_PATH/lib/
PWD
mkdir -p $JMETER_PATH/lib/ext/ && cp -f $JARS $JMETER_PATH/lib/ext/
PWD
cd $JMETER_TEST_PATH
PWD
bzt -o modules.jmeter.path=$JMETER_PATH -o modules.jmeter.version=$JMETER_VERSION testJMeter.yaml || ERROR=$?
PWD
cd $JMETER_PATH/lib/ext/
PWD
rm JARS
PWD
exit $ERROR
PWD

