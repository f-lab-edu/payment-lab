# Introduce
'Payment-lab'은 이커머스 서비스내에서 중요하면서 민감할 수 있는 결제 도메인에 대한 다양한 경험을 갖기 위해 구성한 사이드 프로젝트 입니다. 이 프로젝트를 통해 결제 서비스를 이용하는 사용자의 경험을 침해하지 않으면서 정확한 정산이 가능한 결제 서버를 구축할 수 있는 역량을 얻고자 합니다.

## 프로젝트 구성

- **기간 :** 23.05 ~ (진행중)
- **팀원 구성 :** 백엔드 1인, 리뷰어 1인
- **주요 스킬 :** kotlin, spring boot, jpa, mysql, kafka, rest docs, docker
- **진행도 관리 :** [GitHub project kanban](https://github.com/users/wanniDev/projects/6)
- **api 정의서 :** [https://api.wannidev.com/](https://api.wannidev.com/)

## Git management

## 트렁크 기반 개발(Trunk-Based Development, TBD)

**오직 Trunk(main) 브렌치에서 직접 모든 작업을 처리하는 것.**

![img](https://trunkbaseddevelopment.com/trunk1a.png)

> 이미지 출처: https://trunkbaseddevelopment.com

### WHY?

1. 몇 가지 안전한 규칙을 기반으로 하여 브렌치 하나를 가지고 관리하여 개발 생명주기의 과정을 최대한 단순화하여, 코드 리뷰 빈도수를 높이고, 배포 주기를 늘려서 기능 개발 및 품질 개선 작업의 템포를 높이고자 한다.
2. 각 팀원이 항상 최신의 코드를 공유하는 환경을 조성하여 협업 환경을 유도하고, 영역이 달라도 서로의 작업에 대한 이해도를 높여 개인의 능력 보다는 팀의 능력 개선을 추구하는 방향을 가지고자 한다.
3. 단순하고 직관적인 깃 로그 추적 및 관리를 위해 해당 깃 관리 정책을 채택하였다.
4. main 브렌치에 직접 푸시함으로서, 즉시 코드 통합 시키는 진정한 의미의 CI(Continuous Integration)을 실현시키고자 한다.
5. merge로 인한 충돌 자체가 일어날 수 없기 때문에 리팩토링이 용이하다.

### HOW?

1. 버그 및 작업 관련 이슈가 발생하면, 작업 영역에 상관없이 같이 해결하기
2. 항상 릴리즈가 가능한 상태를 유지하여 신뢰할 수 있는 빌드 전략 수립(테스트 자동화 및 배포 전략 수립)
3. '[Branch by Abstraction](https://trunkbaseddevelopment.com/branch-by-abstraction/)' 또는 '[Feature Flags](https://martinfowler.com/articles/feature-toggles.html)'를 활용하여 작업이 완료되지 않는 부분은 숨기기
4. '[소규모 배치](https://cloud.google.com/architecture/devops/devops-process-working-in-small-batches?hl=ko)'로 작업하기
5. 빠른 빌드 필요(빌드 및 테스트는 수 분 내에 실행되어야 한다.)

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
