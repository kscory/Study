
/**
 * 
 * @author Kyung
 * Controlflow 난이도3.8 예제 풀이
 *
 */
public class Test038 {
	
	public void run(String mark, int lines) {
		
		//라인수 만큼 반복
		for(int index=0 ; index<lines*2-1 ; index=index+1) {
			
			//라인수가 입력값보다 작은경우 실행
			if(index<lines){
				
				// 시작할 때 공백값
				for(int innerSpaceIndex=0 ; innerSpaceIndex < lines-index-1 ; innerSpaceIndex++) {
					System.out.print(" ");
				}
				
				// mark 표시
				for(int innerMarkIndex=0 ; innerMarkIndex<2*index+1 ; innerMarkIndex++) {
					System.out.print(mark);
				}
				System.out.println("");
			}
			// 라인수가 입력값보다 크거나 같은 경우 실행
			else {
				
				//시작할 때 공백값
				for(int downSpaceIndex=0 ; downSpaceIndex < index-lines+1 ; downSpaceIndex++) {					
					System.out.print(" ");
				}
				
				//값 표시
				for(int downMarkIndex=0 ; downMarkIndex<4*lines-2*index-3 ; downMarkIndex++) {
					System.out.print(mark);
				}
				System.out.println("");
			}
		}
	}

}
