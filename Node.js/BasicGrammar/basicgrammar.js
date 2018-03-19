// 1. 변수의 선언 : var 변수명
// 2. 콘솔에 로그출력 : console.log("");
// 3. 반복문 : for(int i=0 ; i<10 ; i++){};
// 4. 조건문 : if(비교){} else if(비교) else {}
// 5. 문자열 기본연산 : "문자열" + "문자열" = "문자열문자열"

// 6. 함수 사용하기
// 함수만들기 1
function sum(param1, param2){
  return param1 + param2; <= 리턴타입이 있는 경우
  // console.log(param1 + param2) // <= 리턴타입이 없는 경우
}
// 함수만들기 2
var sum = function(param1,param2){
  return param1 + param2;
}

// 7. class 사용하기
function Num(param1,param2){
  var a = 0; // private 선언된 변수 : 외부에서 접근 안됨
  this.b = 10; // public 으로 선언된 변수 : 외부에서 접근가능
  this.c = function(param1,param2);

}
// 클래스의 사용
var num = new Num("Hello", 157);
num.b=500;
num.c("a",49949);

// 8. javascript의 object
var request = {
  one : 157,
  two : "hello",
  three : 5
  sum : function(){
    return one + three;
  }
};

console.log(request.one);
console.log(request.two);
console.log(request.sum());
//ex> 피보나치
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
// 서버에서 실행
// res.end(fibonacci.run());
