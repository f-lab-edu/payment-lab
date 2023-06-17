#!/bin/bash

PROJECT_ROOT="../"

cd "$(dirname "$PROJECT_ROOT")"

if [ "$(docker ps -q -f name=payments-lab)" ]; then
    echo "현재 실행되어 있는 payments-lab 컴포즈를 중단하고 삭제합니다."
    docker-compose down
else
    echo "해당 컴포즈가 실행되어 있는지 조회가 안되고 있습니다. 'docker ps' 명령어로 확인해주세요."
fi