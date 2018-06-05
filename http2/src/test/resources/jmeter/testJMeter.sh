#!/usr/bin/env bash
set -e

export JMETER_VERSION=$1
export JMETER_PATH=${project.basedir}/.jmeter/$JMETER_VERSION
export JMETER_TEST_PATH=${project.basedir}/target/jmeter-test

echo $JMETER_VERSION
echo $JMETER_PATH
echo $JMETER_TEST_PATH
pwd
APLN_JAR=$(ls $JMETER_TEST_PATH/lib/ | grep alpn-boot)
echo $APLN_JAR
pwd
JARS=$(ls $JMETER_TEST_PATH/lib/ | grep -v alpn-boot)
echo $JARS
pwd
cd $JMETER_TEST_PATH/lib/
pwd
mkdir -p $JMETER_PATH/lib/ext/ && cp -f $JARS $JMETER_PATH/lib/ext/
pwd
cd $JMETER_TEST_PATH
pwd
ls
bzt -o modules.jmeter.path=$JMETER_PATH -o modules.jmeter.version=$JMETER_VERSION testJMeter.yaml || ERROR=$?
pwd
cd $JMETER_PATH/lib/ext/
pwd
rm $JARS
pwd
exit $ERROR

