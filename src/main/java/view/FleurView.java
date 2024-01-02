package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import queries.FleurViewQueries;

/**
 * Class permettant de gérer l'onglet fleur.
 */
public class FleurView extends JPanel {
  private static JTable table;

  String[] colonne = {"Id", "Nom", "Age (Jour)", "Durée de vie (Jour)", "Prix Unitaire (Euro)", "Quantité", "Fournisseur Id"};
  public static FleurViewQueries conn;

  /**
   * Constructeur de la classe FleurView.
   *
   * @param password mot de passe de la base de données
   */
  public FleurView(String password) {
    conn = new FleurViewQueries(password);
    //Creation du panel principale
    this.setLayout(new BorderLayout(0, 0));
    this.setBackground(new Color(78, 160, 164));

    //Creation panel de gauche pour mettre les boutons
    JPanel panel = new JPanel();
    this.add(panel, BorderLayout.WEST);

    //Creation d'une boite pour insérer les boutons
    Box boiteVertical = Box.createVerticalBox();
    panel.add(boiteVertical);

    //Initialisation de la table
    table = new JTable();
    table.setBounds(20, 20, 200, 400);
    //Initialisation du model
    DefaultTableModel model = new DefaultTableModel(colonne, 0);
    table.setModel(model);
    //Ajout de la possibilité de trier les données de la table
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>((DefaultTableModel) table.getModel());
    table.setRowSorter(sorter);
    //Ajout de la table dans un scrollPane
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.createVerticalScrollBar();
    //Ajout des données de la base de donnée dans la table
    conn.getFleurTable(model);
    this.add(scrollPane);
    //Ajout de la possibilité de selectionner une ligne et une case de la table
    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    this.add(scrollPane);

    //Titre de la catégorie de bouton
    JLabel titre1 = new JLabel("Fleur");
    titre1.setHorizontalAlignment(SwingConstants.CENTER);
    titre1.setFont(titre1.getFont().deriveFont(20f));
    boiteVertical.add(titre1);

    //Création du bouton pour rajouter une fleur
    JButton ajouterFleur = new JButton("Ajouter");
    boiteVertical.add(ajouterFleur);
    ajouterFleur.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String nom = JOptionPane.showInputDialog("Nom :");
        if (Objects.equals(nom, "") || Objects.equals(nom, null)) {
          return;
        }
        String age = JOptionPane.showInputDialog("age : (Chiffre seulement)");
        if (Objects.equals(age, "") || Objects.equals(age, null)) {
          return;
        }
        try {
          Float.parseFloat(age);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "L'age doit être un chiffre");
          return;
        }
        if (Float.parseFloat(age) < 0) {
          JOptionPane.showMessageDialog(null, "L'age ne peut pas être négatif");
          return;
        }

        String propositionDuree = "";
        String propositionPrix = "";
        switch (nom.toLowerCase()) {
          case "rose":
            propositionDuree = "10";
            propositionPrix = "6";
            break;
          case "tulipe":
            propositionDuree = "7";
            propositionPrix = "5";
            break;
          case "orchidee":
            propositionDuree = "18";
            propositionPrix = "8";
            break;
          case "marguerite":
            propositionDuree = "9";
            propositionPrix = "4";
            break;
          case "lys":
            propositionDuree = "14";
            propositionPrix = "7";
            break;
          case "oeillet":
            propositionDuree = "17";
            propositionPrix = "6";
            break;
          case "pivoine":
            propositionDuree = "8";
            propositionPrix = "5";
            break;
          default:
            break;
        }
        String dureeVie = JOptionPane.showInputDialog("duree de vie : (Chiffre seulement)", propositionDuree);
        if (Objects.equals(dureeVie, "") || Objects.equals(dureeVie, null)) {
          return;
        }
        try {
          Float.parseFloat(dureeVie);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "La durée de vie doit être un chiffre");
          return;
        }
        if (Float.parseFloat(dureeVie) < 0) {
          JOptionPane.showMessageDialog(null, "La durée de vie ne peut pas être négatif");
          return;
        }

        String prix = JOptionPane.showInputDialog("prix : (Chiffre seulement)", propositionPrix);
        if (Objects.equals(prix, "") || Objects.equals(prix, null)) {
          return;
        }
        try {
          Float.parseFloat(prix);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "Le prix doit être un chiffre");
          return;
        }
        if (Float.parseFloat(prix) < 0) {
          JOptionPane.showMessageDialog(null, "Le prix ne peut pas être négatif");
          return;
        }

        String quantite = JOptionPane.showInputDialog("quantite : (Chiffre seulement)");
        if (Objects.equals(quantite, "") || Objects.equals(quantite, null)) {
          return;
        }
        try {
          Float.parseFloat(quantite);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "La quantité doit être un chiffre");
          return;
        }
        if (Float.parseFloat(quantite) < 0) {
          JOptionPane.showMessageDialog(null, "La quantité ne peut pas être négatif");
          return;
        }

        String fournisseurId = JOptionPane.showInputDialog("fournisseur_id : (Chiffre seulement)");
        if (Objects.equals(fournisseurId, "") || Objects.equals(fournisseurId, null)) {
          return;
        }
        try {
          Float.parseFloat(fournisseurId);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "L'id du fournisseur doit être un chiffre");
          return;
        }
        if (Float.parseFloat(fournisseurId) < 0) {
          JOptionPane.showMessageDialog(null, "Il n'y a pas d'id négatif");
          return;
        }

        if (Float.parseFloat(age) > Float.parseFloat(dureeVie)) {
          JOptionPane.showMessageDialog(null, "L'age ne peut pas être supérieur à la durée de vie");
        } else {
          conn.ajoutFleur(nom, Float.parseFloat(age), Float.parseFloat(dureeVie), Float.parseFloat(prix), Integer.parseInt(quantite), Integer.parseInt(fournisseurId));
          resetTable();
        }
      }
    });

    //création du bouton 'supprimer'
    JButton supprFleur = new JButton("Supprimer");
    boiteVertical.add(supprFleur);
    supprFleur.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        if (table.getSelectedRow() != -1) {
          // Suppression de la ligne sélectionnée dans la base de donnée
          conn.supprFleur(table.getValueAt(table.getSelectedRow(), 0).toString());
          // Rafraichit la table pour afficher les nouvelles données
          resetTable();
        }
      }
    });

    //création du bouton 'modifier'
    JButton modifierProduit = new JButton("Modifier");
    boiteVertical.add(modifierProduit);
    modifierProduit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (table.getSelectedRow() != -1) {
          // Vérification de la colonne selectionné
          if (table.getSelectedColumn() != -1 && table.getSelectedColumn() != 0) {
            String valeur = JOptionPane.showInputDialog("Nouvelle Valeur :");
            String clePrim = table.getValueAt(table.getSelectedRow(), 0).toString();
            conn.modifFleur(table.getColumnName(table.getSelectedColumn()), valeur, clePrim);
            resetTable();
          } else {
            JOptionPane.showMessageDialog(null, "Veuillez selectionner une colonne valide !");
          }
        }
      }
    });
  }

  /**
   * Méthode qui permet de rafraichir la table.
   */
  public static void resetTable() {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
    conn.getFleurTable(model);
  }
}