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
    console.log(" 호출됨 ");
    // 방 이름을 저장할 변수
    var roomName = null;
    console.log("roomName : " + roomName);

    // join 이벤트
    socket.on('join', function (data) {
        roomName = data;
        console.log("room의 data : " + data);
        socket.join(data);
    });

    // message 이벤트
    socket.on('message', function (data) {
        console.log("message 발생 / " + 'roomName :' + roomName);
        io.sockets.in(roomName).emit('message', 'test');
    })
});