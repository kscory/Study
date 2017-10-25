# MongoDB 명령어
  - MongoDB 란
  - MongoDB 명령어

---

## MongoDB란
  ### 1. MongoDB의 특징
  - 스키마가 없으면서(noSQL) 설정도 간단하고 빠른 성능을 가진 데이터베이스
  - (Json 형태의 동적 스키마형 문서 이용 가능)
  - 테이블과 비슷한 컬렉션이 존재하지만 스키마가 없음
  - 문서 지향 데이터베이스
  - 문서는 RDB의 행에 해당
  - CRUD의 작업 외 여러 쿼리를 제공

  ### 2. 단점
  - ACID를 포기하여 consistency가 중요한 곳에 사용 어려움
  - 안전성 문제가 거론되기도 함
  - 각 솔루션의 특징을 이해해야 한다.

---

## MongoDB 명령어
  ### 1. MongoDB 기본 명령어
  - mongoDB 버전 확인
  ```
  > mongod -- version
  ```
  - 현재 사용중인 데이터베이스 확인
  ```
  > db
  ```
  - 만든 데이터베이스 리스트 확인
  ```
  > show dbs
  ```
  - 사용할 데이터베이스 지정
  ```
  > use (db이름)
  ```
  - 데이터베이스의 collection 확인
  ```
  > show collections
  혹은
  > show tables
  ```
  - collection 생성
  ```
  > db.createCollection("테이블명");
  ```
  - 특정 테이블(collection) 의 내용 조회
  ```
  db.테이블명.find()
  ```

  - 예시

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/MongoDB/Command/picture/ex1.png)

  ### 2. CRUD 명령어
  - Insert (배열로 한번에 입력이 가능하다.)
  ```
  > db.테이블명.insert({"key" : "value"})

  ex> db.users.insert({_id:"terry",city:"seoul"})
  ```
  - Select
  ```
  > db.테이블명.find()
  > db.테이블명.find({ id :"value1"}) // id가 value1인 것을 찾는다.

  ex>  db.users.find({_id:"terry"})
  ```
  - Update
  ```
  > db.테이블명.update( { 키 : '값' }, { $set: { 수정할항목 : '수정할값' } })

  ex> db.users.update( {_id:"terry"}, {$set :{ city:"Busan" } } )
  ```
  - Delete
  ```
  > db.테이블명.remove( {"key1" : "value1"} )

  ex> db.users.remove({_id:"terry"})

  cf> db.[테이블명].drop() // 테이블 삭제
  ```
---
