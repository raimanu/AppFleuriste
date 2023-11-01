package classJava;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
    Connection connection = null;
    public ConnectDB(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
            if (connection != null) {
                System.out.println("Connexion à la base de données réussie !");
            } else {
                System.out.println("Erreur de connexion à la base de données !");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
