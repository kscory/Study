/**
 * controller
 * router로부터 요청값을 받아, dao를 통해 db 데이터를 가져와서 처리 후 view에 전달
/*
    Result 구조
    bbsex = {
        code : 처리 코드 // 200=정상, 400=실패
        msg : "오류메시지" // 
        totalcount : 조회한 데이터의 totalcount(페이지 로드시 이용)
        data : 데이터셋 
    }
 */
var bbsdao = require("../dao/bbsDao");
var signdao = require("../dao/signDao")
var ObjectId = require("mongodb").Object;

// 추가 요청
exports.create = function(request, response, bbs_add){
    bbsdao.create(bbs_add, function(result_code, msg){
        if(result_code==200){
            successmsg(request,response,msg);
        } else {
            failmsg(request,response,msg);            
        }
    })
}

// 조회 요청
exports.read = function(request, response, bbs_read){
    var readQuery = {};
    if(bbs_read.type === "all"){

    } else if(bbs_read.type === "_id"){

    } else if(bbs_read.type === "title"){

    } else if(bbs_read.type === "content"){

    } else if(bbs_read.type === "username"){

    } else{
        failmsg(request,response,"검색태그가 존재하지 않습니다.");
    }
}

// 업데이트 요청
exports.update = function(request, response, bbs_update){
    dates = new Date
    var updateQuery = {
        _id : -1,
        content : "",
        date : new Date()+"",
        files : ""
    };
    updateQuery._id = ObjectId(bbs_update_id);
    bbsdao.update(updateQuery, function(result_code, msg){
        if(result_code==200){
            successmsg(request,response,msg);
        } else {
            failmsg(request,response,msg);            
        }
    })
}

// 삭제 요청
exports.delete = function(request, response, bbs_remove){
    var deleteQuery = { _id : -1};
    deleteQuery._id = ObjectId(bbs_remove._id);
    bbsdao.delete(deleteQuery, function(result_code, msg){
        if(result_code==200){
            successmsg(request,response,msg);
        } else {
            failmsg(request,response,msg);            
        }
    })
}

// 로그인 요청
exports.signin = function(request,response,signindata){
    if(!signindata.id || !signindata.pw){
        fails(request,response,"id 혹은 pw 를 입력해주세요");
    } else {
        signdao.signin(signindata,function(result_code){
            if(result_code == 200){
                successmsg(request,response,"로그인 성공");
            } else{
                fails(request,response,"id 혹은 pw가 잘못되었습니다");
            }
        })
    }
}

// read를 제외한 요청 성공시 메시지 전달
exports.successmsg = function(request, response, message){
    var result = {
        code : 200,
        msg : message
    };
    response.end(JSON.stringify(result));
};

// 요청 실패시 메시지 전달
exports.failmsg = function(request, response, message){
    var result = {
        code : 400,
        msg : message
    };
    response.end(JSON.stringify(result));
};