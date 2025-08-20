#!/bin/bash
cd /home/kavia/workspace/code-generation/fitbuddy-tracker-8901/fitbuddy_mobile_app
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

