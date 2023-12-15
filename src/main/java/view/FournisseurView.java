package view;

import queries.ClientViewQueries;
import queries.FournisseurViewQueries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

public class FournisseurView extends JPanel{

    private static JTable table;
    public static FournisseurViewQueries conn;

    public FournisseurView(String password) {
        conn = new FournisseurViewQueries(password);
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
        String[] colonne = {"Id","Nom","Adresse"};
        DefaultTableModel model = new DefaultTableModel(colonne, 0);
        table.setModel(model);
        //Ajout de la table dans un scrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.createVerticalScrollBar();
        //Ajout des données de la base de donnée dans la table
        conn.GetFournisseurTable(model);
        this.add(scrollPane);
        //Ajout de la possibilité de selectionner une ligne et une case de la table
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //Ajout de la table dans le panel
        this.add(scrollPane);

        //Création du bouton pour rajouter un fournisseur
        JButton ajouterFournisseur = new JButton("Ajouter");
        boiteVertical.add(ajouterFournisseur);
        ajouterFournisseur.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(null, "Nom du fournisseur", "Ajouter un fournisseur", JOptionPane.QUESTION_MESSAGE);
            String adresse = JOptionPane.showInputDialog(null, "Adresse du fournisseur", "Ajouter un fournisseur", JOptionPane.QUESTION_MESSAGE);
            conn.ajoutFournisseur(nom, adresse);
            model.setRowCount(0);
            conn.GetFournisseurTable(model);
        });

        //Création du bouton pour supprimer un fournisseur
        JButton supprFournisseur = new JButton("Supprimer");
        boiteVertical.add(supprFournisseur);
        supprFournisseur.addActionListener(e -> {
            if(table.getSelectedRow() != -1){
                String result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce fournisseur ? Cela supprimera aussi les fleurs associées",
                        "Supprimer un fournisseur", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION ? "oui" : "non";
                if(result.equals("oui")){
                    int row = table.getSelectedRow();
                    String id = table.getModel().getValueAt(row, 0).toString();
                    conn.supprFournisseur(id);
                    model.setRowCount(0);
                    conn.GetFournisseurTable(model);
                }
            }
        });

        //Création du bouton pour modifier un fournisseur
        JButton modifFournisseur = new JButton("Modifier");
        boiteVertical.add(modifFournisseur);
        modifFournisseur.addActionListener(e -> {
            if(table.getSelectedRow() != -1 && table.getSelectedColumn() != 0){
                int row = table.getSelectedRow();
                String id = table.getModel().getValueAt(row, 0).toString();
                String nomColonne = table.getColumnName(table.getSelectedColumn());
                String valeur = JOptionPane.showInputDialog(null, "Nouvelle valeur de la colonne" + nomColonne, "Modifier un fournisseur", JOptionPane.QUESTION_MESSAGE);
                conn.modifFournisseur(nomColonne, valeur, id);
                model.setRowCount(0);
                conn.GetFournisseurTable(model);
            }
        });
        }
    }

