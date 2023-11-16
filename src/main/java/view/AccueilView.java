package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTable;


public class AccueilView extends JPanel{
    private static JTable table;

    public AccueilView(String password) {
        //Création du panel principale
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(new Color(78, 160, 164));

        //Création panel de gauche pour mettre les boutons
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.WEST);

        //Création boite verticale pour inserer les composants du panel de gauche
        Box verticalBox = Box.createVerticalBox();
        panel.add(verticalBox);


    }
}
