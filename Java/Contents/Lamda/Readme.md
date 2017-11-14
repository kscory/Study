# Lambda Expression (람다 표현식)
  - 람다식 사용법
  - [참고_람다식pdf](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/LamdaExpression/pdf/Java8_Lambda.pdf)

---

## 람다식 사용 특징 및 사용 방법
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

  >1. 함수명을 없애고 () {} 사이에 화살표를 넣는다.

  ```java
  (int x) -> {
    System.out.println(x);
  }
  ```

  >2. 파라미터가 하나이면 타입을 생략할 수 있다.

  ```java
  (x) -> {
    System.out.println(x);
  }
  ```

  >3. 하나의 파라미터면 파라미터 측의 괄호를 생략, 로직이 한줄이면 로직측의 괄호를 생략한다.

  ```java
  x -> System.out.println(x)
  ```

  >4. 파라미터가 없으면 괄호를 반드시 작성한다.

  ```java
  () -> System.out.println("Hello")
  ```

  >5. 리턴값이 있을 경우 리턴값을 작성한다.

  ```java
  () -> return "Hello"
  ```

  >6. 참조형 -> 파라미터의 개수가 __예측 가능할 경우__ 객체::메서드 형태로 호출할 수 있다.

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

  		// 클래스 생성
  		OneProcess processOne = new OneProcess();

  		// 기존 사용 방식
  		processOne.process( new One() {
  			@Override
  			public void run(int x) {
  				System.out.println(x);
  				}
  			}
  		);

  		// 람다식 이용
  		processOne.process( x -> System.out.println(x) );
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
  - Runnable 인터페이스를 사용할 때 아래와 같이 사용하게 된다.

  > Runnable 이용

  ```java
  public class LambdaMain {
  	public static void main(String[] args) {

  		// 기존 사용 방식
  		Runnable two = new Runnable() {
  			@Override
  			public void run() {
  				System.out.println("Hello");
  			}
  		};

  		// 람다식 이용
  		Runnable two = () -> {
  			System.out.println("Hello");
  		};

  		// 실행
  		new Thread(two).start();
  	}
  }
  ```

  ---

## 람다 인터페이스

  ### 1. 단항 인터페이스
  - Supplier : 입력값이 없고, 반환값이 있을때 사용

  ```java
  Supplier<Integer> supplier = () -> 180 + 20;
  System.out.println(supplier.get());
  ```

  - Consumer : 입력값이 있고, 반환값이 없을때 사용 (코드 블럭에서 사용처리가 되어야 한다.)

  ```java
  Consumer<Integer> consumer = System.out::println;
  consumer.accept(100);
  ```

  - Function : 입력값도 있고, 반환값도 존재 (입력값과 반환값의 타입을 제네릭으로 정의)

  ```java
  Function<Integer, Double> function = x -> x*0.55;
  System.out.println(function.apply(50));
  ```

  - Predicate : 입력값에 대하 참 거짓을 판단 (return type = boolean)

  ```java
  Predicate<Integer> predicate = x -> x <100;
  System.out.println("50은 100보다 작습니다 : " + predicate.test(50));
  ```

  - UnaryOperator : 입력과 반환 타입이 동일할 때 사용

  ```java
  UnaryOperator<Integer> unary = x -> x+20;
  System.out.println(unary.apply(100));
  ```

  ### 2. 이항 인터페이스
  - Consumer : Consumer에 입력값이 2개

  ```java
  BiConsumer<Integer, Integer> biConsumer = (x,y) -> System.out.println( x + y) ;
  biConsumer.accept(30, 27);
  ```

  - BiFunction : Function 에 입력값이 2개

  ```java
  BiFunction<Integer, Integer, Double> biFunction = (x,y) -> x+y*0.55;
  System.out.println(biFunction.apply(50,20));
  ```

  - BiPredicate : Predicate 에 입력값이 2개

  ```java
  BiPredicate<Integer, Integer> biPredicate = (x,y) -> x+y <100;
  System.out.println("50+20은 100보다 작습니다 : " + biPredicate.test(50,20));
  ```

  - BinaryOperator : UnaryOperator 에 입력값이 2개

  ```java
  BinaryOperator<Integer> binary = (x,y) -> x+y+20;
  System.out.println(binary.apply(100,30));
  ```
