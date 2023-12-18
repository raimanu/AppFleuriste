package queries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLOutput;
import java.util.Objects;
public class CommandeViewQueries {
    Connection connection = null;

    public CommandeViewQueries(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void GetCommandeTable(DefaultTableModel model){
        try {
            String requete = "SELECT * FROM commande ORDER BY commande_id ASC;";
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
            System.out.println(e);
        }
    }

    public void ajoutCommande(String date, int client_id){
        try {
            String requete = "INSERT INTO COMMANDE (date_commande, montant_total, client_id) VALUES ('"+date+"', 0, "+client_id+")";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la commande");
            System.out.println(e +" Ajouter Commande");
        }
    }

    public void supprCommande(String id){
        try {
            String select_compose = "SELECT * FROM compose WHERE commande_id = " + id;
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(select_compose);
            while (resultSet.next()) {
                int fleur_id = resultSet.getInt("fleur_id");
                int quantite = resultSet.getInt("quantite");
                String requete_fleur = "UPDATE fleur SET quantite = quantite + " + quantite + " WHERE fleur_id = " + fleur_id;
                PreparedStatement preparedStatementFleur = connection.prepareStatement(requete_fleur);
                preparedStatementFleur.executeUpdate();
            }
            String requete_compose = "DELETE FROM compose WHERE commande_id = '"+id+"'";
            PreparedStatement preparedStatementCompose = connection.prepareStatement(requete_compose);
            preparedStatementCompose.executeUpdate();
            String requete = "DELETE FROM commande WHERE commande_id = '"+id+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la commande");
            System.out.println(e +" Supprimer Commande");
        }
    }

    public void modifCommande(String nomColonne, String valeur, String clePrim){
        try {
            if (nomColonne.equals("Prix Total")) nomColonne = "montant_total";
            if (nomColonne.equals("Date")) nomColonne = "date_commande";
            if (nomColonne.equals("Client Id")) nomColonne = "client_id";
            if(nomColonne.equals("Id")) nomColonne = "commande_id";
            String requete = "UPDATE commande SET "+nomColonne+" = '"+valeur+"' WHERE commande_id = '"+clePrim+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification de la commande");
            System.out.println(e +" Modifier Commande");
        }
    }

    public void ajoutFleurCommande(int commande_id, int fleur_id, int quantite){
        try {
            if(checkComposeExist(commande_id, fleur_id)) {
                String requete_fleur = "UPDATE fleur SET quantite = quantite + (SELECT quantite FROM compose WHERE commande_id = " + commande_id + " AND fleur_id = " + fleur_id + ") WHERE fleur_id = " + fleur_id;
                PreparedStatement preparedStatementFleur = connection.prepareStatement(requete_fleur);
                preparedStatementFleur.executeUpdate();
                String requete_commande = "UPDATE commande SET montant_total = montant_total - (SELECT prix_unitaire FROM fleur WHERE fleur_id = " + fleur_id + ") * (SELECT quantite FROM compose WHERE commande_id = " + commande_id + " AND fleur_id = " + fleur_id + ") WHERE commande_id = " + commande_id;
                PreparedStatement preparedStatementCommande = connection.prepareStatement(requete_commande);
                preparedStatementCommande.executeUpdate();
                String requete_compose = "DELETE FROM compose WHERE commande_id = " + commande_id + " AND fleur_id = " + fleur_id;
                PreparedStatement preparedStatementCompose = connection.prepareStatement(requete_compose);
                preparedStatementCompose.executeUpdate();
            }
            if(checkFleurQuantite(quantite, fleur_id)) {
                String requete_compose = "INSERT INTO COMPOSE (commande_id, fleur_id, quantite) VALUES (" + commande_id + ", " + fleur_id + ", " + quantite + ")";
                PreparedStatement preparedStatementCompose = connection.prepareStatement(requete_compose);
                preparedStatementCompose.executeUpdate();
                String requete_fleur = "UPDATE fleur SET quantite = quantite - " + quantite + " WHERE fleur_id = " + fleur_id;
                PreparedStatement preparedStatementFleur = connection.prepareStatement(requete_fleur);
                preparedStatementFleur.executeUpdate();
                String requete_commande = "UPDATE commande SET montant_total = montant_total + (SELECT prix_unitaire FROM fleur WHERE fleur_id = " + fleur_id + ") * " + quantite + " WHERE commande_id = " + commande_id;
                PreparedStatement preparedStatementCommande = connection.prepareStatement(requete_commande);
                preparedStatementCommande.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la fleur à la commande");
            System.out.println(e +" Ajouter Fleur Commande");
        }
    }

    public void confirmCommande(String commande_id){
        try {
            String requete_compose = "DELETE FROM compose WHERE commande_id = "+commande_id;
            PreparedStatement preparedStatementCompose = connection.prepareStatement(requete_compose);
            preparedStatementCompose.executeUpdate();
            String requete_fleur = "DELETE FROM fleur WHERE quantite = 0 AND fleur_id NOT IN (SELECT fleur_id FROM compose WHERE commande_id != " + commande_id + ")";
            PreparedStatement preparedStatementFleur = connection.prepareStatement(requete_fleur);
            preparedStatementFleur.executeUpdate();
            String requete = "DELETE FROM Commande WHERE commande_id = " + commande_id;
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la confirmation de la commande");
            System.out.println(e +" Confirmer Commande");
        }
    }

    public boolean checkFleurQuantite(int quantite, int fleur_id){
        try {
            String requete_fleur_quantite = "SELECT quantite FROM fleur WHERE fleur_id = " + fleur_id;
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete_fleur_quantite);
            int quantite_fleur = 0;
            while (resultSet.next()) {
                quantite_fleur = resultSet.getInt("quantite");
            }
            if (quantite_fleur < quantite) {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la fleur à la commande : quantité insuffisante");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la fleur à la commande");
            System.out.println(e +" Ajouter Fleur Commande");
        }
        return true;
    }

    public boolean checkComposeExist(int commande_id, int fleur_id){
        try {
            String requete_compose = "SELECT * FROM compose WHERE commande_id = " + commande_id + " AND fleur_id = " + fleur_id;
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete_compose);
            while (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la vérification de l'existence de la fleur dans la commande");
            System.out.println(e +" Vérifier Fleur Commande");
        }
        return false;
    }
}
