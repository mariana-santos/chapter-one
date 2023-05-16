package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() {
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String user = "RM96466";
        String password = "220693";
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("ERRO AO ESTABELECER A CONEXÃO COM O BANCO DE DADOS. ERRO: " + e.getMessage());
        }
        
        return connection;
    }

}