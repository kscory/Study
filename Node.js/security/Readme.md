# Security
  - 암호화??
  - md5를 이용한 암호화
  - sha256을 이용한 암호화
  - salt 주기
  - PBKDF2 이용하여 암호화

---

## Security
  ### 1. 암호화??
  - 만약 비밀번호가 탈취되면 보안상의 사용자 계정이 해킹당할 수 있기 때문에 이를 암호화 한다.
  - 만약 비밀번호를 암호화하지 않으면 소송당하므로... 주의....
  - 복호화가 불가능하면 단방향, 둘다 되면 양방향이라 부른다.
  - 보통 해시값들이 나온다!!

  ### 2. md5를 이용한 암호화
  - MD5 를 이용해서 암호화 (현재 사용X 단방향이다..)
  - 설치 : `npm install md5`
  - 모듈 추출 : `md5 = require('md5')`
  - 사용 : `md5('내용')`
    - ex> md5('javascript') =>  'de9b9ed78d7e2e1dceeffee780e2f919'

  ### SHA 를 이용한
  - 현재 사용하는 암호화 방법
  - 설치 : `npm install sha256` (256 512 등 사용 가능)
  - 모듈 추출 : `require('sha256')`
  - 사용 : `sha256('내용')`

  ### 3. salt
  - 특정 salt 값을 지정한후 비밀번호에 더해서 사용하면 어려운 해시값을 얻을 수 있다.
  - 검증할 때도 반드시 붙여주어야 한다.
  - 사용자마다 다른 salt 를 지정해서 사용해서 모든 사람마다 패스워드의 해시를 다르게 저장해야 한다.
  - cf> keystreching (여러번 암호화 시킨다..)

  ```javascript
  var md5 = require('md5');
  var sha256 = require('sha256');

  var user1Salt = '#@#F4rg1$%fs@@';
  var user1Pwd = '111';

  var user2Salt = '#@$asd1%f@&^';
  var user2Pwd = '111';

  // var passSave1 = md5(password + salt);
  var passSave1 = sha256(password + salt);
  // var passSave2 = md5(password + salt);
  var passSave2 = sha256(password + salt);
  ```

  ### 4. PBKDF2
  - 암호화를 도와주는 알고리즘
  - [pbkdf2-password](https://www.npmjs.com/package/pbkdf2-password) 모듈 사용
  - 설치 : `npm install pbkdf2-password`
  - 설명
    - `hasher` 를 불러오고 비밀번호를 지정
    - 콜백함수에는 에러, 패스워드, salt, 해시 가 인자로 들어가게 된다.
    - 이 때 salt 는 매번 바뀌는데 이를 모듈에서 자동으로 생성해준 후 해시화시켜준다.

  ```javascript
  // assert 는 내장되어 있는 테스트 모듈
  var bkfd2Password = require("pbkdf2-password");
  var hasher = bkfd2Password();
  var assert = require("assert");
  var opts = {
    password: "helloworld"
  };
  /* salt 를 직접 정해줄 수 있다.
  var opts = {
    password: "helloworld"
    salt: "salt!@SD"
  }
  */

  hasher(opts, function(err, pass, salt, hash) {
    opts.salt = salt;
    hasher(opts, function(err, pass, salt, hash2) {
      assert.deepEqual(hash2, hash);

      // password mismatch
      opts.password = "aaa";
      hasher(opts, function(err, pass, salt, hash2) {
        assert.notDeepEqual(hash2, hash);
        console.log("OK");
      });
    });
  });
  ```
