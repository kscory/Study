package com.kyungseok.java.generic;

public class GenericMain {
	
	public static void main(String args[]) {
		
		NewList<String> tests = new NewList<>();
		
		tests.add("1번");
		System.out.println(tests.get(0));
		tests.add("2번");
		System.out.println(tests.get(1));
		tests.add("3번");
		System.out.println(tests.get(2));
		tests.add("4번");
		System.out.println(tests.get(3));
		
		System.out.println(tests.size());
		
		tests.remove(1);
		System.out.println(tests.get(0));
		System.out.println(tests.get(1));
		System.out.println(tests.get(2));	
	}
		
}
