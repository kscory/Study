# MySQL 기본
- MySQL 설치 및 커넥션, 종료
- CRUD 기본 - Select / Insert / Update / Delete 사용법
- 웹 서비스에 적용예시
- [Node.js 의 MySQL 문서](https://github.com/mysqljs/mysql)

---

## MySQL 설치 및 커넥션
  ### 1. MySQL 설치 및 모듈 호출
  - 설치 : `npm install mysql`
  - 호출 : `require('mysql')`

  ### 2. MySQL 커넥션 및 종료
  - `createConnection` 을 통해 연결할 수 있다.
    - host, port, user, password, database, debug 등을 object로 넣어준다.
  - `connect()` 는 콜백함수로 error를 가져올 수 있다.
  - `end()` 는 콜백함수로 error 를 가져올 수 있다.
    - 종료하는 방법으로 `destroy()` 도 존재하면 콜백함수는 존재하지 않으며 에러가 발생해도 종료시킨다.
  - 주의 : Node.js 에서는 한번 끊어진 커넷션을 다시 연결하 수 없다.
  - 커넥션.connect 혹은 end 를 안해도 된다...

  ```javascript
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

  /* 로직 */

  conn.end(function (error) {
      // The connection is terminated now
  });
  ```

---

## CRUD 기본
  ### 1. Select
  - sql 문을 정의한다.
  - 이를 query 메서드에 넣어서 콜백함수로 받아 처리할 수 있다.
    - `rows` : 여기에 넣은 데이터값들이 나올 수있다. (아래에서는 author의 데이터 값들을 불러온다.)
  - send로 응답 할 때는 end() 사용에 있어 주의해서 사용하도록 한다.

  ```javascript
  var select = function(con){
      conn.connect();
      var sql = 'SELECT * FROM topic';
      conn.query(sql, function(err, rows, fields){
          if(err){
              console.log(err);
              conn.end();
          } else {
              for(var i=0; i<rows.length; i++){
                  console.log(rows[i].author);
              }
          }
          conn.end();
      });
  };
  ```

  ### 2. Insert
  - `?` 토큰 : 이를 이용하여 배열요소를 순서대로 입력할 수 있으며, 보안에도 이게 효과적
    - params 와 같은 배열 요소를 sql 뒤의 인자에 넣어 준다.
  - query에서 실행

  ```javascript
  var params = ['Supervisor', 'Watcher', 'graphittie'];
  var insert = function(connection, params){
      connection.connect();
      var sql = 'INSERT INTO topic (title, description, author) VALUES(?, ?, ?)';
      conn.query(sql, params,  function(err, rows, fields){
          if(err){
              console.log(err);
              connection.end();
          } else {
              console.log(rows.insertId);
          }
      });
      connection.end();
  };
  ```

  ### 3. Update
  - update 의 쿼리문 사용하며 수정할 인자를 배열 요소로 넣어준다.

  ```javascript
  var params = ['NPM', 'leezche', 1];
  var update = function(connection, params){
      connection.connect();
      var sql = 'UPDATE topic SET title=?, author=? WHERE id=?';
      conn.query(sql, params, function(err, rows, fields){
          if(err){
              console.log(err);
              connection.end();
          } else {
              console.log(rows);
          }
      });
      connection.end();
  };
  ```

  ### 4. Delete
  - 위와 거의 동일한 방식으로 작동

  ```javascript
  var params = [1];
  var deletes = function(connection, params){
      connection.connect();
      var sql = 'DELETE FROM topic WHERE id=?';
      conn.query(sql, params, function(err, rows, fields){
          if(err){
              console.log(err);
              connection.end();
          } else {
              console.log(rows);
          }
      });
      connection.end();
  };

  ```

---

## 참고 - 웹 서비스에 적용
  ### 1. Get
  - GET 으로 요청시 Select 문을 호출
  - 그 외에 특정 페이지를 호출할 수 있다. (다른 의미)
  - connection 을 끊지 않았다.. 만들고 싶다면 새로운 커넥션을 생성시켜 사용한다.

  ```javascript
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
  ```

  ### 2. PUT
  - POST, DELETE 는 body를 포함하므로 body-parser 를 사용

  ```javascript
  var bodyParser = require('body-parser');
  app.use(bodyParser.urlencoded({ extended: false }));
  app.use(bodyParser.json());

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
  ```
