var http = require("http");
var httpUrlConnection = require("request");

// 서버 측 변수
// fcm 서버 url로 정해져 있는 값(url)
const fcmServerUrl = "https://fcm.googleapis.com/fcm/send";
// firebase project의 고유 서버키값을 복붙
const serverKey = "AAAAv-E1Cfk:APA91bGBha4H-7iOxp3ZRYyyCy2DYn2O4X3imzpPzzoibKru2fiQ6PM0l3fF7RcWuKOZ2MfSHl8Ii7v_NM1KoDx-cZJ9YG9q4kk8rCXFr5FBIMmDQwzaBeEZS6J8TLLhPaw_SAul0IYK";

// 메세지 객체로 형식은 to 와 notification (단, title이 유일한 경우 바꿀수도 있음)
var msg = {
	to : "",
	notification : {
		title : "테스트용",
		body : ""
	}
};

var server = http.createServer(function(request, response){
	if(request.url == "/sendNotification"){
		// post 메세지 수신
		var postdata = ""
		request.on("data",function(data){
			postdata += data;
		});

		// 메시지 수신 완료
		request.on("end",function(){
			//json 스트링을 객체로 변환
			var postObj = JSON.parse(postdata);
			// 메시지 데이터를 완성
			msg.to = postObj.to;
			msg.notification.body = postObj.msg;
			// 메시지를 fcm 서버로 전송
			httpUrlConnection(
				// http 메시지 객체
				{
					url : fcmServerUrl,						// 정의한 url
					method : "POST",						// method는 항상 post로
					headers : {								// header에는 아래 두가지 이용
						"Authorization" : "key="+serverKey,
						"Content-Type" : "application/json"
					},										// body에는 msg를 string화하여 담는다.
					body : JSON.stringify(msg)
				},
				// 콜백 함수
				function(error, answer, body){
					var result = {
						code : answer.statusCode,
						msg : body
					};
					response.writeHead(200,{"Content-Type":"plain.text"});
					response.end(JSON.stringify(result));
				}
			);
		})

	} else {
		response.end("404 page not found!!!")
	}
});

server.listen(9000,function(){
	console.log("server is running...")
});