sed -i "s/java \$JVM_ARGS/java -Xbootclasspath\/p:$JMETER_TEST_PATH\/$APLN_JAR \$JVM_ARGS /" $JMETER_PATH/bin/jmeter.sh
