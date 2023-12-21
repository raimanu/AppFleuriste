package view;

import java.awt.BorderLayout;
import java.awt.Color;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import underView.AlerteFleurFanerView;
import queries.AlerteFleurFanerQueries;


public class AccueilView extends JPanel{
    private static JTable table;


    AlerteFleurFanerView alerte;

    public AccueilView(String password) {
        alerte = new AlerteFleurFanerView(password);
        //Création du panel principale
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(new Color(78, 160, 164));

        AlerteFleurFanerQueries conn = new AlerteFleurFanerQueries(password);
        boolean AlerteFleurFaner = conn.GetAlerteFleurFaner(alerte.getModel());

        //Création panel de gauche pour mettre les boutons
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.WEST);

        //Création boite verticale pour inserer les composants du panel de gauche
        Box verticalBox = Box.createVerticalBox();
        panel.add(verticalBox);

        if(AlerteFleurFaner){
            JFrame frame = new JFrame("Fleurs fanant dans moins d'une semaine");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new AlerteFleurFanerView(password));
            frame.pack();
            frame.setVisible(true);
        };
    }
}
