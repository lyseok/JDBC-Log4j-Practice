package basic.jdbcStudy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import basic.util.DBUtil;
/*
 * 	LPROD 테이블에 새로운 데이터 추가하기
	LPROD_GU와 LPROD_NAME은 직접 입력 받아서 처리하고, 
	LPROD_ID는 현재 LPROD_ID 값들 중에 제일 큰 값보다 1크게 한다
	입력받은 LPROD_GU가 이미 등록되어 있으면 다시 입력받아서 처리한다
 */
public class jdbcStudy05 {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public static void main(String[] args) {
		new jdbcStudy05().start();
	}
	
	public void start() {
		Scanner sc = new Scanner(System.in);
		
		try {			
			conn = DBUtil.getConnection();
			
			System.out.print("LPROD_GU >> ");
			String lprodGu = sc.next();
			System.out.print("LPROD_NAME >> ");
			String lprodName = sc.next();
			sc.close();
			
			int nextId = getLprodId() + 1;
			
			if(!getLprodGu(lprodGu)) {
				String str = "INSERT INTO LPROD (LPROD_ID,LPROD_GU,LPROD_NAME) "
						+ "VALUES (?,?,?)";
				pstmt = conn.prepareStatement(str);
				pstmt.setInt(1, nextId);
				pstmt.setString(2, lprodGu);
				pstmt.setString(3, lprodName);
				pstmt.executeLargeUpdate();
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
			if(pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			if(pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			if(conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}
	
	public int getLprodId() throws SQLException {
		String sql = "SELECT MAX(LPROD_ID) FROM LPROD";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		rs.next();
		int max = rs.getInt(1);

		return max;
	}
	
	public boolean getLprodGu(String lprodGu) throws SQLException {
		String sql = "SELECT COUNT(*) FROM LPROD WHERE LPROD_GU = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, lprodGu);
		rs = pstmt.executeQuery();
		rs.next();

		if(rs.getInt(1) >= 1) {
			return true;	
		} else {
			return false;
		}
	}
}
