@echo off
echo ================================================
echo              Maven Clean Install
echo ================================================

echo Resolving Dependencies - check log status in %cd%\resolve.log . . . . 

call mvn dependency:resolve > resolve.log 

echo Cleaning Workspace - check log status in %cd%\clean.log . . . . 


call mvn clean > clean.log

echo Compiling Workspace - check log status in %cd%\compile.log . . . . 


call mvn compile > compiled.log

echo Generating Package - check log status in %cd%\installed.log . . . . 

call mvn install > installed.log

echo Created JAR file in %cd%\target\wsdlconsumer.jar

set /p installed=Done. Hit ENTER to Exit.