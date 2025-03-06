package basic.jdbcStudy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class jdbcStudy03 {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XEPDB1",
					"LYS00",
					"java"
					);
			
			System.out.print("min max >> ");
			int min = sc.nextInt();
			int max = sc.nextInt();
			
			sc.close();
			
			// Statement 객체를 이용하여 처리하기
			/*
			String sql = "SELECT * FROM LPROD "
					+ "WHERE LPROD_ID >= " + min +" AND LPROD_ID <= " + max;
			
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			*/
			
			// PreparedStatement 객체를 이용하여 처리하기
			
			String sql = "select * from lprod where lprod_id >= ? and lprod_id <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			
			rs = pstmt.executeQuery();
			
			System.out.println("== 쿼리문 처리 결과 ==");
			System.out.println("LPROD_ID\tLPROD_GU\tLPROD_NAME");
			
			while(rs.next()) {
				System.out.printf("%d\t\t  %s\t\t  %s\n",
						rs.getInt(1), rs.getString(2), rs.getString(3));		
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { }
			if(stmt != null) try { stmt.close(); } catch (SQLException e) { }
			if(conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}
}
