## Common-Component

- JWT, Security 등 써드파티 기능들을 관리하는 모듈입니다.
- 다른 도메인 컴포넌트들은 이 컴포넌트를 필요한 경우 의존성을 추가하여 사용합니다.
- 현재 프로젝트에서는 응답메시지, 에러 메시지 포맷과 jwt, security 등의 설정들을 위해 존재하고 있으므로 모든 컴포넌트가 이 컴포넌트에 대한 의존성을 가져야 합니다.