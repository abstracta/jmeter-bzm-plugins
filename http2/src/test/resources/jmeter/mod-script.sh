#!/usr/bin/env bash
set -e

pwd
echo "**********mod-script********"
echo $JMETER_PATH
echo $JMETER_TEST_PATH
echo $APLN_JAR
echo "**********mod-script********"
sed -i "s/java \$JVM_ARGS/java -Xbootclasspath\/p:$JMETER_TEST_PATH/$APLN_JAR \$JVM_ARGS /" $JMETER_PATH/bin/jmeter.sh