# Firebase Basic 2
  - NoSQL의 구조
  - 간단한 게시판 만들기

---

## NoSQL의 구조
  ### 1. 간단한 RDB의 구조
  - RDB(Relational Database)의 경우 테이블의 형태로 데이터를 가지고 있다.
  - 아래 예시처럼 BBs 테이블과 같은 테이블은 "user_id"를 foreign key로 가지고 있다.
  - RDB의 경우 key뿐만아니라 값으로도 테이블을 검색할 수 있다.
  - User 테이블과 BBs 테이블이 join되어 있는 User_Bbs 테이블도 지닐 수 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase2/picture/rdbms.png)

  ### 2. NoSQL의 구조
  - NoSQL은 아래와 같이 tree 구조로 되어있다
  - NoSQL은 값으로 검색할 수 없고 key로만 검색할 수 있다. => 즉, 제목으로 검색할 수 없다.
  - 따라서 검색을 위해서는 아래와 같이 nested model로 되어야 한다.
  - 이로 인해 NoSQL의 경우 검색 속도가 빠르다는 장점이 있다.
  - 또한 검색을 원활하게 하기 위해 tag의 기법이 나왔으며 tag에 값들을 저장하고 있다.
  - 하지만 수정시에는 저장되어 있는 모든 데이터를 수정해주어야 하는 단점이 있다.
  - 예시> bbs의 aaa는 3군데 모두 저장되어 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase2/picture/nosql.png)

---

## 간단한 게시판 만들기
  ### 1. 참고
  - 내용

---

## 참고 문헌
  ### 1. [링크]()
