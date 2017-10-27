# MongoDB를 이용하여 간단한 게시판 구축
  - module화 방법 및 사용법
  - 간단한 게시판 서버 구성
    - server.js (서버 연결)
    - route (형식을 파악해서 로직 요청)
    - controller (데이터를 컨트롤한 후 dao에 요청)
    - dao (CRUD 구현)

---

## module화 방법 및 사용법 & MongoDB 테이블 사용 방법
  ### 1. module화 방법
  - 보통 디렉토리를 따로 만들어 작성한다.
  - 만약 이름이 index 라면 디렉토리만 호출하면 파일을 호출한다. ( gateway를 index로 준다.)
  - 외부에서 이용할 변수 및 함수는 `exports`를 붙여준다. (대부분 동일하지만 코틀린은 반대.. 참고)

  ```javascript
  // public
  exports.a=157;
  exports.sum = function(a,b){
  	return a+b;
  }
  // private
  var b = 137;
  function sum(a, b){
  	return a+b;
  }
  ```

  ### 2. module 사용방법
  - `require` 을 통해 module을 선언해준다. (module로 따로 작성하면 `./`와 같이 경로를 지정해주어야 한다. - 참고로 "."의 개수는 상위디렉토리로 이동)

  ```javascript
  var module = require("./module"); // 경로 조심할 것

  var x = 300;
  var y = 750;
  console.log(module.sum(x,y));
  ```

  ### 3. MongoDB의 table 사용 방법
  - 사용방법1 : new로 선언해서 사용(형식을 index에 만든다.)
  - 사용방법2 : 테이블을 직접 넘긴다.

  ```javascript
  var bbs = require("./db");

  // 사용방법
  //방법 1. new해서 사용하는 방법 (형식을 index에 만들어서 사용)
  var bbsTable = new bbs.Bbs();

  // 방법 2. 테이블을 직접 넘기는 방법
  var table = {
  	no : 147,
  	title : "제목",
  	content : "내용",
  	date : "날짜",
  	user_id : "root"
  };  
  ```

---

## 간단한 게시판 서버 구성
  ### 0. 게시판 서버 설계
  - 그림

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/server_bbs/picture/bbs.png)

  ### 1. server.js

  ### 2. a_route/index.js

  ### 2. b_controller/bbs.js

  ### 3. c_data/bbs.js

---

## 참고 문헌
  ### 1. [링크]()
