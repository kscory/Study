/**
 * 
 * Controlflow 난이도5.7 예제 풀이
 * mark값과 받을 입력값 lines 입력
 * 
 * @author Kyung
 * 
 *
 */
public class Test057 {
	
	public void run(String mark, int lines) {
		
		//첫줄 출력
		for(int firstIndex=0 ; firstIndex<lines*2-1 ; firstIndex++) {
			System.out.print(mark);
		}
		System.out.println("");
		
		//가운데 출력
		for(int middleLine=0; middleLine<lines*2-3 ; middleLine++) {
			System.out.print(mark);
			
			for(int middleSpaceIndex=0; middleSpaceIndex<lines*2-3 ; middleSpaceIndex++) {
				System.out.printf(" ");
			}
			System.out.println(mark);
		}
		
		//마지막 출력
		for(int finalIndex=0 ; finalIndex<lines*2-1 ; finalIndex++) {
			System.out.print(mark);
		}
	}

}
