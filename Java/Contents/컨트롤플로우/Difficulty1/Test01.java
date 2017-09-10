
/**
 * 
 * @author Kyung
 * Controlflow 난이도1 예제 풀이
 *
 */
public class Test01 {
	
	// 표시할 값, 표시할 라인 수 입력
	public void run(String mark, int lines) {
		
		// 라인만큼 반복
		for(int index=0 ; index<lines ; index=index+1) {
			
			// 입력될 값 표시
			for(int innerIndex=0 ; innerIndex<index+1 ; innerIndex++) {
				System.out.print(mark);
			}
			System.out.println("");
		}
	}
}