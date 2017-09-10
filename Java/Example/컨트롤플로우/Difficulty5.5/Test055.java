
/**
 * 
 * @author Kyung
 * Controlflow 난이도5.5 예제 풀이
 *
 */
public class Test055 {
	public void run(String mark, int lines) {
		
		for(int lineIndex =0 ; lineIndex<lines*2-1 ; lineIndex=lineIndex+1) {
			
			//라인수가 입력값보다 작은 경우 실행(윗부분)
			if(lineIndex<lines) {
				
				//처음 공백 출력
				for(int outerSpaceIndex=0 ; outerSpaceIndex<lineIndex ; outerSpaceIndex=outerSpaceIndex+1) {
					System.out.print(" ");
				}
				
				// 처음 mark 찍기
				System.out.print(mark); 
				
				//가운데 공백 출력
				for(int innerSpaceIndex1=0 ; innerSpaceIndex1<2*(lines-lineIndex)-3 ; innerSpaceIndex1=innerSpaceIndex1+1) {
					System.out.print(" ");
				}

				//마지막 mark 찍기
				if(lineIndex==lines-1) {
					System.out.println("");
				} else {
					System.out.println(mark); 					
				}
			}
			
			//라인수가 입력값보다 크거나 같은 경우 실행(아래부분)
			else {
				
				//처음 공백 출력
				for(int downOuterSpaceIndex=0 ; downOuterSpaceIndex<2*lines-lineIndex-2 ; downOuterSpaceIndex=downOuterSpaceIndex+1) {
					System.out.print(" ");
				}
				
				//처음 mark 출력
				System.out.print(mark);
				
				//가운데 공백 출력1
				for(int downInnerSpaceIndex=0 ; downInnerSpaceIndex<2*(lineIndex-lines)+1 ; downInnerSpaceIndex=downInnerSpaceIndex+1) {
					System.out.print(" ");
				}
				
				//마지막 mark 출력
				System.out.println(mark);
				
			}

			
		}
		
	}

}