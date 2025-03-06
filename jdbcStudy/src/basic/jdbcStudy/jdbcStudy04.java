package basic.jdbcStudy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class jdbcStudy04 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XEPDB1",
					"LYS00", "java");
			
			System.out.println("게좌 번호 정보 추가하기");
			System.out.println();
			
			System.out.print("계좌 번호 입력 >> ");
			String bankNo = sc.next();
			
			System.out.print("은행명 입력 >> ");
			String bankName = sc.next();
			
			System.out.print("예금주명 입력 >> ");
			String bankUserName = sc.next();
			sc.close();
			
			/*
			// Statement 객체를 이용한 처리
			String sql = "INSERT INTO BANKINFO "
					+ "(BANK_NO, BANK_NAME, BANK_USER_NAME, BANK_DATE) "
					+ "VALUES ('"+bankNo+"', '"+bankName+"', '"+bankUserName+"', SYSDATE)";
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			
			// 'SELECT'문을 실행할 때는 executeQuery()메서드를 사용하고,
			// 'INSERT', 'UPDATE', 'DELETE'문과 같이 'SELECT'문이 아닌 쿼리문을
			// 실행할 때는 executeUpdate()메서드를 사용한다
			
			int cnt = stmt.executeUpdate(sql);
			
			System.out.println("반환값 : " + cnt);
			
			// ------------------------
			*/
			
			// PreparedStatement 객체를 이용하여 처리하기
			// SQL문을 작성할 때 SQL문에 데이터가 들어갈 자리를 물음표(?)로 표시해서 작성한다.
			String sql = "INSERT INTO BANKINFO "
					+ "(BANK_NO, BANK_NAME, BANK_USER_NAME, BANK_DATE) "
					+ "VALUES (?, ?, ?, SYSDATE)";
			
			// PreparedStatement 객체 생성
			//    => 이 때 사용할 SQL문을 인수값으로 넣어준다
			pstmt = conn.prepareStatement(sql);
			
			// SQL문의 물음표(?)자리에 들어갈 데이터를 셋팅한다
			// 형식) pstmt.set자료형이름(물음표번호, 셋팅할데이터)
			pstmt.setString(1, bankNo);
			pstmt.setString(2, bankName);
			pstmt.setString(3, bankUserName);
			
			// 데이터의 셋팅이 완료되면 SQL문을 실행한다
			// SQL문이 'SELECT'문일 경우에는 executeQuery()메서드를
			// 'SELECT'문이 아닐 경우에는 executeUpdate() 메서드를 사용한다
			
			int cnt = pstmt.executeUpdate();
			
			System.out.println("반환값 : " + cnt);
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {			
			if(pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			if(stmt != null) try { stmt.close(); } catch (SQLException e) { }
			if(conn != null) try { conn.close(); } catch (SQLException e) { }
		}
		
	}
}
