/*
	postman에서 전송시 Content-type을 제거 bu> 안드로이드에서는 반드시 써주어야 한다.!!!
*/

var http = require("http");
// 파일 업로드하기
var formidable =require("formidable");
var fs = require("fs");

var server = http.createServer(function(request, response){
	if(request.url == "/upload"){
		var form = new formidable.IncomingForm();
		form.multiples = true;
		form.parse(request, function(error, names, files){ // 에러랑, 필드 이름이랑 파일이 넘어온다. => 이를 파싱, 임시 폴더에 저장됨
			if(error){
				console.log(error);
			} else{
				console.log(names);
				console.log(files);

				for(i in files){
					var oldpath = files[i].path;
					var realpath = "C:/Temp/upload/"+files[i].name;
					
					renameFile(oldpath,realpath,0);

					// fs.renameSync(oldpath, realpath); // suffix에 Sync 를 붙이면 동기가 된다. (그러면서 콜백함수를 제거한다.)
					// fs.rename(oldpath,realpath,function(){}); // 비동기로 동작함
				}
				response.end("upload completed!");
			}

		});

		// request.on(end,..)랑 동일
		/*
		form.on("end",function(error, names, files){
			console.log(names);
			console.log(files);
			response.end("");
		});
		*/
	} else{
		response.end("404 not found");
	}
});

// 파일 중복 재귀처리
function renameFile(oldpath, realpath, index){
	if(newFile.exists)
		renameFile(oldpath, realpath+"_"+index, index++);
	else
		fs.renameSync(oldpath,realpath);
}

server.listen(8090,function(){
	console.log("server is running...");
});