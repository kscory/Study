# Socket.io 사용하기
  - Socket.io 기본적인 사용방법
  - 소켓 통신 종류별 사용방법
  - 방 생성 방법
  - Express에 적용 방법

---

## Socket.io 기본
  ### 1. Socket 사용 이유
  - Socket 을 이용하면 양방향 통신이 가능해진다. 즉, 클라이언트의 요청이 있기 전에도 서버에서 클라이언트에 데이터를 전송할 수 있게 된다.
    - ex> 채팅 프로그램같은 경우 서버에서 바로 요청해야 한다.
  - Node.js 를 사용하는 이유 중 하나가 바로 Socket.io 를 통해 쉽게 구현할 수 있기 때문
  - soceket.io 는 [롱풀링 방식]()을 사용한다
  - 소켓 통신 종류
    - `public` : 자신을 포함한 모든 클라이언트에 데이터를 전달
    - `broadcast` : 자신을 제외한 모든 클라이언트에 데이터를 전달
    - `private` : 특정 클라이언트에 데이터를 전달

  ### 2. Socket.io 설치 및 사용
  - 설치 : `npm install socket.io`
  - 모듈 추출 : `require(socket.io)`
  - 사용(js) : 소켓서버를 실행한 뒤, 일반 서버와 비슷하게 코드를 작성
  - 사용(html) : script 태그에 소켓을 연결하는 코드 작성

  > 기본 틀 (js)

  ```javascript
  var http = require('http');
  var fs = require('fs'); // html 읽어오기 위해 사용
  var socketio = require('socket.io');

  var server = http.createServer(function(req, res){
      /* 로직 */
  });

  server.listen(9000, function(){
     console.log("server is running...")
  });

  // 소켓 서버를 생성 및 실행
  var io = socketio.listen(server);
  io.sockets.on('connection', function(socket){
      /* 로직 */
  });
  ```

  > html

  ```html
  <!DOCTYPE html>
  <html>
  <head>
      <script src="/socket.io/socket.io.js"></script>
      <script>
          window.onload = function(){
              // 소켓을 연결
              var socket = io.connect();
          };
      </script>
      <title>Title</title>
  </head>
  <body>

  </body>
  </html>
  ```

  ### 3. 웹 소켓 이벤트
  - socketio 이벤트
    - `connection` : 클라이언트가 연결할 때 발생
    - `disconnect` : 클라이언트가 연결을 해제할 때 발생
  - socketio 메서드
    - `on()` : 소켓 이벤트를 연결
    - `emit()` : 소켓 이벤트를 발생
  - 아래 예제에서 rint 와 smart 는 임의로 정한 것(예시)
    - rint 는 클라이언트 -> 서버 로 전송한 이벤트 명
    - smart 는 서버 -> 클라이언트 로 전달한 이벤트 명

  > javascript 에서 소켓 연결

  ```javascript
  io.sockets.on('connection', function(socket){
      // 이벤트 연결
      socket.on('rint', function(data){
          // 클라이언트에 이벤트를 발생
          socket.emit('smart', data);
      })
  });
  ```

  > html scrip 태그 에서 소켓 연결

  ```html
  <script>
         window.onload = function(){
             // 소켓을 연결
             var socket = io.connect();
             // 소켓 이벤트를 연결
             socket.on('smart', function (data) {
                 alert(data);
             });

             // 문서 객체 이벤트를 연결
             document.getElementById('buuton').onclick = function () {
                 // 변수를 선언
                 var text = document.getElementById('text').value();
                 // 데이터를 전송
                 socket.emit('rint', text);
             };
         };
  </script>
  ```

---
## 2. 소켓 통신 종류별 작성
  ### 1. public
  - `io.sockets` 객체의 `emit()` 메서드 사용

  ```javascript
  var io = socketio.listen(server);
  io.sockets.on('connection', function(socket){
      // 이벤트 연결
      socket.on('rint', function(data){
          // public 통신
          io.sockets.emit('smart', data);
      })
  });
  ```

  ### 2. broadcast
  - `socket` 객체의 `broadcast` 속성을 사용
  - `broadcast` 객체의 `emit()` 메서드 사용

  ```javascript
  var io = socketio.listen(server);
  io.sockets.on('connection', function(socket){
      // 이벤트 연결
      socket.on('rint', function(data){
          // broadcast 통신
          socket.broadcast.emit('smart', data);
      })
  });
  ```

  ### 3. private
  - `connection` 이벤트 발생 시 `socket` 객체의 `id` 속성 저장
  - `io.sockets` 객체의 `to()` 메서드로 특정 `id` 를 선택하여 `emit()` 메서드 호출

  ```javascript
  var id = 0;
  var io = socketio.listen(server);
  io.sockets.on('connection', function (socket) {
      // id를 설정
      id = socket.id;

      socket.on('rint', function (data) {
          // private 통신
          io.sockets.to(id).emit('smart', data);
      });
  });
  ```

---

## 3. 방 생성
  ### 1.방 생성 기본
  - 방을 생성하는 메서드
    - `socket.join()` : 클라이언트를 방에 넣는다.
    - `io.sockets.in() / io.sockets.to()` : 특정 방에 있는 클라이언트를 추출 (private 통신을 통해 특정 방에 있는 멤버에게만 전달)
  - 아래 코드는 방번호가 null 로 세팅될 수 있으므로 프런트에서 백으로 보낼 때 방을 지정해주고 보내줘야 한다. (생성 O, but> null 값 조심)
  - 서버에서 보낸 특정한 값에 대입되어 html 에서 실행!!

  ```javascript
  // 모듈 추출
  var fs = require('fs');

  // 서버 생성
  var server = require('http').createServer();
  var io = require('socket.io').listen(server);

  // 서버 실행
  server.listen(9000, function () {
     console.log('Server is running...')
  });

  // 웹 서버 이벤트 연결
  server.on('request', function (req, res) {
      // Html 파일 읽기
      fs.readFile("HTMLPage.html", function(error, data){
          if(error){
              console.log("error 발생" + error)
          } else {
              res.writeHead(200, {'Content-Type': 'text/html'});
              res.end(data);
          }
      });
  });

  // 소켓 서버 이벤트 연결
  io.sockets.on('connection', function (socket) {
      // 방 이름을 저장할 변수 (조심)
      var roomName = null;

      // join 이벤트
      socket.on('join', function (data) {
          roomName = data;
          socket.join(data);
      });

      // message 이벤트
      socket.on('message', function (data) {
          console.log("message 발생 / " + 'roomName :' + roomName); // roomName이 null 값 나올 수 있다. => html 에서 data 에 방을 넘겨주어야 한다.(session or cookie 이용)
          io.sockets.in(roomName).emit('message', 'test');
      })
  });
  ```

  > html 코드

  ```Html
  <!DOCTYPE html>
  <html>
  <head>
      <meta charset="utf8"/>
      <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
      <script src="/socket.io/socket.io.js"></script>
      <script>
          window.onload = function(){
              // 변수를 선언
              var room = prompt("방 이름을 입력하세요.", '');

              // 소켓을 연결
              var socket = io.connect();

              // 소켓 이벤트를 연결
              socket.emit('join', room);
              socket.on('message', function (data) {
                  $('<p>' + data + '</p>').appendTo('body');
              });

              // 문서 객체 이벤트를 연결
              document.getElementById('button').onclick = function () {
                  socket.emit('message', 'socket.io room message');
              };
          };
      </script>
  </head>
  <body>
      <button id="button">EMIT</button>
  </body>
  </html>
  ```

---

## 참고
  ### 1. express를 이용해 socket 과 연결
  - 웹서버와 소켓서버를 둘다 만들어 이를 연결(attach) 해준다.
  - 만약 연결이 잘 안된다면? socket.io 를 사용할 때 `io` -> `app.io` 로 고쳐본다.
    - `app.io = socketio();` :  이렇게 써서 나중에 bin/www 에 연결하기 쉽게 해준다.
    - `app.io.attach(server);` : 웹서버와 소켓서버 연결

  ```javascript
  var express = require('express');
  var http = require('http');
  var socketio = require('socket.io');

  // 웹 서버와 소켓 서버 생성
  var app = express();
  var io = socketio();
  var server = http.createServer(app);

  // 소켓 서버를 웹 서버에 연결
  io.attach(server);

  /* 로직 작성 */

  // 서버 실행
  server.listen(9000);
  ```
