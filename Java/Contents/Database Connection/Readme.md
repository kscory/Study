# Database 연동
Database의 연동에 대한 간단한 설명


## 들어가기 전에..
프로토콜 / IP / Port / Socket / URL에 대한 간단한 설명
### 1. __Protocol__
프로토콜이란 컴퓨터간의 정보교환이 필요한 경우, 원활하게 행하기 위한 협정 또는 규칙으로 Port 번호가 정해져 있는 경우가 존재
* ex1> TCP/IP (Transmission Control Protocol/Internet Protocol)
</br>- 컴퓨터와 컴퓨터를 통신회선 등으로 연결하기 위한 통신 규약
* ex2> HTTP (Hyper Text Transfer protocol) _ Port 번호 :80
</br>- 주로 인터넷을 하는데 사용하는 프로토콜
* ex3> FTP (File transfer protocol) _ Port 번호 : 21
</br>- 컴퓨터간 파일을 주고 받는 프로토콜

### 2. __IP__
IP 주소란 TCP/IP 프로토콜을 사용하여 통신을 할 때, 송신자와 수신자를 구별하기 위한 고유의 주소 및
인터넷에 연결된 모든 통신망과 그 통신망에 연결된 컴퓨터에 부여되는 고유의 식별 주소
* ex> 213.12.142.132

### 3. __Port__
포트란 호스트 내에서 실행되고 있는 프로세스를 구분짓기 위한 16비트의 논리적 할당
</br> - IP주소는 컴퓨터를 찾을 때 필요한 주소를 나타낸다면 포트는 컴퓨터 안에서 프로그램을 찾을 때 를 나타내는 것으로 이해 가능
</br> - 각각의 프로그램에 그 프로그램이 사용할 포트번호를 지정되어 있는 경우가 많다.(보통 2000번대 아래는 표준으로 사용되고 있음)
* ex1> HTTP의 경우 : 80
* ex2> HTTPS의 경우 : 443

### 4. __Socket__
소켓은 정규 유닉스 파일 기술자를 이용하여 다른 프로그램과 정보를 교환하는 방법
</br>- TCP/IP로 통신하는 컴퓨터가 가지는 네트워크 내의 주소인 IP address,IP address의 서브 address인 포트 번호를 조합한 Network address
</br>- __데이터베이스__ 는 소켓구조로 되어 있음

### 5. __URL__
URL이란 해당 정보 자원의 위치와 종류를나타내는 일련의 규칙
</br> - <구성> 프로토콜://정보 자원을 가진 컴퓨터의 위치/파일 디렉토리/정보 자원 이름
</br> - 프로토콜 :// 아이피(주소) : 포트
* ex1> jdbc:mysql://localhost:3306/mem
* ex2> https://github.com:443


## Databae와 연동
연동 방법
### __JDBC__
JDBC는 자바 프로그램과 관계형 데이터 원본에 대한 인터페이스</br> JDBC 라이브러리(Library)는 관계형 데이터베이스에 접근하고, SQL 쿼리문을 실행하는 방법을 제공
</br> - java.sql 패키지에 의해 구현

### __Statement와 PreparedStatement 차이__
가장 큰 차이점은 캐시(cache) 사용여부로<br>
Statement는 쿼리 수행마다 "1) 쿼리 문장 분석 2) 컴파일 3) 실행"을 반복하며</br>
PreStatement는 처음 한 번만 세 단계를 거친 후 캐시에 담아 재사용한다.
* 대량의 data를 처리할때 PreparedStatement 가 유리하다.

> Statement 사용 예시

```java
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
```
> PreparedStatement 사용 예시

```java
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
```

### __연동 순서__

> 1. 드라이버를 동적으로 로드 </br>- 드라이버들이 읽히면 하면 자동 객체가 생성되고 DriverManager에 등록된다.

```java
try {
  Class.forName("com.mysql.jdbc.Driver");
  // 오라클의 경우 : Class.forName("oracle.jdbc.driver.OracleDriver");
} catch (Exception e) {
  e.printStackTrace();
}
```

> 2. 데이터베이스 연결

```java
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/memo", "mysql ID", "mysql PW");
```

> 3. 쿼리 실행

```java
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
```

> 4. 데이터베이스 연결 해제

```java
if(pstmt != null) {
  try {
    pstmt.close();
  } catch (SQLException e) {
    e.printStackTrace();
  }
}
if(con != null) {
  try {
    con.close();
  } catch (SQLException e) {
    e.printStackTrace();
  }
}
```
## 참고 문제
Database를 활용한 문제
1. [Database를 활용한 Memo](https://github.com/Lee-KyungSeok/Study/tree/master/Java/Example/MemoDatabase)
