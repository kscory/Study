module.exports = function(app){
    var express = require('express');
    var router = express.Router(); // 라우터를 하나 만들었다

    router.get('/r1', function(req, res){ // r1 만 해도 p1 을 라우터로 했으므로 /p1/r1 으로 하면 연결이 된다.
        res.send('Hello /p1/r1');
    });
    router.get('/r2', function(req, res){
        res.send('Hello /p1/r2');
    });

    app.get('/p3/r1', function(req, res) {
        res.send("hello / p3/r1");
    });

    return router;
};