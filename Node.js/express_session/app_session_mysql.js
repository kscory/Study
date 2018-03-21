var express = require('express');
var session = require('express-session');
var MySQLStore = require('express-mysql-session')(session);
var app = express();


app.use(session({
    secret: 'kanfknenknfsdnfk',
    resave: false,
    saveUninitialized: true,
    store: new MySQLStore({
        host: 'localhost',
        port: 3306,
        user: 'root',
        password: 'mysql',
        database: 'nodetest'
    })
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