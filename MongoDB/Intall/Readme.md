# MongoDB 설치 및 실행방법
  - 기본 설치방법 및 환경변수 설정
  - 실행 방법

---

## 기본 설치방법 및 환경변수 설정
  ### 1. 인터넷에 접속하여 설치
  - https://www.mongodb.com/download-center 에 접속하여 다운

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/MongoDB/Intall/picture/install.png)

  ### 2. 환경변수 설정
  - 내pc 오른쪽클릭 후 설정클릭 -> 고급시스템 설정 -> 환경변수 -> 시스템변수의 Path 수정(없으면 생성) -> `C:\Program Files\MongoDB\Server\3.4\bin` 과 같이 bin 파일이 존재하는 path를 지정

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/MongoDB/Intall/picture/envrionmentpath.png)

  ### 3. DB를 저장할 디렉토리 생성
  - 아래의 `datas` 와 같이 새로운 폴더 생성

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/MongoDB/Intall/picture/datas.png)

---

## 실행 방법
  ### 1. DB를 실행(연결)-서버기능(`mongod` 명령어 사용)
  - 관리자 권한으로 powershell을 실행시킨다.
  - 경로를 입력한다.
  - `mongod --dbpath "C:\Program Files\MongoDB\datas"` 와 같이 dbpath를 입력하여 실행 시킨다.(Program Files가 있는경우 큰따옴표로 구분시킨다.)
  - 실행시 폴더가 생성되어야 한다.
  - 포트는 `27017`번 포트로 이용됨. (powershell에 출력됨)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/MongoDB/Intall/picture/dbstart.png)


  ### 2. Client를 실행-`mongo` 실행
  - DB를 실행시킨 후에 powershell을 실행한다.
  - `mongo` 명령어 실행시켜 client를 연결(db명령어 이용가능)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/MongoDB/Intall/picture/clientstart.png)

---
