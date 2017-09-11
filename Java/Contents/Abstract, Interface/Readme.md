# Abstract / Interface
설계에서는 기능의 명세를 알려주는데 이때 주로 사용되는 클래스


## Abstract / Interface 설명
각각에 대한 기본적인 설명
### 1. __Abstract__
> 추상클래스는 abstract로 선언되고 반드시 추상 매소드(내용 없이 비어있음) 1개 이상을 갖는다.
> 추상클래스 안에 코드 블럭은 가질 수 있다.

```java
abstract class Base {
	abstract void create();
	abstract void read();
	abstract void update();
}
```
> 추상메소드를 사용하기 위해서는 해당 메소드를 Override 하고 재정의 한다..

```java
abstract class Memo extends Base {
	@Override
	public void create() {
		read();
	}
	@Override
	public void read() {
		System.out.println("called read!");
	}
	@Override
	public void update() {
		System.out.println("called update!");
	}
}
```
> 아래와 같이 추상클래스를 연속해서 상속받을 수 있다.
> 단 다중으로 상속은 불가능하다. (extends Memo, Animal 불가능)

```java
// 위와 연속해서 상속받음
class MemoImpl extends Memo{
	@Override
	public void read() {
		update();
	}
	public void newRead() {
	}
}
```
> 아래와 같이 실행하게 되면 결과는 update가 실행된다.</br>
> - 여기서 다형성을 이야기하면... memo.NewRead는 불가능하다. 이는 "이름만" 참조한다.</br>
> - NewRead를 쓰기 위해서는 MemoImpl memo = new MemoImpl(); 로 해주어야 한다.

```java
public class Animal {
	public static void main(String args[]) {
		Memo memo = new MemoImpl();
		memo.read();
	}
}
```
### 2. __Interface__
인터페이스는 통신을 하기위해 제공하는 설계 도구로 이해 가능 (서로다른 클래스를 연결시켜주는 연결 장치 )</br>

> 인터페이스는 추상메소드들로만 이루어져 있으며 '메소드의 선언만' 가능 </br>
> - 코드블럭 사용은 불가능하다.

```java
public interface Building {
	public String getName();
	public int getHeight();
}
```
> 인터페이스를 상속받을 때는 implements를 사용한다.
> 상속받은 메소드는 재정의 해주어야 한다.

```java
public class Animal implements Building {
  public static void main(String args[]) {
  }
  // 상속받은 메소드 재정의 필요
  @Override
  public String getName() {
    System.out.println("이름");
    return null;
  }
  @Override
  public int getHeight() {
    return 0;
  }
}
```
> 인터페이스는 다중상속이 가능하다.

```java
public class Animal implements Building, Example {
  public static void main(String args[]) {
  }
  /* 상속받은 메소드 재정의*/
```

### 3. __Abstract와 Interface 차이__
* Abstract는 코드블럭을 넣을 수 있지만 Interface는 불가능
* Abstract는 다중상속이 불가능, Interface는 다중상속 가능</br>- SOLID 원칙에서 코드를 바꿀 수 없기 때문에 다중상속처리르 많이 하여 Interface를 많이 사용
*

## Interface 사용 예시
인터페이스 사용 예시
### 1. __인터페이스 설계__
>InterfaceC 라는 인터페이스를 설계

```java
interface InterfaceC{
	public String getValue();
}
```

### 2. __인터페이스를 구현한 구현체__
> "C"와 "D"라는 클래스를 구현

```java
//인터페이스를 구현한 구현체 1
class C implements InterfaceC{
	@Override
	public String getValue() {
		return "c값";
	}
}

//인터페이스를 구현한 구현체 2
class D implements InterfaceC{

	@Override
	public String getValue() {
		return "d값";
	}
}
```

### 3. __C와 D 호출__
다른객체랑 통신하면서 값을 주고 받을 때 그 객체의 함수호출이 아니라 설계된 인터페이스를 통해서 함수를 호출
</br> Interface를 통해서 가져오고, new는 C,D로  한다.
</br> ※ 이때 Casting이 이루어지면서 Interface의 함수를 사용하지만 그 내용은 C에 저장된 내용을 사용한다.
> "C"와 "D"라는 클래스를 구현
```java
public class A {
	public void process() {
		InterfaceC c = new C();
		c.getValue();
		InterfaceC d = new D();
		d.getValue();
	}
}
```
### #참고 그림
![]()

## 심화
Callback 개념
1. [CallBack (Interface 심화)](링크)
