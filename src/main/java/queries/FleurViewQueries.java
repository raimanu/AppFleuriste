package queries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Objects;


public class FleurViewQueries {
    Connection connection = null;

    /**
     * Constructeur de la classe FleurViewQueries
     * @param password mot de passe de la base de données
     */
    public FleurViewQueries(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Méthode qui permet de récupérer les fleurs
     * @param model modèle de la table
     */
    public void GetFleurTable(DefaultTableModel model){
        try {
            String requete = "SELECT * FROM fleur ORDER BY fleur_id ASC;";
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("fleur_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("age"),
                        resultSet.getString("duree_vie"),
                        resultSet.getString("prix_unitaire"),
                        resultSet.getString("quantite"),
                        resultSet.getString("fournisseur_id")
                });
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Méthode qui permet d'ajouter une fleur
     * @param nom           Le nom de la fleur
     * @param age           L'âge de la fleur
     * @param dureeVie      La durée de vie de la fleur
     * @param prix          Le prix de la fleur
     * @param quantite      La quantité de la fleur
     * @param fournisseur_id    L'id du fournisseur de la fleur
     */
    public void ajoutFleur(String nom, float age, float dureeVie, float prix, int quantite, int fournisseur_id){
        try {
            String requete = "INSERT INTO FLEUR (nom, age, duree_vie, prix_unitaire, quantite, fournisseur_id) VALUES ('"+nom+"', "+age+", "+dureeVie+", "+prix+", "+quantite+", "+fournisseur_id+")";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e +" Ajouter FLeur");
        }
    }

    /**
     * Méthode qui permet de supprimer une fleur
     * @param id    L'id de la fleur
     */
    public void supprFleur(String id){
        try {
            String requete = "DELETE FROM fleur WHERE fleur_id = '"+id+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ligne selectionné supprimé !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression, la fleur compose une commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }

    /**
     * Méthode qui permet de modifier une fleur par rapport à la colonne sélectionnée
     * @param nomColonne    Le nom de la colonne à modifier
     * @param valeur        La valeur à modifier
     * @param clePrim       La clé primaire de la fleur
     */
    public void modifFleur(String nomColonne, String valeur, String clePrim){
        try {
            String requete;
            if (isInt(valeur)){
                Double.parseDouble(valeur);
                if(Objects.equals(nomColonne, "Quantité")) nomColonne = "Quantite";
                if(Objects.equals(nomColonne, "Durée de vie")) nomColonne = "Duree_vie";
                if(Objects.equals(nomColonne, "Prix Unitaire")) nomColonne = "Prix_unitaire";
                if(Objects.equals(nomColonne, "Fournisseur Id")) nomColonne = "Fournisseur_id";
                requete = "UPDATE FLEUR SET " + nomColonne +" = " + valeur + " WHERE " + "fleur_id = '" + clePrim + "'";
            } else {
                requete = "UPDATE FLEUR SET " + nomColonne +" = '" + valeur + "' WHERE " + "fleur_id = '" + clePrim + "'";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Valeur modifié !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification");
            System.out.println(e + " Modif Fleur");
        }
    }

    /**
     * Méthode qui permet de savoir si une valeur est un entier
     * @param valeur    La valeur à tester
     * @return boolean
     */
    public boolean isInt(String valeur){
        try {
            Integer.parseInt(valeur);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
