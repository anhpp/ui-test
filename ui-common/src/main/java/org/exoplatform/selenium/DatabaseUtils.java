package org.exoplatform.selenium;

import static org.exoplatform.selenium.TestLogger.info;

import java.sql.*;

public class DatabaseUtils {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/database";

	static final String USER = "username";
	static final String PASS = "password";

	private static Connection conn;
	private static Statement stmt;

	public DatabaseUtils() throws ClassNotFoundException, SQLException{
		info("Connecting to database...");
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
	}

	public static Connection connectDatabase(String jdbcDriver, String dbUrl, String user, String pass) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{		   
		info("Connecting to database...");
		Class.forName(jdbcDriver).newInstance();
		conn = DriverManager.getConnection(dbUrl,user,pass);
		return conn;
	}

	public static void closeDatabase(Connection conn) throws SQLException{
		conn.close();
		stmt.close();
	}

	public static String[][] getData(String sql) throws SQLException{
		String[][] xData = new String[100][1000];
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int i = 0;
		while(rs.next()){
			String title  = rs.getString("title");
			String content = rs.getString("content");
			String attachfile = rs.getString("attachfile");
			xData[i][0]=title;
			xData[i][1]=content;
			xData[i][2]=attachfile;		
			i++;
		}
		rs.close();
		return xData;
	}
}