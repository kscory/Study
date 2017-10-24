// Rest Api 설계

// 1. 서버 모듈(라이브러리)을 import
var http = require("http");

// 2. 서버 모듈을 사용해서 서버를 정의
var server = http.createServer( function(request, response){

  // 1. 요청이 온 주소체계가 내가 제공하는 api 구조와 일치하는지 확인
  // decodeURI(주소) : (일부) %20 등의 주소문자를 원래 문자로 변환
  var cmds = decodeURI(request.url).split("/");
  response.writeHead(200, {'Content-Type':'text/html'});
  // 2. 주소체계가 잘못되었다면 알려준다.
  if(cmds.length < 3){
    response.end("<h>Your request is wrond!!!</h>");
  // 3. 주소체계가 정상이면
  } else {
    if(cmds[1] == "fibonacci"){
      if(!isNaN(cmds[2])){ // 숫자면 true
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

  // var array = request.url.split("/");
  // 일반 for문은 배열의 index를 리턴
  // for(i in array)
  //   response.write(" ["+array[i]+"] ");
  // 향상된 for문은 배열안의 실제 item을 리턴
  // response.writeHead(200, {'Content-Type':'text/html'});
  // array.forEach(function(item){
  //   response.write(" ["+item+"] </br>") // html일 경우 \n이 아니라 </br>로 한다.
  // });
  // response.end("");
});

// 3. 서버 실행
server.listen(8089, function(){
  console.log("server is running..."); // 콜백이 있으면 호출, 아니면 띄어만 준다.
});

// 피보나치
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

// 아나그램
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
