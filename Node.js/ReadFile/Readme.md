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

  ![](![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/BasicGrammar%26Server/picture/query.png))

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
  - 간단한 예제

  ```javascript

  ```

---

## 참고 개념
  ### 1. 참고
  - 내용

---

## 참고 문헌
  ### 1. [링크]()
