var http = require("http");
var u = require("url");
var fs = require("fs"); // // 파일 처리를 해주는 모듈

var server = http.createServer(function(request, response){
	var url = u.parse(request.url);
	var path = url.pathname; // path에 해당하는 파일을 읽는다.
	response.writeHead(200,{'Content-type':'text/html'});
	fs.readFile(path.substring(1),'utf-8',function(error, data){
		var text=""
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

function htmlText(str){
	text = "<html><meta charset='utf-8'/><body>서버오류 : ";
	text += str;
	text += "</body></html>";
	return text;
}