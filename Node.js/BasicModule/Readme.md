# Basic Module
  - 모듈화 방법
  - 외부 모듈 사용
  - 참고
    - Exoress 사용 정보 숨기기
    - 오래된 패키지 확인

---

## 모듈화 방법
  ### 1. module화 방법
  - 보통 디렉토리를 따로 만들어 작성한다.
  - 만약 이름이 index 라면 디렉토리만 호출하면 파일을 호출한다. ( gateway를 index로 준다.)
  - 외부에서 이용할 변수 및 함수는 `exports`를 붙여준다. (대부분 동일하지만 코틀린은 반대.. 참고)
  - module 화 할 때는 보통 `module.exports = 값` 으로 표시한다.

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

  // 보통 모듈화를 시키면 아래와 같이 모듈이라고 표시함
  function sub(a, b){
    return a-b;
  }
  module.exports = sub;
  ```

  ### 2. module 사용방법
  - `require` 을 통해 module을 선언해준다. (module로 따로 작성하면 `./`와 같이 경로를 지정해주어야 한다. - 참고로 "."의 개수는 상위디렉토리로 이동)

  ```javascript
  var module = require("./module"); // 경로 조심할 것

  var x = 300;
  var y = 750;
  console.log(module.sum(x,y));
  ```

---

## 외부 모듈 사용
  ### 1. supervisor 모듈
  - 코드를 고치고 나서 계속 실행을 반복해야하는데 이를 안하게 해줌
  - 코드를 고치고 파일을 저장하면 자동으로 supervisor 모듈이 서버를 재시작 한다.
  - 설치 : `npm install supervisor`
  - 실행 : `supervisor [파일명]`
  - 차이
    - 기존 : `node [파일명]` => `코드 수정` => `서버 종료` => `node [파일명]`
    - supervisor : `코드수정` 으로 끝

  ### 2. forever 모듈
  - 서버에 예외가 발생하거나 해서 서버가 중단되는것을 막는다.
  - forever 모듈은 서버가 꺼지면 앱을 다시 실행시켜준다.
  - 설치 : `npm install forever`
  - 실행 : `forever start [파일명]`
    - package.json 파일에서 `script` 의 `start` 를 `"forever [파일명]"` 으로 바꾸어 start 를 이용해 실행할 수도 있다. (ex> express)

---

## 참고
  ### 1. Exoress 사용 정보 숨기기
  - 해커에게 Express 가 사용되었다는 것을 숨길 수 있다.
  - 사용 : `app.disable("x-powered-by ");`
    - `x-powered-by` 옵션을 꺼버린다. (해킹에 완벽하지는 않지만 도움은 됨)

  ### 2. 오래된 패키지 확인
  - 오래된 패키지가 있다면 최신 버전을 가져올 필요가 있다.
  - 확인 방법 : `npm outdated --depth 0`
