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
> * 예시

```java
// finally를 하지 않더라도 try-with 절에서 자동으로 fis.close가 발생
try(FileInputStream fis = new FileInputStream(database)) {
	InputStreamReader isr = new InputStreamReader(fis, "MS949");
	BufferedReader br = new BufferedReader(isr);
	String row;
	while( ...) {
        /* 생략 */
	}
} catch(Exception e) {
	e.printStackTrace();
}

// 이전에는 아래와 같이 finally 사용
FileInputStream fis = null
try {
  fis = new FileInputStream(database);
	InputStreamReader isr = new InputStreamReader(fis, "MS949");
	BufferedReader br = new BufferedReader(isr);
	String row;
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
###  __입력 스트림__
1. 읽는 스트림을 연다.
```java
FileInputStream fis = new FileInputStream(database)
```

2. 스트림을 중간처리 한다.(실제 파일 엔코딩을 바꿔주는 래퍼 클래스를 사용)
```java
InputStreamReader isr = new InputStreamReader(fis, "MS949");
```

3. 버퍼처리를 한다.
```java
BufferedReader br = new BufferedReader(isr);
String row;
//버퍼를 읽음
while((row = br.readLine()) != null) {
				index = Integer.parseInt(row); // 라인별로 처리
			}
```

4. 스트림을 닫는다.
```java
if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
```
5. 예시 (Memo에서 List 내용 보기)
```java
public ArrayList<Memo> showList(){

	// 데이터가 중복해서 쌓이지 않도록 저장소를 지워주는 작업이 필요한 경우가 있다.
	list.clear();

	// 1. 읽는 스트림을 연다.
	try(FileInputStream fis = new FileInputStream(database)){ //try-with 절에서 자동으로 fis.close가 발생
		// 2. 실제 파일 엔코딩을 바꿔주는 래퍼 클래스를 사용
		InputStreamReader isr = new InputStreamReader(fis, "MS949");
		// 3. 버퍼처리
		BufferedReader br = new BufferedReader(isr);

		String row;
		// 새로운 줄을 한줄씩 읽어서 row에 저장하고
		// 더 이상 읽을 데이터가 없으면 null이 리턴되므로 로직이 종료
		while( (row = br.readLine()) != null ) { //newLine표시를 만나면 읽을 수 있도록.... flush가 실행되는것이랑 비슷
			// 1::fds::fdfd::12344344
			// tempRow[0] = 1
			// tempRow[1] = fds
			// tempRow[2] = fdfd
			// tempRow[3] = 12344344
			// 위와 같이 들어가게 된다.
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
	/*
	 * 정석은 아래와 같이 역순으로 모두 닫아주어야 한다.
	 * finally{
	 * 		br.close();
	 * 		isr.close();
	 * 		fis.close();
	 * }
	 * */

	return list;
}
```

###  __출력 스트림__

> 설명

```java

```

## 참고 문제
Array / Collection을 활용한 문제
1. [문제 이름](링크)
