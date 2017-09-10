
/**
 * 
 * @author Kyung
 * Controlflow 난이도3.6 예제 풀이
 *
 */
public class Test036 {
	
	public void run(String mark, int lines) {
		
		//라인수 만큼 반복
		for(int index=0 ; index<lines-1 ; index=index+1) {
			
			// 시작 공백 표시
			for(int innerSpaceIndex=0 ; innerSpaceIndex < lines-index-1 ; innerSpaceIndex++) {				
				System.out.print(" ");
			}
			
			//왼쪽 값 mark
			System.out.print(mark);
			
			//가운데 공백 표시
			for(int innerMarkIndex=0 ; innerMarkIndex<index*2-1 ; innerMarkIndex++) {
				System.out.print(" ");
			}
			
			//오른쪽 갑 mark
			if(index==0) {
				System.out.println("");
			} else {
				System.out.println(mark);				
			}			
		}
		
		//마지막 라인 값 mark
		for(int finalIndex=0 ; finalIndex < lines*2-1 ; finalIndex++) {
			System.out.print(mark);
		}
	}

}
