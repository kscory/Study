var express = require('express');
// cookie-parser 미들웨어 사용
// 쿠키는 같은 웹브라우저에서만 사용 가능하다는 것 주의!!
var cookieParser = require('cookie-parser');

var app = express();
app.use(cookieParser()); // cookie Parser 사용

app.get('/count', function (req, res){
    // count 라는 변수가 있다면 저장..
    if(req.cookies.count) {
        var count = parseInt(req.cookies.count); // 모두 string 으로 오므로 강제로 int로 바꿈
    } else {
        var count = 0;
    }
    count += 1;
    res.cookie('count', count); // 웹브라우저- 네트워크의 set-cookie 에 count=1 이라는 값을 저장한다.
    res.send('count : ' + count); // 내가 지정한 count 라는 변수를 가져올 수 있다.
});

app.listen(9000, function(){
    console.log("server is running (9000)")
});