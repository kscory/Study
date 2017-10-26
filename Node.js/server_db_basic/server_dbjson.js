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