# ReadFile & Http 통신 & html
  - get / post 등 사용
  - file을 읽음
  - html 약간

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
  - html을 만들어서 출력시켜본 예시 및 동작 방식

   ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/ReadFile/picture/postget.png)
   ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/ReadFile/picture/postget2.png)

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

## 참고 개념
  ### 1. 참고
  - 내용

---

## 참고 문헌
  ### 1. [링크]()
