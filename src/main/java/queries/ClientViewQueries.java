package queries;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ClientViewQueries {
    Connection connection = null;

    /**
     * Constructeur de la classe ClientViewQueries
     * @param password  Le mot de passe pour se connecter à la base de données
     */
    public ClientViewQueries(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Méthode qui permet de récupérer les clients
     * @param model modèle de la table
     */
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

    /**
     * Méthode qui permet d'ajouter un client
     * @param nom       Le nom du client
     * @param prenom    Le prénom du client
     * @param adresse   L'adresse du client
     */
    public void ajoutClient(String nom, String prenom, String adresse){
        try {
            String requete = "INSERT INTO client (nom, prenom, adresse) VALUES ('"+nom+"', '"+prenom+"', '"+adresse+"')";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Ajouter Client");
        }
    }

    /**
     * Méthode qui permet de supprimer un client
     * @param id    L'id du client
     */
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

    /**
     * Méthode qui permet de modifier un client par rapport à la colonne selectionnée
     * @param nomColonne    Le nom de la colonne à modifier
     * @param valeur        La valeur à modifier
     * @param clePrim       La clé primaire du client
     */
    public void modifClient(String nomColonne, String valeur, String clePrim){
        try {
            String requete = "UPDATE client SET "+nomColonne+" = '"+valeur+"' WHERE client_id = '"+clePrim+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Modifier Client");
        }
    }

    /**
     * Méthode qui permet de savoir si un client est lié à des commandes et de récupérer les id des commandes
     * @param id    L'id du client
     * @return String[] Un tableau contenant les id des commandes
     */
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

    /**
     * Méthode qui permet de récupérer les commandes d'un client
     * @param id    L'id du client
     * @param model modèle de la table
     */
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
