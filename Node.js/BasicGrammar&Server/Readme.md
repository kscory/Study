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
  ### 1.
  - 내용

  > dfdf.java

  ``java

  ```

---

## 참고 개념
  ### 1. Node.js 특징
  - 자바스크립트 기반의 서버 프레임워크
  - Callback 함수를 많이 사용한다.
  - 자바스크립트는 자바가 중간에 .class를 한번더 컴파일하는 것과 다르게 바로 코드를 엔진이 네이티브로 컴파일 후 사용한다.
