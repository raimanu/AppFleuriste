package view;

import queries.AlerteFleurFanerQueries;
import underView.AlerteFleurFanerView;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

public class PrincipalView extends JFrame {
    private static JTabbedPane voletOnglet;
    private JPanel ongletFleur, ongletCommande, ongletClient, ongletFournisseur;

    AlerteFleurFanerView alerte;

    /**
     * Constructeur de la classe PrincipalView
     * @param password mot de passe de la base de données
     */
    public PrincipalView(String password) {
        this.setTitle("Gestion des commandes et du stock");

        //Couleur gris du content pane
        this.getContentPane().setBackground(new Color(79, 150, 155));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout(0, 0));

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
            JFrame frame = new JFrame("Alerte : Fleurs qui vont faner dans moins de 7 jours");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new AlerteFleurFanerView(password));
            frame.pack();
            frame.setVisible(true);
        };

        voletOnglet = new JTabbedPane(JTabbedPane.TOP);
        this.getContentPane().add(voletOnglet);

        //Couleur du volet d'onglet
        voletOnglet.setBackground(new Color(9, 145, 143));
        voletOnglet.setForeground(Color.BLACK);


        //Ajout de l'onglet classJava.Fleur, permettant de gérer les fleurs disponible
        ongletFleur = new FleurView(password);
        voletOnglet.addTab("Fleur",new ImageIcon("images"+ File.separator+"item.png"), ongletFleur, null);

        //Ajout de l'onglet classJava.Commande, permettant de gérer les commandes
        ongletCommande = new CommandeView(password);
        voletOnglet.addTab("Commande",new ImageIcon("images"+File.separator+"commande.png"), ongletCommande, null);

        ongletCommande.setBackground(new Color(9, 145, 143));

        //Ajout de l'onglet classJava.Client, permettant de gérer les clients
        ongletClient = new ClientView(password);
        voletOnglet.addTab("Client",new ImageIcon("images"+File.separator+"commande.png"), ongletClient, null);

        //Ajout de l'onglet classJava.Fournisseur, permettant de gérer les fournisseurs
        ongletFournisseur = new FournisseurView(password);
        voletOnglet.addTab("Fournisseur",new ImageIcon("images"+File.separator+"commande.png"), ongletFournisseur, null);

        //Centrer la fenetre
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

    }
}
