package com.kyungseok.java.lambda;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LambdaInterfaceBasic {
	public void run() {
		/*
		 * 단항 인터페이스
		 */
		// 1. Supplier : 입력값이 없고, 반환값이 있을때 사용
		Supplier<Integer> supplier = () -> 180 + 20;
		System.out.println(supplier.get());
		
		// 2. Consumer : 입력값이 있고, 반환값이 없을때 사용
		//				  코드 블럭에서 사용처리가 되어야 한다. 
		Consumer<Integer> consumer = System.out::println;
		consumer.accept(100);
		
		// 3. Function : 입력값도 있고, 반환값도 있다.
		//				  입력값과 반환값의 타입을 제네릭으로 정의
		Function<Integer, Double> function = x -> x*0.55;
		System.out.println(function.apply(50));
		
		// 4. Predicate : 입력값에 대하 참 거짓을 판단. return type = boolean
		Predicate<Integer> predicate = x -> x <100;
		System.out.println("50은 100보다 작습니다 : " + predicate.test(50));
		
		// 5. UnaryOperator : 입력과 반환 타입이 동일할 때 사용
		UnaryOperator<Integer> unary = x -> x+20;
		System.out.println(unary.apply(100));
		
		/*
		 * 이항 인터페이스
		 */
		// 1. BiConsumer  	 : Consumer  에 입력값이 두개
		BiConsumer<Integer, Integer> biConsumer = (x,y) -> System.out.println( x + y) ;
		biConsumer.accept(30, 27); // 결과는 57
		
		// 2. BiFunction  	 : Function   	"
		BiFunction<Integer, Integer, Double> biFunction = (x,y) -> x+y*0.55;
		System.out.println(biFunction.apply(50,20));
		
		// 3. BiPredicate 	 : Predicate  	"
		BiPredicate<Integer, Integer> biPredicate = (x,y) -> x+y <100;
		System.out.println("50+20은 100보다 작습니다 : " + biPredicate.test(50,20));
		
		
		// 4. BinaryOperator : 				"
		BinaryOperator<Integer> binaryOperator = (x,y) -> x+y+20;
		System.out.println(binaryOperator.apply(100,30));
		
	}

}
