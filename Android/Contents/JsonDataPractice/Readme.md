# JsonData 및 네트워크 활용
  - Json 형식에 대한 설명
  - Glide 라이브러리 사용방법
  - Gson 라이브러리 사용방법
  - sodhanalibrary 사용방법
  - 오픈Api를 이용해 Json 데이터를 불러와 뿌려주는 예제 2가지
  - <참고>
    - 서버 연결 오류시 정의할 값
    - Restful 방식과 주소 전달 체계

---

## Json 형식에 대해
  ### 2. Json 형태
  - json string 은 아래와 같이 세 가지의 조합으로 이루어진다.
    - ① 배열형을 표현하는 대괄호
    - ② 객체(오브젝트)를 표현하는 중괄호
    - ③ key값
  - 중괄호를 포함하는 경우 클래스의 타입은 제일 앞을 대문자로 바꾼 것이 된다.
  - 이름이 없으면 개발자가 임의로 정해줄 수 있으며, 대괄호는 배열을 참조한다.
  - 대괄호가 있으면 배열을 참조하게 되며, 오브젝트까지는 변수를 리턴한다.
  - 사용 예시

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/json.png)

  ### 2. 통신 개요
  - 아래 그림과 같이 접근한다. (타입은 정해줄 수 있다.)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/json2.png)

---

## Glide & Gson 라이브러리 & sodhanalibrary
  ### 1. Glide 사용법 ([글라이드 라이브러리](https://github.com/bumptech/glide))
  - Gradle 설정 (버전을 4.0 or 3.+로.. 업데이트 가능)

  ```
  compile 'com.github.bumptech.glide:glide:3.+'
  ```

  - 사용방법 (이미지를 imageView에 url을 가져와서 세팅)

  ```java
  Glide.with(context)                                 // 글라이드 초기화
          .load(url)   // 주소에서 이미지 가져오기
          .into(imageView);                    // 실제 대상에 꽂는다.
  ```

  ### 2. Gson 사용법 ([Gson 라이브러리](https://github.com/google/gson))
  - Gson 설정

  ```
  compile 'com.google.code.gson:gson:2.8.2'
  ```

  - 사용방법 (json string 포멧을 가져와서 정의한 클래스에 따라 값을 대입)
  ```java
  Gson gson = new Gson();
  User user = gson.fromJson(item,User.class);
  ```

  ### 3. sodhanalibrary 사용법
  - http://pojo.sodhanalibrary.com/Convert 접속
  - 생성할 클래스가 다른 변수명과 겹치지 않도록 조심해서 대입
  - 생성된 변수는 변경할 수 없으며 클래스는 변경 가능

 ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/sodhanalibrary.png)

---

## 예제1 (githubusers)
  ### 1. f
  - dd


---

## 예제2 (TransferTation 유동인구_서울열린데이터)
  ### 1. f
  - dd


---

## 참고 개념

  ### 1. 서버 연결시 Flag값 주기
  - ff

  ### 2. Restful 방식과 주소 전달 체계
  - ff
