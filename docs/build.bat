@echo off
cls

set X=%cd%
cd \.
cd Program Files
cd Java
cd jdk*
cd bin
set PT=%cd%
set PATH=%PATH%;%PT%
cd %X%


cd ..
set PRAC_BIN=.\bin
set PRAC_DOCS=.\docs
set PRAC_LIB=.\lib\*
set PRAC_SRC=.\src
set PRAC_PATH=%cd%

echo *** Cleaning ***
del %PRAC_BIN%\*.class
IF %ERRORLEVEL% GTR 0 GOTO ERROR
echo *** Compiling ***
javac -sourcepath %PRAC_SRC% -cp "%PRAC_BIN%;%PRAC_LIB%" -d %PRAC_BIN% %PRAC_SRC%\Main.java
IF %ERRORLEVEL% GTR 0 GOTO ERROR
echo *** Building JavaDoc ***
javadoc -subpackages %PRAC_SRC% -sourcepath %PRAC_SRC% -classpath %PRAC_BIN%;%PRAC_LIB% -use -author -d %PRAC_DOCS%\JavaDocs %PRAC_SRC%\*.java
IF %ERRORLEVEL% GTR 0 GOTO ERROR
echo *** Running application ***
java -cp %PRAC_BIN%;%PRAC_LIB% Main
IF %ERRORLEVEL% GTR 0 GOTO ERROR
GOTO END
:ERROR
echo !!! An error has occured !!!
echo Error number is %ERRORLEVEL%
:END
echo *** Completed ***
cd %PRAC_DOCS%
pause
