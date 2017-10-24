// 1. 서버 모듈 import
var http = require("http");
var url = require("url"); // split을 내장하고 있는 모듈
var query = require("querystring"); // query를 쪼개는, split을 내장하고 있는 모듈

// 2. 서버와 모듈을 사용해서 서버를 정의
var server = http.createServer( function(req, res){
	// get을 호출
	var parseURL = url.parse(req.url);
	console.log(parseURL);
	var parseQuery = query.parse(parseURL.query);
	console.log(parseQuery);
	res.end("aaa's value is " + parseQuery.aaa);
});

// 3. 서버를 실행
server.listen(9000,function(){
	console.log("server is running...")
});