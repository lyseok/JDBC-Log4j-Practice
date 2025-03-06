package basic.jdbcStudy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class jdbcStudy02 {
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XEPDB1",
					"LYS00",
					"java"
					);
			
			System.out.print("입력 >> ");
			int input = sc.nextInt();
			
			String sql = "SELECT * FROM LPROD WHERE LPROD_ID > " + input
					+ " ORDER BY 1";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			sc.close();
			
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
