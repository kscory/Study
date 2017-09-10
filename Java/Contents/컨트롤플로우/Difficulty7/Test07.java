
// 0+1+2+3+4+..., ..., 0+1+2, 0+1, 0 으로 초반 공백
// 라인수 만큼 공통 공백
// 2*((라인수-라인번호-1)), 2*((라인수-라인번호-1)+(라인수-라인번호-2)),... 

/**
 * 
 * Controlflow 난이도7 예제 풀이
 * mark값과 받을 입력값 lines 입력
 * 
 * @author Kyung
 * 
 *
 */
public class Test07 {

	public void run(String mark, int lines) {
		
		for(int lineIndex=0; lineIndex<lines ; lineIndex=lineIndex+1) {
			
			//처음 공백 출력
			for(int firstSpaceIndex=0 ; firstSpaceIndex<lines-lineIndex-1 ; firstSpaceIndex++) {
				
				for(int i=0 ; i<firstSpaceIndex+1 ; i++)
				{
					System.out.print(" ");
				}
			}
			
			//왼쪽 mark
			System.out.print(mark);
			
			//값 사이 공백 표시
			//가운데 공백 출력
			for(int innerSpaceIndex=0 ; innerSpaceIndex<lines ; innerSpaceIndex=innerSpaceIndex+1) {
				System.out.print(" ");

			}
			//추가 공백 출력
			for(int innerSpaceIndex2=0 ; innerSpaceIndex2<lineIndex ; innerSpaceIndex2++) {
				
				for(int i=0 ; i<2*(lines-innerSpaceIndex2)-2 ; i++)
				{
					System.out.print(" ");
				}	
			}
			
			//오른쪽 mark
				System.out.println(mark);
		}
	}

}