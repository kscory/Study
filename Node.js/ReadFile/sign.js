var http = require("http");
var u = require("url");
var qs = require("querystring");
var fs = require("fs");
var mime = require("mime");

var server = http.createServer(function(request, response){
	var url = u.parse(request.url);
	var path = url.pathname;
	var cmds = path.split("/");
	if(cmds[1] == "getfile") {
		if(request.method == "POST"){
			// body에서 꺼내기
		} else if(request.method == "GET"){
			query = qs.parse(url.query);
			if(query.filepath){
				var filepath = query.filepath;
				var mtype = mime.getType(filepath)
				// 비디오면 스트림 처리
				if(mtype == "video/mp4"){
					var stream = fs.createReadStream(filepath);
					stream.on('data', function(fragdata){
						response.write(fragdata);
					});
					stream.on('end', function(){
						console.log("complete!!");
						response.end();
					});
					stream.on('error', function(){
						response.end(error);
					});
				} else {
					fs.readFile(filepath, function(error, data){
						if(error){
							response.writeHead(500,{'Content-type':mtype});
							console.log("getfile > readfile error")
							response.end(error);
						} else{
							response.writeHead(200,{'Content-type':mtype});
							console.log("getfile > readfile complete")
							response.end(data);
						}
					});
				}
			} else{
				response.writeHead(404,{'Content-type':'text/html'});
				response.end("filepath is not found!!");
			}
		}
	} else if(cmds[1] == "html") {
		filepath = path.substring(1);
		fs.readFile(filepath, 'utf-8', function(error, data){
			if(error){
				response.writeHead(500, {'Content-type':"text/html"});
				console.log("html > readfile error")
				response.end(error);
			} else{
				response.writeHead(200, {'Content-type':"text/html"});
				console.log("html > readfile complete")
				response.end(data);
			}
		});
	} else if(cmds[1] == "signin") {
		// request.url은 위에서 parsing해서 url 변수에 담아둔 상태
		var id = "root";
		var pw = "qwer1234";

		var sign;

		var postdata = "";
		request.on('data', function(data){ // data 이벤트가 발생하고 postData에 송신된 데이타를 쌓는다.
			postdata += data;
		});
		request.on('end', function(){ // 이벤트가 끝나면 실행된다.
			// postData = "id=root&pw=qwer1234"; 의 형식으로 되어있기 때문에 이를 parsing하여 sign에 저장한다.
			sign = qs.parse(postdata);
			if(sign.id == id && sign.pw == pw){
				response.writeHead(200,{'Content-type':'text/html'});
				console.log("ID or PW is ok...");
				response.end("OK");
			} else {
				response.writeHead(200,{'Content-type':'text/html'});
				console.log("ID or PW is wrong...");
				response.end("FAIL");
			}
		});

	} else{
		response.writeHead(404,{'Content-type':'text/html'});
		response.end("404 page is not found!!");
	}
});

server.listen(8090, function(){
	console.log("server is running...");
});