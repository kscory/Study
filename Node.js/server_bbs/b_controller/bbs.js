var dao = require("../c_dao/bbs");
var ObjectID = require("mongodb").Object;

exports.read = function(request, response, search){
	var query = {};
	if(search.type === "all"){
		query = {page : parseInt(search.page)};
	} else if(search.type === "no"){
		// query = {no : -1};
		// query.no = parseInt(search.no);

		// id를 가져올 경우 object이기 때문에 다음과 같이 한다.
		query = {_id : -1};
		query._is = ObjectID(search._id); 
	}	// 등등등

	console.log(query);

	dao.read(query, function(dataset){
		console.log(dataset);
		var result = {
			code : 200,
			msg : "정상처리",
			data : dataset
		}
		response.end(JSON.stringify(result));
	});
};

exports.create = function(request, response, bbs){
	dao.create(bbs,function(result_code){
		var result = {
			code : result_code,
			msg : "입력완료"
		};

		response.end(JSON.stringify(result));
	});
};

exports.update = function(request, response, bbs){

};

exports.delete = function(request, response, bbs){

};