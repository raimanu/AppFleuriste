package queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 * Classe qui permet de faire des requêtes pour la vue Commande.
 */
public class CommandeViewQueries {
  Connection connection = null;

  /**
   * Constructeur de la classe CommandeViewQueries.
   *
   * @param password mot de passe de la base de données
   */
  public CommandeViewQueries(String password) {
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Méthode qui permet de récupérer les commandes.
   *
   * @param model modèle de la table
   */
  public void getCommandeTable(DefaultTableModel model) {
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

  /**
   * Méthode qui permet d'ajouter une commande.
   *
   * @param date      La date de la commande
   * @param clientId L'id du client
   */
  public void ajoutCommande(String date, int clientId) {
    try {
      String requete = "INSERT INTO COMMANDE (date_commande, montant_total, client_id) VALUES ('" + date + "', 0, " + clientId + ")";
      PreparedStatement preparedStatement = connection.prepareStatement(requete);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la commande");
      System.out.println(e + " Ajouter Commande");
    }
  }

  /**
   * Méthode qui permet de supprimer une commande.
   *
   * @param id L'id de la commande
   */
  public void supprCommande(String id) {
    try {
      String selectCompose = "SELECT * FROM compose WHERE commande_id = " + id;
      java.sql.Statement statement = connection.createStatement();
      java.sql.ResultSet resultSet = statement.executeQuery(selectCompose);
      while (resultSet.next()) {
        int fleurId = resultSet.getInt("fleur_id");
        int quantite = resultSet.getInt("quantite");
        String requeteFleur = "UPDATE fleur SET quantite = quantite + " + quantite + " WHERE fleur_id = " + fleurId;
        PreparedStatement preparedStatementFleur = connection.prepareStatement(requeteFleur);
        preparedStatementFleur.executeUpdate();
      }
      String requeteCompose = "DELETE FROM compose WHERE commande_id = '" + id + "'";
      PreparedStatement preparedStatementCompose = connection.prepareStatement(requeteCompose);
      preparedStatementCompose.executeUpdate();
      String requete = "DELETE FROM commande WHERE commande_id = '" + id + "'";
      PreparedStatement preparedStatement = connection.prepareStatement(requete);
      preparedStatement.executeUpdate();

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la commande");
      System.out.println(e + " Supprimer Commande");
    }
  }

  /**
   * Méthode qui permet de modifier une commande par rapport à la colonne selectionnée.
   *
   * @param nomColonne Le nom de la colonne à modifier
   * @param valeur     La valeur à modifier
   * @param clePrim    La clé primaire de la commande
   */
  public void modifCommande(String nomColonne, String valeur, String clePrim) {
    try {
      if (nomColonne.equals("Prix Total")) nomColonne = "montant_total";
      if (nomColonne.equals("Date")) nomColonne = "date_commande";
      if (nomColonne.equals("Client Id")) nomColonne = "client_id";
      if (nomColonne.equals("Id")) nomColonne = "commande_id";
      String requete = "UPDATE commande SET " + nomColonne + " = '" + valeur + "' WHERE commande_id = '" + clePrim + "'";
      PreparedStatement preparedStatement = connection.prepareStatement(requete);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur lors de la modification de la commande");
      System.out.println(e + " Modifier Commande");
    }
  }

  /**
   * Méthode qui permet d'ajouter une fleur à une commande.
   *
   * @param commandeId L'id de la commande
   * @param fleurId    L'id de la fleur
   * @param quantite    La quantité de fleur
   */
  public void ajoutFleurCommande(int commandeId, int fleurId, int quantite) {
    try {
      if (checkComposeExist(commandeId, fleurId) && checkFleurQuantite(quantite, fleurId)) {
        String requeteCompose = "UPDATE compose SET quantite = quantite + " + quantite + " WHERE commande_id = " + commandeId + " AND fleur_id = " + fleurId;
        PreparedStatement preparedStatementCompose = connection.prepareStatement(requeteCompose);
        preparedStatementCompose.executeUpdate();
        String requeteFleur = "UPDATE fleur SET quantite = quantite - " + quantite + " WHERE fleur_id = " + fleurId;
        PreparedStatement preparedStatementFleur = connection.prepareStatement(requeteFleur);
        preparedStatementFleur.executeUpdate();
        String requeteCommande = "UPDATE commande SET montant_total = montant_total + (SELECT prix_unitaire FROM fleur WHERE fleur_id = " + fleurId + ") * " + quantite + " WHERE commande_id = " + commandeId;
        PreparedStatement preparedStatementCommande = connection.prepareStatement(requeteCommande);
        preparedStatementCommande.executeUpdate();
      } else if (checkFleurQuantite(quantite, fleurId)) {
        String requeteCompose = "INSERT INTO COMPOSE (commande_id, fleur_id, quantite) VALUES (" + commandeId + ", " + fleurId + ", " + quantite + ")";
        PreparedStatement preparedStatementCompose = connection.prepareStatement(requeteCompose);
        preparedStatementCompose.executeUpdate();
        String requeteFleur = "UPDATE fleur SET quantite = quantite - " + quantite + " WHERE fleur_id = " + fleurId;
        PreparedStatement preparedStatementFleur = connection.prepareStatement(requeteFleur);
        preparedStatementFleur.executeUpdate();
        String requeteCommande = "UPDATE commande SET montant_total = montant_total + (SELECT prix_unitaire FROM fleur WHERE fleur_id = " + fleurId + ") * " + quantite + " WHERE commande_id = " + commandeId;
        PreparedStatement preparedStatementCommande = connection.prepareStatement(requeteCommande);
        preparedStatementCommande.executeUpdate();
      } else {
        JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la fleur à la commande : quantité insuffisante");
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la fleur à la commande");
      System.out.println(e + " Ajouter Fleur Commande");
    }
  }

  /**
   * Méthode qui permet de supprimer une fleur d'une commande.
   *
   * @param commandeId L'id de la commande
   * @param fleurId    L'id de la fleur
   */
  public void supprFleurCommande(int commandeId, int fleurId) {
    try {
      String selectCompose = "SELECT * FROM compose WHERE commande_id = " + commandeId + " AND fleur_id = " + fleurId;
      java.sql.Statement statement = connection.createStatement();
      java.sql.ResultSet resultSet = statement.executeQuery(selectCompose);
      String requeteCompose = "DELETE FROM compose WHERE commande_id = " + commandeId + " AND fleur_id = " + fleurId;
      PreparedStatement preparedStatementCompose = connection.prepareStatement(requeteCompose);
      preparedStatementCompose.executeUpdate();
      while (resultSet.next()) {
        int quantite = resultSet.getInt("quantite");
        String requeteFleur = "UPDATE fleur SET quantite = quantite + " + quantite + " WHERE fleur_id = " + fleurId;
        PreparedStatement preparedStatementFleur = connection.prepareStatement(requeteFleur);
        preparedStatementFleur.executeUpdate();
        String requeteCommande = "UPDATE commande SET montant_total = montant_total - (SELECT prix_unitaire FROM fleur WHERE fleur_id = " + fleurId + ") * " + quantite + " WHERE commande_id = " + commandeId;
        PreparedStatement preparedStatementCommande = connection.prepareStatement(requeteCommande);
        preparedStatementCommande.executeUpdate();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la fleur de la commande");
      System.out.println(e + " Supprimer Fleur Commande");
    }
  }

  /**
   * Méthode qui permet de confirmer une commande (supprimer la commande et les fleurs qui ne sont plus dans aucune commande avec 0 quantité).
   *
   * @param commandeId L'id de la commande
   */
  public void confirmCommande(String commandeId) {
    try {
      String requeteCompose = "DELETE FROM compose WHERE commande_id = " + commandeId;
      PreparedStatement preparedStatementCompose = connection.prepareStatement(requeteCompose);
      preparedStatementCompose.executeUpdate();
      String requeteFleur = "DELETE FROM fleur WHERE quantite = 0 AND fleur_id NOT IN (SELECT fleur_id FROM compose WHERE commande_id != " + commandeId + ")";
      PreparedStatement preparedStatementFleur = connection.prepareStatement(requeteFleur);
      preparedStatementFleur.executeUpdate();
      String requete = "DELETE FROM Commande WHERE commande_id = " + commandeId;
      PreparedStatement preparedStatement = connection.prepareStatement(requete);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur lors de la confirmation de la commande");
      System.out.println(e + " Confirmer Commande");
    }
  }

  /**
   * Méthode qui permet savoir s'il y a assez de fleur pour la commande.
   *
   * @param quantite La quantité de fleur
   * @param fleurId L'id de la fleur
   */
  public boolean checkFleurQuantite(int quantite, int fleurId) {
    try {
      String requeteFleurQuantite = "SELECT quantite FROM fleur WHERE fleur_id = " + fleurId;
      java.sql.Statement statement = connection.createStatement();
      java.sql.ResultSet resultSet = statement.executeQuery(requeteFleurQuantite);
      int quantiteFleur = 0;
      while (resultSet.next()) {
        quantiteFleur = resultSet.getInt("quantite");
      }
      if (quantiteFleur < quantite) {
        JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la fleur à la commande : quantité insuffisante");
        return false;
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la fleur à la commande");
      System.out.println(e + " Ajouter Fleur Commande");
    }
    return true;
  }

  /**
   * Méthode qui permet de vérifier si une fleur est déjà dans une commande.
   *
   * @param commandeId L'id de la commande
   * @param fleurId    L'id de la fleur
   * @return boolean
   */
  public boolean checkComposeExist(int commandeId, int fleurId) {
    try {
      String requeteCompose = "SELECT * FROM compose WHERE commande_id = " + commandeId + " AND fleur_id = " + fleurId;
      java.sql.Statement statement = connection.createStatement();
      java.sql.ResultSet resultSet = statement.executeQuery(requeteCompose);
      while (resultSet.next()) {
        return true;
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur lors de la vérification de l'existence de la fleur dans la commande");
      System.out.println(e + " Vérifier Fleur Commande");
    }
    return false;
  }

  /**
   * Méthode qui permet de récupérer les fleurs d'une commande.
   *
   * @param commandeId L'id de la commande
   * @param model       Le modèle de la table
   */
  public void getCommandeFleur(String commandeId, DefaultTableModel model) {
    try {
      String requete = "SELECT * FROM FLEUR WHERE fleur_id IN (SELECT fleur_id FROM COMPOSE WHERE commande_id = " + commandeId + ");";
      java.sql.Statement statement = connection.createStatement();
      java.sql.ResultSet resultSet = statement.executeQuery(requete);
      requete = "SELECT * FROM COMPOSE WHERE commande_id = " + commandeId + ";";
      statement = connection.createStatement();
      java.sql.ResultSet resultSetQuantiteCompose = statement.executeQuery(requete);
      while (resultSet.next() && resultSetQuantiteCompose.next()) {
        model.addRow(new Object[]{
            resultSet.getString("fleur_id"),
            resultSet.getString("nom"),
            resultSet.getString("age"),
            resultSet.getString("duree_vie"),
            resultSet.getString("prix_unitaire"),
            resultSetQuantiteCompose.getInt("quantite"),
            resultSet.getString("fournisseur_id")
        });
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Méthode qui permet de récupérer les fleurs d'une commande.
   *
   * @param commandeId L'id de la commande
   * @param model       Le modèle de la table
   */
  public void resetCommandeFleur(int commandeId, DefaultTableModel model) {
    try {
      String requete = "SELECT * FROM FLEUR WHERE fleur_id IN (SELECT fleur_id FROM COMPOSE WHERE commande_id = " + commandeId + ");";
      java.sql.Statement statement = connection.createStatement();
      java.sql.ResultSet resultSet = statement.executeQuery(requete);
      requete = "SELECT * FROM COMPOSE WHERE commande_id = " + commandeId + ";";
      statement = connection.createStatement();
      java.sql.ResultSet resultSetQuantiteCompose = statement.executeQuery(requete);
      while (resultSet.next() && resultSetQuantiteCompose.next()) {
        model.addRow(new Object[]{
            resultSet.getString("fleur_id"),
            resultSet.getString("nom"),
            resultSet.getString("age"),
            resultSet.getString("duree_vie"),
            resultSet.getString("prix_unitaire"),
            resultSetQuantiteCompose.getInt("quantite"),
            resultSet.getString("fournisseur_id")
        });
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
