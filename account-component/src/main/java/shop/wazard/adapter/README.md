# Adapter Port

## 주의사항
- 다른 계층에서 접근할 수 없도록 패키지 내부의 모든 클래스 접근 제어자를 package-private으로 설정합니다.

## in port
- 현재 프로젝트는 REST API를 사용하므로 RestController를 통해 클라이언트의 요청을 받습니다.
- request, response는 웹 레이어에 따라 변경됩니다. 따라서 converter 또한 adpater계층에 위치합니다.

## out port
- Application계층에서 외부 인프라 계층에 접근하기 위해 진출을 할 수 있는 출구 포트입니다.
- 이 계층에는 인프라에 대한 구현체들이 위치하게 됩니다.
- 또한 이 계층의 모든 클래스에 대하여 접근 제어자는 package-private으로 설정하여 Application계층과의 경계를 명확이 구분합니다.
- 현재 프로젝트는 JPA를 사용합니다.