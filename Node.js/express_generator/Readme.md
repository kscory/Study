# Express-generator
  - Express-generator 기본 (설치, 실행, 프로젝트 구성 설명)
  - package.json
  - ./bin/www 파일
  - app.js 파일의 구성
  - 실행 환경 설정

---

## Express-generator 기본
  ### 1. express-generator 기본 (express 프레임워크)
  - express-generator 는 express 모듈을 이용한 프로젝트의 기본 형식을 만들어준다.
  - 매우 light 하며 구현해야 할 것은 많다.
  - 설치 : `npm install -g express-generator` (혹은 뒤에 \@버전 을 사용할 수 있다, 커멘드 사용하므로 글로벌 설치 필요)
  - 프로젝트 생성 : `express [폴더명] [옵션]`
    - 옵션 종류
    - `-h, --help` : Express generator 도움말 본다
    - `-V, --version` : Express generator 버전을 확인
    - `-e, --ejs, --hbs` : 템플릿 렌더링 모듈을 변경(ejs, handlebars engine, 기본은 jade)
    - `-H, --hogan` : hogan.js 엔진 모듈을 사용
    - `-c, --css <engine>, --git` : 스타일시트 엔진을 설정(<sass, less, stylus, compass>, 기본은 plain css)
    - `--git` : gitignore 파일을 함께 생성
    - `-f, --force` : 해당 폴더가 비어있지 않아도 강제로 작업
  - 필요한 기본 모듈 설치 : `npm install`
  - express 프로젝트 실행 : `npm start`
  - 참고 - 디버그모드로 실행
    - window : `SET DEBUG=[프로젝트명]:* && npm start`
    - MacOS or Linux : `$ DEBUG=[프로젝트명]:* && npm start`

  ### 2. 프로젝트 구성
  - `bin 폴더` : 프로그램의 실행과 관련된 파일이 들어있는 폴더
  - `public 폴더` : static 미들웨어를 사용해 웹서버에 올라가는 폴더, 이 폴더에 js 파일, css 파일, 그리 파일 등 리소스 파일 생성
  - `routes 폴더` : 페이지 라우트와 관련된 모듈, index.js 파일과 routes 파일 존재
  - `view 폴더` : ejs 또는 jade 파일과 같은 템플릿 파일을 저장
  - `app.js` : 프로젝트에서 중심이 되는 파일
  - `package.json` : 현재 프로젝트와 관련된 정보와 모듈을 설치하는 데 필요한 내용을 담고 있는 파일

  ### 2. package.json 파일
  - `name`, `version`, `private` : 이름, 버전, 배포여부에 대한 정보
  - `scripts` : 명령을 실행시키도록 한다. (즉, `start` 를 하면 `bin` 폴더 내부의 `www` 파일이 실행되도록 한다.)
  - `dependencies` : 설치한 모듈들에 대한 정보를 보여준다. ([Express Start & Middleware](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/express_start_middleware) 에서 거의 설명됨)
    - [debug](https://www.npmjs.com/package/debug) : 디버그를 위한 모듈
    - [serve-favicon](https://github.com/expressjs/serve-favicon?_ga=1.119961741.469852810.1452258638) : 파비콘을 설정해 준다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/express_generator/picture/package.png)

  ### 3. ./bin/www
  - 모듈 설정
    - `app` : app.js 파일을 가져옴 (각종 express 설정이 있다)
    - `debug` : debug를 위하여 선언
    - `http` : http 서버를 열기 위해 필요
  - 포트 설정 : 환경변수에 PORT 이름으로 저장되엉 있는 포트를 열고 없다면 3000번을 연다.

  ```javascript
  var app = require('../app');
  var debug = require('debug')('generator-test:server');
  var http = require('http');

  var port = normalizePort(process.env.PORT || '3000');
  app.set('port', port);
  ```

---

## 2. app.js 파일의 구성
  ### 1. 서버 생성
  - 모듈을 추출(require)하고 서버 객체를 생성한다.

  ```javascript
  // 외부 모듈을 추출
  var createError = require('http-errors');
  var express = require('express');
  var path = require('path');
  var cookieParser = require('cookie-parser');
  var logger = require('morgan');

  // 사용자 정의 모듈을 추출
  var indexRouter = require('./routes/index');
  var usersRouter = require('./routes/users');

  // 서버를 생성
  var app = express();
  ```

  ### 2. 미들웨어 설정
  - 미들웨어를 설정하는 부분 (use / set 등 사용)
  - `set` : express 의 설정 옵션을 지정하는 메서드
    - case sensitive routing : 페이지 라우트를 할 때 대소문자 구분을 지정
    - views : 뷰 폴더를 지정
    - view engine : 뷰 엔진을 지정
    - 그 외 여러가지 존재..
  - `use` : 사용할 미들웨어를 설정하는 메서드
  - 라우터 미들웨어 : url 경로를 지정하고, 사용자 정의 모듈에서 추출한 값을 넣는다.
    - ex> '/' 로 호출하면 indexRouter 에서 지정한 경로의 페이지를 사용자에게 제공

  ```javascript
  // 서버를 설정
  app.set('views', path.join(__dirname, 'views'));
  app.set('view engine', 'jade');
  app.set('case sensitive routes', true); // 대소문자를 구분하겠다

  // 미들웨어를 설정
  app.use(logger('dev'));
  app.use(express.json());
  app.use(express.urlencoded({ extended: false }));
  app.use(cookieParser());
  app.use(express.static(path.join(__dirname, 'public')));

  // 라우터 미들웨어 설정
  app.use('/', indexRouter);
  app.use('/users', usersRouter);
  ```

  ### 3. 에러 핸들링 및 모듈화
  - 에러를 처리하는 부분
  - 이 파일을 모듈화 시킨다.

  ```javascript
  // 에러 핸들링
  app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error');
  });

  // 모듈화
  module.exports = app;
  ```

---

## 실행 환경 설정
  ### 1. development 와 production
  - express 프레임워크는 development 와 production 이라는 2가지 실행 환경을 제공한다.
  - 위 코드에서 `res.locals.error = req.app.get('env') === 'development' ? err : {};` 에서 development 모드면 err 를 사용하는 것을 알 수 있다.
  - 둘의 차이

  내용 | development 모드 | production 모드
  :----: | :----: | :----:
  오류 출력 | 출력 | 출력 안 함
  view cache | 사용 안 함 | 사용
  디버그 모드 | 설정하면 사용 | 사용 불가

  ### 2. 실행 환경 설정 변경
  - development 모드 (디폴트)
    - `export NODE_ENV=development`
  - production 모드
    - 윈도우 : `SET NODE_ENV=production`
    - MacOS or Linux : `export NODE_ENV=production`
  - 어떤 모드인지 확인 : `app.get('env');`
  - 그러나 프로젝트를 마지막에 배포할때는 아래와 같이 코드를 지정하는 것이 낫다.

  ```javascript
  // 서버 객체를 생성
  var app = express();

  // 서버 환경을 설정
  app.settings.env = 'production';
  ```

---
## 참고
  ### 1. 설치 후 주의사항
  - 처음 설치하면 jade 로 설치되므로 이를 pug 로 변경해야 한다.
  - 그 외에도 deprecated 되지 않았는지 확인한다.
