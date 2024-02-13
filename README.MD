# Introduce
'Payment-lab'은 이커머스 서비스내에서 중요하면서 민감할 수 있는 결제 도메인에 대한 다양한 경험을 갖기 위해 구성한 사이드 프로젝트 입니다. 이 프로젝트를 통해 결제 서비스를 이용하는 사용자의 경험을 침해하지 않으면서 정확한 정산이 가능한 결제 서버를 구축할 수 있는 역량을 얻고자 합니다.

## 프로젝트 구성

- **기간 :** 23.05 ~ (진행중)
- **팀원 구성 :** 백엔드 1인, 리뷰어 1인
- **주요 스킬 :** kotlin, spring boot, jpa, mysql, kafka, rest docs, docker
- **진행도 관리 :** [GitHub project kanban](https://github.com/users/wanniDev/projects/6)
- **api 정의서 :** [wiki api 정의서 페이지](https://github.com/f-lab-edu/payment-lab/wiki/%F0%9F%93%82-API-%EC%A0%95%EC%9D%98%EC%84%9C)

## 실행 방법
해당 방법은 리포지토리를 클론하여, 바로 로컬에 빌드하고 실행하는 방법입니다.
배포 환경에서 실행하고 싶다면, 별도로 db 및 kafka를 구성하고 별도로 토스페이먼츠 pg 테스트 환경을 구성하셔야 합니다.

### 1. Docker-compose로 카프카 구성 실행

```bash
docker-compose -f ./docker-compose-kafka-single-broker.yml up -d
```

### 2. 프로젝트 빌드

```bash
./gradlew build
```

### 3. 프로젝트 실행
gradle로 실행
```bash
./gradlew bootRun
```
jar로 실행
```bash
java -jar ${projectDir}/app/build/libs/app-0.0.1.jar
```

## Git management

### 커밋 메세지 제목 작성 가이드

> 출처 : [Git Commit Message StyleGuide](https://github.com/slashsbin/styleguide-git-commit-message#message-subjectfirst-line)

**"태그: 제목"의 형태이고, : 뒤에 공백이 있음에 유의한다.**

| 태그 이름 | 설명                                                         |
| --------- | ------------------------------------------------------------ |
| Feat      | 새로운 기능을 추가할 경우                                    |
| Fix       | 버그를 고친 경우                                             |
| !HOTFIX   | 급하게 치명적인 버그를 고쳐야하는 경우                       |
| Refactor  | 프로덕션 코드 리팩토링                                       |
| Comment   | 필요한 주석 추가 및 변경                                     |
| Document  | 문서를 수정한 경우                                           |
| Test      | 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)           |
| Chore     | 빌드 task 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X) |
| Rename    | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우           |
| Remove    | 파일을 삭제하는 작업만 수행한 경우                           |

## 프로젝트 진행 프로세스
- 칸반에 진행할 task를 'todo' 섹션에 등록합니다.
- task의 진행 계획을 구성하고 리뷰어와 트러블 슈팅을 진행하여 계획의 방향을 보완합니다.
- 수행한 task를 pr을 통해, 리뷰어와 코드 리뷰를 진행합니다.
- 리뷰 내용을 반영하고, approved를 받으면 merge를 수행합니다. 그후 해당 'task'는 'done' 섹션으로 전환 합니다.
- 이 과정을 프로젝트가 완료될때까지 반복합니다.
