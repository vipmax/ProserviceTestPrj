package RMIServer;

import java.sql.*;

/**
 * Created by vipmax on 08.02.14.
 */


public class DataBase {
	Connection c = null;
	Statement s = null;
	ResultSet r = null;
	String dbUrl = "jdbc:postgresql://localhost:5432/ProserviceTestDB";
	String user = "root";
	String password = "root";

	public Long getAmount(Integer id) {

		// Загружаем драйвер (регистрирует себя)
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(dbUrl, user, password);
			s = c.createStatement();
			r = s.executeQuery("select * from account_balance; ");
			//читаем инфу с БД по запросу
			while (r.next()) {

				if (id == r.getLong("id")) {
					System.out.println("Find in SocketServer.DataBase:  " + r.getString("id") + ", " + r.getString("value"));
					return (Long) r.getLong("value");
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close(); // Закрываем ResultSet
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return Long.valueOf(-1);

	}

}
