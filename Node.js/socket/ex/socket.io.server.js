var http = require('http');
var fs = require('fs');
var socketio = require('socket.io');

var server = http.createServer(function(req, res){
    // Html 파일 읽기
    fs.readFile("HTMLPage.html", function(error, data){
        res.writeHead(200, { 'Content-Type' : 'text/html'} );
        res.end(data);
    });
});

server.listen(9000, function(){
   console.log("server is running...")
});


// 소켓 서버를 생성 및 실행
var io = socketio.listen(server);
io.sockets.on('connection', function(socket){
    // 이벤트 연결
    socket.on('rint', function(data){
        // 클라이언트가 전송한 데이터를 출력
        console.log("Client Send Data:", data);
        
        // 클라이언트에 이벤트를 발생
        socket.emit('smart', data);
    })
});

/*
var io = socketio.listen(server);
io.sockets.on('connection', function (socket) {
    // id를 설정
    id = socket.id;

    socket.on('rint', function (data) {
        // public 통신
        io.sockets.emit('smart', data);
    });
});
*/

/*
var io = socketio.listen(server);
io.sockets.on('connection', function (socket) {
    socket.on('rint', function (data) {
        // broadcast 통신
        socket.broadcast.emit('smart', data);
    });
});
*/

/*
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
*/