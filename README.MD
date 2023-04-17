# Introduce
'Payments-lab'은 결제 도메인 서비스를 구성하는 데 필요한 기능을 구현해보고, 해당 서비스를 운영하면서 발생할 수 있는 여러 이슈들을 찾고 해결해보는 경험을 갖기 위해 구성한 사이드 프로젝트 입니다.

# Architecture
결제 도메인이 속한 대부분의 비즈니스 모델이 BtoC 혹은 CtoC 기반의 e-commerce 라는 점에서 단순하지만 확장성이 부족한 monolithic 구조 보다는 MSA 구조로 전환이 용이한 [modular monolithic](https://giljae.com/2022/10/13/Moduler-Monolithic-아키텍처.html) 구조를 채택 하였습니다.

# Git management

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