
/**
 * 
 * @author Kyung
 * Controlflow 난이도3 예제 풀이
 *
 */
public class Test03 {
	
	public void run(String mark, int lines) {
		
		// 라인 수 만큼 반복
		for(int index=0 ; index<lines ; index=index+1) {
			
			// 공백 출력
			for(int innerSpaceIndex=0 ; innerSpaceIndex < lines-index-1 ; innerSpaceIndex++) {
				
				System.out.print(" ");
			}
			
			// 값 출력
			for(int innerMarkIndex=0 ; innerMarkIndex<index*2+1 ; innerMarkIndex++) {
				System.out.print(mark);
			}
			System.out.println("");
		}
	}
}
