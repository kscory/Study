var u = require("url");
var qs = require("querystring");
var bbs = require("../b_controller/bbs"); // 한번 빠져나가서 db로 들어감 따라서 "../" 사용

exports.process = function(request, response){
	var url = u.parse(request.url);
	var method = request.method.toLowerCase()
	var cmds = url.pathname.split("/");
	if(cmds[1] == "bbs"){
		if(method == "get"){
			var query = qs.parse(url.query);
			console.log("확인===================");
			console.log(query);
			bbs.read(request, response, query);
		} else{
			// get 이외의 method는 body 데이터를 가져온다.
			var body = "";
			request.on('data',function(data){
				body += data;
			});
			// 데이터 로딩이 완료되면 각 method로 분기
			request.on('end',function(){
				var bbs_body = JSON.parse(body);
				if(method == "post"){
					bbs.create(request, response, bbs_body);
				} else if(method == "put"){
					bbs.update(request, response, bbs_body);
				} else if(method == "delete"){
					bbs.delete(request, response, bbs_body);
				}
			});

		}
	} else{

	}
};