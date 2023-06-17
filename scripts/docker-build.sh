#!/bin/bash

PROJECT_ROOT="../"

cd "$(dirname "$PROJECT_ROOT")"

echo "payments-lab 이미지를 새로 빌드하고 있습니다."
docker build -t payments-lab .

BUILD_STATUS=$?

if [ $BUILD_STATUS -eq 0 ]; then
    echo "이미지 빌드가 성공했습니다."
else
    echo "도커 이미지 빌드가 실패했습니다. : $BUILD_STATUS"
fi
