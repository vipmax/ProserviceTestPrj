package RMIServer;

import java.sql.*;

/**
 * Created by vipmax on 08.02.14.
 */


public class DataBase {


	public Long getAmount(Integer id) {

		String sqlRequest = "select * from account_balance where id = " + id;
		System.out.println(sqlRequest);
		ResultSet data = sqlRequestToDataBaseWithString(sqlRequest);

		try {

			if (data.next()) {
				System.out.println("Find in SocketServer.DataBase:  " + data.getString("id") + ", " + data.getString("value"));
				return (Long) data.getLong("value");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return Long.valueOf(-1);

	}

	public void addAmount(Integer id, Long amount) {

	}


	private ResultSet sqlRequestToDataBaseWithString(String request) {
		Connection c = null;
		Statement s = null;
		ResultSet r = null;
		String dbUrl = "jdbc:postgresql://localhost:5432/ProserviceTestDB";
		String user = "root";
		String password = "root";
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

}
