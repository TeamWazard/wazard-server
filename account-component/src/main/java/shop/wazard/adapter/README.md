# Adapter Port

## 주의사항
- 다른 계층에서 접근할 수 없도록 패키지 내부의 모든 클래스 접근 제어자를 package-private으로 설정합니다.

## in port
- 현재 프로젝트는 REST API를 사용하므로 RestController를 통해 클라이언트의 요청을 받습니다.

## out port
- Application계층에서 외부 인프라 계층에 접근하기 위한 출구 포트입니다.
- 이 계층에는 인프라에 대한 구현체들이 위치하게 됩니다.