package com.estsoft.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

// Service도 Servlet도 Repository도 아니지만 서버에 올려줘야 할 때~~
//@Component
public class MariaDBConnection implements DBConnection {
	public Connection getConnection() throws SQLException {
		return null;
	}
}
