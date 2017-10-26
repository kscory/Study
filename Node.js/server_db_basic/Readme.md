# MongoDB를 이용하여 간단한 login server 구축
  - 기본적인 구현
  - Json 형식으로 구현
  - [Mongo]
  - [안드로이드 loginActivity 참고](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/LoginHttp)
  - [MongoDB 참고](https://github.com/Lee-KyungSeok/Study/tree/master/MongoDB)

---

## MongoDB 설치
- `npm install mongodb` 를 이용하여 디렉토리에 mongodb 모듈 설치

![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/server_db_basic/picture/mongoinstall.png)

---

## 서버 구현
  ### 1. 기본 형식으로 구현
  - post 형식으로 데이터가 넘어온 경우 데이터를 쌓은 후 결과처리
  - mongo db 주소 구조 = `프로토콜://주소:포트/데이터베이스 이름` -> db변수에 전달 (포트가 27017로 정해져 있음)
  - `db.collection('테이블명').명령어();` 의 형식으로 mongodb 사용
    - `db.collection('user').find('쿼리');` : user 테이블에서 쿼리 조건의 데이터를 불러옴 (javascript object는 json object랑 1:1로 매핑되므로 query만 넘겨준다.)
    - `db.collection('user').insert({name:'hong',age:18});` : user 테이블에 조건의 값을 생성
  - `cursor.toArray(function(err, dataset)};` : cursor의 data들을 dataset에 배열 형태로 저장한다.

  > server_db.js

  ```javascript
  var http = require("http");
  var u = require("url");
  var qs = require("querystring");
  var mongo = require("mongodb").MongoClient;

  var server = http.createServer(function(request, response){
  	var url = u.parse(request.url);
  	var cmds = url.pathname.split("/");

  	if(cmds[1] == "signin"){
  		// post로 넘어온 데이터를 읽는다.
  		var postdata = "";
  		request.on('data', function(data){
  			postdata += data;
  		});

  		// 모두 읽으면 변수와 값을 분리해서 처리한다.
  		request.on('end', function(){
  			// postdata = id=xxx&pw=12345; 형태로 넘어옴
  			var query = qs.parse(postdata);

  			if(!query.id || !query.pw){
  				response.end("id or pw is wrong!!");
  			} else{
  				mongo.connect("mongodb://localhost:27017/testdb",function(error, db){
  					if(error){
  						response.write(error);
  						response.end("");
  					} else{

  						var cursor = db.collection('user').find(query);
  						var result="FAIL";
  						cursor.toArray(function(err, dataset){
  							// 쿼리에 맞는 조건값 존재할 시 로그인 성공
  							if(dataset.length>0){
  								result="OK";
  							}
  							response.write(result);
  							response.end("");
  						});
  					}
  				});
  			}
  		});
  	} else {
  		response.end("page is not found!!");
  	}
  });

  server.listen(9000, function(){
  	console.log("server is running...");
  });
  ```

  ### 2. Json 형식으로 구현
  - Json 형식의 경우 자바스크립트의 오브젝트 형식과 같다.
  - `JSON.parse(postdata)` : json 형태의 데이터를 파싱한다.
  - json 형태로 전송하기 위해 obj(object)를 생성한후 전송한다.
  - `JSON.stringify(obj)` : object를 jsonstring의 형태로 변환한다.
  - 안드로이드 등과 통신하기 위해서는 json형태가 아닌 String 형태로 통신하며 이를 파싱 및 클래스로 변환하여 이용한다.

  > server_dbjson.js

  ```javascript
  var http = require("http");
  var u = require("url");
  var mongo = require("mongodb").MongoClient;

  var server = http.createServer(function(request, response){
  	url = u.parse(request.url);
  	cmds = url.pathname.split("/");

  	if(cmds[1] == "signin"){
  		// post로 넘어온 데이터 읽기
  		var postdata="";
  		request.on('data',function(data){
  			postdata += data;
  		});

  		// 데이터를 모두 읽으면 변수값 분리해 처리
  		request.on('end', function(){
  			var query = JSON.parse(postdata);
  			// Json 형식인 경우
  			// query = {
  			//		id : "xxx",
  			//		pw : "123456"
  			// } 와 같이 자바스크립트 오브젝트로 들어가 있다.

  			if(!query.id || !query.pw){
  				response.end("id or pw is wrong!");
  			} else{
  				// mongo db 주소 구조 = 프로토콜://주소:포트/데이터베이스 이름
  				mongo.connect("mongodb://localhost:27017/testdb", function(error, db){
  					if(error){
  						response.write(error);
  						response.end("");
  					} else{
  						console.log("[query]");
  						console.log(query);
  						var cursor = db.collection('user').find(query);
  						var obj = {
  							code : "",
  							msg : ""
  						};
  						obj.code = "300";
  						obj.msg = "FIAL";

  						cursor.toArray(function(err,dataset){
  							if(dataset.length>0){
  								obj.code = "200";
  								obj.msg = "OK";
  							}
  							console.log("[obj]");
  							console.log(obj);
  							response.write(JSON.stringify(obj));
  							response.end("");
  						});
  					}
  				});
  			}
  		});
  	} else{
  		response.end("page not found!!");
  	}
  });

  server.listen(9000, function(){
  	console.log("server is running...");
  });
  ```

---

## 안드로이드와의 통신 결과
- [참고_LoginActivity](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/LoginHttp)

![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/server_db_basic/picture/result.png)

---

## 참고
  ### 1. 내 ip 가져오기
  - Cmd(커멘드)에 ipconfig 입력시 내 ip 주소를 보여주며 `IPv4 주소` 값에서 내 주소를 가져올 수 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/server_db_basic/picture/ipconfig.png)

  ### 2. module 설치
  - module을 node.js 파일 디렉토리(상위디렉토리)에 설치하면 매 폴더마다 설치를 안해주어도 된다.

  ### 3. 데이터셋의 처리방법
  - `forEach` 및 `each` : 비동기이기 때문에 문제가 발생할 수 있다.
  - 따라서 for(key in data) 를 이용한다.
    - key를 출력하면 index가 출력
    - data[key] 를 하면 특정 data값 출력
