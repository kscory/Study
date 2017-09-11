package com.kyungseok.java.callback;

import com.kyungseok.java.inter.C;
import com.kyungseok.java.inter.InterfaceC;

public class A implements InterfaceC{

	public void process(){
		C c = new C();
		c.process(this);
		// 실행이 끝난다음에 afterProcess
	}
	
	public void afterProcess(){
		System.out.println("실행이 완료되었습니다.");
	}

	@Override
	public void callback() {
		afterProcess();
	}
}