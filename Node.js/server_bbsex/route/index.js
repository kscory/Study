/**
 * router
 * 요청자 및 method를 파악해서 controller에 전달
 */
var u = require("url");
var qs = require("querystring");
var controller = require("../controller");

exports.process = function(request, response){
    var url = u.pasrse(request.url);
    var method = request.method.toLowerCase();
    var cmds = url.pathname.split("/");
    // 게시판 관련된 요청 처리
    if(cmds[1] == "bbs"){
        // 게시판 읽기(get)
        if(method = "get"){
            var query = qs.parse(url.query);
            controller.read(request,response,query);

            // 게시글 추가,수정,삭제 (post,put,delete)
        } else{
            // body의 데이터 가져오기
            var bodydata = "";
            request.on('data',function(data){
                bodydata += data;
            });
            // body 데이터를 모두 가져온 후 요청 처리
            request.on('end',function(){
                // json형식으로 변환
                var body = JSON.parse(bodydata);
                if(method = "post"){
                    controller.create(request,response,body);
                } else if(method = "put"){
                    controller.update(request,response,body);
                } else if(method = "delete"){
                    controller.delete(request,response,body);
                } else{
                    controller.fails(request,response,"메소드오류");
                }
            });
        }
      // 로그인 관련 요청 처리
    } else if(cmds[1] == "signin"){
        var bodydata = "";
        request.on('data',function(data){
            bodydata += data;
        });
        request.on('end',function(){
            var body = JSON.parse(bodydata);
            controller.signin(request,response,body);
        });
    } else {
        controller.fails(request,response,"요청자 오류");
    }
};