/*
    데이터베이스 구조
    ueserinfo = {
        id : "사용자 아이디"
        pw : "사용자 패스워드"
        name : '사용자 이름
        group : "가입한 group들"// 배열
    }
    검색 구조
    search = {
        type : "all" // all = 전체, title = 제목, content = 내용, username = 사용자 이름 
    }
*/

var mongo = require("mongodb").MongoClient;
var dbname = "bbsdb";
var dburl = "mongodb://loacalhost:27017/"+dbname;
var table = "userinfo";

exports.signin = function(siginQuery, callbackResult){
    
};