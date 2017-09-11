# Generic을 활용한 ArrayList 만들기

## __문제__
ArrayList의 직접적인 구현
1. size
2. add
3. remove
4. get

## __설명__
### ArrayLsit
#### 0. 생성자 및 배열 설정

```java
public Object list[];
// 값을 넣지 않은 상태에서 사이즈 등의 체크를 할 수 있기 때문에 저장소를 초기화 해주는 작업이 필요
public NewList() {
	// 참고 : if( type instanceOf target) : 타입이 맞으면 true를 반환
	list =new Object[0];
}
```
#### 1. size
> 1. 배열의 길이를 불러온다.

````java
public int size() {
	return list.length;
}
````

#### 2. add
>1. 임시 배열을 배열사이즈가 하나 크도록 생성한다.
>2. 임시 배열에 새로운 값을 포함한 모든 값을 대입한다.
>3. 임시배열을 list에 덮어 씌운다.

````java
public void add(T item) {
	// 배열의 크기를 임시로 늘려서 사용
	Object tempList[] = new Object[list.length+1];
	for(int i=0 ; i<list.length ; i++) {
		tempList[i] = list[i];
	}
	tempList[list.length] = item;
	list = tempList;
}
````

#### 3. remove
>1. 임시 배열을 배열사이즈가 하나 작도록생성한다.
>2. 임시 배열에 입력된 값(index = postion인 값)을 제외한 모든 값을 대입한다.
>3. 임시배열을 list에 덮어 씌운다.

````java
public void remove(int position) {
	//배열의 크기를 임시로 늘려서 사용
	Object tempList[] = new Object[size()-1];
	//position 이전의 데이터를 임시공간으로 복사
	for(int i=0 ; i<position ; i++) {
		tempList[i] = list[i];
	}
	//position 이후의 데이터를 임시공간으로 복사
	for(int i=position+1 ; i<list.length ; i++) {
		tempList[i-1] = list[i];
	}
	list = tempList;
}
````
> 아래와 같이 반복문 한번만 사용해도 됨

```java
int realIndex=0;
for(int i=0 ; i<list.length-1 ; i++) {
	if(i==position) {
		realIndex++;
	}
	tempList[i] = list[realIndex];
	realIndex++;
}
list = tempList;
```

#### 4. get
>1. 리스트에서 인덱스에 맞는 값을 불러온다.

````java
public Object get(int i) {
	Object result = 0;
	result = list[i];
	return result;
}
````
