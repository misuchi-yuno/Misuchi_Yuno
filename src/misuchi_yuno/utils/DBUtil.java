package misuchi_yuno.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import misuchi_yuno.exception.SQLRuntimeException;

public class DBUtil {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/misuchi_yuno?useUnicode=true&characterEncoding=utf8";
	private static final String USER = "root";
	private static final String PASSWORD = "19941124";

	static {

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * コネクションを取得します。
	 *
	 * @return
	 */
	public static Connection getConnection() {

		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

			connection.setAutoCommit(false);

			return connection;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	/**
	 * コミットします。
	 *
	 * @param connection
	 */
	public static void commit(Connection connection) {

		try {
			connection.commit();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	/**
	 * ロールバックします。
	 *
	 * @param connection
	 */
	public static void rollback(Connection connection) {

		if (connection == null) {
			return;
		}

		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}
}