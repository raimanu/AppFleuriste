package view;

import queries.CommandeViewQueries;
import underView.CommandeFleurView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class CommandeView extends JPanel{
    private static JTable table;

    private static JButton ajouterCommande, supprCommande, modifierCommande, ajouterFleur, supprFleur, confirmerCommande, voirFleurs;

    String[] colonne = {"Id" ,"Date", "Prix Total", "Client Id"};

    public CommandeViewQueries conn;

    /**
     * Constructeur de la classe CommandeView
     * @param password mot de passe de la base de données
     */
    public CommandeView(String password) {
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
        table.setBounds(20,20,200,400);
        //Initialisation du model
        DefaultTableModel model = new DefaultTableModel(colonne, 0);
        table.setModel(model);
        //Ajout de la table dans un scrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.createVerticalScrollBar();
        //Ajout des données de la base de donnée dans la table
        conn.GetCommandeTable(model);
        this.add(scrollPane);
        //Ajout de la possibilité de selectionner une ligne et une case de la table
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //Ajout de la table dans le panel
        this.add(scrollPane);

        //Création du bouton pour rajouter une commande
        ajouterCommande = new JButton("Ajouter une commande");
        boiteVertical.add(ajouterCommande);
        ajouterCommande.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = JOptionPane.showInputDialog(null, "Date de la commande");
                int client_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Id du client"));
                conn.ajoutCommande(date, client_id);
                model.setRowCount(0);
                resetTable();
            }
        });

        //Création du bouton pour ajouter une fleur à la commande
        ajouterFleur = new JButton("Ajouter une fleur");
        boiteVertical.add(ajouterFleur);

        ajouterFleur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1){
                    int commande_id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    int fleur_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Id de la fleur"));
                    int quantite = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantité"));
                    conn.ajoutFleurCommande(commande_id, fleur_id, quantite);
                    model.setRowCount(0);
                    resetTable();
                    view.FleurView.resetTable();
                }
            }
        });

        //Création du bouton pour modifier une commande
        modifierCommande = new JButton("Modifier une commande");
        boiteVertical.add(modifierCommande);
        modifierCommande.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1){
                    // Vérification de la colonne selectionné
                    if (table.getSelectedColumn() != -1 && table.getSelectedColumn() != 0){
                        String valeur = JOptionPane.showInputDialog("Nouvelle Valeur :");
                        String clePrim = table.getValueAt(table.getSelectedRow(), 0).toString();
                        conn.modifCommande(table.getColumnName(table.getSelectedColumn()),valeur,clePrim);
                        resetTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner une colonne valide !");
                    }
                }
            }
        });

        //Création du bouton pour supprimer une commande
        supprCommande = new JButton("Supprimer une commande");
        boiteVertical.add(supprCommande);
        supprCommande.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1) {
                    // Suppression de la ligne sélectionnée dans la base de donnée
                    conn.supprCommande(table.getValueAt(table.getSelectedRow(), 0).toString());
                    // Rafraichit la table pour afficher les nouvelles données
                    resetTable();
                    view.FleurView.resetTable();
                    JOptionPane.showMessageDialog(null, "Ligne selectionné supprimé !");
                }
            }
        });

        //Création du bouton pour supprimer une fleur d'une commande
        confirmerCommande = new JButton("Confirmer la commande");
        boiteVertical.add(confirmerCommande);
        confirmerCommande.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1) {
                    // Suppression de la ligne sélectionnée dans la base de donnée
                    conn.confirmCommande(table.getValueAt(table.getSelectedRow(), 0).toString());
                    // Rafraichit la table pour afficher les nouvelles données
                    resetTable();
                    view.FleurView.resetTable();
                    JOptionPane.showMessageDialog(null, "Commande confirmée !");
                }
            }
        });

        //Création du bouton pour voir les fleurs d'une commande selectionnée dans un nouveau panel
        voirFleurs = new JButton("Voir les fleurs");
        boiteVertical.add(voirFleurs);
        voirFleurs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1) {
                    String commande_id = table.getValueAt(table.getSelectedRow(), 0).toString();
                    JFrame frame = new JFrame("Fleurs de la commande " + commande_id);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(800, 600);
                    frame.setLocationRelativeTo(null);
                    frame.setResizable(false);
                    frame.setContentPane(new CommandeFleurView(password, commande_id));
                    frame.setVisible(true);
                }
            }
        });

    }

    /**
     * Méthode qui permet de récupérer la table
     * @return JTable
     */
    public void resetTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        conn.GetCommandeTable(model);
    }
}
