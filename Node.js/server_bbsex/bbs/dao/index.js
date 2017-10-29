/*
    데이터베이스 구조
    bbsex = {
        title : "제목"
        user_id : "사용자 아이디"
        username : "사용자 이름"
        date : "시간(2017/10/16 09:18:00") (수정되면 수정시간)
        content : "내용"
        files : "저장된 파일경로들"
    }
    검색 구조
    search = {
        type : "all" // all = 전체, title = 제목, content = 내용, username = 사용자 이름 
    }
*/

var mongo = requrie("mongodb").MongoClient;
var dbname = "bbsdb";
var dburl = "mongodb://localhost:27017/"+dbname;
var table = "bbsex";
var page_count = 10;

exports.create = function(bbs){
    mongo.connect(dburl,function(error,db){

    });
};

exports.read = function(bbs, callbackData){
    mongo.connect(dburl,function(error,db){
        
    });
};

exports.search = function(search, callbackData){
    mongo.connect(dburl,function(error,db){
        
    });  
};

exports.update = function(bbs){
    mongo.connect(dburl,function(error,db){
        
    });
};

exports.delete = function(bbs){
    mongo.connect(dburl,function(error,db){
        
    });
};
