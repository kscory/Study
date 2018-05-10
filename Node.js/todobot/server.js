const express = require('express');
const bodyParser = require('body-parser');
const routes = require('./routers');

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

// 라우터들을 모두 등록할 수 있다.
app.use('/',routes);

app.listen(process.env.PORT || 3000, () => {
    console.log('Server started!')
});

// <tip>
// heroku --log tail
// 를 하면 에러로그를 볼 수 있다.