var express = require('express');
var app = express();

var p1 = require('./routes/p1')(app);
var p2 = require('./routes/p2');

app.use('/p1', p1); // p1 으로 들어오는 경로를 라우터로 요청 !!
app.use('/p2', p2);

app.listen(9000, function(){
    console.log('Server is Running...');
});