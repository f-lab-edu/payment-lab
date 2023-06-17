#!/bin/bash

PROJECT_ROOT="../"

cd "$(dirname "$PROJECT_ROOT")"

if [ "$(docker ps -q -f name=payments-lab)" ]; then
    echo "현재 실행되어 있는 payments-lab 컴포즈를 중단하고 삭제합니다."
    docker-compose down
fi

echo "이미지 빌드가 성공했습니다. payments-lab 컴포즈를 실행 하겠습니다."
docker-compose up -d
