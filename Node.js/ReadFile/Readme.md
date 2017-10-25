# Http 통신(POST&GET) & html 약간
  - get / post 등 사용
  - file을 읽음
  - html 약간
  - 동영상의 경우 파일이 커서 제거함.

---

## Get을 호출하는 방법 & 파일 읽는 방법
  ### 1. get을 호출
  - `var url = require("url");` : split을 내장하고 있는 모듈
  - `var query = require("querystring");` :  query를 쪼개는, split을 내장하고 있는 모듈
  - `var parseURL = url.parse(req.url);` : url을 "/"로 스플릿
  - `var parseQuery = query.parse(parseURL.query);` : 쿼리에 따라 parse
  - `parseQuery.aaa` : aaa가 있는 쿼리를 실행
  - 출력 예시

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/ReadFile/picture/query.png)

  ```javascript
  // 1. 서버 모듈 import
  var http = require("http");
  var url = require("url");
  var query = require("querystring");

  // 2. 서버와 모듈을 사용해서 서버를 정의
  var server = http.createServer( function(req, res){
  	// get을 호출하는 경우
  	var parseURL = url.parse(req.url);
  	console.log(parseURL);
  	var parseQuery = query.parse(parseURL.query);
  	console.log(parseQuery);
  	res.writeHead(200, {'Content-type':'text/html'});
  	res.end("aaa's value is " + parseQuery.aaa +"</br>"+"bbb's value is " + parseQuery.bbb);
  });

  // 3. 서버를 실행
  server.listen(9000,function(){
  	console.log("server is running...")
  });
  ```

  ### 2. 파일 읽는 방법
  - `var fs = require("fs");` : 파일 처리를 해주는 모듈
  - `var path = url.pathname;` : path명을 가져온다.
  - `fs.readFile(path.substring(1),'utf-8',function(error, data){...};`
    - path에 해당되는 파일을 읽는다.
    - 파일경로, 엔코딩, 콜백함수로 구성
    - 이 파일 읽기는 다른 쓰레드에서 실행된다.(쓰레드 처리를 알아서 해준다.)
    - 따라서 쓰레드에서 연속으로 동작하기 위해 `readFile(){...}` 에서 동작시켜 주어야만 한다.
  - html을 만들어서 출력시켜본 예시

   ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/ReadFile/picture/postget.png)

  > file.js

  ```javascript
  var http = require("http");
  var u = require("url");
  var fs = require("fs");

  var server = http.createServer(function(request, response){
  	var url = u.parse(request.url);
  	var path = url.pathname;
  	response.writeHead(200,{'Content-type':'text/html'});
     // substring(1) 을 하면 첫글자를 제외한 모든 글자를 가져온다.(제일 앞 "/" 제거)
  	fs.readFile(path.substring(1),'utf-8',function(error, data){
  		var text=""
      // 자바스크립트의 경우 error가 존재할경우 그냥 error만 쓰면 된다.
  		if(error){
  			text = htmlText(error);
  		} else{
  			text = data;
  		}
  		response.end(text);
  	});
  });

  server.listen(8090,function(){
  	console.log("server is running...")
  });
  // 한글로 써주기 위해 html에서 타입을 utf-8로 바꿔준다.
  function htmlText(str){
  	text = "<html><meta charset='utf-8'/><body>서버오류 : ";
  	text += str;
  	text += "</body></html>";
  	return text;
  }
  ```

  > post.html

  ```html
  <html>
  <head>
    <meta charset="utf-8"/>
    <title> Hello Html </title>
    <!--주석 처리는 이렇게 : 여기는 브라우저에 현재 페이지 정보를 알려주는 역할-->
  </head>
  <body>
    <!--여기는 브라우저 화면에 나타남 mehthod를 POST or GET으로 바꾸어 실행-->
    <form action="/post" method="POST">
      아이디 : <input type = "text" id="id" name="id"/></br>
      비밀번호 : <input type = "text" id="pw" name = "pw"/></br>
      <input type = "submit" value="전송"/>
    </form>
  </body>
  </html>
  ```

---

## 위를 활용하여 사진 및 동영상 읽기
  ### 1. post.js (사진 및 동영상 출력)
  - `npm insatall -g mime` 을 콘솔에 입력하여 mime을 다운받는다. (-g를 하면 모두 사용하겠다는 뜻, npm : node program manager)
  - `var mime = require("mime");` : mimetype을 알 수 있는 라이브러리 제공
  - html 파일은 모두 html 폴터에 따로 저장하여 javascript에서 태그로 찾도록 한다.
  - post의 경우는 body에서 filepath를 꺼내고 get은 query에서 filepath를 꺼낸다.
  -  `mime.getType(filepath)` 은 파일의 mimetype을 알려주며 이는 파일에서 앞의 두자리를 뜻한다.(안되면 lookup으로 해볼 것.)
  - 동영상의 경우 용량이 크기 때문에 stream 처리를 해준다.
    - stream 처리의 경우 세가지의 `stream.on` 가 모두 다른 thread에서 동작하다가 node.js가 맞는 코드를 실행시킨다.
    - `stream.on('data', function(fragment)` : 데이터를 읽을 수 있는 최소 단위로의 조각이 콜백함수를 통해 전달된다
    - `stream.on('end', function()` : 이벤트 완료
    - `stream.on('error', function()` : 이벤트 에러
  - html이 아닌 파일이 큰 경우(stream인 경우) 끊어서 가져오기 때문에 서버 호출이 더 많이 일어난다.

  ```javascript
  var http = require("http");
  var u = require("url");
  var fs = require("fs");
  var qs = require("querystring");
  var mime = require("mime"); // mimetype을 알 수 있는 함수 제공

  var server = http.createServer(function(req, res) {
    var url = u.parse(req.url);
    // method 를 꺼낸다.
    // 주소에서 명령어=서버자원의id(uri)를 먼저 꺼낸다.
    var path = url.pathname;
    var cmds = path.split("/");
    if(cmds[1] == "getfile"){
      if(req.method == "POST"){
        // ..body에 넘어온 filepath를 꺼낸다.
      } else if(req.method == "GET"){
        // query에서 filepath를 꺼낸다.
        var query = qs.parse(url.query);
        if(query.filepath){
          var filepath = query.filepath;
          console.log("filepath="+filepath);
          var mtype = mime.getType(filepath); // 파일의 mime type을 알려준다.
          //mimetype을 체크해서 동영상이면 stream 처리
          if(mtype == "video/mp4"){
            // 1. stream 생성
            var stream = fs.createReadStream(filepath);
            // 2. stream 전송 이벤트 등록
            var count=0;
            stream.on('data', function(fragment){ .
              console.log("count=" +count++);
              res.write(fragment);
            });
            // 3. stream 완료 이벤트 등록
            stream.on('end', function(){
              console.log("complete!!");
              res.end();
            });
            // 4. stream 에러 이벤트 등록
            stream.on('error', function(){
              res.end(error+"");
            });
          } else {
            fs.readFile(filepath, function(error, data){
              if(error){
                res.writeHead(500,{'Content-type':mtype});
                res.end(error+"");
              } else {
                res.writeHead(200,{'Content-type':mtype});
                res.end(data);
              }
            });
          }
        }
      }
    } else if (cmds[1] == "html"){
      filepath = path.substring(1);
      fs.readFile(filepath, 'utf-8' ,function(error, data){
        if(error){
          res.writeHead(404,{'Content-type':'text/html'});
          res.end("<h1>404 Page not Found</h1>")
        } else {
          res.writeHead(200,{'Content-type':'text/html'});
          res.end(data);
        }
      });
    } else {
      res.writeHead(404,{'Content-type':'text/html'});
      res.end("<h1>404 Page not Found</h1>")
    }
  });

  server.listen(8090, function(){
    console.log("server is running.....");
  });
  ```

  ### 2. view.html
  - video의 경우 controls를 넣어주지 않으면 아래 스크롤바가 나오지 않는다.
  - src에 경로를 지정한다.
    - ex>`<src = /getfile?filepath=file/SampleVideo.mp4 type="video/mp4"/>` </br>
     => `getfile` 경로이며 path가  `file/SampleVideo.mp4` 인 것을 가져온다.

  > view.html

  ```html
  <html>
  <head>
  	<meta charset="utf-8"/>
  	<title>Movie Viewer</title>
  </head>
  <body>
  	<video width="640" height="480" controls>
  		<src = /getfile?filepath=file/SampleVideo.mp4 type="video/mp4"/>
  		당신의 브라우저는 이 타입을 지원하지 않습니다.
  	</video>
  </body>
  </html>
  ```

---

## 참고 개념
  ### 1. POST와 GET의 동작 방식
  - Get을 제외한 나머지 메소드는 body를 함께 보낸다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/ReadFile/picture/postget2.png)
