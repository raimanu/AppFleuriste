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

    public void RequeteSelect(){
        try {
            String requete = "SELECT * FROM fleur";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete);
            System.out.println("Nom\t\t\t\t" + "Prix\t " + "Age\t " + "Duree de vie\t " + "Vivante\t " + "Quantite ");
            while (resultSet.next()) {
                System.out.format("%s,\t\t\t %s,\t\t %s,\t\t\t %s,\t\t\t\t %s,\t\t\t %s\n",
                        resultSet.getString("nom"),
                        resultSet.getString("prix"),
                        resultSet.getString("age"),
                        resultSet.getString("dureevie"),
                        resultSet.getString("vivante"),
                        resultSet.getString("quantite"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
