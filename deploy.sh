#!/bin/bash
#
rm /devel/sonarqube-4.1.2/extensions/plugins/MMetrics*.jar
cp ./target/MMetri*.jar /devel/sonarqube-4.1.2/extensions/plugins

/devel/sonarqube-4.1.2/bin/macosx-universal-64/sonar.sh restart