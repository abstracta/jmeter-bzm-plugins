#!/usr/bin/env bash
set -e

JMETER_VERSION=$1
JMETER_PATH=${project.basedir}/.jmeter/$JMETER_VERSION
JMETER_TEST_PATH=${project.basedir}/http2/target/jmeter-test

APLN_JAR=$(ls $JMETER_TEST_PATH/lib/ | grep alpn-boot)
JARS=$(ls $JMETER_TEST_PATH/lib/ | grep -v alpn-boot)
cd $JMETER_TEST_PATH/lib/
mkdir -p $JMETER_PATH/lib/ext/ && cp -f $JARS $JMETER_PATH/lib/ext/
bzt -o modules.jmeter.path=$JMETER_PATH -o modules.jmeter.version=$JMETER_VERSION $JMETER_TEST_PATH/testJMeter.yaml || ERROR=$?
cd $JMETER_PATH/lib/ext/
rm JARS
exit $ERROR

