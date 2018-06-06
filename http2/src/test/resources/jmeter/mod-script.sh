JVM_ARGS
sed -i "s?java \$JVM_ARGS?java -Xbootclasspath/p:$JMETER_TEST_PATH/lib/alpn-boot.jar \$JVM_ARGS ?" $JMETER_PATH/bin/jmeter.sh
