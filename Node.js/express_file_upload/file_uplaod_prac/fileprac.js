var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var multer = require('multer');
// var upload = multer({dest: 'uploads/'});
// storage 라는 변수에 적용하여 경로, 이름 등을 지정할 수 있다.
var _storage = multer.diskStorage({
    destination: function(req, file, cb) {
        cb(null, 'uploads/');
    },
    filename: function(req, file, cb){
        /* 파일 리네임 할 수 있음
        if(파일.exits....){
            파일 이름 다시 설정 로직
        }*/
        cb(null, file.originalname);
    }
});
var upload = multer({storage: _storage});

app.use('/user', express.static('uploads'));
app.use(bodyParser.urlencoded({ extended: false }))
app.set('view engine', 'pug');
app.set('views', './views_file');
app.locals.pretty = true;

app.get('/upload', function(req, res){
   res.render('upload');
});

app.post('/upload', upload.single('userfile'), function(req, res){
    console.log(req.file);
    res.send('Uploaded : ' + req.file.filename);
});

app.listen(9000, function(){
    console.log("server is running...")
});