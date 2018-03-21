var express = require('express');
var bodyParser = require('body-parser');
var mysql = require('mysql');

var app = express();

app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json());

// 데이터베이스
var conn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'mysql',
    database: 'nodetest'
});

app.get('/collection/:id', function(req, res){
    var result = {
        code : 200,
        msg : "정상처리",
        data : ""
    };
    var collection_id = req.params.id;
    var sql = 'SELECT * FROM collection WHERE idcollection = ' + collection_id;
    conn.query(sql, function(err, rows, fields){
        if(err){
            console.log(err);
            conn.end();
            throw err;
        } else {
            result.data = rows;
            res.send(JSON.stringify(result));
        }
    });
});

app.put('/collection/:id',function(req,res){
    var result = {
        code : 200,
        msg : "정상처리",
        data : ""
    };
    var collection_id = req.params.id;
    var body = req.body;
    console.log(body);
    var sql = 'UPDATE collection SET idname = ?, sample = ?, content = ? WHERE idcollection = ?';
    var params = [body.idname, body.sample, body.conntent, collection_id];
    conn.query(sql, params, function(err, rows, fields){
        if(err){
            console.log(err);
            conn.end();
            throw err;
        } else {
            result.data = rows;
            res.send(JSON.stringify(result));
        }
    });
});