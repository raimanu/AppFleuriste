package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

public class PrincipalView extends JFrame {
    private static JTabbedPane voletOnglet;
    private JPanel ongletAccueil, ongletFleur, ongletCommande, ongletClient, ongletFournisseur;

    public PrincipalView(String password) {
        this.setTitle("Gestion des commandes et du stock");

        //Couleur gris du content pane
        this.getContentPane().setBackground(new Color(79, 150, 155));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout(0, 0));

        voletOnglet = new JTabbedPane(JTabbedPane.TOP);
        this.getContentPane().add(voletOnglet);

        //Couleur du volet d'onglet
        voletOnglet.setBackground(new Color(9, 145, 143));
        voletOnglet.setForeground(Color.BLACK);

        //Ajout de l'onglet Acceuil
        ongletAccueil = new AccueilView(password);
        voletOnglet.addTab("Accueil",new ImageIcon("images"+File.separator+"commande.png"), ongletAccueil, null);

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
