# File Upload in Express
  - 준비 : 템플릿 파일을 만들고 경로를 지정해 두어야 한다.
  - Connect-multiparty 를 이용하여 파일 업로드 (rename 방법 추가)
  - Multer 를 이용하여 파일 업로드 (생활코딩 참고)

---
## 준비
  ### 1. pug 파일 만들기
  - multiparty/form-data 인코딩 방식을 사용해야만 한다. (file 추가할 때 보통 사용)
  - meta 데이터와 form 데이터를 지정하고, submit 형식으 만든다.

  ```
  doctype html
  html
      head
          meta(charset='utf-8')
      body
          form(action = 'upload' method = 'post' enctype = "multipart/form-data")
              input(type='file' name='userfile')
              input(type='submit')
  ```

  ### 2. GET 을 이용한 라우팅 방식으로 pug 연결
  - 파일 업로드는 get 방식으로 라우터를 연결한다.

  ```javascript
  app.get('/upload', function(req, res){
     res.render('upload');
  });
  ```

---
## connect-multiparty 모듈 이용
  ### 1. connect-multiparty
  - 설치 : `npm install connect-multiparty`
  - 모듈 추출 : `require('connect-multiparty')`
  - 사용 : `app.use(multiparty({ uploadDir: _dirname + '/multipart' }))`
    - uploadDir 속성을 지정하면 파일 업로드 시 지정된 경로로 파일이 업로드 된다.
  - 주의 : 파일 관련정보는 `body` 속성이 아니라 모두 `files` 속성에 존재한다.
  - 참고1> multiple 속성이면 파일이 여러개로 넘어오는데 이 때는 배열 형태로 파일이 전달된다.
  - 참고2> multipart/form-data 처리 모듈은 `busboy`, `formidable` 등 많은 미들웨어가 존재

  ```javascript
  app.post('/', function(req, res){
      console.log(req.body); // 바디를 출력
      console.log(req.files); // file 을 가져온다.

      res.redirct('/') // 특정 html로 리다이렉트
  })
  ```

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

---

## multer 모듈 이용
  ### 1. 모듈 기본
  - [multer 문서](https://github.com/expressjs/multer)
  - 설치 : `npm install multer`
  - 모듈 추출 : `require('multer');`
  - 사용 : `multer({ dest: 'uploads/'})`

  ```javascript
  var multer = require('multer'); // express에 multer모듈 적용 (for 파일업로드)
  var upload = multer({ dest: 'uploads/' })
  // 입력한 파일이 uploads/ 폴더 내에 저장된다.
  // multer라는 모듈이 함수라서 함수에 옵션을 줘서 실행을 시키면, 해당 함수는 미들웨어를 리턴한다.
  ```

  ### 2. post 를 이용하여 전송된 파일 처리
  - 사용자가 post 방식으로 전송한 데이터가 upload 라는 디렉토리를 향하고 있다면, 그 다음 함수를 실행하여 콘트롤러로 연결한다.
  - 미들웨어 `upload.single('avatar')` 는 뒤의 `function(req, res)` 함수가 실행되기 전에 먼저 실행.
  - 미들웨어는 사용자가 전송한 데이터 중에서 만약 파일이 포함되어 있다면, 그 파일을 가공해서 req객체에 file 이라는 프로퍼티를 암시적으로 추가도록 약속되어 있는 함수.
  - `upload.single('avatar')` 의 매개변수 `'avatar'` 는 form을 통해 전송되는 파일의 name속성을 가져야 한다.

  ```javascript
  app.post('/upload', upload.single('userfile'), function(req, res){
      console.log(req.file);
      res.send('Upload : ' + req.file.filename);
  });
  ```

  ### 3. 심화
  - multer 모듈을 통해서 post로 전송된 파일의 저장경로와 파일명 등을 처리하기 위해서는 DiskStorage 엔진이 필요 (dest 대신 입력)
  - 여러 속성이 존재하며 각각 입력할 수 있다.
  - 저장한 파일을 조회하기 위해 static 파일을 사용할 수 있다.
    - 정적인 파일이 위치할 디렉토리의 이름을 선언하고
    - 라우터 path를 설정한다.
    - ex> `localhost/포트/user/dd.png` 와 같이 호출 가능

  ```javascript
  var _storage = multer.diskStorage({
      destination: function(req, file, cb) {
          cb(null, 'uploads/');
      },
      filename: function(req, file, cb){
          /* 파일 리네임 할 수 있음
          if(파일.exits....){
              파일 이름 다시 설정 로직
          }*/
          cb(null, file.originalname);
      }
  });
  var upload = multer({storage: _storage});

  app.use('/user', express.static('uploads'));
  ```

  ### 4. 전체 코드 보기

  ```javascript
  var express = require('express');
  var bodyParser = require('body-parser');
  var app = express();
  var multer = require('multer');
  // var upload = multer({dest: 'uploads/'});
  var _storage = multer.diskStorage({
      destination: function(req, file, cb) {
          cb(null, 'uploads/');
      },
      filename: function(req, file, cb){
          /* 파일 리네임 할 수 있음
          if(파일.exits....){
              파일 이름 다시 설정 로직
          }*/
          cb(null, file.originalname);
      }
  });
  var upload = multer({storage: _storage});

  app.use('/user', express.static('uploads'));
  app.use(bodyParser.urlencoded({ extended: false }))
  app.set('view engine', 'pug');
  app.set('views', './views_file');
  app.locals.pretty = true;

  app.get('/upload', function(req, res){
     res.render('upload');
  });

  app.post('/upload', upload.single('userfile'), function(req, res){
      console.log(req.file);
      res.send('Uploaded : ' + req.file.filename);
  });

  app.listen(9000, function(){
      console.log("server is running...")
  });
  ```
