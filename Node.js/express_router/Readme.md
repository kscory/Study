# Express 에서 라우터 분리하기
  - [모듈화 방법](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/server_bbs) 참고 (모듈화 부분 먼저 볼 것!)
  - 좀 더 쉽게 구조를 파악하기 위해 라우터를 사용해보자 !!
  - 종류는 많지만 express 에서 사용하는 라우터 레벨의 미들웨어를 사용해보자. (공식문서 참고)

---

## Router
  ### 1. 라우터 분리 사용 기본

  - 사용 방법 : `express.Router()` 변수 설정 후 값 설정
  - 아래의 포멧과 같이 작성한다.

  > p2.js

  ```javascript
  var express = require('express');
  var router = express.Router();

  router.get('/directory', function(req, res){
      /* 로직 */
  });

  module.exports = router;
  ```

  ### 2. 변수를 사용해서 넘기기
  - 변수로 넘기기 위해서는 함수를 이용해서 변수로 넘긴 뒤 return 값으로 사용
  - 아래 포멧처럼 이용

  > ex> app을 넘겨서 사용한 경우(좋은 예는 아님.)
  > p1.js

  ```javascript
  module.exports = function(app){
      var express = require('express');
      var router = express.Router();

      router.get('/directory', function(req, res){
          /* 로직 */
      });

      app.get('/directory2/subDirectory', function(req, res) {
          /* 로직 */
      });

      return router;
  };
  ```

  ### 3. 분리한 라우터 사용하기
  - 라우터를 이용하기 위해서는 모듈을 불러온 것 처럼 하면 된다.
  - ex> routes 디렉토리 아래 있는 p1.js 파일을 모듈 형식으로 불러옴
    - 변수를 넘기는 경우는 `( )` 안에 변수를 넣어 준다.
    - `app.use('디렉토리', 모듈)` : 모듈로 들어오는 경로를 라우터에 요청하게 된다.

  ```javascript
  var express = require('express');
  var app = express();

  var p1 = require('./routes/p1')(app); // app 을 routes/p1.js 에 넘긴다.
  var p2 = require('./routes/p2');

  app.use('/p1', p1); // p1 으로 들어오는 경로를 라우터로 요청 !!
  app.use('/p2', p2);

  app.listen(9000, function(){
      console.log('Server is Running...');
  });
  ```
