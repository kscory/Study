// session 은 쿠키를 좀 더 발전시킨 것
// 쿠키로 사용자의 id(식별자) 만 사용자 브라우저에 저장하고 나머지는 서버쪽에 실제 데이터를 저장한다.
// 여기서 브라우저는 식별자만 서버로 보내고, 이를 식별한 서버가 그 사용자를 인식하고 데이터를 처리한다.
// 즉, 쿠키는 웹 브라우저에 정보를 저장하는 기술, 세션은 서버에 정보를 저장하는 기술이라고 생각

var express = require('express');
var session = require('express-session'); // express-session 모듈은 메모리에 저장시켜준다. => 서버를 끄면 세션이 리셋된다.
var app = express();

// secret : session id 를 심을 때 쿠키 세팅과 비슷하게 심어준다. (랜덤으로 설정...)
// resave : 사용자가 접속할때 마다 session 값을 바꾸는지 (권장값 : false)
// saveUninitialized : session id 를 세션을 사용하기 전까지 발급하지 말아라 (권장값 : true)
app.use(session({
    secret: 'kanfknenknfsdnfk',
    resave: false,
    saveUninitialized: true
}));

app.get('/count', function(req, res){
    if(req.session.count) {
        req.session.count ++;
    } else {
        req.session.count = 1;
    }
    res.send('count : ' + req.session.count);
});

app.get('/tmp', function(req, res){
    res.send('result : ' + req.session.count);
});

app.listen(9000, function(){
    console.log("server is running (9000)")
});