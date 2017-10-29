/**
 * controller
 */
/*
    Result 구조
    bbsex = {
        code : 처리 코드 // 200=정상, 400=실패
        msg : "오류메시지" // 
        data : 데이터셋 
    }
 */
var bbsdao = require("../dao/bbsDao");
var signdao = require("../dao/signDao")
var ObjectId = require("mongodb").Object;

exports.create = function(request, response, bbs_add){

}

exports.read = function(request, response, bbs_readQuery){

}

exports.update = function(request, response, bbs_update){

}

exports.delete = function(request, response, bbs_remove){

}

exports.signin = function(request,response,signindata){

}

exports.fail = function(request, response, messge){
    var result = {
        code : 400,
        msg : messge
    }
    response.end(JSON.stringify(result));
}