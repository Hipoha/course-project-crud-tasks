call gradlew build
if "%ERRORLEVEL%" == "0" goto rename
echo.
echo GRADLEW BUILD encountered problems. Stopping the script.
goto fail

:rename
del build\libs\crud.war
ren build\libs\tasks-0.0.1-SNAPSHOT.war crud.war
if "%ERRORLEVEL%" == "0" goto stoptomcat
echo Could not rename file
goto fail

:stoptomcat
call %CATALINA_HOME%\bin\shutdown.bat

:copyfile
copy build\libs\crud.war %CATALINA_HOME%\webapps
if "%ERRORLEVEL%" == "0" goto runtomcat
echo Could not copy file
goto fail

:runtomcat
call %CATALINA_HOME%\bin\startup.bat
goto end

:fail
echo.
echo Errors were found. Script stopped.

:end
echo.
echo Script execution finished successfully.