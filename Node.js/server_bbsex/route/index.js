var u = require("url");
var qs = require("querystring");
var bbs_controller = require("../bbs/controller");
var singin = require("../signin");

exports.process = function(request, response){
    var url = u.pasrse(request.url);
    var method = request.method.toLowerCase();
    var cmds = url.pathname.split("/");
    if(cmds[1] == "bbs"){
        if(method = "get"){

        } else{

            if(method = "post"){

            } else if(method = "put"){

            } else if(method = "delete"){

            } else{
                response.end("") // result의 형태로 보낼 것(수정 필요)
            }
        }

    } else if(cmds[1] == "signin"){

    } else {
        response.end("") // result의 형태로 보낼 것(수정 필요)
    }
};