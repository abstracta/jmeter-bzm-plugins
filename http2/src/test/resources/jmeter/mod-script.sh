#!/usr/bin/env bash
set -e

JMETER_PATH=${project.basedir}/.jmeter/$JMETER_VERSION
sed -i "s/java \$JVM_ARGS/java -Xbootclasspath\/p:$APLN_JAR \$JVM_ARGS /" JMETER_PATH/bin/jmeter.sh