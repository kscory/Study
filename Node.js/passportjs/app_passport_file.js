var express = require('express');
var session = require('express-session');
var FileStore = require('session-file-store')(session);
var bodyParser = require('body-parser');
var pkfd2Password = require('pbkdf2-password');
var hasher = pkfd2Password();

var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;

var app = express();

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use(session({
    secret: 'asdnfkjn334#!$i8%#%df',
    resave: false,
    saveUninitialized: true
}));

// 모듈을 사용
app.use(passport.initialize()); // passport 구동
app.use(passport.session()); // 반드시 세션을 사용하기 위한 use 뒤에 반드시 사용해야 한다.

// 콜백함수가 false 가 아니라면 이를 실행 (여기서 done 의 두번째 인지가 user에 들어간다.)
passport.serializeUser(function(user, done) {
    done(null, user.username); // done에서 보낸 user의 인자를 받을 수 있다.
});

// 세션에 저장되어 있는 유저가 접근하면 이를 실행시키게 되어 있다.
// 저장된 사용자의 식별자가 id 로 넘어온다
passport.deserializeUser(function(id, done) {
    for(var i=0 ; i<users.length; i++){
        var user = users[i];
        if(user.username === id) {
            return done(null, user);
        }
    }
    done(null, false);
});

// LocalStrategy 전략을 새로 만들어 사용한다.
// 공식문서 안에는 MongoDB API 를 사용하고 있으니 참고
// done 이라는 인자는 함수를 담고 있다.
passport.use(new LocalStrategy(
    function(username, password, done){
        var uname = username;
        var pwd = password;
        for(var i=0 ; i<users.length; i++){
            var user = users[i];
            if(uname === user.username){
                return hasher({password:pwd, salt:user.salt}, function(err, pass, salt, hash){
                    // done 함수를 이용해서 맞으면 user 를 아니면 false 를 반환시킨다.
                    if(hash === user.password){
                        console.log(user.password);
                        done(null, user);
                    } else {
                        done(null, false);
                        // cf>
                        // 아래와 fauilureFlash 가 true 이면 아래와 같이 메세지를 전달해서 보여줄 수 있다.
                        // done(null, false, {message: 'Incorrect username or password'});
                    }
                });
            }
        }
        done(null, false);
    }
));

// pasasport 에서 미들웨어를 통해 콜백함수를 쓸 수 있다.
// local : 로컬 전략을 사용하겠다.
// successRedirect : 성공 시 리다이렉트 시킴
// failureRedirect : 실패 시 리다이렉트 시킴
// failureFlash : 사용자에게 한번만 인증에 실패했습니다 라는 메세지를 보낼 수 있다.
app.post('/auth/login', passport.authenticate(
    'local',
    {
        successRedirect: '/welcome',
        failureRedirect: '/auth/login',
        failureFlash: false,
    }),
        req.session.save(function(){
            res.redirect('/welcome');
    })
);
/* 아래와 같이 콜백함수를 인자로 추가하여 로그인의 경우에도 세션이 저장된 뒤 리다이렉트 할 수 있다.
app.post('/auth/login', passport.authenticate(
    'local',
    {
        successRedirect: '/welcome',
        failureRedirect: '/auth/login',
        failureFlash: false,
    }),
        req.session.save(function(){
            res.redirect('/welcome');
    })
);
*/

// 그냥 req.logout 하면 로그아웃이 된다.
app.get('/auth/logout', function(req,res){
    req.logout();
    req.session.save(function(){
        res.redirect('/welcome');
    });
});

app.get('/welcome', function(req, res){
    console.log(req.user);
    if(req.user && req.user.displayName) {
        res.send(`
            <h1>Hello, ${req.user.displayName}</h1>
            <a href="/auth/logout">logout</a>
    `);
    } else {
        res.send(`
      <h1>Welcome</h1>
      <ul>
        <li><a href="/auth/login">Login</a></li>
        <li><a href="/auth/register">Register</a></li>
      </ul>
    `);
    }
});

var users = [
    {
        username:'egoing',
        password:'mTi+/qIi9s5ZFRPDxJLY8yAhlLnWTgYZNXfXlQ32e1u/hZePhlq41NkRfffEV+T92TGTlfxEitFZ98QhzofzFHLneWMWiEekxHD1qMrTH1CWY01NbngaAfgfveJPRivhLxLD1iJajwGmYAXhr69VrN2CWkVD+aS1wKbZd94bcaE=',
        salt:'O0iC9xqMBUVl3BdO50+JWkpvVcA5g2VNaYTR5Hc45g+/iXy4PzcCI7GJN5h5r3aLxIhgMN8HSh0DhyqwAp8lLw==',
        displayName:'Egoing'
    }
];

app.post('/auth/register', function(req, res){
    hasher({password:req.body.password}, function(err, pass, salt, hash){
        var user = {
            username:req.body.username,
            password:hash,
            salt:salt,
            displayName:req.body.displayName
        };
        users.push(user);
        // pasport 를 사용하여 사용자 등록 가능
        req.login(user, function(err){
            req.session.save(function(){
                res.redirect('/welcome');
            })
        });
    });
});

app.get('/auth/register', function(req, res){
    var output = `
  <h1>Register</h1>
  <form action="/auth/register" method="post">
    <p>
      <input type="text" name="username" placeholder="username">
    </p>
    <p>
      <input type="password" name="password" placeholder="password">
    </p>
    <p>
      <input type="text" name="displayName" placeholder="displayName">
    </p>
    <p>
      <input type="submit">
    </p>
  </form>
  `;
    res.send(output);
});

app.get('/auth/login', function(req, res){
    var output = `
  <h1>Login</h1>
  <form action="/auth/login" method="post">
    <p>
      <input type="text" name="username" placeholder="username">
    </p>
    <p>
      <input type="password" name="password" placeholder="password">
    </p>
    <p>
      <input type="submit">
    </p>
  </form>
  `;
    res.send(output);
});
app.listen(9000, function(){
    console.log('Server is running..');
});