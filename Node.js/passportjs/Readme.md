# Passprotjs 1
  - [Passprotjs](http://www.passportjs.org/) 참고
  - Passprotjs 란 인증을 통합해서 관리해주는 라이브러리!!
    - (OAuth 등을 같은 포멧으로 사용할 수 있도록 해줌)
  - local(자체 인증체제) 사용방법

---

## local 사용 방법
  ### 1. Passprotjs 기본 사용법
  - 설치 : `npm install passport-local`
  - 모듈 추출 : `var passport = require('passport');`
  - 모듈 추출2 : `var LocalStrategy = require('passport-local').Strategy)`
  - 사용1 : `app.use(passport.initialize());`
  - 사용2 : `app.use(passport.session());` (반드시 세션을 사용하기 위한 use 뒤에 반드시 사용해야 함에 주의)
  - 실행 순서 : LocalStrategy => serializeUser =>  deserializeUser

  ``` javascript
  // 모듈 추출
  var session = require('express-session');
  var passport = require('passport');
  var LocalStrategy = require('passport-local').Strategy;

  // 세션 정의
  app.use(session({
      secret: 'asdnfkjn334#!$i8%#%df',
      resave: false,
      saveUninitialized: true
  }));

  // passport 구동
  app.use(passport.initialize());
  // 반드시 세션을 사용하기 위한 use 뒤에 반드시 사용해야 한다.
  app.use(passport.session());
  ```

  ### 2. form 및 데이터 예시
  - passportjs 에서 passportjs 에 사용할 식별자와 패스워드를 설정한다.
    - ex> email, password

  ```html
  <form action="/login" method="post">
      <div>
          <label>Username:</label>
          <input type="text" name="email"/>
      </div>
      <div>
          <label>Password:</label>
          <input type="password" name="password"/>
      </div>
      <div>
          <input type="submit" value="Log In"/>
      </div>
  </form>
  ```

  > 사용할 객체 예시

  ```javascript
  var users = [
      {
          username:'kyung',
          password:'패스워드!!!',
          salt:'솔트값!!!!',
          displayName:'화면에 표시할 거!!'
      }
  ];
  ```

  ### 3. Passport use 사용
  - LocalStrategy 을 새로 만들어 사용
  - LocalStrategy에서 username 과 password 로 사용할 값을 정해준다.
    - `usernameField` : 다음 콜백 인자에 username 을 던져줄 값을 정함
    - `passwordField` : 다음 콜백 인자에 passowrd 를 던져줄 값을 정함
    - `passReqToCallback` : 증을 수행하는 인증 함수로 HTTP request를 그대로  전달할지 여부를 결정 (true 면 functon에 request 값을 전달, 아니면 빈칸으로 둔다.)
  - done 이라는 인자를 콜백으로 넘겨주며 여기에는 넘길 값, 메세지를 넣을 수 있다.
    - 유저 인증이 되면 `user` 를  아니면 `false` 를 대입
    - 만약 뒤의 라우터에서 `failureFlash` 를 true로 했을 경우 전달할 메세지를 작성한다.
  - 만약 인증에 성공하면 user 라는 객체를 라우터에서 설정한 리다이렉트한 라우터로 넘기며 `request.user` 를 통해 넘긴 값을 가져올 수 있다.
  - 참고 : 공식 문서는 MongoDB 를 사용하므로 이에 대한 예제임...

  ```javascript
  // LocalStrategy use
  passport.use(new LocalStrategy({
          usernameField: 'email',
          passwordField: 'password',
          passReqToCallback: true
      },
      function(req, username, password, done){
          var uname = username;
          var pwd = password;
          for(var i=0 ; i<users.length; i++){
              var user = users[i];
              if(uname === user.username){
                  return hasher({password:pwd, salt:user.salt}, function(err, pass, salt, hash){
                      // done 함수를 이용해서 맞으면 user 를 아니면 false 를 반환시킨다.
                      if(hash === user.password){
                          console.log(user.password);
                          done(null, user);
                      } else {
                          done(null, false);
                          // cf>
                          // 아래와 fauilureFlash 가 true 이면 아래와 같이 메세지를 전달해서 보여줄 수 있다.
                          // done(null, false, {message: 'Incorrect username or password'});
                      }
                  });
              }
          }
          done(null, false);
      }
  ));
  ```

  ### 4. Route
    - pasasport 에서 `authenticate` 란 미들웨어를 통해 콜백함수를 쓸 수 있다.
    - `local` : 로컬 전략을 사용하겠다. ( 따로 지정.. )
    - `successRedirect` : 성공 시 리다이렉트 시킴
    - `failureRedirect` : 실패 시 리다이렉트 시킴
    - `failureFlash` : 사용자에게 한번만 "인증에 실패했습니다" 와 같은 메세지를 보낼 수 있다.
  - 그 뒤에 function(req, res){ req.session.save(function(){ }) } 과 같이 콜백 사용 가능


  ```javascript
  // pasasport 에서 미들웨어를 통해 콜백함수 이용
  app.post('/auth/login', passport.authenticate(
      'local',
      {
          successRedirect: '/welcome',
          failureRedirect: '/auth/login',
          failureFlash: false,
      })
  );

  /* 아래와 같이 콜백함수를 인자로 추가하여 로그인의 경우에도 세션이 저장된 뒤 리다이렉트 할 수 있다.
  app.post('/auth/login', passport.authenticate(
      'local',
      {
          successRedirect: '/welcome',
          failureRedirect: '/auth/login',
          failureFlash: false,
      }),
      function(req,res){
          req.session.save(function() {
              res.redirect('/welcome');
          })
      }
  );
  */
  ```

  ### 5. Serialize
  - `serializeUser` : use 에서 done 에 false 를 넘기지 않았다면 이 부분을 실행하며 done에서 넘긴 두번째 인자(user) 를 받을 수 있다.
    - 그 후 session 에 저장한다.
  - `deserializeUser` : 션에 저장되어 있는 유저가 접근하면 이를 실행시킨다.
    - 저장도니 사용자의 식별자가 id로 넘어온다.

  ```javascript
  // use의 done에서 보낸 user의 인자를 받을 수 있다.
  passport.serializeUser(function(user, done) {
      done(null, user.username); // 이 done은 use의 done 과 다르므로 주의
  });

  // 세션에 저장되어 있는 유저가 접근하면 이를 실행시키게 되어 있다.
  // 저장된 사용자의 식별자가 id 로 넘어온다
  passport.deserializeUser(function(id, done) {
      for(var i=0 ; i<users.length; i++){
          var user = users[i];
          if(user.username === id) {
              return done(null, user);
          }
      }
      done(null, false);
  });
  ```

  ### 6. login 과 logout
  - passport 에서 정의한 함수를 이용하여 login 과 logout을 할 수 있다.

  ```javascript
  app.get('/auth/logout', function(req,res){
      // req.logout 을 통해 logout 을 하고 이를 save
      req.logout();
      req.session.save(function(){
          res.redirect('/welcome');
      });
  });

  app.post('/auth/register', function(req, res){
      hasher({password:req.body.password}, function(err, pass, salt, hash){
          var user = {
              username:req.body.username,
              password:hash,
              salt:salt,
              displayName:req.body.displayName
          };
          users.push(user);
          // req.login 을 통해 login 을 하고 이를 save
          req.login(user, function(err){
              req.session.save(function(){
                  res.redirect('/welcome');
              })
          });
      });
  });
  ```

  ### 7. isAuthenticated()
  - 현재 사용자가 인증되었는지 확인할 수 있다.
  - 이를 통해 인증이 안되었다면 login 으로 보내고 인증이 되어 있다면 원래 메소드로 실행시킬 수 있다.

  ```javascript
  var checkAuth = function(req, res, next){
      if (req.isAuthenticated())
          return next();
      res.redirect('/auth/login');
  };

  // welcome 리다이렉트
  app.get('/welcome', checkAuth , function(req, res){
      if(req.user && req.user.displayName) {
          res.send(`
              <h1>Hello, ${req.user.displayName}</h1>
              <a href="/auth/logout">logout</a>
      `);
      } else {
          res.send('Error');
      }
  });
  ```
