package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import javax.swing.*;


import queries.AlerteFleurFanerQueries;
import underView.AlerteFleurFanerView;

/**
 * Classe PrincipalView qui permet d'afficher la fenêtre principale de l'application.
 *
 * <p>Elle hérite de JFrame.</p>
 *
 * @see JFrame
 */
public class PrincipalView extends JFrame {

  AlerteFleurFanerView alerte;

  /**
   * Constructeur de la classe PrincipalView.
   *
   * @param password mot de passe de la base de données
   */
  public PrincipalView(String password) {
    this.setTitle("Gestion des commandes et du stock");

    //Couleur grise du content pane
    this.getContentPane().setBackground(new Color(74, 171, 105, 218));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.getContentPane().setLayout(new BorderLayout(0, 0));

    alerte = new AlerteFleurFanerView(password);
    //Création du panel principale
    this.setLayout(new BorderLayout(0, 0));

    //Création panel de gauche pour mettre les boutons
    JPanel panel = new JPanel();
    this.add(panel, BorderLayout.WEST);

    //Création boite verticale pour inserer les composants du panel de gauche
    Box verticalBox = Box.createVerticalBox();
    panel.add(verticalBox);

    JTabbedPane voletOnglet = new JTabbedPane(JTabbedPane.TOP);
    this.getContentPane().add(voletOnglet);

    //Couleur du volet d'onglet
    voletOnglet.setBackground(new Color(74, 171, 105, 218));


    //Ajout de l'onglet classJava.Fleur, permettant de gérer les fleurs disponibles
    JPanel ongletFleur = new FleurView(password);
    voletOnglet.addTab("Fleur", new ImageIcon("images" + File.separator + "item.png"), ongletFleur, null);

    //Ajout de l'onglet classJava.Commande, permettant de gérer les commandes
    JPanel ongletCommande = new CommandeView(password);
    voletOnglet.addTab("Commande", new ImageIcon("images" + File.separator + "commande.png"), ongletCommande, null);

    //Ajout de l'onglet classJava.Client, permettant de gérer les clients
    JPanel ongletClient = new ClientView(password);
    voletOnglet.addTab("Client", new ImageIcon("images" + File.separator + "commande.png"), ongletClient, null);

    //Ajout de l'onglet classJava.Fournisseur, permettant de gérer les fournisseurs
    JPanel ongletFournisseur = new FournisseurView(password);
    voletOnglet.addTab("Fournisseur", new ImageIcon("images" + File.separator + "commande.png"), ongletFournisseur, null);

    //Centrer la fenetre
    this.setLocationRelativeTo(null);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setVisible(true);
    this.setAlwaysOnTop(true);

    AlerteFleurFanerQueries conn = new AlerteFleurFanerQueries(password);
    boolean alerteFleurFaner = conn.getAlerteFleurFaner(alerte.getModel());

    if (alerteFleurFaner) {
      JFrame frame = new JFrame("Alerte : Fleurs qui vont faner dans moins de 7 jours");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setContentPane(new AlerteFleurFanerView(password));
      frame.pack();
      frame.setVisible(true);
      frame.setAlwaysOnTop(true);
      frame.setBounds(0, 0, 800, 500);
      frame.setLocationRelativeTo(null);
    }
    this.setAlwaysOnTop(false);
  }
}
