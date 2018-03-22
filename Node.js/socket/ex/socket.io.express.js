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


// 연결이 잘 안된다면? 아래와 같이 고쳐보자
// app.io = socketio(); // 이렇게 써서 나중에 bin/www 에 연결하기 쉽게 해준다.
// app.io.attach(server); // 웹서버와 소켓서버 연결