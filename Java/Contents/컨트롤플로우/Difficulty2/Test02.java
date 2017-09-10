
/**
 * 
 * @author Kyung
 * Controlflow 난이도2 예제 풀이
 *
 */
public class Test02 {
	
	public void run(String mark, int lines) {
		
		// 라인만큼 반복
		for(int index=0 ; index<lines ; index=index+1) {
			
			// 처음 공백 추가
			for(int innerSpaceIndex=0 ; innerSpaceIndex < lines-index-1 ; innerSpaceIndex++) {
				
				System.out.print(" ");
			}
			
			// mark 표시
			for(int innerMarkIndex=0 ; innerMarkIndex<index+1 ; innerMarkIndex++) {
				System.out.print(mark);
			}
			
			System.out.println("");
		}
	}
}