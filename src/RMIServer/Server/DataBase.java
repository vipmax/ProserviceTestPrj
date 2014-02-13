package RMIServer.Server;

import java.sql.*;

/**
 * Created by vipmax on 08.02.14.
 */


public class DataBase {

	String dbUrl = "jdbc:postgresql://localhost:5432/ProserviceTestDB";
	String user = "root";
	String password = "root";
	public static final Long notFount = new Long((long) -1);
	private Connection connection;
	private Statement statement;

	public DataBase() {
		try {

			connection = DriverManager.getConnection(dbUrl, user, password);
			statement = connection.createStatement();


		} catch (SQLException e) {
			e.printStackTrace();
		}
		CacheManager cacheManager = CacheManager.getInstance();
		try {
			cacheManager.loadCache(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Long getAmount(Integer id) {


		String sqlRequest = "select * from account_balance where id = " + id;

		ResultSet data = getInfoFromDB(sqlRequest);

		try {

			if (data.next()) {
				//	System.out.println("Find in Server.DataBase:  " + data.getString("id") + ", " + data.getString("value"));
				return (Long) data.getLong("value");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notFount;

	}

	public void addAmount(Integer id, Long amount) {
		String sqlRequest = "insert into account_balance values (" + id + ", " + amount + ");";

		try {
			updateDB(sqlRequest);
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}


	public ResultSet getInfoFromDB(String request) {

		ResultSet r = null;

		// Загружаем драйвер (регистрирует себя)
		try {
			Class.forName("org.postgresql.Driver");
			r = statement.executeQuery(request);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;

	}

	private boolean updateDB(String request) throws SQLException {
		statement.execute(request);
		return true;
	}


}
