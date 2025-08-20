@echo off
REM Proxy script to forward Gradle wrapper calls to the Android app container wrapper.
setlocal enabledelayedexpansion

set ROOT_DIR=%~dp0
set WRAPPER=%ROOT_DIR%fitbuddy_mobile_app\gradlew.bat

if not exist "%WRAPPER%" (
  echo Error: Gradle wrapper not found at %WRAPPER% 1>&2
  exit /b 127
)

call "%WRAPPER%" %*
endlocal
