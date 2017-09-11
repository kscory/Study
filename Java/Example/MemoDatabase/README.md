# Database(MySQL)을 활용한 MEMO 만들기
#### ※[MVC 패턴을 활용한 Memo 예제](https://github.com/Lee-KyungSeok/MemoExample) 의 활용이므로 이전문서 참고

## __문제__
Database로 메모 관리

## __설명 (Model만 설명)__
데이터베이스 연결 -> 쿼리 실행 -> 데이터베이스 연결해제 순서
### ● Model

#### 0. 클래스 상수 정의
* 데이터 베이스 연동에 필요한 url / mysql id / mysql pw 정의
```java
private static final String url ="jdbc:mysql://localhost:3306/memo";
private static final String ID = "root";
private static final String PW = "mysql";
Connection con = null;
```

#### 1. 생성자
* 데이터 베이스에 connection 하기 위한 driver를 동적으로 로드

```java
public ModelWithDB() {
  try {
    Class.forName("com.mysql.jdbc.Driver"); //드라이버를 동적으로 로드
  } catch (Exception e) {
    System.out.println("연결 실패");
    e.printStackTrace();
  }
}
```

#### 2. create
* 입력된 메모를 데이터베이스에 저장
* PreparedStatement 를 사용함
* "insert into 테이블명(컬럼1, 컬럼2, 컬럼3..) values(내용,내용,내용);" 사용

```java
public void create(Memo memo) {
  // 1. 데이터베이스 연결
  try(Connection con = DriverManager.getConnection(url, ID, PW)) { // 데이터베이스 자동 연결 해제
    // 2. 쿼리를 실행
    // 2.1 쿼리 생성
    String query = " insert into memo(name, content, datetime) values(?, ?, ?)";
    // 2.2 쿼리를 실행 가능한 상태로 만들어준다.
    PreparedStatement pstmt = con.prepareStatement(query);

    // 2.3 물음표에 값을 세팅
    pstmt.setString(1, memo.name);
    pstmt.setString(2, memo.content);
    pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

    // 2.4 쿼리를 실행
    pstmt.executeUpdate();

  } catch (Exception e) {
    System.out.println("연결 실패");
    e.printStackTrace();
  }
}
```

#### 3. read
* 특정 memo를 데이터베이스에서 불러온다.
* Statemnet 사용
* "select 컬럼1, 컬럼2, 컬럼3 from 테이블명 where 조건절" 사용

```java
public Memo read(int no) {
  Memo memo = new Memo();

  // 1. 데이터베이스 연결
  try(Connection con = DriverManager.getConnection(url, ID, PW)) {
    // 2. 쿼리를 실행
    // 2.1 쿼리 생성
    String query = "select * from memo where no ="+no;

    // 2.2 앞으로 쿼리를 실행 가능한 상태로 만들어준다.
    Statement stmt = con.createStatement();

    // 2.3 select한 결과값을 돌려받기 위한 쿼리를 실행
    ResultSet rs = stmt.executeQuery(query);

    //결과셋을 반복하면서 하나씩 꺼낼 수 있다.
    if(rs.next()) {
      memo.no = rs.getInt("no");
      memo.name = rs.getString("name");
      memo.content = rs.getString("content");
      memo.datetime = rs.getLong("datetime");
    }
    if(memo.no==0) {
      return null;
    }
  } catch (Exception e) {
    System.out.println("연결 실패");
    e.printStackTrace();
  }
  return memo;
}
```

#### 4. update
* 특정 메모 번호를 입력받아 데이터베이스에서 정보를 수정
* "update 테이블명 set 변경할컬럼1 = '변경값' ,변경할컬럼2 = 변경값 where 특정컬럼명 = '어떤값'" 사용

```java
public boolean update(int no, Memo memoTemp) {
  boolean check=false;
  // 1. 데이터베이스 연결
  try(Connection con = DriverManager.getConnection(url,ID,PW)){
    // 2. 쿼리를 실행
    String query = "update memo set name = ?, content = ? where no = ?";
    PreparedStatement psmt = con.prepareStatement(query);
    psmt.setString(1, memoTemp.name);
    psmt.setString(2, memoTemp.content);
    psmt.setInt(3, no);
    psmt.executeUpdate();

    check = true;
  } catch(Exception e) {
    System.out.println("연결 실패");
    e.printStackTrace();
  }
  return check;
}
```

#### 5. delete
* 특정 메모 번호를 입력받아 데이터베이스에서 정보를 삭제
* "delete from 테이블명 where 특정컬럼명 = '어떤값'" 사용

```java
public boolean delete(int no) {
  boolean check = false;
  // 1. 데이터베이스 연결
  try(Connection con = DriverManager.getConnection(url,ID,PW)){
    // 2. 쿼리를 실행
    String query = "delete from memo where no = ?";
    PreparedStatement psmt = con.prepareStatement(query);
    psmt.setInt(1, no);
    psmt.executeUpdate();

    check=true;
  } catch(Exception e) {
    System.out.println("연결 실패");
    e.printStackTrace();
  }
  return check;
}
```

#### 6. showList
* 데이터베이스에 저장된 모든 메모를 불러옴

```java
public ArrayList<Memo> showList(){
  ArrayList<Memo> list = new ArrayList<>();
  // 1. 데이터베이스 연결
  try(Connection con = DriverManager.getConnection(url, ID, PW)) { // 데이터베이스 자동 연결 해제
    // 2. 쿼리를 실행
    String query = "select * from memo";
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    // 반복문으로 모든 리스트를 불러옴 (iterator 내장되어 있음)
    while(rs.next()) {
      Memo memo = new Memo();
      memo.no = rs.getInt("no");
      memo.name = rs.getString("name");
      memo.content = rs.getString("content");
      memo.datetime = rs.getLong("datetime");
      list.add(memo);
    }
  } catch (Exception e) {
    System.out.println("연결 실패");
    e.printStackTrace();
  }
  return list;
}
```

## __전체 소스 코드 링크__
### [코드 보기](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Example/MemoDatabase/src/ModelWithDB.java)
