# Generic(제네릭)
 클래스 내부에서 사용할 데이터 타입을 외부에서 지정하는 기법을 의미


## Generic 설명
예시
### 1. __게시판 예시__
> 게시판 목록과 뉴스 목록이 다른데 이를 "T" 라는 타입을 지정해서 사용한다.

```java
class ListsBoard<T> {
	//T는 type의 약자.
	public T lists;
}
class News{
	String thumbnale;
	String title;
	String summary;
}
class Gallery{
	int no ;
	String title;
	String author;
	String datetime;
	int count;
}
```

### 2. __Animal 예시__
> 동물의 타입이 다르므로 "T" 라는 타입을 지정해 사용
```java
class Animal<T> {
	public T animals[];
}
class Tiger {
	String name = "호랑이";
}
class Lion {
}

public static void main(String args[]) {

	Tiger t1 = new Tiger();
	Tiger t2 = new Tiger();
	// tiger라는 타입을 지정
	Animal<Tiger> tigers = new Animal<>();
	tigers.animals = new Tiger[10];
	tigers.animals[0] = t1;
	tigers.animals[1] = t2;

	Animal<Lion> lions = new Animal<>();
	lions.animals = new Lion[10];
}
```

## 참고 문제
Generic을 활용한 문제
1. [Generic을 활용한 ArrayList 개발](https://github.com/Lee-KyungSeok/Study/tree/master/Java/Example/GenericExampleAnsnwer)
