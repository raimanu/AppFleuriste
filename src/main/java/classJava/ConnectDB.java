package classJava;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectDB {
    Connection connection = null;
    public ConnectDB(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
        } catch (Exception e) {
            System.out.println("Erreur à la connexion : " + e);
        }
    }
    public Connection getConnection() {
        return connection;
    }
}
