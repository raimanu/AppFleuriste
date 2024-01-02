package underView;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import queries.AlerteFleurFanerQueries;

/**
 * Classe qui permet de créer la vue de la table AlerteFleurFaner.
 */
public class AlerteFleurFanerView extends JPanel {
  private static JTable table;
  String[] colonne = {"Id", "Nom", "Age (Jour)", "Durée de vie (Jour)", "Prix Unitaire (Euro)", "Quantité", "Fournisseur Id"};

  public static AlerteFleurFanerQueries conn;

  /**
   * Constructeur de la classe AlerteFleurFanerView.
   *
   * @param password mot de passe de la base de données
   */
  public AlerteFleurFanerView(String password) {
    conn = new AlerteFleurFanerQueries(password);
    //Creation du panel principale
    this.setLayout(new BorderLayout(0, 0));
    this.setBackground(new Color(78, 160, 164));

    //Creation d'une boite pour insérer les boutons
    Box boiteVertical = Box.createVerticalBox();
    this.add(boiteVertical);

    //Initialisation de la table
    table = new JTable();
    table.setBounds(20, 20, 200, 400);
    //Initialisation du model
    DefaultTableModel model = new DefaultTableModel(colonne, 0);
    table.setModel(model);
    //Ajout de la table dans un scrollPane
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.createVerticalScrollBar();
    //Ajout des données de la base de donnée dans la table
    conn.getAlerteFleurFaner(model);
    this.add(scrollPane);
    //Ajout de la possibilité de selectionner une ligne et une case de la table
    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    //Ajout de la table dans le panel
    this.add(scrollPane);
  }

  /**
   * Méthode qui permet de récupérer la table.
   *
   * @return JTable
   */
  public DefaultTableModel getModel() {
    return (DefaultTableModel) table.getModel();
  }
}
