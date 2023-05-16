package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String user = "RM96466";
        String password = "220693";
        connection = DriverManager.getConnection(url, user, password);

        return connection;
    }
}