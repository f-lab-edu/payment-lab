## Introduce
'Payments-lab'은 결제 도메인 서비스를 구성하는 데 필요한 기능을 구현해보고, 해당 서비스를 운영하면서 발생할 수 있는 여러 이슈들을 찾고 해결해보는 경험을 갖기 위해 구성한 사이드 프로젝트 입니다.

## Architecture
결제 도메인이 속한 대부분의 비즈니스 모델이 BtoC 혹은 CtoC 기반의 e-commerce 라는 점에서 단순하지만 확장성이 부족한 monolithic 구조 보다는 MSA 구조로 전환이 용이한 [modular monolithic](https://giljae.com/2022/10/13/Moduler-Monolithic-아키텍처.html) 구조를 채택 하였습니다.
