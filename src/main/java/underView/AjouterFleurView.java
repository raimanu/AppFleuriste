package underView;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import queries.CommandeViewQueries;
import queries.FleurViewQueries;


/**
 * Classe qui permet de gérer la vue AjouterFleurView.
 */
public class AjouterFleurView extends JPanel {
  private static JTable table;

  String[] colonne = {"Id", "Nom", "Age (Jour)", "Durée de vie (Jour)", "Prix Unitaire (Euro)", "Quantité", "Fournisseur Id"};
  public static FleurViewQueries connFleur;
  public static CommandeViewQueries connCommande;

  /**
   * Constructeur de la classe AjouterFleurView.
   *
   * @param password mot de passe de la base de données
   * @param id       id de la commande
   */
  public AjouterFleurView(String password, int id) {
    connFleur = new FleurViewQueries(password);
    connCommande = new CommandeViewQueries(password);
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
    connFleur.getFleurTable(model);
    this.add(scrollPane);
    //Ajout de la possibilité de selectionner une ligne et une case de la table
    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    //Ajout de la table dans le panel
    this.add(scrollPane);

    //Création du champ de texte pour la quantité
    JTextPane titre = new JTextPane();
    titre.setText("Quantité");
    titre.setEditable(false);
    boiteVertical.add(titre);
    JTextField quantite = new JTextField();
    boiteVertical.add(quantite);

    //Création du bouton pour rajouter une fleur
    JButton ajouterFleur = new JButton("Ajouter");
    boiteVertical.add(ajouterFleur);
    //Ajout d'un listener pour ajouter la fleur selectionné dans la commande avec la quantité indiqué
    ajouterFleur.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int fleurId = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
        connCommande.ajoutFleurCommande(id, fleurId, Integer.parseInt(quantite.getText()));
        model.setRowCount(0);
        view.CommandeView.resetTable();
        view.FleurView.resetTable();
        resetTable();
      }
    });
  }

  /**
   * Fonction qui permet de reset la table.
   */
  public static void resetTable() {
    connFleur.getFleurTable((DefaultTableModel) table.getModel());
  }
}
