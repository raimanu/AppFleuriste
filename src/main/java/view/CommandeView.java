package view;

import view.FleurView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class CommandeView extends JPanel{
    private static JTable table;

    private static JButton ajouterCommande, supprCommande, modifierCommande;

    public CommandeView() {
        //Creation du panel principale
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(new Color(78, 160, 164));

        String[] colonne = {"Nom", "Prix", "Couleur", "Taille","Age","Durée de vie", "Quantité", };
        DefaultTableModel model = new DefaultTableModel(colonne, 0);
        model.addRow(new Object[]{"Rose","100","Rouge",10,2,5,2});

        //Creation panel de gauche pour mettre les boutons
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.WEST);

        //Creation d'une boite pour insérer les boutons
        Box boiteVertical = Box.createVerticalBox();
        panel.add(boiteVertical);

        //Création du bouton pour rajouter une fleur
        ajouterCommande = new JButton("Ajouter");
        boiteVertical.add(ajouterCommande);
        String textBox = "some text here";
        ajouterCommande.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
