// db/index.js
// db에 대한 CRUD 함수 작성
// 보통 디렉토리를 테이블 이름이랑 동일하게 만든다. (일단 db로 함)

var mongo = require("mongodb").MongoClient;
var dbname = "bbsdb";
var dburl = "mongodb://localhost:27017/"+dbname;
var table = "bbs";
var page_count = 20;

/* 데이터베이스 구조 설정
	bbs = {
		no : 12,
		title : "제목",
		content : "내용",
		date : "2017/10/26 11:21:30"
		user_id : "root"	
	}
	search = {
		type : "all",		// all = 전체검색, no = 글 한개 검색, title = "제목검색"
		no : 137;
		title : "제목검색"
		content : "내용검색",
		date = "날짜검색",
		user_id = "사용자아이디로 검색"
	}
*/

// exports.bbs = function(){
// 	this.no = -1,;
// 	this.title = "";
// 	this.content = "";
// 	this.date = "";
// 	this.user_id = "";

// 	this.toQuery = function(){
// 		var bbs = {
// 			no : -1,
// 			title : "제목",
// 			content : "내용",
// 			date : "2017/10/26 11:21:30"
// 			user_id : "root"	
// 		}
// 		bbs.no = this.no;
// 		bbs.title = this.title;

// 		return bbs;
// 	}
// }

exports.create = function(bbs, callback){
	mongo.connect(dburl, function(error, db){

		db.collection(table).insert(bbs, function(err, inserted){
			if(error){
				callback(400);
			} else{
				callback(200);
			}
			db.close();
		});
	});
}

exports.read = function(search,callback){
	mongo.connect(dburl, function(error, db){
		// var projection = {title:1,} // title만 가져온다.
		// var projection = {title:1,content:0} // 1과 0이 함께 있을 수 없다., 이는 true/false값
		// 소팅 조건만 담아서 뒤에 sort만 붙이면 소팅이 된다.
		var sort = {
			_id : -1 // -1: 오름차순 , 1: 내림차순
		}; // 소팅조건 
		// 집합
		// skip - 카운트를 시작할 index의 위치
		// limit - 가져올 개수
		var start = (search.page - 1) * page_count;
		// 사용하지 않는 검색 컬럼은 삭제처리
		delete search.page;
		console.log(start);
		var cursor = db.collection(table)
						.find(search)
						.sort(sort)
						.skip(start)
						.limit(page_count);

		cursor.toArray(function(error, documents){
			if(error){

			} else {
				// document 처리
				callback(documents);
			}
		});
		db.close();
	});
}

exports.readOne = function(search,callback){
	mongo.connect(dburl, function(error, db){
		var query = {};
		if(search.type === "all"){
			query = {};
		} else if(search.type === "no"){
			query = {no : search.no};
		}
		var cursor = db.collection(table).find(query);

		cursor.toArray(function(error, documents){
			if(error){

			} else {
				// document 처리
				callback(documents);
			}
		});
		db.close();
	});
}

exports.update = function(bbs){
	mongo.connect(dburl, function(error, db){
		// 1. 수정대상 쿼리
		var query = {_id: -1};
		query._id = bbs._id;
		// 2. 데이터 수정명령 var operator = {title:"수정된 타이틀",date:"2018-11-15 11:13:23"};
		
		//딜리트 하는 일반적인 =====================
		// var operator = {
		// 	no : 1,
		// 	title = "";
		// };
		// operator.no = bbs.no;
		// operator.title = bbs.title;
		//==========================

		// 특정 대상을 딜리트
		var operator = bbs;
		delete operator._id;
		// 3. 수정옵션 - upsert true 일때 데이터가 없으면 insert
		var option = {upsert:true};

		db.collection(table).update(query,operator,option, function(err, upserted){
			if(err){

			} else{
				// 정상 처리
			}
		});
		db.close();
	});
}

exports.delete = function(bbs){
	mongo.connect(dburl, function(error, db){
		//1. 삭제대상 쿼리
		var query = {no : -1};
		query.no = bbs.no;

		db.collection(table).remove(query, function(err, removed){
			if(err){

			} else{
				// 정상 삭제
			}
			db.close();
		});
	});
}

// 일단 빼지 않음....
function con(){

}