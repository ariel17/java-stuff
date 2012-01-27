#!/bin/bash -x

PROJECT_ROOT="/home/ariel17/develop/jexcel"
BUILD_DIR="$PROJECT_ROOT/build"
TEST_DIR="$PROJECT_ROOT/test"
LIB_DIR="$PROJECT_ROOT/lib"

CLASSPATH=".:$LIB_DIR/poi-3.7-20101029.jar"
MAIN_CLASS="org.apache.poi.hssf.usermodel.examples.HSSFReadWrite"

JAVA="/usr/bin/java"


cd $BUILD_DIR;
$JAVA -classpath $CLASSPATH $MAIN_CLASS $TEST_DIR/example.xls;
