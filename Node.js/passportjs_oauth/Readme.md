# Passprotjs 2
  - [Passprotjs 1](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/passprotjs) 과 이어짐...
  - 타사 인증 방법
    - KaKao
    - Naver
    - Facebook

---

## 타사인증 사용 방법
  ### 1. 개발자 등록
  - 개발자 등록은 업체마다 다르므로 생각할 것
  - 주의 : secret_id 는 절대 잊어버리면 안됨 !!

  ### 2. 타사인증 passport 설치 및 모듈 추출
  - 설치
    - 카카오 : `npm install passport-kakao`
    - 네이버 : `npm install passport-naver`
    - 페이스북 : `npm install passport-facebook`
  - 모듈 추출
    - 카카오 : `require('passport-kakao').Strategy;`
    - 네이버 : `require('passport-naver').Strategy;`
    - 페이스북 : `require('passport-facebook').Strategy;`

  ### 3. 개발자 등록 정보 오브젝트 생성
  - 각 객체마다 정보를 만든다.
    - `client_id` : 개발자 등록할 때 사용한 client_id
    - `secret_id` : 개발자 등록할 때 사용한 sercret_id
    - `callback_url` : 타사에서 인증 처리후 우리에게 보내는 url 로 정해져 있으므로 똑같이 쓸 것 (라우터를 2개 설정해야 한다.)

  ```javascript
  var developerInfo = {
    'secret' :  '',
    'db_info': {
      local: { // localhost
      ...
      },
      real: { // real
      ...
      },
      dev: { // dev
      ...
      }
    },
    'federation' : {
      'naver' : {
        'client_id' : '',
        'secret_id' : '',
        'callback_url' : '/auth/login/naver/callback'
      },
      'facebook' : {
        'client_id' : '',
        'secret_id' : '',
        'callback_url' : '/auth/login/facebook/callback'
      },
      'kakao' : {
        'client_id' : '',
        'callback_url' : '/auth/login/kakao/callback'
      }
    }
  };
  ```

  ### 4. Passport use 사용
  - LocalStratgy 와 비슷하게 사용할 수 있다. (ex> new KakaoStrategy)
  - `done(null, 객체)` 를 통해 이전 설명에서 말한 Serialized 에 정의한 함수로 객체를 넘긴 뒤 한번 더 자체인증하게 된다.
  - 페이스북의 경우 `profileFields` 에 원하는 값을 지정하여 가져와야 한다!! (공식 문서에 없으므로 참고)

  > 카카오

  ```javascript
  passport.use(new KakaoStrategy({
      clientID: developerInfo.federation.kakao.client_id,
      callbackURL: developerInfo.federation.kakao.callback_url
    },
    function (accessToken, refreshToken, profile, done) {
      var kakapProfile = profile._json; // 프로필에 json 형식으로 들어옴

      /* 로그인 로직 혹은 함수 (user에 객체를 넣는다.) */

      return done(null, user); // 비동기므로 아마 로그인 로직에 들어갈 듯...
    }
  ));
  ```

  > 네이버

  ```javascript
  var secret_config = require('../commons/secret');
  passport.use(new NaverStrategy({
      clientID: developerInfo.federation.naver.client_id,
      clientSecret: developerInfo.federation.naver.secret_id,
      callbackURL: developerInfo.federation.naver.callback_url
    },
    function (accessToken, refreshToken, profile, done) {
      var _profile = profile._json;// 프로필에 json 형식으로 들어옴

      /* 로그인 로직 혹은 함수 (user에 객체를 넣는다.) */

      return done(null, user); // 비동기므로 아마 로그인 로직에 들어갈 듯...
    }
  ));
  ```

  > 페이스북

  ```javascript
  passport.use(new FacebookStrategy({
      clientID: developerInfo.federation.facebook.client_id,
      clientSecret: developerInfo.federation.facebook.secret_id,
      callbackURL: developerInfo.federation.facebook.callback_url,
      profileFields: ['id', 'email', 'gender', 'link', 'locale', 'name', 'timezone', 'updated_time', 'verified', 'displayName']
    }, function (accessToken, refreshToken, profile, done) {
      var _profile = profile._json; // 프로필에 json 형식으로 들어옴

      /* 로그인 로직 혹은 함수 (user에 객체를 넣는다.) */

      return done(null, user); // 비동기므로 아마 로그인 로직에 들어갈 듯...
    }
  ));
  ```

  ### 5. 라우터
  - 라우터는 2개를 설정해야 한다.
    - client 가 요청하는 url 로 연결
    - 타사에서 인증 후 결과를 전송해주는 url (콜백으로 받는다.)
  - 로그인이 성공하면 `successRedirect` 에 설정된 곳으로 가고 실패하면 `failureRedirect` 에 설정된 곳으로 보낸다.

  ```javascript
  // naver 로그인
  app.get('/auth/login/naver',
    passport.authenticate('naver')
  );
  // naver 로그인 연동 콜백
  app.get('/auth/login/naver/callback',
    passport.authenticate('naver', {
      successRedirect: '/welcome',
      failureRedirect: '/auth/login'
    })
  );

  // kakao 로그인
  app.get('/auth/login/kakao',
    passport.authenticate('kakao')
  );
  // kakao 로그인 연동 콜백
  app.get('/auth/login/kakao/callback',
    passport.authenticate('kakao', {
      successRedirect: '/welcome',
      failureRedirect: '/auth/login'
    })
  );

  // facebook 로그인
  app.get('/auth/login/facebook',
    passport.authenticate('facebook')
  );
  // facebook 로그인 연동 콜백
  app.get('/auth/login/facebook/callback',
    passport.authenticate('facebook', {
      successRedirect: '/welcome',
      failureRedirect: '/auth/login'
    })
  );
  ```

  ### 6. 주의점!!
  - 서버를 끈 뒤 키게 될 때 세션에 의해 로그인 정보가 남아 있게 되어 오류가 뜰 수 있으므로 이를 처리해야 한다.
  - 로그인 시 id 와 같이 인증해야 될 부분이 중복값이 될 수 있으므로 어떻게 처리할 지 생각해야 한다.
    - ex> 타사 아이디로 회원가입을 진행하고 자신의 계정에 대해서 아이디, 패스워드를 한 번 더 입력받게 하는 경우도 많다
  - 만약 페이스북 인증 시 url 이 오류난다면... [페이스북 url 처리](https://jwkcp.github.io/2017/10/19/social-login-facebook-strictmode/) 를 참고할 것
  - 가끔 redirect 오류가 뜨는데 아래처럼 콜백을 이용해 리다이렉트 시키면 해결됨

  > 세션 저장 후 리다이렉트!!

  ```javascript
  // facebook 로그인 연동 콜백
  app.get('/auth/login/facebook/callback',
      passport.authenticate('facebook', {
          // successRedirect: '/welcome',
          failureRedirect: '/auth/login'
      }),
      function(req,res){
          req.session.save(function(){
              res.redirect('/welcome')
          })
      }
  );
  ```
