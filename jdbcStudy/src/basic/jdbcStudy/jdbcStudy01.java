package basic.jdbcStudy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcStudy01 {
	public static void main(String[] args) {
		// DB 작업에 필요한 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DB 연결 -> Connection객체 생성
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XEPDB1", // url
					"LYS00",// user
					"java"// password
					);
			
			// 3. 질의
			// 3.1 SQL문 작성
			String sql = "SELECT * FROM LPROD";
			
			// 3.2 Statement 객체 생성 -> 질의를 서버에 전송하고 결과를 얻어오는 객체
			stmt = conn.createStatement();
			
			//3.3 SQL문을 DB서버로 보내서 결과를 얻어온다.
			//    (실행할 SQL문이 'SELECT'문이기 때문에 
			//     결과가 result 객체에 저장되어 반환된다.)
			rs = stmt.executeQuery(sql);
			
			// 4. 결과 처리하기 -> 한 레코드씩 화면에 출력하기
			//    (ResultSet객체에 저장된 데이터를 차례로 꺼내오려면
			//     반복문과 next()메서드를 이용하여 처리한다.)
			System.out.println(" == 쿼리문 처리 결과 ==");
			
			// rs.next() -> Result객체의 데이터를 가리키는 포인터를 다음번째의 레코드로
			//              이동시키고 그 곳에 데이터가 있으면 true, 없으면 false를 반환한다
			
			while(rs.next()) {
				// 포인터가 가리키는 곳의 자료를 가져오는 방법
				// 형식 1) rs.get자료형이름("컬럼명" 또는 "Alias명")
				// 형식 2) rs.get자료형이름(컬럼번호); -> 컬럼번호는 1부터 시작
				System.out.println("LPROD_ID : " + rs.getInt("LPROD_ID"));
				System.out.println("LPROD_GU : " + rs.getString(2));
				System.out.println("LPROD_NAME : " + rs.getString("LPROD_NAME"));
				System.out.println("------------------------------------------");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// 5. 사용했던 자원 반납
			if(rs != null) try { rs.close(); } catch (SQLException  e) { }
			if(stmt != null) try { stmt.close(); } catch (SQLException  e) { }
			if(conn != null) try { conn.close(); } catch (SQLException  e) { }
		}
	}
}
