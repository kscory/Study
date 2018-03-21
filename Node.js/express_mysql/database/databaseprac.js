var mysql = require('mysql');
var conn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '111111',
    database: 'o2'
});
conn.connect(function (error) {
    if (error) {
        console.log('error connecting: ' + error.stack);
        return;
    }
});

var select = function(connection){
    connection.connect();
    var sql = 'SELECT * FROM topic';
    conn.query(sql, function(err, rows, fields){
        if(err){
            console.log(err);
        } else {
            for(var i=0; i<rows.length; i++){
                console.log(rows[i].author);
            }
        }
    });
    connection.end();
};

var params = ['Supervisor', 'Watcher', 'graphittie'];
var insert = function(connection, params){
    connection.connect();
    var sql = 'INSERT INTO topic (title, description, author) VALUES(?, ?, ?)';
    conn.query(sql, params,  function(err, rows, fields){
        if(err){
            console.log(err);
        } else {
            console.log(rows.insertId);
        }
    });
    connection.end();
};

var params = ['NPM', 'leezche', 1];
var update = function(connection, params){
    connection.connect();
    var sql = 'UPDATE topic SET title=?, author=? WHERE id=?';
    conn.query(sql, params, function(err, rows, fields){
        if(err){
            console.log(err);
        } else {
            console.log(rows);
        }
    });
    connection.end();
};

var params = [1];
var deletes = function(connection, params){
    connection.connect();
    var sql = 'DELETE FROM topic WHERE id=?';
    conn.query(sql, params, function(err, rows, fields){
        if(err){
            console.log(err);
        } else {
            console.log(rows);
        }
    });
    connection.end();
};

conn.end(function (error) {
    // The connection is terminated now
});