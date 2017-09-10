import java.util.ArrayList;
import java.util.Scanner;


/**
 * 
 * Controller 역할 담당
 * 
 * @author Kyung
 *
 */
public class MemoMain {
	Model model = new Model();
	View view = new View();
	
	public static void main(String args[]) {
		// 입력을 받아서 처리하는 도구
		Scanner scanner = new Scanner(System.in);
		MemoMain main = new MemoMain();
		
		// 키보드 입력중에 Enter키가 입력될때까지 대기
		//String test = scanner.nextLine(); 
		//System.out.println(test);
		
		String command = "";		
		while(!command.equals("exit")) {
			
			// 명령어를 입력받아서 후속처리
			// c - create : 데이터 입력모드로 전환
			// r - read : 데이터 읽기모드로 전환
			// u - update : 데이터 수정모드로 전환
			// d - delete : 데이터 삭제모드로 전환
			main.view.println("------------명령어를 입력하세요-------------");
			main.view.println("c : 쓰기, r : 읽기, u : 수정, d : 삭제, l : 목록");
			main.view.println("exit : 종료");
			main.view.println("--------------------------------------");
			command = scanner.nextLine();
			
			// 명령어를 분기처리
			switch(command) {
			case "c":
				Memo cMemo = main.view.create(scanner);				
				main.model.create(cMemo);
				break;
			case "r":
				String rTempNo = main.view.findNo(scanner);
				// ------ 숫자가 입력되지 않았을 때의 예외 처리 ------//
				try {
					int no = Integer.parseInt(rTempNo);
					//Model에서 no를 받아 데이터를 불러온다.
					Memo rMemo = main.model.read(no);
					
					//no가 없는 경우 메세지를 보여준다.
					if(rMemo == null) {
						main.view.message(0);
					} else {
						// 데이터 memo를 화면에 보여준다.
						main.view.read(rMemo);
					}
				} catch(NumberFormatException e) {
					main.view.message(1);
				}				
				break;
			case "u":
				String uTempNo = main.view.findNo(scanner);
				// ------ 숫자가 입력되지 않았을 때의 예외 처리 ------//
				try {
					int no = Integer.parseInt(uTempNo);
					//Model에서 no를 받아 데이터를 불러온다.
					Memo uMemo = main.model.read(no);
					
					//no가 없는 경우 메세지를 보여준다.
					if(uMemo ==null) {
						main.view.message(0);
					} else {
						// 데이터의 수정사항을 받는다.
						uMemo = main.view.update(uMemo,scanner);
						// 수정사항을 Model에 전달하고 수정 완료여부를 받아온다.
						boolean updateCheck = main.model.update(no, uMemo);
						// 수정이 완료여부를 사용자에게 알린다.
						main.view.update(updateCheck);
					}
				} catch(NumberFormatException e) {
					main.view.message(1);
				}		
				break;
			case "d":
				String dTempNo = main.view.findNo(scanner);
				// ------ 숫자가 입력되지 않았을 때의 예외 처리 ------//
				try {
					int no = Integer.parseInt(dTempNo);
					//Model에서 no를 받아 데이터를 불러온다.
					Memo dMemo = main.model.read(no);
					
					//no가 없는 경우 메세지를 보여준다.
					if(dMemo ==null) {
						main.view.message(0);
					} else {
						// 데이터 memo를 삭제하고 완료 여부를 알린다.
						boolean deleteCheck = main.model.delete(no);
						// 데이터 삭제여부를 사용자에게 알린다.
						main.view.delete(deleteCheck);
					}
				} catch(NumberFormatException e) {
					main.view.message(1);
				}
				break;
			case "l":
				//전체 데이터를 Model로 부터 받아온다.
				ArrayList<Memo> memoList = main.model.showList();
				//받아온 데이터를 View에 보여준다.
				main.view.showList(memoList);
				break;
			}
		}
		main.view.message(100);

	}
}
