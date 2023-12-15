package queries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Objects;

public class FournisseurViewQueries {
Connection connection = null;
    public FournisseurViewQueries(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void GetFournisseurTable(DefaultTableModel model){
        try {
            String requete = "SELECT * FROM fournisseur ORDER BY fournisseur_id ASC;";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("fournisseur_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("adresse")
                });
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ajoutFournisseur(String nom, String adresse){
        try {
            String requete = "INSERT INTO fournisseur (nom, adresse) VALUES ('"+nom+"', '"+adresse+"')";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Ajouter Fournisseur");
        }
    }

    //Fonction pour supprimer un fournisseur et les fleurs qui lui sont associées
    public void supprFournisseur(String id){
        try {
            String requete = "DELETE FROM fleur WHERE fournisseur_id = '"+id+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
            requete = "DELETE FROM fournisseur WHERE fournisseur_id = '"+id+"'";
            preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Supprimer Fournisseur");
        }
    }

    public void modifFournisseur(String nomColonne, String valeur, String clePrim){
        try {
            String requete = "UPDATE fournisseur SET "+nomColonne+" = '"+valeur+"' WHERE fournisseur_id = '"+clePrim+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Modifier Fournisseur");
        }

    }
}
