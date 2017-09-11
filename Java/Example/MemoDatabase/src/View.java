import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
	// 키보드 입력을 받는 함수
			public Memo create(Scanner scanner) {
				// 글 하나를 저장하기 위한 메모리 공간 확보
				Memo memo = new Memo();
				
				println("이름을 입력하세요 : ");
				memo.name = scanner.nextLine();
				println("내용을 입력하세요 : ");
				memo.content = scanner.nextLine();
				
				// 날짜는 자동으로 입력(시스템 시간)
				memo.datetime = System.currentTimeMillis();
				
				return memo;
			}
			
			public void read(Memo memo) {
				// 입력받은 메모에 저장된 값 출력
				println("No: "+memo.no);
				println("Author: "+memo.name);
				println("Content: "+memo.content);
				
				//숫자로 입력받은 날짜값을 실제 날짜로 변경
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String formattedDate = sdf.format(memo.datetime);
				println(formattedDate);		
			}
			//새로운 메모리 저장
			public Memo update(Memo memo, Scanner scanner) {
				
				Memo memoTemp = new Memo();
				// 업데이트 할 글을 새로운 메모리에 저장
				println("이름을 입력해주세요: ");
				memo.name = scanner.nextLine();
				println("내용을 입력해주세요: ");
				memo.content = scanner.nextLine();
				memoTemp = memo;

				return memoTemp;
			}
			//update여부 체크
			public void update(boolean check) {
				if(check==true) {
					println("메모가 성공적으로 수정되었습니다");
				} else {
					println("메모가 수정이 실패하였습니다.");
				}
			}
			
			//delete 여부 체크
			public void delete(boolean check) {
				if(check==true) {
					println("메모가 성공적으로 삭제되었습니다");
				} else {
					println("메모가 삭제가 실패하였습니다.");
				}
			}
			
			public void showList(ArrayList<Memo> memoList) {
				// ArrayList 저장소를 반복문을 돌면서 한줄씩 출력
				for (Memo memo : memoList) {
					print("No: "+memo.no);
					print(" | Author: "+memo.name);
					println(" | Content: "+memo.content);
				}
			}
			
			public void print(String string) {
				System.out.print(string);
			}
			
			public void println(String string) {
				System.out.println(string);
			}
			
			// 글번호를 입력받아 리턴
			public String findNo(Scanner scanner){
				println("글 번호를 입력하세요");
				String tempNo = scanner.nextLine();
				return tempNo;		
			}
			
			// 특정 번호에 맞게 메시지 전달
			public void message(int num) {
				if(num==0) {
					println("입력한 글 번호가 존재하지 않습니다");
				} else if(num==1) {
					println("숫자만 입력해 주세요.");
				} else if(num==100) {
					println("시스템이 종료되었습니다");
				}
			}
}
