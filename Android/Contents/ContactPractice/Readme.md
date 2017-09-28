# Content Resolver / Intent
- Content 사용법
- 주소록(전화번호부) 구현
- Intent에 대한 간단한 설명

---

## Content Resolver 란
- `Content Provider` 는 어플리케이션 사이에서 Data 를 공유하는 통로 역할을 하며
- `Content Resolver` 결과를 반환하는 브릿지 역할을 한다.
</br>

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/ContactPractice/picture/contentResolver.png)

</br>

- 사용 방법 (전화번호부 예시)

  ### 1. Content Resolver 를 불러오기

  ```java
  // 처음 데이터 정의
  List<Contact> contacts = new ArrayList<>();
  // Content resolver 불러오기
  ContentResolver resolver = getContentResolver();
  ```

  ### 2. 데이터 URI 정의
    - 일종의 DB에서의 테이블 이름 (URI는 주소의 프로토콜을 포함하고 있다-> 일종의 상위개념)

  > 전화번호 URI : ContactsContract.CommonDataKinds.Phone.CONTENT_URI
  > 주소록 URI : ContactsContract.Contacts.CONTENT_URI
  ```java
  Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
  ```

  ### 3. 가져올 컬럼 정의
    - 조건절을 정의할 수도 있다. (옵션)

  ```java
  String projection[] = {
          ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
          ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
          ContactsContract.CommonDataKinds.Phone.NUMBER
  };  
  ```

  ### 4. 불러온 Content Resolver로 쿼리한 데이터를 Cursor에 담는다.
    - 쿼리 결과 -> Cursor (쿼리뒤에 순서대로)

  ```java
  Cursor cursor = resolver.query(
          uri,        // 데이터의 주소 (URI)
          projection, // 가져올 데이터 컬럼명 배열 (projection)
          null,       // 조건절에 들어가는 컬럼명들 지정
          null,       // 지정된 컬럼명과 매핑되는 실제 조건 값
          null        // 정렬
  );
  ```

  ### 5. Cursor에 담긴 데이터를 반복문을 돌면서 처리한다.
    - `set~~` 에 바로 담을 수 없으므로 index를 사용한다.

  ```java
  if(cursor != null){
      while(cursor.moveToNext()){
          Contact contact = new Contact();

          int index = cursor.getColumnIndex(projection[0]);
          contact.setId(cursor.getInt(index));

          index = cursor.getColumnIndex(projection[1]);
          contact.setName(cursor.getString(index));

          index = cursor.getColumnIndex(projection[2]);
          contact.setNumber(cursor.getString(index));

          contacts.add(contact);
      }
  }
  ```

---

## Intent
  ### [pdf 참고](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/ContactPractice/pdf/007_Intent.pdf)

  - Intent는 Explicit / Implicit Intent가 존재
  - Intent Filter (정의된 내장 액션들이 존재)
  - Category 지정시 나중에 검색하기 편리

---

## 주소록 구성 전체 코드
