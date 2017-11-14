package com.kyungseok.java.lambda;

public class LambdaMain {
	public static void main(String[] args) {
		
		// Thread(runnable) 의 경우
		// 기존 사용 방식
		Runnable two = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello");
			}
		};
		
		// 람다식 이용
		Runnable two2 = () -> {
			System.out.println("Hello");
		};
		
		// 실행
		new Thread(two2).start();
		
		//========================================================
		
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
