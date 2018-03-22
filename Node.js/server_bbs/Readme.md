# MongoDB를 이용하여 간단한 게시판 구축
  - module화 방법 및 사용법
  - 간단한 게시판 서버 구성
    - server.js (서버 연결)
    - route (형식을 파악해서 로직 요청)
    - controller (데이터를 컨트롤한 후 dao에 요청)
    - dao (CRUD 구현)
  - [참고_RemoteBbs](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/RemoteBbs)

---

## module화 방법 및 사용법 & MongoDB 테이블 사용 방법
  ### 1. module화 방법
  - 보통 디렉토리를 따로 만들어 작성한다.
  - 만약 이름이 index 라면 디렉토리만 호출하면 파일을 호출한다. ( gateway를 index로 준다.)
  - 외부에서 이용할 변수 및 함수는 `exports`를 붙여준다. (대부분 동일하지만 코틀린은 반대.. 참고)
  - module 화 할 때는 `module.exports(값)` 혹은 `module.exports = 값` 으로 보통 표시한다.

  ```javascript
  // public
  exports.a=157;
  exports.sum = function(a,b){
  	return a+b;
  }
  // private
  var b = 137;
  function sum(a, b){
  	return a+b;
  }
  // 보통 모듈화를 시키면 아래와 같이 모듈이라고 표시함
  module.exports(b);
  ```

  ### 2. module 사용방법
  - `require` 을 통해 module을 선언해준다. (module로 따로 작성하면 `./`와 같이 경로를 지정해주어야 한다. - 참고로 "."의 개수는 상위디렉토리로 이동)

  ```javascript
  var module = require("./module"); // 경로 조심할 것

  var x = 300;
  var y = 750;
  console.log(module.sum(x,y));
  ```

  ### 3. MongoDB의 table 사용 방법
  - 사용방법1 : new로 선언해서 사용(형식을 index에 만든다.)
  - 사용방법2 : 테이블을 직접 넘긴다.

  ```javascript
  var bbs = require("./db");

  // 사용방법
  //방법 1. new해서 사용하는 방법 (형식을 index에 만들어서 사용)
  var bbsTable = new bbs.Bbs();

  // 방법 2. 테이블을 직접 넘기는 방법
  var table = {
  	no : 147,
  	title : "제목",
  	content : "내용",
  	date : "날짜",
  	user_id : "root"
  };  
  ```

---

## 간단한 게시판 서버 구성
  ### 0. 데이터베이스 구조&쿼리 설정 및 게시판 서버 설계
  - 데이터베이스 구조 설정

  ```javascript
  bbs = {
      no : 12,
      title : "제목",
      content : "내용",
      date : "2017/10/26 11:21:30"
      user_id : "root"
  }
  // 외부에서 이용시 exports를 사용 현재는 위와 같이 사용
  exports.bbs = function(){
      this.no = -1,;
      this.title = "";
      this.content = "";
      this.date = "";
      this.user_id = "";
  }
  search = {
      // all = 전체검색, no = 글 한개 검색, title = "제목검색"
      type : "all",
      no : 137;
      title : "제목검색"
      content : "내용검색",
      date = "날짜검색",
      user_id = "사용자아이디로 검색"
  }
  ```

  - 게시판 서버 설계

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/server_bbs/picture/bbs.png)

  ### 1. server.js
  - 외부와 통신을 하며 서버를 생성만 해줌
  - route 모듈을 이용

  ```javascript
  var http = require("http");
  var route = require("./a_route");

  var server = http.createServer(function( request, response){
  	route.process(request,response);
  });

  server.listen(8090, function(){
  	console.log("server is running...")
  });
  ```

  ### 2. a_route/index.js
  - 디렉토리를 한번 빠져나가서 db로 들어감 따라서 "`../`" 사용한다.
  - GET / POST, PUT, DELETE 를 구분하여 controller에 전달
    - get의 경우는 url에서, 나머지는 body 에서 데이터를 가져온다.
  - 형식이 틀렸을 경우 end

  ```javascript
  var u = require("url");
  var qs = require("querystring");
  var bbs = require("../b_controller/bbs");

  exports.process = function(request, response){
    var url = u.parse(request.url);
    var method = request.method.toLowerCase()
    var cmds = url.pathname.split("/");
    if(cmds[1] == "bbs"){
      if(method == "get"){
        var query = qs.parse(url.query);
        console.log("확인===================");
        console.log(query);
        bbs.read(request, response, query);
      } else{
        // get 이외의 method는 body 데이터를 가져온다.
        var body = "";
        request.on('data',function(data){
          body += data;
        });
        // 데이터 로딩이 완료되면 각 method로 분기
        request.on('end',function(){
          var bbs_body = JSON.parse(body);
          if(method == "post"){
            bbs.create(request, response, bbs_body);
          } else if(method == "put"){
            bbs.update(request, response, bbs_body);
          } else if(method == "delete"){
            bbs.delete(request, response, bbs_body);
          }
        });

      }
    } else{
      response.end("page not found!");
    }
  };
  ```

  ### 3. b_controller/bbs.js
    - query를 받아서 처리한 후 dao에 전달
    - dao와 연동되는 CRUD 함수 정의
    - object(_id)를 서치할때 이용하는 방법
      - a. ObjectID 변수 선언 `var ObjectID = require("mongodb").Object;`
      - b. `query = {_id : -1};` id 사용 정의
      - c. `query._id = ObjectID(search._id);` object 값을 변경

  ```javascript
  var dao = require("../c_dao/bbs");
  var ObjectID = require("mongodb").Object;

  exports.read = function(request, response, search){
    var query = {};
    if(search.type === "all"){
      query = {page : parseInt(search.page)};
    } else if(search.type === "no"){
      // query = {no : -1};
      // query.no = parseInt(search.no);

      // id를 가져올 경우 object이기 때문에 다음과 같이 한다.
      query = {_id : -1};
      query._id = ObjectID(search._id);
    } else if(search.type="title"){

    } //..... 다른 서치 쿼리 가능

    console.log(query);

    dao.read(query, function(dataset){
      console.log(dataset);
      var result = {
        code : 200,
        msg : "정상처리",
        data : dataset
      }
      response.end(JSON.stringify(result));
    });
  };

  exports.create = function(request, response, bbs){
    dao.create(bbs,function(result_code){
      var result = {
        code : result_code,
        msg : "입력완료"
      };

      response.end(JSON.stringify(result));
    });
  };

  exports.update = function(request, response, bbs){
    /* 생략 */
  };

  exports.delete = function(request, response, bbs){
    /* 생략 */
  };
  ```



  ### 4. c_data/bbs.js
  - MongoDB에 대한 CRUD 함수 정의
  - 보통 디렉토리를 테이블 이름이랑 동일하게 만든다.
  - dbname, dburl 등을 변수에 미리 정해놓고 이용한다.
  - sort 방법 / projection 조건 등 지정
  - upsert를 이용해 update

  ```javascript
  var mongo = require("mongodb").MongoClient;
  var dbname = "bbsdb";
  var dburl = "mongodb://localhost:27017/"+dbname;
  var table = "bbs";
  var page_count = 20;

  exports.create = function(bbs, callback){
    mongo.connect(dburl, function(error, db){

      db.collection(table).insert(bbs, function(err, inserted){
        if(error){
          callback(400);
        } else{
          callback(200);
        }
        db.close();
      });
    });
  }

  exports.read = function(search,callback){
    mongo.connect(dburl, function(error, db){
      // var projection = {title:1,} // title만 가져온다.
      // var projection = {title:1,content:0} // 1과 0이 함께 있을 수 없다., 이는 true/false값
      // 소팅 조건만 담아서 뒤에 sort만 붙이면 소팅이 된다.
      var sort = {
        _id : -1 // -1: 오름차순 , 1: 내림차순
      }; // 소팅조건
      // 집합
      // skip - 카운트를 시작할 index의 위치
      // limit - 가져올 개수
      var start = (search.page - 1) * page_count;
      // 사용하지 않는 검색 컬럼은 삭제처리
      delete search.page;
      console.log(start);
      var cursor = db.collection(table)
              .find(search)
              .sort(sort)
              .skip(start)
              .limit(page_count);

      cursor.toArray(function(error, documents){
        if(error){

        } else {
          // document 처리
          callback(documents);
        }
      });
      db.close();
    });
  }

  exports.readOne = function(search,callback){
    mongo.connect(dburl, function(error, db){
      var query = {};
      if(search.type === "all"){
        query = {};
      } else if(search.type === "no"){
        query = {no : search.no};
      }
      var cursor = db.collection(table).find(query);

      cursor.toArray(function(error, documents){
        if(error){

        } else {
          // document 처리
          callback(documents);
        }
      });
      db.close();
    });
  }

  exports.update = function(bbs){
    mongo.connect(dburl, function(error, db){
      // 1. 수정대상 쿼리
      var query = {_id: -1};
      query._id = bbs._id;
      // 2. 데이터 수정명령 var operator = {title:"수정된 타이틀",date:"2018-11-15 11:13:23"};

      //딜리트 하는 일반적인 =====================
      // var operator = {
      // 	no : 1,
      // 	title = "";
      // };
      // operator.no = bbs.no;
      // operator.title = bbs.title;
      //==========================

      // 특정 대상을 딜리트
      var operator = bbs;
      delete operator._id;
      // 3. 수정옵션 - upsert true 일때 데이터가 없으면 insert
      var option = {upsert:true};

      db.collection(table).update(query,operator,option, function(err, upserted){
        if(err){

        } else{
          // 정상 처리
        }
      });
      db.close();
    });
  }

  exports.delete = function(bbs){
    mongo.connect(dburl, function(error, db){
      //1. 삭제대상 쿼리
      var query = {no : -1};
      query.no = bbs.no;

      db.collection(table).remove(query, function(err, removed){
        if(err){

        } else{
          // 정상 삭제
        }
        db.close();
      });
    });
  }
  ```

---

## 참고
### 1. 데이터를 배열로 한번에 넣는 방법
  - db.collection('테이블명').insertMany 사용

  > test.js

  ```javascript
  var mongo  = require("mongodb").MongoClient;
  var dbname = "bbsdb";
  var dburl = "mongodb://localhost:27017/"+dbname;
  var table = "bbs";

  //insert 를 10만개 처리
  var randomTitle =  [ "STRAT", "ETH", "TIX", "VTC", "NEO", "TRIG", "XLM","ETC","BCH","UBQ","QTUM","XRP","XMR" ];
  var users=  [ "Jae", "hong", "go", "michael", "jordan","curry","zico","gdragon","dokki","young-b"];

  mongo.connect(dburl,function(error, db){
    for(i=0 ; i<100 ; i++){
      var array = [];
      for(j=0 ; j<1000 ; j++){
        var bbs = {
          title :randomTitle[Math.floor(Math.random()*100)%13]+"-"+randomTitle[Math.floor(Math.random()*100)%13],		// 랜덤한 텍스트를 조합해서 입력
          content : "내용입니다",
          user_id : users[j%10],	// 10개를 미리 정해놓고 random 입력
          date : new Date()+""
        }
        array[j] = bbs;
      }
      // multitple insert
      db.collection(table).insertMany(array,function(){
        if(error){

        } else{

        }		
      });
    }
    // single insert
    // db.collection(table).insert(bbs,function(){
    // 	if(error){
    // 		callback(400);
    // 	} else{
    // 		callback(200);
    // 	}
    // });
  });
  ```

  ### 2. 파일 업로드 방법
  - `npm install formidable` 로 formidable 라이브러리 설치
  - `var form = new formidable.IncomingForm();` : 파일을 가져오기 위해 이용
  - `fs.renameSync` : 동기화로 작용(콜백이 제거된다.)

  > server_file.js

  ```javascript
  var http = require("http");
  // 파일 업로드하기
  var formidable =require("formidable");
  var fs = require("fs");

  var server = http.createServer(function(request, response){
    if(request.url == "/upload"){
      var form = new formidable.IncomingForm();
      // 여러 파일일 경우 true로 설정
      form.multiples = true;
      form.parse(request, function(error, names, files){ // 에러랑, 필드 이름이랑 파일이 넘어온다. => 이를 파싱, 임시 폴더에 저장됨
        if(error){
          console.log(error);
        } else{
          for(i in files){
            var oldpath = files[i].path;
            // C:/Temp/upload/ 에 파일 저장
            var realpath = "C:/Temp/upload/"+files[i].name;

            renameFile(oldpath,realpath,0);
          }
          response.end("upload completed!");
        }

      });

      // request.on(end,..)랑 동일
      /*
      form.on("end",function(error, names, files){
        console.log(names);
        console.log(files);
        response.end("");
      });
      */
    } else{
      response.end("404 not found");
    }
  });

  // 파일 중복 재귀처리
  function renameFile(oldpath, realpath, index){
    // fs.renameSync(oldpath, realpath); // suffix에 Sync 를 붙이면 동기가 된다.
    // fs.rename(oldpath,realpath,function(){}); // 비동기로 동작함
    if(newFile.exists)
      renameFile(oldpath, realpath+"_"+index, index++);
    else
      fs.renameSync(oldpath,realpath);
  }

  server.listen(8090,function(){
    console.log("server is running...");
  });
  ```
