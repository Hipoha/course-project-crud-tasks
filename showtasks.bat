call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo runcrud.bat encountered problems. Stopping the script.
goto fail

:openbrowser
call "C:\Program Files (x86)\Mozilla Firefox\firefox.exe" -url http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Error opening the browser.
goto fail

:fail
echo.
echo Errors were found. Script stopped.

:end
echo.
echo Script execution finished successfully.