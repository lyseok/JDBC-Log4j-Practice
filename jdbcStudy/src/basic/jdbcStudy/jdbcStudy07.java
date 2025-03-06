package basic.jdbcStudy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import basic.util.DBUtil;

public class jdbcStudy07 {
	private Scanner sc = new Scanner(System.in); 
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null; 
	
	public static void main(String[] args) {
		new jdbcStudy07().start();
	}
	
	private void start() {
		conn = DBUtil.getConnection();
		
		while(true) {
			switch(display()) {
				case 0: 
					if(conn != null) try { conn.close(); } catch(SQLException e) { } 
					if(sc != null) sc.close(); 
					return;
				case 1: create(); break;
				case 2: remove(); break;
				case 3: update(); break;
				case 4: select(); break;
			}
		}
	}
	
	private int display() {
		System.out.println();
		System.out.println("1. 자료 추가");
		System.out.println("2. 자료 삭제");
		System.out.println("3. 자료 수정");
		System.out.println("4. 자료 출력");
		System.out.println("0. 작업 끝");
		System.out.print("작업을 선택하세요 >> ");
		
		return sc.nextInt();
	}
	
	private boolean idCheck(String memId) {
		try {
			String sql = """
				SELECT COUNT(*)
				  FROM MYMEMBER
				 WHERE MEM_ID = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			rs.next();
			
			if(rs.getInt(1) > 0) {
				return true;
			} else {
				return false;				
			}
			
		} catch (SQLException e) {
			System.out.println("ID값 중복 찾는 중 오류 발생");
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch(SQLException e) { }
			if(pstmt != null) try { pstmt.close(); } catch(SQLException e) { }
		}
		return false;
	}
	
	private void create() {
		System.out.println("추가할 자료의 데이터를 입력해주세요.");
		sc.nextLine();
		String memId;
		
		while(true) {
			System.out.print("아이디 >> ");
			memId = sc.nextLine();
			if(!idCheck(memId)) break;
			System.out.println("이미 있는 아이디입니다 다른 아이디를 입력하세요.");
		}
		
		System.out.print("비밀번호 >> ");
		String memPw = sc.nextLine();
		System.out.print("이름 >> ");
		String memName = sc.nextLine();
		System.out.print("전화번호 >> ");
		String memTel = sc.nextLine();
		System.out.print("주소 >> ");
		String memAddr = sc.nextLine();
		
		System.out.printf("%s %s %s %s %s\n",memId,memPw,memName,memTel,memAddr);
		try {
			String sql = """
				INSERT INTO MYMEMBER
				VALUES (?,?,?,?,?)
				""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			pstmt.setString(2, memPw);
			pstmt.setString(3, memName);
			pstmt.setString(4, memTel);
			pstmt.setString(5, memAddr);
			
			int cnt = pstmt.executeUpdate();
			
			if (cnt > 0) {
				System.out.println("데이터 추가 작업 완료");
			} else {
				System.out.println("데이터 추가 작업 실패");
			}
			
		} catch (SQLException e) {
			System.out.println("데이터 추가 작업 중 오류 발생");
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch(SQLException e) { }
			if(pstmt != null) try { pstmt.close(); } catch(SQLException e) { }
		}
		
	}

	private void remove() {
		sc.nextLine();
		System.out.println("삭제할 자료의 ID를 입력하시오");
		String memId;
		
		while(true) {
			System.out.print("아이디 >> ");
			memId = sc.nextLine();
			
			if(idCheck(memId)) break;
			System.out.println("존재하지 않는 아이디 입니다 다시 입력하세요");
		}
		
		try {
			String sql = """
					DELETE FROM MYMEMBER
					 WHERE MEM_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			int cnt = pstmt.executeUpdate();
			
			if (cnt > 0) {
				System.out.println("데이터 삭제 작업 완료");
			} else {
				System.out.println("데이터 삭제 작업 실패");
			}
			
		} catch (SQLException e) {
			System.out.println("데이터 삭제 작업 중 오류 발생");
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch(SQLException e) { }
			if(pstmt != null) try { pstmt.close(); } catch(SQLException e) { }
		}
	}
	
	private void update() {
		sc.nextLine();
		
		System.out.println("업데이트할 자료의 아이디를 입력하세요");
		String memId;
		while(true) {
			System.out.print("아이디 >> ");
			memId = sc.nextLine();
			if(idCheck(memId)) break;
			System.out.println("존재하지 않는 아이디 입니다 다시 입력하세요");
		}
		System.out.print("비밀번호 >> ");
		String memPw = sc.nextLine();
		System.out.print("이름 >> ");
		String memName = sc.nextLine();
		System.out.print("전화번호 >> ");
		String memTel = sc.nextLine();
		System.out.print("주소 >> ");
		String memAddr = sc.nextLine();
		
		try {
			String sql = """
					UPDATE MYMEMBER
					   SET MEM_PASS = ?,
					       MEM_NAME = ?,
					       MEM_TEL = ?,
					       MEM_ADDR = ?
					 WHERE MEM_ID = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memPw);
			pstmt.setString(2, memName);
			pstmt.setString(3, memTel);
			pstmt.setString(4, memAddr);
			pstmt.setString(5, memId);
			int cnt = pstmt.executeUpdate();
			
			if (cnt > 0) {
				System.out.println("데이터 수정 작업 완료");
			} else {
				System.out.println("데이터 수정 작업 실패");
			}
			
		} catch (SQLException e) {
			System.out.println("데이터 수정 작업 중 오류 발생");
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch(SQLException e) { }
			if(pstmt != null) try { pstmt.close(); } catch(SQLException e) { }
		}
	}

	private void select() {
		try {
			String sql = """
				SELECT *
				  FROM MYMEMBER
				""";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			System.out.println("아이디\t비밀번호\t이름\t전화번호\t\t주소");
			while(rs.next()) {
				String memId = rs.getString(1);
				String memPw = rs.getString(2);
				String memName = rs.getString(3);
				String memTel = rs.getString(4);
				String memAddr = rs.getString(5);
				
				System.out.printf("%s\t%s\t%s\t%s\t%s\n", 
						memId, memPw, memName, memTel, memAddr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch(SQLException e) { }
			if(pstmt != null) try { pstmt.close(); } catch(SQLException e) { }
		}
		
	}
}
