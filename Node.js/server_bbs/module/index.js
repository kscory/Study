// 만약 이름 이 index 라면 디렉토리만 호출하면 파일을 호출한다. 
// 모듈을 만들 경우 거의 디렉토리를 만들어 사용한다. 그리고 gateway를 index로 준다.

// public (외부에서 사용하기 위해서 붙여줌)
exports.a=157; //(외부에서는 exports를 붙인 변수만 사용 가능하다. => 코틀린은 반대..)

// private
var b = 137;

function sum(a, b){
	return a+b;
}

// 외부에서 이용
exports.sum = function(a,b){
	return a+b;
}