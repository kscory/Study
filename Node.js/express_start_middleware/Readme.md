# Express Start & Middleware
  - 정적 / 동적으로 실행
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
    - 특정 디렉토리에 파일을 따로 만들어 호출한다.
  - `static` 이라는 미들웨어 이용
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
  - [미들웨어 모듈](http://expressjs.com/en/resources/middleware.html) 에서 많은 미들웨어 모듈을 확인할 수 있다.

  ### 1. next
  - 미들웨어에서 request 객체와 response 객체에 속성 혹은 메소드를 추가하면 다른 미들웨어에서도 추가한 속성과 메소드를 사용할 수 있다.
  - 즉, 특정한 작업을 수행하는 모듈을 분리해서 만들수 있도록 도와준다.

  ```javascript
  app.use(function(req, res, next){
      req.number = 52;
      res.number = 273;
      next();
  })

  app.use(function(req, res, next){
      res.send(req.number + " / " + res.number);
  })
  // 그 이후 호출하면 52 : 273 이 호출되는 것을 알 수 있다.
  ```

  ### 2. router
  - 페이지 라우팅 기능을 지원, 라우팅을 모듈로 분리하는 기능도 제공
  - `get(path, callback[, callback ...])` : GET 요청이 발생했을 때의 이벤트 리스너 지정
  - `post(path, callback[, callback ...])` : POST 요청이 발생했을 때의 이벤트 리스너 지정
  - `put(path, callback[, callback ...])` : PUT 요청이 발생했을 때의 이벤트 리스너 지정
  - `delete(path, callback[, callback ...])` : DELETE 요청이 발생했을 때의 이벤트 리스너 지정
  - `all(path, callback[, callback ...])` : 모든 요청이 발생했을 때의 이벤트 리스너 지정

  > (라우터 모듈화 활용 예) - 다음과 같이 라우팅을 모듈로 분리화 가능

  ```javascript
  // routerA.js 파일
  var express = require('express');
  var router = express.Router();

  router.get('./index', function(req, res){
      response.send("index Page")
  })

  // 외부로 뺀다.
  exports.router = router
  ```

  ```javascript
  // index.js 파일
  var express = require('express');
  var app = express();

  app.use('/a', require("./routerA").router);
  app.use('/b', require("./routerB").router);
  app.use('/c', require("./routerC").router);

  /* 서버 실행.. */
  ```

  ### 3. static
  - static 으로 지정한 폴더에 있는 내용을 모두 웹 루프 폴더에 올릴 수 있다.
  - 내장되어 있는 미들웨어로 따로 설치가 필요 없다.
  - 사용 : `app.use(express.static('디렉토리'));`

  ### 4. morgan
  - 웹 요청이 들어왔을 때 로그를 출력하는 미들웨어
  - 설치 : `npm install morgan`
  - 모듈 추출 : `require('morgan')`
  - 사용 : `app.use(morgan(매개변수);`
    - :req[header] : 요청 해더를 나타냄
    - :remote_addr : 원격 주소를 나타냄
    - :method : 요청 방식을 나타냄
    - etc...
  - 매개변수 등 더 확인은 => [여기로](http://expressjs.com/en/resources/middleware/morgan.html)

  ```javascript
  morgan = require('morgan');
  app.use(morgan(':method + :date')); // 요청 방식 과 시간을 로그에 찍어준다.
  // app.use(morgan('combined'));     // combined 형식으로 로그에 찍어줌...
  ```

  ### 5. cookie parser
  - 요청 쿠키를 추출하는 미들웨어 (request 객체와 response 객체에 cookes 속성과 cookie() 메서드가 부여)
  - 설치 : `npm install cookie-parser`
  - 모듈 추출 : `require('cookie-parser')`
  - 사용 : `app.use(cookieParser)`
  - 쿠키 생성 및 매개변수 : response.cookie(key, value, option)
    - httpOnly : 클라이언트의 쿠키 접근 권한을 지정
    - secure : secure 속성을 지정
    - expires : expires 속성을 지정
    - maxAge : 상대적인 expires 속성을 지정
    - path : path 속성을 지정
  - 로그인 시 res.cookie('auth',true) 를 추가시켜 로그인을 구성할 수 있다.

  ```javascript
  var cookieParser = require('cookie-parser');
  app.use(cookieParser);

  app.get('/getCookie', function(req, res){
      // 응답
      res.send(req.cookies);
  });

  app.get('/setCookie', function(req, res){
      // 쿠키를 생성
      res.cookie('string', 'cookie');
      res.cookie('json', {
          name: 'cookie',
          property: 'delicious'
      });
      res.cookie('third', 'cookieee', {
          maxAge: 6000,
          secure: true
      })

      // 응답을 호출(리다이렉트로 "주소/getCookie" 를 호출)
      res.redirct('./getCookie');
  });
  ```


  ### 6. body parser
  - POST 요청 데이터를 추출하는 미들웨어 (but> application 인코딩 방식만 지원하고, multiparty 인코딩 지원하지 않음)
  - 설치 : `npm install body-parser`
  - 모듈 추출 : `require('body-parser')`
  - 사용 : `app.use(bodyParser.urlencoded({ extended: false }))`

  > html 에 input 에서 name이 login, password 로 지정되어 있음

  ```javascript
  var login = request.body.login;       // 지정한 이름만 body에서 꺼낼 수 있음
  var password = request.body.password;

  // 로그인이 성공하면 cookie에 auth를 추가시켜 준다
  ```

  ### 7. connect-multiparty
  - multiparty/form-data 인코딩 방식을 사용(file 추가할 때 보통 사용)
  - 설치 : `npm install connect-multiparty`
  - 모듈 추출 : `require('connect-multiparty')`
  - 사용 : `app.use(multiparty({ uploadDir: _dirname + '/multipart' }))`
    - uploadDir 속성을 지정하면 파일 업로드 시 지정된 경로로 파일이 업로드 된다.
  - 주의 : 파일 관련정보는 `body` 속성이 아니라 모두 `files` 속성에 존재한다.
  - 참고1> multiple 속성이면 파일이 여러개로 넘어오는데 이 때는 배열 형태로 파일이 전달된다.
  - 참고2> multipart/form-data 처리 모듈은 `busboy`, `formidable` 등 많은 미들웨어가 존재

  ### 8. express-session
  - `session` : 서버에 정보를 저장하는 기술 _ request 객체에 session 속성을 부여 (cf. `cookie` : 웹 브라우저에 정보를 저장하는 기능)
  - 클라이언트에 세션 식별자 쿠키를 부여하고, 이와 대응되는 서버 별도 저장소에 데이터를 저장
  - 설치 : `npm install express-session`
  - 모듈 추출 : `require('express-session')`
  - 사용 : `app.use(session( 속성 ))`
  - `connect.sid` 쿠키를 생성시키며, 세션의 유지 시간을 변경시키고 싶다면 sesion 옵션에서 cookie 값을 변경할 수 있다.
  - session() 메서드 옵션
    - `name` : 쿠키의 name 속성 지정
    - `store` : 세션 저장소 지정
    - `cookie` : 생성할 쿠키와 관련된 정보 지정(originalMaxAge, expires, httpOnly, path)
    - `resave` : 세션이 변경되지 않아도 세션 저장소에 반영(resave)할지 설정(bool 값)
    - `saveUninitialized` : 초기화되지 않은 세션을 세션 저장소에 저장할지 설정(bool 값)
  - session 객체의 메서드
    - `regenerate()` : 세션을 다시 생성
    - `destroy()` : 세션을 제거
    - `reload()` : 세션을 다시 불러옴
    - `save()` : 세션을 저장

  > 현재 시간을 now 세션에 저장하기

  ```javascript
  var session = require('express-session');
  /* ... */

  app.use(session({
      secret: 'secret key',
      resave: false,
      saveUninitialized: true,
      cookie: {
          maxAge: 60 * 1000
      }
  }))

  app.use(function(req, res){
      // 세션을 저장
      request.session.now = (new Date()).toUTCString();
      // 응답
      response.send(request.session);
  });
  ```

---
## 참고
  ### 1. form 형태를 입력받는 방법
  - \` 를 사용하면 form 형태를 넣을 수 있다.
  - \` 사이에 `&{변수}` 를 사용하면 자바스크립트의 변수를 넣을 수 있다.

  ### 2. 파일을 업로드 할 때 이름중복 시 새로 지정해 주는 경우
  - rename() 메서드를 사용하여 처리
  - 원하는 파일이 아니라면 삭제
  - 하지만 UUID(아이디 중복 방지) 모듈을 이용하여 파일 중복을 방지할 수도 있다.

  ```javascript
  app.post('/', function(req, res){
      var comment = req.body.comment;
      var imageFile = req.files.image;

      // 특정 파일이 넘어왔다면 실행
      if(imageFile){
          var name = imageFile.name;
          var path = imageFile.path;
          var type = imageFile.type;

          // 이미지 파일 확인
          if (type.indexOf('image') != -1) {
              // 이미지 파일의 경우 파일 이름을 변경
              var outputPath = _dirname + "/multipart/" + Date.now() + '_' + name;
              fs.rename(path, outputPath, function(error){
                  res.redirct('/');
              })
          }
      } else {
          // 이미지 파일이 아닌 경우 파일을 제거
          fs.unlink(path, function(error){
              res.sendStatus(400);
          })
      } else {
          // 파일이 없는 경우
          res.sendStatus(404);
      }
  })
  ```
