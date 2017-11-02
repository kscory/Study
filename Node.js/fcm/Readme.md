# Firebase Cloud Messaging 서버 코드
  - Node.js 서버를 실행시켜 firebase에 연결
  - 코드를 firbase에 deploy시켜 연결
  - [참고(BasicFirebase4_firebase Clouding Messaging)_반드시 먼저 읽어야 할듯... ](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/BasicFirebase4)
  - firebase function 모듈은 올리지 않음

---

## 개념도
- 아래와 같이 ServerKey와 함께 FirebaseServer로 메시지를 보내는 서버를 만든다.
- 두 가지 방법이 존재

![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/fcm/picture/concept.png)


---

## 방법1 - Node.js 서버를 실행시켜 firebase에 연결 (fcmNode 폴더)
  ### 1. 코드
  - request 모듈을 다운 (npm install request)
  - 형식이 정해져 있으며 msg의 body에 Obj의 msg를 담고 있다.

  ```javascript
  var http = require("http");
  var httpUrlConnection = require("request");

  // 서버 측 변수
  // fcm 서버 url로 정해져 있는 값(url)
  const fcmServerUrl = "https://fcm.googleapis.com/fcm/send";
  // firebase project에 들어가서 고유 서버키값을 복붙
  const serverKey = "고유 서버키";

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
            url : fcmServerUrl,     // 정의한 url
            method : "POST",        // method는 항상 post로
            headers : {             // header에는 아래 두가지 이용
              "Authorization" : "key="+serverKey,
              "Content-Type" : "application/json"
            },										
            body : JSON.stringify(msg) // body에는 msg를 string화하여 담는다.
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
  ```

  ### 2. 결과
  - 메세지를 보내면 noti가 간다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/fcm/picture/result1.png)


---

## 방법2 - firebase 서버에 deploy 시켜 연결 (fcmByFunction 폴더)
  ### 1. 코드
  - 선언되어야 하는 상수들을 선언
  - exports를 선언하여 공개하고 함수명을 설정
  - 형식이 정해져 있는 것이 많으니 참고할 것

  ```javascript
  // firebase에서 이용
  const fun = require("firebase-functions"); // firebase function 을 상수로 선언 (상수는 const)
  const admin = require("firebase-admin"); // admin을 선언

  admin.initializeApp(fun.config().firebase);

  exports.addMessage = fun.https.onRequest((req, res)=>{		//"function(req, res)"를 단축하는 것(람다식)이 "(req,res)=>"
    // http 요청에서 ? 다음에 있는 변수중에 text 변수 값을 가져온다.
    var text = req.query.text;
    // 파이어베이스 db의 message 레퍼런스에 그 값을 넣는다.
    admin.database().ref('/message') // message 디렉토리에 msg를 보낸다.
      .push({msg:text})  // // msg를 key로 하여 text를 넣는다. (msg는 고정된 값)
      .then(snapshot=>{ // snapshot은 그냥 변수명 (aaa 라고 해도 됨) 
        // res.redirect(303, snapshot.ref); // 이는 스냅샷의 링크(http url connection 이동하는 거)를 나에게 돌려주는 것
        res.end("sucess!!!");
      });
  });

  exports.sendNotification = fun.https.onRequest((req, res)=>{
    // json 데이터로 post 값을 수신
    var dataObj = req.body;
    var msg = {
      notification : {
        title : "노티바에 나타나는 타이틀",
        body : dataObj.msg
      }
    };
    var tokens = [];
    tokens.push(dataObj.to);
    admin.messaging().sendToDevice(tokens, msg)
      .then(function(response){
        res.status(200).send(response);
      })
      .catch(function(error){
        res.status(501).send(error);
      });
  });
  ```

  ### 2. 결과
  - 결과는 동일하지만 보내는 링크를 firebase에서 지정한 url을 이용한다.