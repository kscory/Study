// 쿠키는 누군가에 의해서 읽히게 되면 매우 위험해 질 수 있다.
// https 로 통신을 하는 것이 좋다. => 이는 설정을 해주어야 한다.
// 이를 이용하면 쿠키를 암호화 할 수 있다.

var express = require('express');
var cookieParser = require('cookie-parser');

var app = express();
app.use(cookieParser('asdfenjklkvjf123iuhg')); // 임의의 값을 준다(https) => 그러면 임의의 값이 key가 된다.
// 그러면 암호화된 정보를 보내주고 이 key 를 가지고 열쇠를 여 수 있다.

app.get('/count', function (req, res){
    // signedCookies 로 변경 그러면 암호를 키값으로 해독시킨 값을 가져올 수 있다.
    if(req.signedCookies.count) {
        var count = parseInt(req.cookies.count); // 모두 string 으로 오므로 강제로 int로 바꿈
    } else {
        var count = 0;
    }
    count += 1;
    // {signed:true} 값을 주어 response 해준다.
    res.cookie('count', count, {signed:true});
    res.send('count : ' + count);
});

app.listen(9000, function(){
    console.log("server is running (9000)")
});