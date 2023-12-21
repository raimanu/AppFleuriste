package queries;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class AlerteFleurFanerQueries {
Connection connection = null;


    public AlerteFleurFanerQueries(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public boolean GetAlerteFleurFaner(DefaultTableModel model){
        try {
            String requete = "SELECT * FROM FLEUR WHERE duree_vie - age <= 7;";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete);
            if(!resultSet.next()) {
                return false;
            }
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("fleur_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("age"),
                        resultSet.getString("duree_vie"),
                        resultSet.getString("vivante"),
                        resultSet.getString("prix_unitaire"),
                        resultSet.getString("quantite"),
                        resultSet.getString("fournisseur_id")
                });
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
}
