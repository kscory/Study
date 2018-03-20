# URL 활용
- ※ 설명은 이전 내용이랑 겹치므로 간단히 되어 있음(주로 사용법 위주)
- Querystring
- Semantic Url
- Restful 웹 서비스

---

## Querystring
  ### 1. querystring 이란
  - queryString 이란 url을 전달하는 국제적인 약속
  - 동적으로 작동할 수 있도록 프로그래밍적으로 만들 수 있다.
  - `localhost/9000/topic?id=2` 의 형태로 호출하는 것을 의미

  ### 2. Express 에서 querystring 사용하기
  - `request.query.매개변수` 를 통해 매개변수를 가져올 수 있다.
  - `${값}` 을 통해 밖의 값을 가져온다.

  ```javascript
  app.get('/topic',function(req, res){
      // 동적으로 작동할 수 있도록 프로그래밍 적으로 만들 수 있다.
      // queryString 은 url 을 전달하는 국제적인 약속
      var topics = [
        'JavaScript is ...',
        'NodeJs is ...',
          'Express is...'
      ];
      var output = `
      <a href="/topic?id=0">JavaScript</a><br>
      <a href="/topic?id=1">Nodejs</a><br>
      <a href="/topic?id=2">Express</a><br><br>

      <!--request 의 queryString에 있는 id 를 불러올 수 있다.-->
      ${topics[req.query.id]}
      `
     // res.send(req.query.id); // ?id=xxx 인 경우 xxx 를 의미
      res.send(output);
  });
  ```
---

## Semantic Url
  ### 1. Semantic Url 이란
  - 좀 더 url 을 보기 편리하게 만들어 준다.
  - `localhost:9000/topic/2` 의 형태로 호출하는 것을 의미한다. (cf> localhost/9000/topic?id=2)

  ### 2. Express 에서 Semantic Url 사용하기
  - `request.params.매개변수` 를 통해 매개변수를 가져올 수 있다.
  - `${값}` 을 통해 밖의 값을 가져온다.
  - 두개 이상의 매개변수 사용시 `:id/:mode` 와 같이 이용할 수 있다.

  ```javascript
  app.get('/topic2/:id', function (req, res) {
      var topics = [
          'JavaScript is ...',
          'NodeJs is ...',
          'Express is...'
      ];
      var output2 = `
      <a href="/topic2/0">JavaScript</a><br>
      <a href="/topic2/1">Nodejs</a><br>
      <a href="/topic2/2">Express</a><br><br>

      <!--semantic url을 params 를 통해서 가져올 수 있다.-->
      ${topics[req.params.id]}
      `

      res.send(output2);
  });

  app.get('/topic/:id/:mode', function(req, res){
      res.send(req.params.id+','+req.params.mode)
  });
  ```

---
## Restful 웹서비스
  ### 0. Restful 웹서비스란
  - Representational State Transfer 규정을 맞춰 만든 웹서비스를 의미
  - 웹 서비스 인터페이스 설계를 위한 것으로 하나의 약속이다.
  - 웹 서비스 구조는 GET / POST / PUT / DELETE 가 존재
    - Get은 Url 에 모두 포함되며 나머지는 body 를 가지고 있다.
    - 구조는 [여기 참고개념](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/ReadFile)을 참고
  - body의 경우 body-parser 를 통해 가져올 수 있다. - [공식](http://expressjs.com/en/resources/middleware/body-parser.html), [정리](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/express_start_middleware) 참고

  ### 1. Get 방식
  - 특정 요소를 조회 혹은 가져온다.
  - ex> `/collection/:id` : 컬렉션의 id 값을 조회
    - `/collecion/777` : 777 번 컬렉션을 조회
    - `/collecion` : 컬렉션 전체를 조회

  ```javascript
  app.get('/collecion', function(req, res){
      /* 로직... */
      response.send(/* ... */)
  });

  app.get('/collecion', function(req, res){
      /* 로직... */
      response.send(/* ... */)
  });
  ```

  ### 2. Post 방식
  - 새로운 데이터를 추가한다. (예외적으로 로그인 같은 경우에는 Post를 이용한다.)
  - ex> `/collection` : 컬렉션에 새로운 데이터를 추가(/colleciton/:id 와 같이 사용하지는 않는다.)
    - `/collection` : 컬렉션을 추가

  ```javascript
  app.post('/collecion', function(req, res){
      // 변수를 바디파서를 통해 가져옴
      var name = req.body.name // body의 name 요소를 가져옴
      var spec = req.body.spec
      // 유효성 검사 (위의 변수가 있을 때만 실행 시켜야 하므로...)
      if(name & spec){
          //로직
      } else {
          //에러
          throw new Error('error');
      }
  });
  ```

  ### 3. Put 방식
  - 특정 요소를 한꺼번에 수정한다
  - ex> `/collection/:id` : 컬렉션의 특정 id에 맞는 요소를 모두 수정
    - `/collection/777` : 777번 컬렉션을 수정

  ```javascript
  app.put('/collecion/:id', function(req, res){
      // 변수를 바디파서를 통해 가져옴
      var id = req.params.id;
      var name = req.body.name // body의 name 요소를 가져옴
      var spec = req.body.spec

      /* 데이터베이스 id에 일치하는 것을 수정하는 로직 */

      response.send(item); // item은 데이터베이스에서 가져온 레코드
  });
  ```

  ### 1. Delete 방식
  - 특정 요소를 삭제한다.
  - ex> `/collection/:id` : 컬렉션의 특정 id에 맞는 요소를 삭제
    - `/collection/777` : 777번 컬렉션을 삭제

  ```javascript
  app.delete('/collecion/:id', function(req, res){
      var id = req.params.id

      /* 삭제 로직 */

      res.send( /* 삭제시 보낼 요소 ex> true */ );
  });
  ```
