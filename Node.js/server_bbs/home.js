//==================================================================
// // 외부파일 사용하기
// var module = require("./module"); // 경로 조심할 것

// var x = 300;
// var y = 750;

// console.log(module.sum(x,y));



// var bbs = require("./db");

// // 사용방법
// // 방법 1. new해서 사용하는 방법 (형식을 index에 만들어서 사용)
// // var bbsTable = new bbs.Bbs();

// // 방법 2. 테이블을 직접 넘기는 방법
// var table = {
// 	no : 147,
// 	title : "제목",
// 	content : "내용",
// 	date : "날짜",
// 	user_id : ""
// };

// bbs.create(table);

//=====================================================================
var db = require("./db");

// create예시
var bbs = {
	no : 2,
	title : "제목입니다.",
	content : "내용입니다.",
	date : "2017/10/26 11:12:33",
	user_id : "root222"
};

db.create(bbs,function(answer){
	console.log(answer);
});


// read예시
var search = {
	type : "no",
	no : 2
};

db.read(search, function(dataset){
	dataset.forEach(function(item){
		console.log(item);
	});
	// for(key in dataset){
	// 	console.log(dataset[key]); // key를 입력 하면 index만 찍히고 key로 하면 값이 찍힌다.
	// }
});

// update 예시
db.readOne(search, function(dataset){
	if(dataset.length>0){
		var bbs = dataset[0];
		// 서버에서 수정할 데이터 조회
		var json = JSON.stringify(bbs);

		// ------안드로이드(일단 가상으로...)--------
		// 모바일에서 수정을 거치고
		var modified = JSON.parse(json);
		modified.title = "수정했습니다."
		var mod_json = JSON.stringify(modified);
		//-----------------------------------------

		// ... networking

		// > 다시 서버
		var completed = JSON.parse(mod_json);
		db.update(completed);
	}
});


