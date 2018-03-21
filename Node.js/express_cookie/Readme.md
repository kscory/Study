# Cookie
  - Cookie란
  - 일반적인 Cookie 사용 방법
  - 암호화된 Cookie 사용 방법

---

## Cookie
  ### 1. Cookie 란
  - [위키피디아](https://ko.wikipedia.org/wiki/HTTP_%EC%BF%A0%ED%82%A4) 참고
  - 하이퍼 텍스트의 기록서(HTTP)의 일종으로서 인터넷 사용자가 어떠한 웹사이트를 방문할 경우 그 사이트가 사용하고 있는 서버를통해 인터넷 사용자의 컴퓨터에 설치되는 작은 기록 정보 파일을 일컫는다.
  - 인터넷 사용자가 같은 웹사이트를 방문할 때마다 읽히고 수시로 새로운 정보로 바뀐다.
  - 쿠키는 같은 웹브라우저에서만 사용 가능하다는 것 주의!!!

  ### 2. Cookie 기본 사용법
  - 설치 : `npm install cookie-parser`
  - 모듈 추출 : `require('cookie-parser')`
  - 사용 : `app.use(cookieParser)`
  - request 에 cookies 변수가 포함된다.
  - 쿠키 생성 및 매개변수 :` response.cookie(key, value, option`)
  - 옵션 값 종류
    - `httpOnly` : 클라이언트의 쿠키 접근 권한을 지정
    - `secure` : secure 속성을 지정
    - `expires` : expires 속성을 지정
    - `maxAge` : 상대적인 expires 속성을 지정
    - `path` : path 속성을 지정
  - 로그인 시 res.cookie('auth',true) 를 추가시켜 로그인을 구성할 수 있다.
  - cookie의 값은 모두 string 으로 가져오므로 강제로 int로 변형한다.

  > 예제 1

  ```javascript
  var express = require('express');
  // cookie-parser 미들웨어 사용
  var cookieParser = require('cookie-parser');

  var app = express();
  app.use(cookieParser()); // cookie Parser 사용

  app.get('/count', function (req, res){
      // count 라는 변수가 있다면 저장..
      if(req.cookies.count) {
          // 모두 string 으로 오므로 강제로 int로 바꿈
          var count = parseInt(req.cookies.count);
      } else {
          var count = 0;
      }
      count += 1;
      // 웹브라우저- 네트워크의 set-cookie 에 count 의 값을 저장한다.
      res.cookie('count', count);
      // 내가 지정한 count 라는 변수를 가져올 수 있다.
      res.send('count : ' + count);
  });

  app.listen(9000, function(){
      console.log("server is running (9000)")
  });
  ```

  > 예제 2

  ```javascript
  var cookieParser = require('cookie-parser');
  app.use(cookieParser);

  app.get('/getCookie', function(req, res){
      // 응답
      res.send(req.cookies);
  });

  app.get('/setCookie', function(req, res){
      // 쿠키를 생성
      res.cookie('json', {
          name: 'cookie',
          property: 'delicious'
      });

      res.cookie('test', 'cookieTest', {
          maxAge: 6000,
          secure: true
      });

      // 응답을 호출(리다이렉트로 "주소/getCookie" 를 호출)
      res.redirct('./getCookie');
  });
  ```

  ### 3. 암호화된 Cookie
  - 쿠키는 누군가에 의해서 읽히게 되면 매우 위험해 질 수 있다. (ex> 인증 관련)
  - https 로 통신을 하는 것이 좋다. => 이는 설정을 해주어야 한다.
  - 이를 이용하면 쿠키를 암호화 할 수 있다.
  - 사용법
    - `cookieParser` 에 임의의 값을 주면 이 값이 key 가 되어 암호화 한다.
    - 쿠키를 가져올 때 `signedCookies` 을 사용하면 키값으로 해독시킨 값을 가져올 수 있다.
    - 쿠키를 저장할 때 `{signed:true}` 값을 주어 response 한다.

  ```javascript
  var express = require('express');
  var cookieParser = require('cookie-parser');

  var app = express();
  app.use(cookieParser('asdfenjklkvjf123iuhg')); // 임의의 값을 준다(https)

  app.get('/count', function (req, res){
      // signedCookies 로 변경 그러면 암호를 키값으로 해독시킨 값을 가져올 수 있다.
      if(req.signedCookies.count) {
          var count = parseInt(req.cookies.count);
      } else {
          var count = 0;
      }
      count += 1;
      // {signed:true} 값을 주어 response 해준다.
      res.cookie('count', count, {signed:true});
      res.send('count : ' + count);
  });

  app.listen(9000, function(){
      console.log("server is running (9000)")
  });
  ```
