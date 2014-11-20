package org.exoplatform.selenium.platform.objectdatabase.common;

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

	private static int getRowCount(ResultSet resultSet) {
		if (resultSet == null) {
			return 0;
		}
		try {
			resultSet.last();
			return resultSet.getRow();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			try {
				resultSet.beforeFirst();
			} catch (SQLException exp) {
				exp.printStackTrace();
			}
		}
		return 0;
	}

	public static String[][] getData(String sql) throws SQLException{
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int nColumn = rsmd.getColumnCount();
		int nRow = getRowCount(rs);
		String[][] xData = new String[nRow][nColumn];
		int i = 0;
		rs.beforeFirst();
		while(rs.next()){
			for(int j = 0; j<nColumn; j++){
				xData[i][j]= rs.getString(j+1);
			}
			i++;
		}
		rs.close();
		return xData;
	}
}