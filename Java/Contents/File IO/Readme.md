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
기본 개념 설명
### 1. __입력 스트림__
* 순서
 * 

```java

```

### 2. __출력 스트림__

> 설명

```java

```

## 참고 문제
Array / Collection을 활용한 문제
1. [문제 이름](링크)
