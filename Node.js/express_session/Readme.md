# Session
  - Session 이란
  - Session 사용 방법
  - 세션 외부에 저장(File, MySQL)

---

## Session
  ### 1. Session 이란
  - session 은 쿠키를 좀 더 발전시킨 것
  - 쿠키는 웹 브라우저에 정보를 저장하는 기술, 세션은 서버에 정보를 저장하는 기술이라고 생각
  - 쿠키에 사용자의 id(식별자) 만 사용자 브라우저에 저장하고 나머지는 서버쪽에 실제 데이터를 저장한다.
  - 여기서 브라우저는 식별자만 서버로 보내고, 이를 식별한 서버가 그 사용자를 인식하고 데이터를 처리한다.
  - `connect.sid` 쿠키를 생성시키며, 세션의 유지 시간을 변경시키고 싶다면 sesion 옵션에서 cookie 값을 변경할 수 있다.

  ### 2. Session 기본 사용법
  - 설치 : `npm install express-session`
  - 모듈 추출 : `require('express-session')`
  - 사용 : `app.use(session( 속성 ))`
  - `connect.sid` 쿠키를 생성시키며, 세션의 유지 시간을 변경시키고 싶다면 sesion 옵션에서 cookie 값을 변경할 수 있다.
  - 주의 : 리다이렉션이 있는 경우 세션을 먼저 세이브를 시키고 콜백함수로 리다이렉션을 시켜야 한다.
    - ex> `req.session.save(function(){ res.redirect('/topic') })`
  - session() 메서드 옵션
    - secret : session id 를 심을 때 쿠키 세팅과 비슷하게 심어준다. (랜덤으로 설정...)
    - `resave` : 세션이 변경되지 않아도 세션 저장소에 반영(resave)할지 설정 (권장값 : false)
    - saveUninitialized : session id 를 세션을 사용하기 전까지 발급하지 않는지 설정 (권장값 : true)
    - `store` : 세션 저장소 지정
    - `name` : 쿠키의 name 속성 지정
    - `cookie` : 생성할 쿠키와 관련된 정보 지정(originalMaxAge, expires, httpOnly, path)
  - session 객체의 메서드
    - `regenerate()` : 세션을 다시 생성
    - `destroy()` : 세션을 제거
    - `reload()` : 세션을 다시 불러옴
    - `save()` : 세션을 저장

  > 예제 1

  ```javascript
  var express = require('express');
  // sesson 모듈 추출
  var session = require('express-session');
  var app = express();

  // 세션 사용
  app.use(session({
      secret: 'kanfknenknfsdnfk',
      resave: false,
      saveUninitialized: true
  }));

  // 세셩 값 불러오고 저장, 삭제
  app.get('/count', function(req, res){
      if(req.session.count) {
          req.session.count ++;
          if(req.sesion.count > 10) {
              // 세션을 제거할 때..
              delete req.sesion.count;
          }
      } else {
          req.session.count = 1;
      }
      res.send('count : ' + req.session.count);
  });

  app.get('/tmp', function(req, res){
      res.send('result : ' + req.session.count);
  });

  app.listen(9000, function(){
      console.log("server is running (9000)")
  });
  ```

  > 예제 2 (현재 시간을 now 세션에 저장하기)

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
## 세션의 저장
  ### 1. File
  - [세션파일 스토어](https://www.npmjs.com/package/session-file-store) 미들웨어 이용
  - 설치 : `npm install session-file-store`
  - 추출 : `require('session-file-store')(session)`
  - 사용 : session 사용시 store에 저장(new FileStore(옵션))
    - 옵션값은 공식 홈페이지 참고
  - session 이라는 디렉토리가 생기고 여기에 json 형식으로 session을 파일로 저장한다.
  - 서버를 끈 뒤 켜도 세션이 남아 있게 할 수 있다.

  ```javascript
  var express = require('express');
  var session = require('express-session');
  var FileStore = require('session-file-store')(session);

  var app = express();

  // store 에 파일 스토어 추가
  app.use(session({
      secret: 'kanfknenknfsdnfk',
      resave: false,
      saveUninitialized: true,
      store: new FileStore()
  }));
  /* 위와 동일 */
  ```

  ### 2. MySQL
  - [express-mysql-session](https://www.npmjs.com/package/express-mysql-session) 미들웨어 이용
  - 설치 : `npm install express-mysql-session`
  - 추출 : `require('express-mysql-session')(session)`
  - 사용 : session 사용시 store에 MySQL 스토어를 만들어 저장 (옵션을 반드시 넣어주어야 한다.)
  - session 이라는 테이블이 생기고 여기에 형식에 맞는 스키마를 저장한다.
  - 서버를 끈 뒤 켜도 세션이 남아 있게 할 수 있다.

  ```javascript
  var express = require('express');
  var session = require('express-session');
  var MySQLStore = require('express-mysql-session')(session);
  var app = express();

  app.use(session({
      secret: 'kanfknenknfsdnfk',
      resave: false,
      saveUninitialized: true,
      store: new MySQLStore({
          host: 'localhost',
          port: 3306,
          user: 'root',
          password: 'mysql',
          database: 'nodetest'
      })
  }));
  /* 위와 동일 */
  ```
