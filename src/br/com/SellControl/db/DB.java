package br.com.SellControl.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

	private static Connection conn = null;

	public static Connection openConnection() {
		if (conn == null) {
			try {
				Properties ps = readProperties();
				String url = ps.getProperty("dburl");
				conn = DriverManager.getConnection(url, ps);

			} catch (SQLException e) {
				throw new DbException("Error opening the connection");
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException("Error closing the connection");
			}
		}
	}

	// Read the db.properties
	public static Properties readProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties ps = new Properties();
			ps.load(fs);
			return ps;

		} catch (IOException e) {
			throw new DbException("Error loading db.properties!");
		}
	}
}
