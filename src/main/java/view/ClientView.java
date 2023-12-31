package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import queries.ClientViewQueries;
import underView.ClientCommandeView;


/**
 * Classe ClientView qui permet d'afficher les clients de la base de données.
 *
 * <p>Elle hérite de JPanel et implémente ActionListener.</p>
 *
 * @see JPanel
 * @see java.awt.event.ActionListener
 */
public class ClientView extends JPanel {
  private static JTable table;
  public static ClientViewQueries conn;


  /**
   * Constructeur de la classe ClientView.
   *
   * @param password mot de passe de la base de données
   */
  public ClientView(String password) {
    conn = new ClientViewQueries(password);
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
    String[] colonne = {"Id", "Nom", "Prenom", "Adresse"};
    DefaultTableModel model = new DefaultTableModel(colonne, 0);
    table.setModel(model);
    //Ajout de la possibilité de trier les données de la table
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>((DefaultTableModel) table.getModel());
    table.setRowSorter(sorter);
    //Ajout de la table dans un scrollPane
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.createVerticalScrollBar();
    //Ajout des données de la base de donnée dans la table
    conn.getClientTable(model);
    this.add(scrollPane);
    //Ajout de la possibilité de selectionner une ligne et une case de la table
    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    //Ajout de la table dans le panel
    this.add(scrollPane);

    //Titre de la catégorie de bouton
    JLabel titre1 = new JLabel("Client");
    titre1.setHorizontalAlignment(SwingConstants.CENTER);
    titre1.setFont(titre1.getFont().deriveFont(20f));
    boiteVertical.add(titre1);

    //Création du bouton pour rajouter un client
    JButton ajouterClient = new JButton("Ajouter");
    boiteVertical.add(ajouterClient);
    ajouterClient.addActionListener(e -> {
      String nom = JOptionPane.showInputDialog(null, "Nom du client", "Ajouter un client", JOptionPane.QUESTION_MESSAGE);
      String prenom = JOptionPane.showInputDialog(null, "Prenom du client", "Ajouter un client", JOptionPane.QUESTION_MESSAGE);
      String adresse = JOptionPane.showInputDialog(null, "Adresse du client", "Ajouter un client", JOptionPane.QUESTION_MESSAGE);
      conn.ajoutClient(nom, prenom, adresse);
      model.setRowCount(0);
      conn.getClientTable(model);
    });

    //Création du bouton pour supprimer un client
    JButton supprClient = new JButton("Supprimer");
    boiteVertical.add(supprClient);
    supprClient.addActionListener(e -> {
      if (table.getSelectedRow() != -1) {
        String[] idCommande;
        idCommande = conn.GetClientCommande(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
        //Si idCommande est [null] alors le client n'a pas de commande en cours
        if (idCommande[0] != null) {
          JOptionPane.showMessageDialog(null, "Impossible de supprimer ce client car il a des commandes en cours", "Erreur", JOptionPane.ERROR_MESSAGE);
          for (String s : idCommande) {
            JOptionPane.showMessageDialog(null, "Commande id : " + s, "Erreur", JOptionPane.ERROR_MESSAGE);
          }
        } else {
          int row = table.getSelectedRow();
          String id = table.getModel().getValueAt(row, 0).toString();
          conn.supprClient(id);
          model.setRowCount(0);
          conn.getClientTable(model);
        }
      }
    });

    //Création du bouton pour modifier un client
    JButton modifClient = new JButton("Modifier");
    boiteVertical.add(modifClient);
    modifClient.addActionListener(e -> {
      if (table.getSelectedRow() != -1 && table.getSelectedColumn() != 0) {
        int row = table.getSelectedRow();
        String clePrim = table.getModel().getValueAt(row, 0).toString();
        String nomColonne = table.getColumnName(table.getSelectedColumn());
        String valeur = JOptionPane.showInputDialog(null, "Nouvelle valeur de la colonne " + nomColonne, "Modifier un client", JOptionPane.QUESTION_MESSAGE);
        conn.modifClient(nomColonne, valeur, clePrim);
        model.setRowCount(0);
        conn.getClientTable(model);
      } else {
        JOptionPane.showMessageDialog(null, "Veuillez selectionner une colonne ", "Erreur", JOptionPane.ERROR_MESSAGE);
      }
    });

    //Création du bouton pour afficher les commandes d'un client
    JButton afficherCommande = new JButton("Afficher les commandes");
    boiteVertical.add(afficherCommande);
    afficherCommande.addActionListener(e -> {
      if (table.getSelectedRow() != -1) {
        String id = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
        JFrame frame = new JFrame("Commande du client " + id);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new ClientCommandeView(password, id));
        frame.setVisible(true);
      }
    });
  }
}
