# Basic FireBase
  - [FireBase 홈페이지](https://firebase.google.com/)
  - FireBase의 형태 및 시작 / 구성
  - Realtime database 기초

---

## FireBase의 형태 및 시작 / 구성
  ### 1. FireBase의 형태
  - 백엔드는 BaaS의 형태(로그인과 테이블 기능 제공)에서 PaaS 의 형태로 진화
  - Firebase는 PaaS(Platform as a Service)의 형태로 BaaS 보다 많은 기능들을 제공한다.
  - Firebase는 manifest에 직접 권한 설정을 하지 않아도 자동으로 처리해준다.

 ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/baas%2Cpaas.png)

  ### 2. FireBase 시작
  - [firebase 홈페이지](https://firebase.google.com/) -> [gotoconsole](https://console.firebase.google.com/?hl=ko) -> 새 프로젝트 생성
  - 아래의 순서로 실행

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/process.png)

  ### 3. FireBase 구성
  - 아래와 같이 구성

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/menu.png)

  - 사용하기 위해서는 아래와 같이 gradle의 Dependency 추가

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/dependency.png)

---

## Realtime database 기초
  ### 1. 시작
  - [realtime database 문서로 이동](https://firebase.google.com/docs/database/android/start/?authuser=0) -> get start ->적혀있는 대로 gradle에 추가 및 실행 -> 만든 프로젝트에서 database를 생성 -> 규칙(rule) 클릭후 [규칙 설정 가이드](https://firebase.google.com/docs/database/security/quickstart?authuser=0) 에 따라 rule 설정

  > app gradle

  ```
  compile 'com.google.firebase:firebase-database:11.4.2'
  ```

  > rule 예시 (공개의 경우)

  ```
  {
    "rules": {
      ".read": true,
      ".write": true
    }
  }
  ```

  ### 2. 데이터 만들기
  - NoSQL 형식으로 만들어진다.
  - tree 형태로 값을 입력하기 전까지 하위 디렉토리로 연결할 수 있다.
  - 값을 입력하면 그 아래에는 데이터를 넣을 수 없다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/datainput.png)

  ### 3. 코드 예시
  - `database.getReference()` : 디렉토리(key)를 지정
  - `setValue()` : 값을 저장

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase/picture/datainputex.png)

---

## firebase를 이용한 간단한 예제1 - 채팅
  ### 1. 채팅
  - 내용

---

## firebase를 이용한 간단한 예제2 - 게시판
  ### 1. 채팅
  - 내용


---

## 참고 개념
  ### 1. 웹호스팅 / 서버호스팅 / 클라우드
  - 내용
