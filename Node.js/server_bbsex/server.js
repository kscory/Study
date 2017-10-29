var http = require("http");
var route = require("./route"); // route 모듈 이용
// 서버 연결
var server = http.createServer(function(request, response){
    // route의 process 함수 동작
    route.process(request,response);    
});

server.listen(9000, function(){
    console.log("server is running...");
});