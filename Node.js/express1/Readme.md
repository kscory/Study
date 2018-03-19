# Express 1
  - [참고 - Express 공식 홈페이지](http://expressjs.com/)
  Basic Expres
    - 설치 및 실행
    - 기본 서버
    - get 예시
  - 기본 응답 및 요청 메소드
    - response 객체 메소드
    - request 객체 메소드

---

## Basic Express
  ### 1. Express 설치
  - 내 프로젝트를 모듈화 시킨 후 express 를 디펜던시에 추가시켜 설치

  > 설치 방법 :  `npm init` 후 `npm install --save express` 를 통해 express 모듈을 설치

  ```
  npm init
  npm install --save express
  ```

  ### 2. Express 기본 서버
  - `app.js` 파일 만들기 : 가장 최초의 진입점(entry 파일)을 권장하는 파일이다.
  - express 모듈을 불러오고 변수에 express 함수를 통해서 실행
  - listen(포트번호, 콜백함수) 메소드를 통해 서버에 연결

  ```javascript
  // express 모듈을 불러오고 변수에 express 함수를 통해서 실행
  var express = require('express');
  var app = express();

  // app.listen(포트번호, 콜백함수)
  app.listen(9000,function(){
      console.log("Connectd 9000 port!!");
  });
  ```

  ### 3. get 사용 방법
  - 일반적인 접근은 보통 get 방식으로 접근한다.
  - 특정 경로로 들어왔을 때 어떤 것이 실행될 것인가 하는 것을 라우터라 한다. (get / post 등)
  - get(경로, 콜백함수(request, response)) 를 통해 접근 가능

  ```javascript
  var express = require('express');
  var app = express();

  // get("경로")
  app.get('/', function(req, res){
      // res 에는 send 메소드 존재 여기서 응답 가능
      res.send("hello home page");
  });
  app.get('/login', function (req,res){
     res.send('Login please');
  });

  app.listen(9000,function(){
      console.log("Connectd 9000 port!!");
  });
  ```
---
## 기본 응답 / 요청 메소드
  ### 1. response 객체 메소드
  - `response.send([body])`
    - 매개변수의 자료형에 따라 적절한 형태로 응답
    - 앞에 `status(코드 번호)` 를 입력하면 상태코드를 전달 할 수 있다.
    - cf> response.sendStatus(코드번호) : 상태 코드만을 전달
    - send 메소드 두번 불가능!!!
  - `response.josn([body])`
    - JSON 형태로 응답
  - `response.jsonp([body])`
    - JSONP 형태로 응답
  - `response.redirect([status,] path)`
    - 웹 페이지 경로를 강제로 이동

  ### 2. request 객체 메소드
  - `params`
    - 라우팅 매개변수를 추출
  - `query`
    - 요청 매개변수를 추출
  - `headers`
    - 요청 헤더 추출
  - `header()`
    - 요청 헤더 속성 지정 혹은 추출
  - `accepts(type)`
    - 요청 헤더의 Accpet 속성 확인
  - `is(type)`
    - 요청 헤더의 Content-Type 속성 확인

  > 예시

  ```javascript
  var express = require('express');
  var app = express();

  app.get('/', function(request, response){

      var total = request.query.toString();

      response.send("<h1>Request test</h1>\n "+ "total" + total);
  });

  app.listen(9000,function(){
      console.log("Server is running...");
  });
  ```
