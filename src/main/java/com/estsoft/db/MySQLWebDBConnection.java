package com.estsoft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

// Service도 Servlet도 Repository도 아니지만 서버에 올려줘야 할 때~~
//@Component
//그런데 Interface를 통해 여러개를 구현한 경우에는 걍 설정 파일에 bin만 해주는게 더 낫대
public class MySQLWebDBConnection implements DBConnection {

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. 드라이버 로드
			Class.forName("com.mysql.jdbc.Driver");
			// 2. Connection 얻기
			String url = "jdbc:mysql://localhost/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버를 찾을 수 없습니다 : " + ex);
		}
		return conn;
	}

}
