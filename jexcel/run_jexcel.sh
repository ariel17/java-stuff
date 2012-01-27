#!/bin/bash -x

PROJECT_ROOT=`pwd`
BUILD_DIR="$PROJECT_ROOT/build"
TEST_DIR="$PROJECT_ROOT/test"
LIB_DIR="$PROJECT_ROOT/lib"

CLASSPATH=".:$LIB_DIR/poi-3.7-20101029.jar"
MAIN_CLASS="jexcel.JExcel"

JAVA="/usr/bin/java"
ANT="/usr/bin/ant"


$ANT clean;
$ANT compile;

cd $BUILD_DIR;
$JAVA -classpath $CLASSPATH $MAIN_CLASS $TEST_DIR/example.xls;
