package underView;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import queries.CommandeViewQueries;

/**
 * Classe CommandeFleurView qui permet de créer une fenêtre pour afficher les fleurs d'une commande.
 */
public class CommandeFleurView extends JPanel {
  String[] colonne = {"Id", "Nom", "Age", "Durée de vie", "Prix Unitaire", "Quantité", "Fournisseur Id"};
  JTable table;
  public static CommandeViewQueries conn;

  /**
   * Constructeur de la classe CommandeFleurView.
   *
   * @param password    mot de passe de la base de données
   * @param commandeId id de la commande
   */
  public CommandeFleurView(String password, String commandeId) {
    conn = new CommandeViewQueries(password);
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
    //Ajout de la table dans un scrollPane
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.createVerticalScrollBar();
    //Ajout des données de la base de donnée dans la table
    conn.getCommandeFleur(commandeId, model);
    this.add(scrollPane);
    //Ajout de la possibilité de selectionner une ligne et une case de la table
    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    //Ajout de la table dans le panel
    this.add(scrollPane);
  }
}
