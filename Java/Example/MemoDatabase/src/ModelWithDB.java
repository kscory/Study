import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;


public class ModelWithDB {

	private static final String url ="jdbc:mysql://localhost:3306/memo"; //추가로 데이터베이스 이름도 써주어야 한다.
	private static final String ID = "root";
	private static final String PW = "mysql";
	Connection con = null;
	
	// 생성자
	public ModelWithDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); //드라이버를 동적으로 로드
		} catch (Exception e) {
			System.out.println("연결 실패");
			e.printStackTrace();
		}
	}
	
	// create
	public void create(Memo memo) {
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(url, ID, PW)) { // 데이터베이스 자동 연결 해제	
			// 2. 쿼리를 실행
			// 2.1 쿼리 생성
			String query = " insert into memo(name, content, datetime)"
							+ " values(?, ?, ?)";
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다.
			PreparedStatement pstmt = con.prepareStatement(query);
			// 2.3 물음표에 값을 세팅
			pstmt.setString(1, memo.name);
			pstmt.setString(2, memo.content);
			pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			// 2.4 쿼리를 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("연결 실패");
			e.printStackTrace();
		} // 3. 데이터 베이스 연결 해제
	}
	
	// read
	public Memo read(int no) {
		Memo memo = new Memo();
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(url, ID, PW)) { // 데이터베이스 자동 연결 해제	
			// 2. 쿼리를 실행
			// 2.1 쿼리 생성
			String query = "select * from memo where no ="+no;
			// 2.2 앞으로 쿼리를 실행 가능한 상태로 만들어준다.
			Statement stmt = con.createStatement();
			// 2.3 select한 결과값을 돌려받기 위한 쿼리를 실행
			ResultSet rs = stmt.executeQuery(query);
			//rs에는 iterator가 이미 존재함
			//결과셋을 반복하면서 하나씩 꺼낼 수 있다.
			if(rs.next()) {
				memo.no = rs.getInt("no");
				memo.name = rs.getString("name");
				memo.content = rs.getString("content");
				memo.datetime = rs.getLong("datetime");
				// 메모 클래스에 값을 세팅
			}
			if(memo.no==0) {
				return null;
			}
		} catch (Exception e) {
			System.out.println("연결 실패");
			e.printStackTrace();
		}
		return memo;
	}
	
	// update
	public boolean update(int no, Memo memoTemp) {	
		boolean check=false;
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(url,ID,PW)){
			//2. 쿼리를 실행
			String query = "update memo set name = ?, content = ? where no = ?";
			PreparedStatement psmt = con.prepareStatement(query);
			psmt.setString(1, memoTemp.name);
			psmt.setString(2, memoTemp.content);
			psmt.setInt(3, no);
			psmt.executeUpdate();
			
			check = true;
		} catch(Exception e) {
			System.out.println("연결 실패");
			e.printStackTrace();
		}
		return check;
	}
	
	//delete
	public boolean delete(int no) {
		boolean check = false;
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(url,ID,PW)){
			//2. 쿼리를 실행
			String query = "delete from memo where no = ?";
			PreparedStatement psmt = con.prepareStatement(query);
			psmt.setInt(1, no);
			psmt.executeUpdate();
			
			check=true;
		} catch(Exception e) {
			System.out.println("연결 실패");
			e.printStackTrace();
		}
		return check;
	}
	
	// showList : 목록을 가져옴 
	public ArrayList<Memo> showList(){
		ArrayList<Memo> list = new ArrayList<>();
		// 1. 데이터베이스 연결
		try(Connection con = DriverManager.getConnection(url, ID, PW)) { // 데이터베이스 자동 연결 해제	
			// 2. 쿼리를 실행
			String query = "select * from memo";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Memo memo = new Memo();
				memo.no = rs.getInt("no");
				memo.name = rs.getString("name");
				memo.content = rs.getString("content");
				memo.datetime = rs.getLong("datetime");
				// 메모 클래스에 값을 세팅
				list.add(memo);
			}
		} catch (Exception e) {
			System.out.println("연결 실패");
			e.printStackTrace();
		}
		return list;
	}
}
