
//값은 입력값만큼
//처음 공백은 0, 6, 6+5, 6+5+4...

/**
 * 
 * Controlflow 난이도6 예제 풀이
 * mark값과 받을 입력값 lines 입력
 * 
 * @author Kyung
 * 
 *
 */
public class Test06 {
	
	public void run(String mark, int lines) {
		
		// 라인수만큼 반복
		for(int lineIndex=0; lineIndex<lines ; lineIndex=lineIndex+1) {
			
			//처음 공백 출력
			for(int firstSpaceIndex=0 ; firstSpaceIndex<lineIndex ; firstSpaceIndex++) {
				
				for(int i=0 ; i<lines-firstSpaceIndex-1 ; i++)
				{
					System.out.print(" ");
				}
			}
			
			//(mark+공백)을 한세트로 출력
			for(int setIndex=0;setIndex<lines-lineIndex; setIndex=setIndex+1) {
				
				//mark하고
				System.out.print(mark);
				
				//가운데 공백 출력
				for(int middleSpaceIndex=0; middleSpaceIndex<lines-lineIndex-1 ; middleSpaceIndex=middleSpaceIndex+1) {
					System.out.print(" ");
				}
				
			}
				System.out.println("");

		}
	}

}
