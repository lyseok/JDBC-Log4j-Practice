package basic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

// JDBC 드라이버를 로딩하고 Connection 객체를 생성하는
// 메서드로 구성된 class 만들기 (ResourceBundle 객체 이용하기)

public class DBUtil {
	private static final Logger logger = Logger.getLogger(DBUtil.class);
	private static ResourceBundle bundle = null;
	
	static {
		bundle = ResourceBundle.getBundle("config.db");
		logger.info("ResourceBundle객체 생성 완료 - db.properties파일 읽기");
		
		try {
			Class.forName(bundle.getString("driver"));
			logger.trace("DB 드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버 로딩 실패");
			logger.error("드라이버 로딩 실패", e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("파일 입출력 오류 : 드라이버 로딩 실패");
			e.printStackTrace();
		} 
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(
					bundle.getString("url"),
					bundle.getString("user"), 
					bundle.getString("pass"));
			logger.debug("DB 접속 완료 - Connection 객체 생성");
		} catch (SQLException e) {
			logger.error("DB 접속 실패", e);
			e.printStackTrace();
		}
		
		return conn;
	}
}
