
/**
 * 
 * @author Kyung
 * Controlflow 난이도5 예제 풀이
 *
 */
public class Test05 {
	
	public void run(String mark, int lines) {
		
		for(int lineIndex =0 ; lineIndex<lines*2-1 ; lineIndex=lineIndex+1) {
			
			//라인수가 입력값보다 작은 경우 실행
			if(lineIndex<lines) {
				
				//처음 공백 출력
				for(int outerSpaceIndex=0 ; outerSpaceIndex<lines-lineIndex-1 ; outerSpaceIndex=outerSpaceIndex+1) {
					System.out.print(" ");
				}
				
				// 처음 mark 찍기
				System.out.print(mark); 
				
				//가운데 공백 출력하기
				for(int innerSpaceIndex=0 ; innerSpaceIndex<lineIndex*2-1 ; innerSpaceIndex=innerSpaceIndex+1) {
					System.out.print(" ");
				}
				
				//마지막 mark 찍기
				if(lineIndex==0) {
					System.out.println("");
				} else {
					System.out.println(mark); 					
				}
			}
			
			//라인수가 입력값보다 크거나 같은 경우 실행
			else {
				
				//처음 공백 출력
				for(int downOuterSpaceIndex=0 ; downOuterSpaceIndex<lineIndex-lines+1 ; downOuterSpaceIndex=downOuterSpaceIndex+1) {
					System.out.print(" ");
				}
				
				//처음 mark 출력
				System.out.print(mark);
				
				//가운데 공백 출력
				for(int downInnerSpaceIndex=0 ; downInnerSpaceIndex<4*lines-2*lineIndex-5 ; downInnerSpaceIndex=downInnerSpaceIndex+1) {
					System.out.print(" ");
				}
				
				//마지막 mark 출력
				if(lineIndex!=lines*2-2) {
					System.out.println(mark);					
				} else {
					System.out.println("");
				}
				
			}
			
		}
		
	}

}
