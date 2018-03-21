var express = require('express');
var session = require('express-session');
var FileStore = require('session-file-store')(session);

var app = express();

// store 에 파일 스토어 추가
// session이라는 디렉토리가 생기고 이에 저장되게 된다.
app.use(session({
    secret: 'kanfknenknfsdnfk',
    resave: false,
    saveUninitialized: true,
    store: new FileStore()
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