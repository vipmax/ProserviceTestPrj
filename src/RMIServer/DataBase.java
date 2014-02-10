package RMIServer;

import java.sql.*;

/**
 * Created by vipmax on 08.02.14.
 */


public class DataBase {

	String dbUrl = "jdbc:postgresql://localhost:5432/ProserviceTestDB";
	String user = "root";
	String password = "root";

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


		return Long.valueOf(-1);

	}

	public void addAmount(Integer id, Long amount) {
		String sqlRequest = "insert into account_balance values (" + id + ", " + amount + ");";

		try {
			updateDB(sqlRequest);
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}


	private ResultSet getInfoFromDB(String request) {
		Connection c = null;
		Statement s = null;
		ResultSet r = null;

		// Загружаем драйвер (регистрирует себя)
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(dbUrl, user, password);
			s = c.createStatement();
			r = s.executeQuery(request);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return r;

	}

	private boolean updateDB(String request) throws SQLException {
		DriverManager.getConnection(dbUrl, user, password).createStatement().execute(request);
		return true;
	}


}
