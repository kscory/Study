# BasicGrammar&Server
  - BasicGrammar (기본문법)
  - BasicServer (기본 서버 세팅 및 기본 예제)

---

## BasicGrammar
  ### 0. 실행 방법 (콘솔에서 사용)
  - window의 경우 powershell 혹은 cmd를 키고 `cd 경로` 입력(디렉토리 설정)
  - `node 파일이름` 으로 실행
  - `npm` : node program manager로 install로 설치 가능
  - `ctrl+c` 로 exit

  > 실행방법

  ```
  cd C:\workspaces\node.js\fc4\test
  node test
  ```

  ### 1. 변수의 선언 & 콘솔에 로그 출력
  - 변수의 선언 : `var + 변수명;`
  - 콘솔에 로그 출력 : `console.log("출력");`

  ```javascript
  // 변수 선언 예시
  var a = 10;
  var b = study;
  var c = [ "Will", "James", "David", "Sam"];
  var d = new Array("Will", "James", "David", "Sam");
  var e = c[2];

  // 콘솔에 로그 출력
  console.log(a);
  ```

  ### 2. 반복문 : for / forEach(향상된 for문)
  - 자바와 비슷하게 `for(i=0 ; i<10 ; i++){};` 사용
  - 차이 : for문 안에 int를 생략한다.
  - 배열에서의 일반 for문은 배열의 index를 리턴
  - 향상된 for문은 배열안의 실제 item을 리턴

  ```javascript
  // 일반 for 문
  for(i=0 ; i<10 ; i++){
    console.log(i);
  }

  // 배열에서의 일반 for문
  var array = [ "Will", "James", "David", "Sam"];
  for(i in array)
    response.write(" [" + array[i] + "] ");

  // 향상된 for문
  array.forEach(function(item){
    response.write(" [" + item + "] ");
  }
  ```

  ### 3. 조건문
  - `if(비교){} else if(비교) else {}` 로 동일
  - === : 값도 같고 타입도 같은경우 이용한다. ( javascript의 경우 ==을 사용할때 10=="10" 을하면 같다고 나온다.)

  ```javascript
  var a = 5;
  var b = "5";
  if(a===b){
    console.log(값, 타입 동일);
  } else if(a==b){
    console.log(값 동일, 타입 다름)
  } else{
    전부 다름
  }
  ```

  ### 4. 문자열의 기본 연산
  - `"문자열" + "문자열" = "문자열문자열"` 로 동일

  ```javascript
  var a = javascript;
  var b = Study;
  var c = a + " " + b; // c출력시 javascript Study 출력
  ```

  ### 5. 함수 사용하기
  - 파라미터에 타입이 없으며 이는 문장내에서 return이 있는지 여부에 따라서 결정된다.
  - function을 이용하며 함수를 만드는 방법에는 두가지가 존재한다.

  ```javascript
  // 함수 만들기 1
  function sum(param1, param2){
    return param1 + param2; // <= 리턴타입이 있는 경우
    // console.log(param1 + param2) // <= 리턴타입이 없는 경우
  }

  // 함수 만들기 2
  var sum = function(param1,param2){
    return param1 + param2;
  }
  ```

  ### 6. 클래스의 사용
  - 함수 만드는것과 동일하지만 이름의 첫번째를 대문자로 하는 것이 통용되는 규칙
  - this 사용에 따라 private, public이 결정됨

  ```javascript
  function Num(param1,param2){
    var a = 0; // private 선언된 변수 : 외부에서 접근 안됨
    this.b = 10; // public 으로 선언된 변수 : 외부에서 접근가능
    this.c = function(param1,param2); // 클래스의 함수
  }

  // 클래스의 사용
  var num = new Num("Hello", 157);
  num.b=500;
  num.c("a",49949);
  ```

  ### 7. javascript의 object
  - object에서는 var도 붙이지 않고 콜론을 붙이게 된다.
  - object 내의 함수일 경우 기능이라는 것을 알려주기 위해 function을 사용
  - 함수의 경우 ()를 써줘야 실행하며, 안할 경우 코드를 넘긴다. (자바스크립트의 특징)

  ```javascript
  var objectEx = {
    one : 157,
    two : "hello",
    three : 5
    sum : function(){
      return one + three;
    }
  };
  console.log(request.one);
  console.log(request.two);
  console.log(request.sum()); // ()여부에 따라 달라짐=> sum vs sum()
  ```

  > 피보나치 예시

  ```javascript
  var fibonacci = {
    count : 2,
    run : function(){
      result = "";
      prev = 0;
      result += prev + ",";
      next = 1;
      result += next + ",";
      for(i=0 ; i<this.count ; i++){
        sum = prev+next;
        result +=sum + ",";
        prev = next;
        next = sum;
      }
      return result;
    }
  };
  // 서버 구현후 실행
  res.end(fibonacci.run());
  ```

---

## BasicServer
  ### 1. Rest Api 설계
  - /서비스명/값 으로 호출
  - ① 서버 모듈(라이브러리)을 import
  - ② 서버 모듈을 사용해서 서버를 정의(주소체계 확인 및 로직 구현)
    - `request`에 사용자 요청정보가 담겨있다.
    -  `response.write(응답 정의)` : 사용자 요청에 대해 어떻게 응답할지를 정의
    - `response.end(종료);` 호출시 응답을 끝낸다.
  - ③ 서버 실행

  ```javascript
  // 1. 서버 모듈(라이브러리)을 import
  var http = require("http");

  // 2. 서버 모듈을 사용해서 서버를 정의
  var server = http.createServer( function(request, response){  
    var array = request.url.split("/");
    response.end(text+"");
  }

  // 3. 서버 실행
  server.listen(8089, function(){
    console.log("server is running...");
    // 8089포트에 연결
    // fucntion : 콜백이 있으면 호출, 아니면 띄어만 준다.
  });
  ```

  ### 2. 서버 설계 후 피보나치와 아나그램 함수를 호출 예제
  - `/피보나치/100` 호출시 `숫자배열` 출력
  - `/아나그램/acdagc,Aefbv` 호출시 `true or false` 출력
  - split을 사용해서 url을 분리 ( ex> `var array = request.url.split("/");`)
  - 주소체계 확인
  - `isNaN` : 숫자인지 확인하는 함수 (숫자가 아니면 true 반환_is not a number?)
  - `decodeURI(주소)` : %20 등의 일부 주소문자를 원래 문자로 변환
  - `response.writeHead(200, {'Content-Type':'타입명'});` : 읽을 타입을 정해준다.
  - 출력 예시

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/BasicGrammar%26Server/picture/ana.png)

  ```javascript
  // 1. 서버 모듈(라이브러리)을 import
  var http = require("http");

  // 2. 서버 모듈을 사용해서 서버를 정의
  var server = http.createServer( function(request, response){
    // 1. 요청이 온 주소체계가 내가 제공하는 api 구조와 일치하는지 확인
    var cmds = decodeURI(request.url).split("/");
    response.writeHead(200, {'Content-Type':'text/html'});
    // 2. 주소체계가 잘못되었다면 알려준다.
    if(cmds.length < 3){
      response.end("<h>Your request is wrond!!!</h>");
    // 3. 주소체계가 정상이면 실행
    } else {
      if(cmds[1] == "fibonacci"){
        if(!isNaN(cmds[2])){
          var text = getFibo(cmds[2]);
          response.end(text);
        } else{
          response.end("<h>Value is Wrong!!!</h>");
        }
      } else if(cmds[1] == "anagram"){
        var text = getAnagram(cmds[2],cmds[3]);
        response.end(text+"");
      } else {
        response.end("<h>No Service!!!</h>");
      }
    }
  });

  // 3. 서버 실행
  server.listen(8089, function(){
    console.log("server is running...");
  });

  // 피보나치 알고리즘
  function getFibo(count){
    var result = "";
    var prev=0 ; next=1;
    result = result + 0 +"</br>";
    result = result + 1 +"</br>";
    for(var i=2 ; i<count ; i++){
      var sum = prev+next;
      result = result + sum +"</br>";
      prev=next;
      next=sum;
    }
    return result;
  }

  // 아나그램 알고리즘
  function getAnagram(text1, text2){
    var text1Small = text1.replace(" ","");
    var text2Small = text2.replace(" ","");
    text1Small = text1Small.toLowerCase();
    text2Small = text2Small.toLowerCase();
    var text1Arr = text1Small.split("");
    var text2Arr = text2Small.split("");
    text1Arr.sort();
    text2Arr.sort();
    for(i in text1Arr){
      if(text1Arr[i]!=text2Arr[i]){
        return false;
      }
    }
    return true;
  }
  ```

---

## 참고 개념
  ### 1. Node.js 특징
  - 자바스크립트 기반의 서버 프레임워크
  - Callback 함수를 많이 사용한다.
  - 자바스크립트는 자바가 중간에 .class를 한번더 컴파일하는 것과 다르게 바로 코드를 엔진이 네이티브로 컴파일 후 사용한다.

  ### 2. 위에서의 참고
  - 파비콘 호출때문에 웹브라우저에서 요청시 서버가 두번씩 호출된다.
  - `decodeURI(주소)`와 다르게 (일부 문자만 변환)
    - `decodeURIComponent(주소)` : %20 등의 모든 주소문자를 원래 문자로 변환
    - `encodeURIComponent(문자)` : 모든 주소 문자를 사용할 수 있는 문자열로 변환
  - html 형식에서 출력시  `\n`이 아니라 `</br>`로 출력 한다.
