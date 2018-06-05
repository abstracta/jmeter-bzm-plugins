#!/usr/bin/env bash
set -e

pwd
JMETER_TEST_PATH=${project.basedir}/target/jmeter-test
sed -i "s/java \$JVM_ARGS/java -Xbootclasspath\/p:$APLN_JAR \$JVM_ARGS /" $JMETER_TEST_PATH/bin/jmeter.sh