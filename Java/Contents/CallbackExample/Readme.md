# Callback 개념
Interface가 사용되는 Callback


## Callback 간단한 설명
* 다른 함수의 매개변수로 호출될 함수를 전달하고, 특정이벤트가 발생하고 나서 매개변수로 호출된 함수가 다시 호출되는 것을 의미
* 다른 코드의 인수로서 넘겨주는 실행 가능한 코드
* 콜백함수 문법은 특정 함수의 동작이 끝남과 동시에, 다른 여러 가지 함수를 호출해야 할 경우에 사용
### 멀티쓰레드일 때 사용 예시
![](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/CallbackExample/picture/callback1.png)

## Callback 사용 예제

### 1. __코드(그림)__
A클래스에서 process call -> C클래스에서 process 실행 -> C에서 끝난 후 Callback 실행 -> A클래스에서 afterprocess 실행
</br> ![](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/CallbackExample/picture/callback2.png)

### 2. __몇가지 중요 설명__
A클래스는 InterfaceC를 상속받으므로 c.process(InterfaceC c) 에서 인자로 대입 가능

```java
public class A implements InterfaceC{
	public void process(){
		C c = new C();
		c.process(this); // A를 대입.
```

c.process(this)를 하는 경우 "InterfaceC c = A"로 인식 (다형성 적용)</br>
* 즉, "InterfaceC c = new 인터페이스 상속Class" 와 같이 인식하므로 c 메소드를 사용하되 내용은 A를 실행</br>
* 이 상황에서 callback이 실행됨
</br>
![](https://github.com/Lee-KyungSeok/Study/blob/master/Java/Contents/CallbackExample/picture/callback3.png)
