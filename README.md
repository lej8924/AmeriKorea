
# :chart_with_upwards_trend:AmeriKorea

![License](https://img.shields.io/badge/license-MIT-blue)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)


## 프로젝트 소개

이 프로젝트는 주식 포트폴리오를 효과적으로 관리할 수 있도록 돕는 서비스입니다. 이 프로젝트는 [Java/Spring Boot]을 사용하여 개발되었으며, 주식 관리, 포트폴리오 분석, 배당금 계산 등의 기능을 제공합니다.

   
## 주요 기능

- **주식 관리**: 사용자가 보유한 주식을 관리하고, 현재 가격, 수익 등을 조회할 수 있습니다.
- **포트폴리오 분석**: 사용자의 전체 포트폴리오에 대한 종합적인 분석을 제공하여, 투자 수익률 및 배당 수익률 등을 계산합니다.
- **배당금 캘린더**: 배당금 지급 일정을 캘린더 형식으로 표시하여 쉽게 확인할 수 있습니다.
- **뉴스 연동**: 주식과 관련된 최신 뉴스를 자동으로 수집하고 제공합니다.



## 프로젝트 구조

- `src/main/java/com/hana/amerikorea/` - 프로젝트의 주요 소스 코드 디렉터리
- `src/main/resources/` - 프로젝트의 리소스 파일 (application.yml, 정적 리소스 등)
- `src/test/` - 테스트 코드
- `README.md` - 프로젝트에 대한 정보와 지침을 제공하는 파일
- `application.yml` - 프로젝트 설정 파일



## 설치 및 실행 방법

### 요구 사항

 - Version : Java 17
 - IDE : IntelliJ
 - Framework : SpringBoot  3.3.2
 - ORM : JPA
 - DB : MySQL


### 설치

1. 이 저장소를 클론합니다.
   ```bash
   git clone git@github.com:lej8924/AmeriKorea.git
   cd your-repo-name
   ```

2. 필요한 의존성을 설치합니다.
   ```bash
   ./gradlew build
   ```

3. `src/main/resources/application-local.yml` 파일을 추가하여 데이터베이스 설정을 자신의 환경에 맞게 추가합니다.

4. 애플리케이션을 빌드하고 실행합니다.
   ```bash
   ./gradlew bootRun
   ```

5. 브라우저를 열고 `http://localhost:8081/member-sigin`에 접속합니다.


## 사용법

1. **사용자 등록 및 로그인**: 첫 화면에서 계정을 생성하고 로그인할 수 있습니다.
2. **주식 추가**: 로그인 후, "자산 추가" 메뉴를 통해 주식을 포트폴리오에 추가할 수 있습니다.
3. **포트폴리오 보기**: "대시보드"에서 전체 포트폴리오를 조회하고, 수익률 및 배당 정보를 확인할 수 있습니다.
4. **배당 캘린더**: "배당 캘린더" 메뉴에서 다가오는 배당금 지급 일정을 확인할 수 있습니다.
5. **뉴스 보기**: "뉴스" 메뉴에서 해당 주식과 관련된 최신 뉴스를 확인할 수 있습니다.


## 기여 방법

기여를 환영합니다! 기여 방법은 다음과 같습니다:

1. 이슈를 확인하고 작업할 이슈를 선택합니다.
2. `develop` 브랜치를 기반으로 새 브랜치를 생성합니다.
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. 코드를 작성한 후, 변경 사항을 커밋하고 푸시합니다.
   ```bash
   git add .
   git commit -m "Add new feature: your feature description"
   git push origin feature/your-feature-name
   ```
4. Pull Request를 생성하여 변경 사항을 제출합니다.

## 개발자 소개
 - **연선우** : 팀장, 자산 추가 및 자산세부정보 ui 구현, TradingView api 차트 구현
 - **김지혜** : KIS API 연동 및 처리 과정 구현
 - **이은재** : 포트폴리오 ui 구현, 네이버 뉴스 api 연동, 배당금 캘린더 구현
 - **주세원** : 로그인, 회원가입, 회원정보 조회 기능 구현
 - **김수민** : 로그인, 회원가입, 회원정보 조회 기능 구현

## 라이선스

이 프로젝트는 MIT 라이선스에 따라 라이선스가 부여됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 문의 및 지원

프로젝트에 대한 문의는 [your-email@example.com](mailto:your-email@example.com)으로 연락주시기 바랍니다.
