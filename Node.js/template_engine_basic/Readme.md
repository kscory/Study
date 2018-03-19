# Template Engine Basic
  - Template Engine 이란
  - Pug(Jade) 기본

---

## Template Engine 이란
  ### 1. 템플릿 시스템
  - 템플릿 프로세서(Template Processor)를 사용하여 웹 템플릿(Web Template)를 결합하여 완성된 웹 페이지를 만들어내는 시스템
  - 자료(Data)를 결합하여 페이지를 만들어 내기도 하고 많은 양의 Content를 표현하는 것을 도와줌
  - 구성요소
    - 템플릿 엔진 : 동적인 파일과 정적인 파일의 장단점을 잘 결합한 형태의 새로운 체계
    - 템플린 리소스 : 템플릿 언어로 작성된 웹 템플릿
    - 컨텐트 리소스 : XML, 다양한 종류의 데이터 스트림

  ### 2. 템플릿 엔진
  - 동적인 파일과 정적인 파일의 장단점을 잘 결합한 형태의 새로운 체계
  - 웹 템플릿들(web templates)과 웹 컨텐츠 정보(content information)를 처리하기 위해 설계된 소프트웨어
  - 그 종류는 프레임워크마다 다양하다. (ex> Pug(Jage), Thymeleaf 등)

## Pug(Jade가 바뀜) 기본
  ### 1. 설치
  - [공식 홈페이지 참고](http://expressjs.com/ko/guide/using-template-engines.html)
  - npm 을 통하여 설치한다

  ```
  $ npm install pug --save
  ```

  ### 2. 세팅
  - `app.set('view engine', 'pug');` 을 통해서 사용 가능 하다
  - `app.set('views', './views');` : views 라는 디렉토리를 추가하고 pug의 경로를 views 로 세팅하며 views 디렉토리에 pug 파일을 만든다.(default 또한 views)

  ```javascript
  app.set('view engine', 'pug');
  ```

  ### 3. 특징
  - 닫는 태그 존재하지 않으며 들여쓰기 필요
  - 태그의 속성으로 넣으려면 () 를 사용
  - `-` 를 이용하면 자바스크립트 문법 사용 가능
  - 여러줄 입력시 `|` 로 작성
  - id 입력시 `#`을 활용 (ex> #layout)
  - 클래스틑 `.` 입력(ex> .col)

  ### 4. 사용 예시
  - `app.locals.pretty` : html 코드를 이쁘게 바꿔줄 수 있다.
  - js 파일에서 pug 파일을 랜더링해서 사용자에게 응답한다.
  - 특정 값을 넘길 때는 rendering 할 때 `{}` 를 활용하여 넘긴다.
      - ex> `res.render('temp', {time:Date(), _title:'Pug'})`

  > temp.pug

  ```
  html(lang='en')
      head
          title= _title
      body
          h1 Hello Jade
          ul
              -for(var i=0; i<5; i++)
                  li coding
          div= time
  ```

  > app.js

  ```javascript
  app.locals.pretty = true; // html 코드를 이쁘게 바꾼다...
  app.set('view engine', 'pug');
  app.set('views', './views');

  app.use(express.static('public'));

  app.get('/template', function(req, res){
      // temp 라는 파일을 랜더링해서 사용자에게 응답
     res.render('temp', {time:Date(), _title:'Pug'}); // pug 파일에 있는 변수에 값을 넘길 수 있다
  });
  ```
