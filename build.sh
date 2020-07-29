#!/bin/bash

# Emilien Delplancq 13/02/2020

buildPath="build" 

if [[ $2 == "" ]]
then 
	echo "default path"
	export PATH_TO_FX=$(pwd)/lib/javafx-sdk-11.0.2/lib
else	
	echo "Your own path is selected"
	export PATH_TO_FX=$2
fi

echo "javafx sdk is suppose to be here"
echo $PATH_TO_FX 

if [[ $1 == "compile" ]]
then 
	javac -d $buildPath -Xlint:unchecked --source-path src --module-path $PATH_TO_FX --add-modules javafx.controls src/root/gui/TestProg.java

elif [[ $1 == "run" ]]
then
	echo "run" 
	cd $buildPath
	java --module-path $PATH_TO_FX --add-modules javafx.controls -cp $(pwd)/lib/*:. root.gui.TestProg  
	cd ..
elif [[ $1 == "test" ]]
then
	classpath=".:"$(pwd)"/lib/junit-4.13.jar:"$(pwd)"/lib/hamcrest-core-1.3.jar"
	javac -d $buildPath -cp $classpath  -Xlint:unchecked --source-path src --module-path $PATH_TO_FX --add-modules javafx.controls src/root/test/UnitTest.java
	echo "test" 
	cd $buildPath
	java --module-path $PATH_TO_FX --add-modules javafx.controls -cp $classpath org.junit.runner.JUnitCore root.test.UnitTest
	cd ..
else 
	echo "No command detected"
fi