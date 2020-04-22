set projectLocation=%MAVEN_BASEDIR%
cd %projectLocation%

call mvn test
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" exit /b

call mvn package
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" exit /b

PAUSE
