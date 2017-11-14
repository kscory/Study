# Lambda Expression (람다 표현식)
  - 람다식 사용법
  - [참고_람다식pdf](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/LamdaExpression/pdf/Java8_Lambda.pdf)

---

## 람다 표현식 사용 특징 및 상용 방법
  ### 1. 특징
  - 람다식은 java8에서 지원
  - 람다식은 코드량을 줄이는 것이 가장 큰 목표이다.
  - 람다식은 하나의 클래스(인터페이스)에 하나의 함수만이 존재해야 한다.
  - 하나의 함수 안에 여러 인자가 있어도 상관 없다.

  > interface

  ```java
  interface One{
    // 하나의 함수만 존재하지만 인자의 개수는 제한이 없다.
  	public void run(int x, int y, int z);
  }
  ```

  > 구현

  ```Java
  // 이전 방식
  One one = new One() {
    @Override
    public void run(int x, int y, int z) {
        System.out.println("Hello");
    }
  };

  // 람다식 (이를 사용하면 코드량이 줄어든다.)
  One one = (x, y, z) -> {
    System.out.println("Hello");
  };

  // 실행
  one.run(1,2,3);
  ```

  ### 2. 사용 방법
  - Callback클래스를 Lambda로 변경

  > 기존 함수 형태

  ```java
  new Three() {
    public void run(int x) {
      System.out.println(x);
    }
  }
  ```

  > 1. 함수명을 없애고 () {} 사이에 화살표를 넣는다.

  ```java
  (int x) -> {
    System.out.println(x);
  }
  ```

  > 2. 파라미터가 하나이면 타입을 생략할 수 있다.

  ```java
  (x) -> {
    System.out.println(x);
  }
  ```

  > 3. 하나의 파라미터면 파라미터 측의 괄호를 생략, 로직이 한줄이면 로직측의 괄호를 생략한다.

  ```java
  x -> System.out.println(x)
  ```

  > 4. 파라미터가 없으면 괄호를 반드시 작성한다.

  ```java
  () -> System.out.println("Hello")
  ```

  > 5. 리턴값이 있을 경우 리턴값을 작성한다.

  ```java
  () -> return "Hello"
  ```

  > 6. 참조형 -> 파라미터의 개수가 __예측 가능할 경우__ 객체::메서드 형태로 호출할 수 있다.

  ```java
  System.out::println
  ```

---

## 사용 예제
  ### 1. interface 정의 후 사용
  - 보통 interface를 정의하게 되면 이를 실행하는 class를 따로 만들어 주는 경우가 많다.

  > 예시 1

  ```java
  public class LambdaMain {

  	public static void main(String[] args) {
  		/* 이전 사용 방식
  		process.process(
  				new One() {
  					public void run(int x) {
  						System.out.println(x);
  					}
  				}
  		);
  		*/

          // 람다식의 사용
  		OneProcess process = new OneProcess();
  		process.process( x -> System.out.println(x) );
  	}
  }

  class OneProcess{
  	public void process(One one) {
  		one.run(10002);
  	}
  }

  interface One{
  	public void run(int x);
  }
  ```

  ### 2. Thread에서의 사용
  - 내용

  > dfdf.java

  ```java

  ```

  ---

## 사용 인터페이스
  ### 1. 특징
  - 내용

  > dfdf.java

  ```java

  ```
