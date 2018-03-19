# Express ㅇㅇㅇ
  - 정적 / 동적 파일 호출
  - 미들웨어

---

## 정적 / 동적으로 실행
  ### 1. 동적으로 실행
  - get 혹은 use 안에서 직접 실행
  - 파일이 바뀌면 서버를 껐다가 다시 켜야하는 불편함이 존재
  - 하지만 자바스크립트의 문법을 활용할 수 있는 장점 존재
    - ex> 아래 예제에서 for 문과 같이 사용 가능
    - 특정 변수를 사용할 때 `${변수}` 를 사용할 수 있다.

  ```javascript
  var express = require('express');
  var app = express();

  app.get('/dynamic', function(req, res){
      var temp = '';
      for(i = 0 ; i<5 ; i ++){
          temp = temp + '<li>coding</li>';
      }
      var output = `
      <!DOCTYPE html>
          <html lang="en">
              <head>
                  <meta charset="UTF-8">
                  <title>Title</title>
              </head>
              <body>
              Hello, Dynamic
              <ul>
              ${temp}
              </ul>
              </body>
          </html>`;
  res.send(output);
  });
  /* ... */
  ```

  ### 2. 정적으로 실행
  - `app.use(epress.static(디렉토리))` 를 호출
    - localhost:포트번호/디렉토리/파일 => `localhost:포트번호/파일` 로 호출할 수 있다.
  - `static` 이라는 미들웨어 이용(뒤에서 다시 설명)
  - 파일이 바뀌어도 정적으로 실행하면 서버를 껐다가 다시 킬 필요가 없다.
  - 그러나 파일이 따로 존재하기 때문에 자바스크립트의 문법을 활용하기 어려운 단점이 존재 (ex> html 같은 경우에는 많은 문법이 포함되어 있지 않다.)

  ```javascript
  var express = require('express');
  var app = express();

  // public 디렉토리 안에 있는 정적 파일을 바로 호출 가능
  app.use(express.static('public'));
  /* ... */
  ```

---
## 미들웨어
  ### 0. express 에서? 미들웨어란?
  - 요청의 응답을 완료하기 전까지 요청 중간중간에 여러 가지 일을 처리하도록 해주는 함수들
  - 미들웨어에서 request 객체와 respone 객체에 속성 혹은 메소드를 추가하면 다른 미들웨어에서도 추가한 속성과 메소드를 사용할 수 있다.
  - 즉, 특정한 작업을 수행하는 모듈을 분리해서 만들수 있도록 도와준다.

  ### 1. next
  - d

  ### 2. router
  - d

  ### 3. static
  - d

  ### 4. morgan
  - d

  ### 5. cookie parser
  - d

  ### 6. body parser
  - d

  ### 7. connect-multiparty
  - dd

  ### 8. express-session
  - dd

---
## 참고
  ### 1. form 형태를 입력받는 방법
  - \` 를 사용하면 form 형태를 넣을 수 있다.
  - \` 사이에 `&{변수}` 를 사용하면 자바스크립트의 변수를 넣을 수 있다.
