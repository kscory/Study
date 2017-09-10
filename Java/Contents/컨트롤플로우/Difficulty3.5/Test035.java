
/**
 * 
 * @author Kyung
 * Controlflow 난이도3.5 예제 풀이
 *
 */
public class Test035 {
	
	public void run(String mark, int lines) {
		
		//라인수만큼 반복
		for(int index=0 ; index<lines ; index=index+1) {
			
			//시작할 때 공백 표시
			for(int innerSpaceIndex=0 ; innerSpaceIndex < lines-index-1 ; innerSpaceIndex++) {				
				System.out.print(" ");
			}
			
			// 왼쪽 mark
			System.out.print(mark);
			
			//값 사이 공백 표시
			for(int innerMarkIndex=0 ; innerMarkIndex<index*2-1 ; innerMarkIndex++) {
				System.out.print(" ");
			}
			
			//오른쪽 mark
			if(index==0) {
				System.out.println("");
			} else {
				System.out.println(mark);				
			}
		}
	}
}
