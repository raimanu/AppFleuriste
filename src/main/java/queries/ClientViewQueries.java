package queries;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ClientViewQueries {
    Connection connection = null;
    public ClientViewQueries(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void GetClientTable(DefaultTableModel model){
        try {
            String requete = "SELECT * FROM client ORDER BY client_id ASC;";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("client_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse")
                });
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ajoutClient(String nom, String prenom, String adresse){
        try {
            String requete = "INSERT INTO client (nom, prenom, adresse) VALUES ('"+nom+"', '"+prenom+"', '"+adresse+"')";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Ajouter Client");
        }
    }

    //Fonction pour supprimer un client et les commandes qui lui sont associées
    public void supprClient(String id){
        try {
            String requete = "DELETE FROM commande WHERE client_id = '"+id+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
            requete = "DELETE FROM client WHERE client_id = '"+id+"'";
            preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Supprimer Client");
        }
    }

    public void modifClient(String nomColonne, String valeur, String clePrim){
        try {
            String requete = "UPDATE client SET "+nomColonne+" = '"+valeur+"' WHERE client_id = '"+clePrim+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Modifier Client");
        }
    }

    //Fonction pour savoir si un client est lier a des commandes et savoir qu'elles sont les id des commandes
    public String[] GetClientCommande(String id){
        String[] tab = new String[1];
        try {
            String requete = "SELECT commande_id FROM commande WHERE client_id = '"+id+"'";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                tab[0] = resultSet.getString("commande_id");
            }
        } catch (Exception e) {
            System.out.println(e +" Client Commande");
        }
        return tab;
    }

    public void TableClientCommande(String id, DefaultTableModel model){
        try {
            String requete = "SELECT * FROM commande WHERE client_id = '"+id+"'";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("commande_id"),
                        resultSet.getString("date_commande"),
                        resultSet.getString("montant_total"),
                        resultSet.getString("client_id")
                });
            }
        } catch (Exception e) {
            System.out.println(e +" Table Client Commande");
        }
    }
}
