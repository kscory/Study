// firebase에서 이용
const fun = require('firebase-functions'); // firebase function 을 상수로 선언 (상수는 const)
const admin = require("firebase-admin"); // admin을 선언

admin.initializeApp(fun.config().firebase);

// addMesaage를
exports.addMesaage = fun.https.onRequest((req, res)=>{ //"function(req, res)"를 단축하는 것(람다식)이 "(req,res)=>"
	// http 요청에서 ? 다음에 잇는 변수중에 text 변수값을 가져온다.
	var text = req.query.text;
	// 파이어베이스 db의 message 레퍼런스에 그 값을 넣는다.
	admin.database().ref('/message') // message 디렉토리에 값을 넣는다.
		.push({msg : text}) // msg를 key로 하여 text를 넣는다. (msg는 고정된 값)
		.then(snapshot=>{ // snapshot은 변수명임(aaa라고 해도 됨)
			// res,redirect(303,snapshot.ref); // 이는 스냅샷의 링크(http url connection 이동하는 거)를 나에게 돌려주는 것
			res.end("success!!")
		});
});

exports.sendNotification = fun.https.onRequest((req, res)=>{
	// json 데이터로 post 값을 수신
	var dataObj = req.body;
	var msg = {
		notification : {
			title : "노티바 타이틀",
			body : dataObj.msg
		}
	};
	var tokens = [];
	tokens.push(dataObj.to);
	admin.messaging().sendToDevice(tokens, msg)
		.then(function(response){
			res.status(200).send(response);
		})
		.catch(function(response){
			res.status(501).send(error);
		});
});