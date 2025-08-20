@echo off
REM Proxy script at workspace root to forward Gradle wrapper calls to app container wrapper.
setlocal

set ROOT_DIR=%~dp0
set APP_DIR=%ROOT_DIR%fitbuddy_mobile_app
set WRAPPER=%APP_DIR%\gradlew.bat

if not exist "%WRAPPER%" (
  echo Error: Gradle wrapper not found at %WRAPPER% 1>&2
  exit /b 127
)

pushd "%APP_DIR%"
call .\gradlew.bat %*
popd
endlocal
