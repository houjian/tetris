@echo off

setlocal

cd /d %~dp0..
set "APP_HOME=%cd%"

set "CLASSPATH=%APP_HOME%\config;.;"
set "CLASSPATH=%APP_HOME%\lib\*;%CLASSPATH%"

set "JAVA_OPTS=%JAVA_OPTS% -Xmx512m -Xmx512m"
set "JAVA_OPTS=%JAVA_OPTS% -XX:+UseConcMarkSweepGC -XX:-OmitStackTraceInFastThrow"
set "JAVA_OPTS=%JAVA_OPTS% -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="%APP_HOME%\logs""
set "JAVA_OPTS=%JAVA_OPTS% -Dlog4j.configurationFile="%APP_HOME%\config\log4j2.xml""
set "JAVA_OPTS=%JAVA_OPTS% -Dlogging.path="%APP_HOME%\logs""

if not ""%1"" == ""gc"" goto gc
set "GC_OPTS=%GC_OPTS% -XX:+PrintGCDetails -Xloggc:"%APP_HOME%\logs\gc.log""
:gc

if not ""%1"" == ""jmx"" goto jmx
set "JMX_OPTS=%JMX_OPTS% -Dcom.sun.management.jmxremote"
set "JMX_OPTS=%JMX_OPTS% -Dcom.sun.management.jmxremote.port=8999"
set "JMX_OPTS=%JMX_OPTS% -Dcom.sun.management.jmxremote.ssl=FALSE"
set "JMX_OPTS=%JMX_OPTS% -Dcom.sun.management.jmxremote.authenticate=FALSE"
:jmx

set "JAVA_OPTS=%JAVA_OPTS% %GC_OPTS%"
set "JAVA_OPTS=%JAVA_OPTS% %JMX_OPTS%"

if exist "%APP_HOME%\logs" goto log
md "%APP_HOME%\logs"
:log

start javaw %JAVA_OPTS% -cp "%CLASSPATH%" io.github.houjian.tetris.Bootstrap
:end
