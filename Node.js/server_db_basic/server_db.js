var http = require("http");
var u = require("url");
var qs = require("querystring");
var mongo = require("mongodb").MongoClient;

var server = http.createServer(function(request, response){
	var url = u.parse(request.url);
	var cmds = url.pathname.split("/");

	if(cmds[1] == "signin"){
		// post로 넘어온 데이터를 읽는다.
		var postdata = "";
		request.on('data', function(data){
			postdata += data;
		});

		// 모두 읽으면 변수와 값을 분리해서 처리한다.
		request.on('end', function(){
			// postdata = id=xxx&pw=12345; 형태로 넘어옴
			var query = qs.parse(postdata);

			if(!query.id || !query.pw){
				response.end("id or pw is wrong!!");
			} else{

				mongo.connect("mongodb://localhost:27017/testdb",function(error, db){
					if(error){
						response.write(error);
						response.end("");
					} else{

						var cursor = db.collection('user').find(query);
						var result="FAIL";
						cursor.toArray(function(err, dataset){
							// 쿼리에 맞는 조건값 존재할 시 로그인 성공
							if(dataset.length>0){
								result="OK";
							}
							response.write(result);
							response.end("");
						});
					}
				});
			}
		});
	} else {
		response.end("page is not found!!");
	}
});

server.listen(9000, function(){
	console.log("server is running...");
});

// 데이터셋의 처리방법 2가지 (// 1. forEach : 비동기 // 2. each : 비동기) 비동기 문제 발생