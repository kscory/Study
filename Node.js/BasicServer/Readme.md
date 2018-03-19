# BasicGrammar&Server
  - BasicServer (기본 서버 세팅 및 기본 예제)
    - RestApi 설계
    - anagram 예제

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

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Node.js/BasicServer/picture/ana.png)

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
  ### 1. 위에서의 참고
  - 파비콘 호출때문에 웹브라우저에서 요청시 서버가 두번씩 호출된다.
  - `decodeURI(주소)`와 다르게 (일부 문자만 변환)
    - `decodeURIComponent(주소)` : %20 등의 모든 주소문자를 원래 문자로 변환
    - `encodeURIComponent(문자)` : 모든 주소 문자를 사용할 수 있는 문자열로 변환
  - html 형식에서 출력시  `\n`이 아니라 `</br>`로 출력 한다.
