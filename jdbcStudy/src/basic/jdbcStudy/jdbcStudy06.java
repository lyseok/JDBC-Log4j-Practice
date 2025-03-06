package basic.jdbcStudy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import basic.util.DBUtil;

public class jdbcStudy06 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			conn = DBUtil.getConnection();
			
			System.out.println("계좌 정보 검색하기");
			System.out.println();
			
			System.out.print("검색할 계좌번호 입력 >> ");
			String bank_no = sc.nextLine();
			String sql = """
					SELECT *
					  FROM BANKINFO
					 WHERE BANK_NO = ?""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bank_no);
			
			rs = pstmt.executeQuery();
			
			
			System.out.println();
			System.out.println("  ==  검 색 결 과 ==");
			System.out.println("      계좌번호\t\t은행명\t예금주명\t개설날짜");
			while(rs.next()) {
				String bNo = rs.getString("BANK_NO");
				String bName = rs.getString("bank_name");
				String bUserName = rs.getString("bank_user_name");
				String bDate = rs.getString("bank_date");
				System.out.println(bNo + "\t" + bName  + "\t" +bUserName + "\t" + bDate);
			}
			sc.close();
			
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			if(rs!=null) try { rs.close(); } catch(SQLException e) {} 
			if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {} 
			if(stmt!=null) try { stmt.close(); } catch(SQLException e) {} 
			if(conn!=null) try { conn.close(); } catch(SQLException e) {} 
		}
	}
}
