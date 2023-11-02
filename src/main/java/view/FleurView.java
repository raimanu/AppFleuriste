package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import classJava.ConnectDB;

public class FleurView extends JPanel
{
    private static JButton ajouterFleur, supprFleur, modifierProduit;
    private static JTable table;

    String[] colonne = {"Nom", "Prix","Age","Durée de vie", "Vivante", "Quantité"};
    private ConnectDB conn = new ConnectDB("palaumae");
    public FleurView()
    {
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
        //Ajout des données de la base de donnée dans la table
        conn.GetTable(model,colonne);
        //Ajout de la table dans un scrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        this.setVisible(true);
        //Ajout de la possibilité de selectionner une ligne de la table
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //Ajout de la table dans le panel
        this.add(table);

        //Création du bouton pour rajouter une fleur
        ajouterFleur = new JButton("Ajouter");
        boiteVertical.add(ajouterFleur);
        ajouterFleur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Boolean correct = false;
                while(!correct) {
                    String nom = JOptionPane.showInputDialog("Nom :");

                    double prix = Double.parseDouble(JOptionPane.showInputDialog("prix : (Chiffre seulement)"));

                    double age = Integer.parseInt(JOptionPane.showInputDialog("age : (Chiffre seulement)"));

                    double dureeVie = Integer.parseInt(JOptionPane.showInputDialog("dureeVie :"));

                    int quantite = Integer.parseInt(JOptionPane.showInputDialog("quantite : (Chiffre seulement)"));

                    if(age > dureeVie){
                        JOptionPane.showMessageDialog(null, "L'age ne peut pas être supérieur à la durée de vie");
                    }
                    else{
                        correct = true;
//                        model.addRow(new Object[]{nom, prix, age, dureeVie, true, quantite});
                        conn.addFleur(nom, prix, age, dureeVie, quantite);
                        resetTable();
                    }
                }
            }
        });

        //création du bouton supprimer
        supprFleur = new JButton("Remove");
        boiteVertical.add(supprFleur);
        supprFleur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(table.getSelectedRow() != -1) {
                    // Suppression de la ligne sélectionnée dans la base de donnée
                    conn.deleteFleur(table.getValueAt(table.getSelectedRow(), 0).toString());
                    resetTable();
                    JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
                }
            }
        });

        //création du bouton modifier
        modifierProduit = new JButton("Modifier");
        boiteVertical.add(modifierProduit);
        modifierProduit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn.RequeteSelect();
            }
        });
    }

    public void resetTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        conn.GetTable(model, colonne);
    }
}