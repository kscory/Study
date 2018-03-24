# Basic API
  - [참고_yun의 기술 블로그](https://cheese10yun.github.io/API-CALL/)
  - 다른 서버의 Api 호출 방법 [전체코드]()
  -

---

## 다른 서버의 Api 호출
  ### 1. Api 호출 이유 및 사용 방법
  - 프런트에서 Ajax 를 통해 다른 서버의 API 호출할 수 있지만 서버에 해당 기록을 남겨야 하는 경우 백엔드에서 처리해야 한다.
  - request 모듈 사용(웹 요청) : `npm install request`

  ### 2. HOST 설정
  - 즉시 실행함수를 사용하며, switch 문을 이용
  - 타 서버의 API 주소에 따라 유동적으로 변경할 수 있게 설정
  - `require('.../service/API_Call')('dev')` 와 같이 모듈을 require 할 때 해당 API 서버를 쉽게 정할 수 있다.

  ```javascript
  var HOST = null;
  (function () {
      switch (callee) {
          case 'dev':
              HOST = 'https://dev-api.com';
              break;
          case 'prod':
              HOST = 'https://prod-api.com';
              break;
          case 'another':
              HOST = 'http://localhost';
              break;
          default:
              HOST = 'http://localhost';
      }
  })(callee);
  ```

  ### 3. 타 서버의 API 정보 설정
  - headers : header 를 정해준다 (여기서는 json 형식으로 content-type 을 정함)
  - `url` : 해당 API의 URL 을 의미(특정 요청에 따라 달라지니 null 로 설정)
  - `body` : body 값을 전달해주는 객체로 사용
  - `PORT` : 포트 번호
  - `BASE_PATH` : 기본 경로
  - EX> `require('.../service/API_Call')('another')` 로 설정
    - http://localhost:3500/api/v1 으로 기본 url 이 설정됨

  ```javascript
  function API_Call(callee) {
      var OPTIONS = {
          headers: {'Content-Type': 'application/json'},
          url: null,
          body: null
      };
      const PORT = '3500';
      const BASE_PATH = '/api/v1';
      var HOST = null;
      /* ... */
  }
  ```

  ### 4. 에러 핸들링
  - statusCode 를 넘겨 받고 statusCode 가 200 인 경우에만 결과값을 받고 나머지는 에러처리(아래보다 더 많이 에러처리르 해야 한다.)
  - statusCode 참고>
    - `200` : 성공
    - `400` : Bad Request (fild validation 실패 시)
    - `401` : Unauthorized (API 인증, 인가 실패)
    - `404` : Not found (해당 리소스가 없음)
    - `500` : Internal Server Error (서버 에러)

  ```javascript
  function statusCodeErrorHandler(statusCode, callback , data) {
      switch (statusCode) {
          case 200:
              callback(null, JSON.parse(data));
              break;
          default:
              callback('error', JSON.parse(data));
              break;
      }
  }
  ```

  ### 5. 특정 API 호출 로직
  - ex> 로그인 로직이 있다면?? (/login 으로 호출, body에는 id, pwd 들어감)

  ```javascript
  return {
      login : function (user_id, password, callback) {
          OPTIONS.url = HOST + ':' + PORT + BASE_PATH + '/login';
          OPTIONS.body = JSON.stringify({
              "user_id": user_id,
              "password": password
          });
          request.post(OPTIONS, function (err, res, result) {
              statusCodeErrorHandler(res.statusCode, callback, result);
          });
      }
  };
  ```

  ### 6. API 호출
  - 위의 모듈을 가지고 다른 파일에서 API를 호출할 수 있다.
  - 아래 프로세스의 순서(ex> 로그인이라 하면)
    - client 가 로그인을 백엔드에 요청(ex> 주소 : 백/login/another/api)
    - 백엔드에서 외부 API에 로그인 요청 (ex> 주소 : 외부:3500/api/v1)
    - 외부 API의 결과를 가지고 client 에 전달

  ```javascript
  var API_Call = require('../service/API_Call')('another');
  router.post('/login/another/api', function (req, res) {
      var
          user_id = req.body.user_id,
          password = req.body.password;
      API_Call.login(user_id, password, function (err, result) {
          if (!err) {
              res.json(result);
          } else {
              res.json(err);
          }
      });
  });
  ```
