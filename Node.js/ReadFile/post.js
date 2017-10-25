// 주소(Rest Api) 요청의 형태
// http://localhost:8090/post?filepath=/dir1/xxx.png

var http = require("http");
var tmepUrl = require("url");
var fs = require("fs");
var mime = require("mime"); 
// mimetype을 알 수 있는 함수 제공
// npm install mime (-g를 하면 모두 사용하겠다고 한다.)(node program manager)

var server = http.createServer( function(require, response){

});

server.listen(8099, function(){
	console.log("server is running...");
});