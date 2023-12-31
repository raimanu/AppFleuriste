package queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;


/**
 * Classe qui permet de gérer les requêtes de la vue FournisseurView.
 */
public class FournisseurViewQueries {
  Connection connection = null;

  /**
   * Constructeur de la classe FournisseurViewQueries.
   *
   * @param password mot de passe de la base de données
   */
  public FournisseurViewQueries(String password) {
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Fleuriste", "postgres", password);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Méthode qui permet de récupérer les fournisseurs et les ajoutés dans le modèle de la table.
   *
   * @param model modèle de la table
   */
  public void getFournisseurTable(DefaultTableModel model) {
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

  /**
   * Méthode qui permet d'ajouter un fournisseur.
   *
   * @param nom     Le nom du fournisseur
   * @param adresse L'adresse du fournisseur
   */
  public void ajoutFournisseur(String nom, String adresse) {
    try {
      String requete = "INSERT INTO fournisseur (nom, adresse) VALUES ('" + nom + "', '" + adresse + "')";
      PreparedStatement preparedStatement = connection.prepareStatement(requete);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      System.out.println(e + " Ajouter Fournisseur");
    }
  }

  /**
   * Méthode qui permet de supprimer un fournisseur et les fleurs qui lui sont associées.
   *
   * @param id L'id du fournisseur
   */
  public void supprFournisseur(String id) {
    try {
      String requete = "DELETE FROM fleur WHERE fournisseur_id = '" + id + "'";
      PreparedStatement preparedStatement = connection.prepareStatement(requete);
      preparedStatement.executeUpdate();
      requete = "DELETE FROM fournisseur WHERE fournisseur_id = '" + id + "'";
      preparedStatement = connection.prepareStatement(requete);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      System.out.println(e + " Supprimer Fournisseur");
    }
  }

  /**
   * Méthode qui permet de modifier un fournisseur.
   *
   * @param nomColonne Le nom de la colonne à modifier
   * @param valeur     La valeur à modifier
   * @param clePrim    La clé primaire du fournisseur
   */
  public void modifFournisseur(String nomColonne, String valeur, String clePrim) {
    try {
      String requete = "UPDATE fournisseur SET " + nomColonne + " = '" + valeur + "' WHERE fournisseur_id = '" + clePrim + "'";
      PreparedStatement preparedStatement = connection.prepareStatement(requete);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      System.out.println(e + " Modifier Fournisseur");
    }
  }

  /**
   * Méthode qui permet de récupérer les fleurs d'un fournisseur et les ajoutés dans le modèle de la table.
   *
   * @param id    L'id du fournisseur
   * @param model modèle de la table
   */
  public void getFournisseurFleur(String id, DefaultTableModel model) {
    try {
      String requete = "SELECT * FROM fleur WHERE fournisseur_id = '" + id + "'";
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
}
