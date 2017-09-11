# Annotation(어노테이션)
어노테이션에 대한 간단한 설명
> 참고 : http://www.nextree.co.kr/p5864/

## Annotation 설명
전체 소스코드에서 비즈니스 로직에는 영향을 주지는 않지만 해당 타겟의 연결 방법이나 소스코드의 구조를 변경해주는 문법요소로 자바코드에 주석처럼 달아 특수한 의미를 부여
> 이 속성을 어떤 용도로 사용할까, 이 클래스에게 어떤 역할을 줄까?"를 결정해서 붙여준다고 볼 수 있다. 어노테이션은 소스코드에 메타데이터를 삽입하는 것이기 때문에 잘 이용하면 구독성 뿐 아니라 체계적인 소스코드를 구성하는데 도움을 준다.

### 1. __Annotation 사용 이유__
다양한 도메인 객체를 단순한 map 저장소에 저장하기 위해서, 서로 다른 객체에서 정의되어 있는 key를 식별하는 방법에 대한 연구를 통한 공부

#### 방법1. 객체마다 방법을 설정
![](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/Annotation/annotation1.png)

#### 방법2. 인터페이스 상속으로 객체의 형태를 통일
![](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/Annotation/annotation2.png)

#### 방법3. 어노테이션을 이용하여  key설정
![](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/Annotation/annotation3.png)

#### 비교
* 방법1 : </br> - 도메인 객체가 늘어날 때마다 데이터 관리 방법을 추가 지정해줘야하는 번거로움 존재</br> - Data-Server에서 Service-Server의 도메인객체의 속성을 알고 있어야 하는 문제 존재

* 방법2 :</br> - 도메인 객체가 데이터 저장을 위해 불필요하게 인터페이스를 상속받아야 함 </br> -  객체의 충돌 가능성 존재

* 방법3 :</br> - 위에대한 문제 해결 뿐만 아니라</br> - 어노테이션을 사용하면 소스코드를 사용하는 것보다 통제가 쉬움 (ex>Spring에서 AOP)</br>- 사전에 미리 컴파일 하기전에 에러처리 가능 -> 코드에 대한 예외 처리가 필요없으므로

### 2. __Annotation 사용 방법__
* @Target 지정 : 어떤 대상을 위한 어노테이션인지 설정</br> - TYPE / FIELD / METHOD / PARAMETER / CONSTRUCTOR / LOCAL_VARIABLE / ANNOTATION_TYPE / PACKAGE 존재
* @Retention 지정 :  어노테이션의 지속기간 설정</br> - SOURCE : 어노테이션이 컴파일러에 의해 버려짐 </br> - CLASS : 클래스 저장, JVM에게 무시 </br> - RUNTIME : 클래스 저장, JVM에게 읽혀짐
* @interface 어노테이션 설정 : 커스텀 어노테이션 정의 및 멤버 추가

> 어노테이션 정의

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation{
	public String value() default "값";
	public String key();
}
```
> 어노테이션 이용

```java
@CustomAnnotation(key = "Student")
class UseAnnotation{
}
```

> 메인함수에서 어노테이션 사용

```java
public static void main(String args[]) {

  UseAnnotation use = new UseAnnotation();

  String key = use.getClass().getAnnotation(CustomAnnotation.class).key();
  if(key.equals("Student")) {
    /*런타임시에 해줄 행동을 정의*/
  }
}
```

### 3. __Annotation 예시__
데이터 베이스 연동하는 경우

> GetCnnection

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface GetConnection{
	public String url();
	public String id();
	public String pw();
}
```

>  연동

```java
public class AnnoTest {

	@GetConnection(url="주소",id="아이디",pw="비밀번호")
	public void process() {
		int a = 156;
		int b = 121312;
		int result = a+b/1450;

		System.out.println("result: "+result);
		String array[] = {"a","b","c"};
		for(String item : array) {
			System.out.printf("내용은 %d 입니다", item);
		}
		try {
			DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
```
