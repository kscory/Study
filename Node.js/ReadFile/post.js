// 주소(Rest Api) 요청의 형태
// http://localhost:8090/post?filepath=/dir1/xxx.png

var http = require("http");
var tempUrl = require("url");
var qs = require("querystring");
var fs = require("fs");
var mime = require("mime"); 
// mimetype을 알 수 있는 함수 제공
// npm install mime (-g를 하면 모두 사용하겠다고 한다.)(node program manager)

var server = http.createServer( function(request, response){
	var url = tempUrl.parse(request.url);
	// method를 꺼낸다
	// 주소에서 명령어 = 서버자원의id(url)을 먼저 꺼낸다.
	var path = url.pathname;
	var cmds = path.split("/");
			console.log("찍힘 ===="+path+" |||| "+cmds[1]);
	if(cmds[1] == "getfile"){
		if(request.method == "POST"){
			// body에서 넘어온 filepath를 꺼낸다.
		} else if(request.method == "GET"){
			//query에서 filepath를 꺼낸다.
			var query = qs.parse(url.query);

			if(query.filepath){
				var filepath = query.filepath;
				console.log("filepath : " + filepath);
				var mtype = mime.getType(filepath); // 파일의 mime type을 알려준다.

				//mimetype을 체크해서 동영상이면 stream 처리
				if(mtype == "video/mp4"){
					// 1. stream 생성
					var stream = fs.createReadStream(filepath);
					// 2. stream 전송 이벤트 등록
					var count = 0; // 스트림의 개수를 한번 파악하기 위해 사용
					stream.on('data', function(frag){
						console.log("count = " + count++);
						response.write(frag);
					});
					// 3. stream 완료 이벤트 등록
					stream.on('end', function(){
						response.end();
					});
					// 4. stream 에러 이벤트 등록
					stream.on('error', function(){
						response.end(error); // "" 안되도 되는지 체크!!;
					});
				} else {
					fs.readFile(filepath, function(error, data){
						if(error){
							response.writeHead(500,{'Content-type':mtype});
							response.end(error);
						} else{
							response.writeHead(200,{'Content-type':mtype});
							response.end(data);
						}
					});
				}
			}
		} 
	}else if(cmds[1] == "html") {
		filepath = path.substring(1);
		fs.readFile(filepath, 'utf-8', function(error, data){
			if(error){
				response.writeHead(404,{'Content-type': 'text/html'});
				response.end("<h1>404 Page not Found</h1>");
			} else{
				response.writeHead(200,{'Content-type':'text/html'});
				response.end(data);
			}
		});
	} else {
		response.writeHead(404,{'Content-type': 'text/html'});
		response.end("<h1>404 Page not Found</h1>");
	}
});

server.listen(8099, function(){
	console.log("server is running...");
});