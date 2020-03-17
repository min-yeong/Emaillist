package com.bit.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bit.emaillist.vo.EmailVO;

public class EmaillistDAOImpl implements EmaillistDAO {
	//사용자 정보 데이터베이스
	private String dbuser = null;
	private String dbpass = null;
	
	// 생성자
	public EmaillistDAOImpl(String dbuser, String dbpass) {
		this.dbuser = dbuser;
		this.dbpass = dbpass;
	}
	
	// connection 받아오는 메서드
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			// 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// DB 접속 URL
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			// DB Connect
			conn = DriverManager.getConnection(dburl, dbuser, dbpass);
		} catch(ClassNotFoundException e) {
			// 드라이버가 없을 경우
			e.printStackTrace();
		} 
		return conn;
	}

	@Override
	public List<EmailVO> getList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<EmailVO> list = new ArrayList<>();
		
		try {
			conn = getConnection();
			// Query 작성
			String sql = "SELECT no, last_name, first_name, email, created_at FROM emaillist ORDER BY created_at DESC"; //가장마지막에등록된것부터 출력
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			// ResultSet 루프
			while(rs.next()) {
				//ResultSet으로부터 컬럼 데이터 출력
				Long no = rs.getLong(1);
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
				String email = rs.getString(4);
				Date createdAt = rs.getDate(5);
				
				// EmailVO 생성
				EmailVO vo = new EmailVO(no, lastName, firstName, email, createdAt);
				list.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean insert(EmailVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO emaillist (no, last_name, first_name, email) VALUES (seq_emaillist_pk.nextval, ?, ?,?)";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, vo.getLastName());		
			pstmt.setString(2, vo.getFirstName());			
			pstmt.setString(3, vo.getEmail());			
			
			insertedCount = pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(Exception e) {}			
		}
		return insertedCount == 1;
	}

	@Override
	public boolean delete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int deletedCount = 0;
		
		try {
			conn = getConnection();
			String sql = "DELETE FROM emaillist WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			
			deletedCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(Exception e) {}
		}
		return deletedCount == 1;
	}
}
