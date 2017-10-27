var mongo  = require("mongodb").MongoClient;
var dbname = "bbsdb";
var dburl = "mongodb://localhost:27017/"+dbname;
var table = "bbs";

//insert 를 10만개 처리 하시오
var randomTitle =  [ "STRAT", "ETH", "TIX", "VTC", "NEO", "TRIG", "XLM","ETC","BCH","UBQ","QTUM","XRP","XMR" ];
var users=  [ "Jae", "hong", "go", "michael", "jordan","curry","zico","gdragon","dokki","young-b"];

var len = content.length;



mongo.connect(dburl,function(error, db){



	for(i=0 ; i<100 ; i++){
		var array = [];
		for(j=0 ; j<1000 ; j++){
			var bbs = {
				title :randomTitle[Math.floor(Math.random()*100)%13]+"-"+randomTitle[Math.floor(Math.random()*100)%13],		// 랜덤한 텍스트를 조합해서 입력
				content : "내용입니다",
				user_id : users[j%10],	// 10개를 미리 정해놓고 random 입력
				date : new Date()+""
			} 
			array[j] = bbs;
		}

		// multitple insert
		db.collection(table).insertMany(array,function(){
			if(error){

			} else{

			}		
		});
	}

	// single insert
	// db.collection(table).insert(bbs,function(){
	// 	if(error){
	// 		callback(400);
	// 	} else{
	// 		callback(200);
	// 	}
	// });
});