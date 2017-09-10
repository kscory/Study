# File IO
File IO 에 대한 스터디

## 들어가기 전에
Stream / JDK 1.7 이상의 예외처리 에 대해
### 1. __Stream 이란__
* Stream(스트림) 이란 "데이터 입출력 처리의 중간자 역할" 로 생각할 수 있다.
* 입력 스트림 :  데이터를 먼저 스트림으로 읽어 들이며, 스트림에 존재하는 데이터를 하나씩 읽는다.
* 출력 스트림 : 출력스트림으로 데이터를 보내며 출력 스트림에 보내진 데이터는 비워진다. 즉, 출력 스트림에 존재하는 데이터는 모두 목표지점에 저장된다.

### 2. __JDK 1.7 이상의 예외처리 (try with)__
* finally가 존재하지 않더라도 자동으로 close 처리를 한다.
> * 예시 - try-with

```java
// finally를 하지 않더라도 try-with 절에서 자동으로 fis.close가 발생
try(FileInputStream fis = new FileInputStream(database)) {
	InputStreamReader isr = new InputStreamReader(fis, "MS949");
	BufferedReader br = new BufferedReader(isr);
	while( ...) {
        /* 생략 */
	}
} catch(Exception e) {
	e.printStackTrace();
	/* 아래와 같이 모든 에러를 따로 써주어야 한다. Exception e 는 최상위 에러체크
	catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}*/
}
	/*
	 * 정석은 아래와 같이 역순으로 모두 닫아주어야 한다.
	 * finally{
	 * 		br.close();
	 * 		isr.close();
	 * 		fis.close();
	 * }
	 * */
```

> * 이전 버전 예시 - try-catch-finally

```java
FileInputStream fis = null
try {
  fis = new FileInputStream(database);
	InputStreamReader isr = new InputStreamReader(fis, "MS949");
	BufferedReader br = new BufferedReader(isr);
	while( ...) {
        /* 생략 */
	}
} catch(Exception e) {
    e.printStackTrace();
} finally {
    if(fis != null) {
      try {
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}
```

## File IO
사용 방법

### __file 생성__
> 1. 디렉토리 경로 파악</br> 	mkdir : 자바 디렉토리가 없으면 에러</br>mkdirs : 경로 상에 디렉토리가 없으면 자동생성
> 2. 파일 생성

```java
//윈도우는 역방향 슬래시, 맥은 정방향 슬래시로 경로 구분
private final String DB_DIR = "D:\\workspaces\\java\\database"; //경로 지정
private final String DB_FILE = "memo.txt"; // 파일
private File database = null;

File dir = new File(DB_DIR);
// 디렉토리 존재유무
if(!dir.exists()) {
	dir.mkdirs();
}
//separator는 구분자를 자동으로 생성
File file = new File(DB_DIR + File.separator + DB_FILE);
//파일의 존재유무 파악후 파일 생성
if(!file.exists()) {
	try {
		file.createNewFile();
	} catch(Exception e){
		e.printStackTrace();
	}
}
//생성된 파일 메모리에 저장
database = file;

```

###  __입력 스트림__
> 1. 읽는 스트림을 연다.

```java
File database = new File("경로" + File.separator +"파일명");
FileInputStream fis = new FileInputStream(database)
// "database" 는 file의 종류..
```

> 2. 스트림을 중간처리 한다.(실제 파일 엔코딩을 바꿔주는 래퍼 클래스를 사용)

```java
InputStreamReader isr = new InputStreamReader(fis, "MS949"); //(입력스트림, "인코딩 형태")
```

> 3. 버퍼처리를 한다.

```java
BufferedReader br = new BufferedReader(isr);
String row;
//버퍼를 읽음
while((row = br.readLine()) != null) {
	index = Integer.parseInt(row); // 라인별로 처리
}
```

> 4. 스트림을 닫는다. </br> 스트림이 생성되기 전에 오류가 발생했을 수도 있으므로 null 체크를 먼저 해야 한다.

```java
if(fis != null) {
	try {
		fis.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
```
> 5. 예시 (Memo에서 List 내용 보기)

```java
public ArrayList<Memo> showList(){

	// 1. 읽력 스트림을 연다.
	try(FileInputStream fis = new FileInputStream(database)){ //try-with 절에서 자동으로 fis.close가 발생
		// 2. 스트림을 중간처리
		InputStreamReader isr = new InputStreamReader(fis, "MS949");
		// 3. 버퍼처리
		BufferedReader br = new BufferedReader(isr);
		String row;
		// 새로운 줄을 한줄씩 읽어서 row에 저장하고
		// 더 이상 읽을 데이터가 없으면 null이 리턴되므로 로직이 종료
		// newLine표시를 만나면 읽을 수 있도록 한다-> flush가 실행되는것이랑 비슷
		while( (row = br.readLine()) != null ) {
			String tempRow[] = row.split(COLUM_SEP);
			Memo memo = new Memo();
			memo.no = Integer.parseInt(tempRow[0]);
			memo.name = tempRow[1];
			memo.content = tempRow[2];
			memo.datetime = Long.parseLong(tempRow[3]);

			list.add(memo);
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
	return list;
}
```

###  __출력 스트림__

> 1. 출력 스트림을 연다.

```java
File database = new File("경로" + File.separator +"파일명");
FileOutputStream = fos = new FileOutputStream(database, true);
// true이면 뒤에서 추가로 작성
// false인 경우 새로 덮어 씌운다.
```

> 2. 스트림을 중간처리 한다.

```java
OutputStreamWriter osw = new OutputStreamWriter(fos);
```

> 3. 버퍼처리를 한다.<br> flush()를 반드시 사용하여 는 현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비우는 작업을 실행해야 한다.

```java
BufferedWriter bw = new BufferedWriter(osw);
bw.append(row); //버퍼에 추가
bw.flush();
```

> 4. 스트림을 닫는다.

```java
if(fos != null) {
	try {
		fos.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
```

> 5. 예시 (Memo에서 file에 내용 추가)

```java
public void create(Memo memo) {

	FileOutputStream fos =null;
	try {
		String row = "문자열"

		// 1. 쓰는 스트림을 연다. (Output 스트림)
		fos = new FileOutputStream(database, true);
		// 2. 스트림을 중간처리...(텍스트의 엔코딩을 변경...)
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		// 3. 버퍼처리...
		BufferedWriter bw = new BufferedWriter(osw);
		bw.append(row);
		bw.flush();

	} catch (Exception e) {
		e.printStackTrace();
		// 4. 스트림을 닫는다.
	} finally {
		if(fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
```

## 참고 문제
File IO를 활용한 문제
1. [File을 활용한 Memo](링크)
