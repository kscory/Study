import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class Model {
	
	private final String COLUM_SEP ="::";
	
	private final String DB_DIR = "D:\\workspaces\\java\\database";//윈도우는 역방향 슬래시, 맥은 정방향 슬래시 ////<- 수정
	private final String DB_FILE = "memo.txt"; ////<- 수정
	private final String DB_FILE_INDEX = "Index.txt";
	
	private File database = null;
	private File indexDatabase = null;
	
	// 전체 메모를 저정하는 저장소
	// 여러개의 객체를 저장하는 list가 된다.
	ArrayList<Memo> list = new ArrayList<>();
	
	//생성자
	public Model() {
		File dir = new File(DB_DIR);
		// 디렉토리 존재유무
		if(!dir.exists()) {
			dir.mkdirs(); 
		}
		File file = new File(DB_DIR + File.separator + DB_FILE);
		File fileIndex = new File(DB_DIR + File.separator + DB_FILE_INDEX);
		
		//파일의 존재유무 파악
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(!fileIndex.exists()) {
			try {
				fileIndex.createNewFile();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		database = file;
		indexDatabase = fileIndex;
	}
	
	public void create(Memo memo) {
		
		//글번호 불러오기
		memo.no = showIndex()+1;
		
		FileOutputStream fos =null;
		try {
			// 저장할 내용을 구분자로 분리하여 한줄의 문자열로 바꾼다.
			String row = memo.no + COLUM_SEP 
					+ memo.name + COLUM_SEP 
					+ memo.content + COLUM_SEP 
					+ memo.datetime + "\n";
			
			// 1. 쓰는 스트림 활용 (Output 스트림)
			fos = new FileOutputStream(database, true);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.append(row);
			bw.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally { //무조건 실행
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//read
	public Memo read(int no) {
		
		list.clear();
		showList();
			
		// no에 맞는 memo 찾기
		for(Memo memo : list) {
			if(memo.no == no) {
				
				return memo;
			}
		}
		return null;
	}
	
	//update
	public boolean update(int no, Memo memoTemp) {		
		boolean check=false;
		boolean checkTemp = false;
		//글번호를 받아 특정 memo Search
		
		list.clear();
		showList();
		
		String row="";
		for(Memo memo : list) {
			if(memo.no == no) {
				memo.name = memoTemp.name;
				memo.content = memoTemp.content;
				checkTemp=true;
			}
			
			row = row
				+ memo.no + COLUM_SEP 
				+ memo.name + COLUM_SEP 
				+ memo.content + COLUM_SEP 
				+ memo.datetime + "\n";
		}		
		
		FileOutputStream fos =null;
		try {
						
			// 쓰는 스트림 (Output 스트림)
			fos = new FileOutputStream(database, false);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.append(row);
			bw.flush();
			if(checkTemp==true) {
				check = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return check;
	}
	
	//delete
	public boolean delete(int no) {
		boolean check = false;
		boolean checkTemp = false;
		
		list.clear();
		showList();
		
		String row = "";
		Iterator<Memo> iter = list.iterator();
		while(iter.hasNext()) {
			Memo memo = iter.next();
			
			if(memo.no == no) {
				checkTemp = true;
			} else {
				row = row
						+ memo.no + COLUM_SEP 
						+ memo.name + COLUM_SEP 
						+ memo.content + COLUM_SEP 
						+ memo.datetime + "\n";
			}			
		}
		
		FileOutputStream fos =null;
		try {
			//쓰는 스트림 활용 (Output 스트림)
			fos = new FileOutputStream(database, false);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.append(row);
			bw.flush();
			if(checkTemp==true) {
				check = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return check;
	}
	
	// 목록을 가져옴 
	public ArrayList<Memo> showList(){
		
		// 데이터가 중복해서 쌓이지 않도록 저장소를 지워주는 작업이 필요한 경우가 있다.
		list.clear();
		
		// 읽는 스트림 활용
		try(FileInputStream fis = new FileInputStream(database)){
			InputStreamReader isr = new InputStreamReader(fis, "MS949");
			BufferedReader br = new BufferedReader(isr);
			
			String row;
			while( (row = br.readLine()) != null ) { 
				String tempRow[] = row.split(COLUM_SEP);
				Memo memo = new Memo();
				memo.no = Integer.parseInt(tempRow[0]);
				memo.name = tempRow[1];
				memo.content = tempRow[2];
				memo.datetime = Long.parseLong(tempRow[3]);
				
				list.add(memo);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return list;
	}
	
	public int showIndex() {
		int index=0 ; 
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(indexDatabase);
			InputStreamReader isr = new InputStreamReader(fis,"MS949");
			BufferedReader br = new BufferedReader(isr);
			String row;
			
			while((row = br.readLine()) != null) {
				index = Integer.parseInt(row);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return index;
	}

}