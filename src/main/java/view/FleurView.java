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
        //Donnée de la table / Devra être connecté à la BDD
        String[][] data = {{"Rose","100","Rouge","10","2","5","2"},{"Tiare","200","Blanc","50","3","4","3"}};
        String[] colonne = {"Nom", "Prix", "Couleur", "Taille","Age","Durée de vie", "Quantité", };

        table = new JTable();
        table.setBounds(20,20,200,400);

        DefaultTableModel model = new DefaultTableModel(colonne, 0);
        model.addRow(new Object[]{"Tiare",100,10,15,true,2});
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        this.setVisible(true);

        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        this.add(table);

        //Création du bouton pour rajouter une fleur
        ajouterFleur = new JButton("Ajouter");
        boiteVertical.add(ajouterFleur);
        String textBox = "some text here";
        ajouterFleur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = JOptionPane.showInputDialog("Nom :");

                double prix = Double.parseDouble(JOptionPane.showInputDialog("prix : (Chiffre seulement)"));

                int age = Integer.parseInt(JOptionPane.showInputDialog("age : (Chiffre seulement)"));

                int dureeVie = Integer.parseInt(JOptionPane.showInputDialog("dureeVie :"));

                int quantite = Integer.parseInt(JOptionPane.showInputDialog("quantite : (Chiffre seulement)"));

                //Fleur fleur = new Fleur(nom,prix,couleur,taille,age,dureeVie,quantite);
                model.addRow(new Object[]{nom,prix,age,dureeVie,true,quantite});
                //table.add("fleur", fleur);
                //fleur.showProduct();
            }
        });

        //création du bouton supprimer
        supprFleur = new JButton("Remove");
        boiteVertical.add(supprFleur);
        supprFleur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(table.getSelectedRow() != -1) {
                    model.removeRow(table.getSelectedRow());
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

    public JTable getComponent(JTable table) {
        return table;
    }
}