package underView;

import queries.CommandeViewQueries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CommandeFleurView extends JPanel {
    String[] colonne = {"Id","Nom","Age","Durée de vie","Prix Unitaire", "Vivante", "Quantité", "Fournisseur Id"};
    JTable table;
    public static CommandeViewQueries conn;

    /**
     * Constructeur de la classe CommandeFleurView
     * @param password mot de passe de la base de données
     * @param commande_id id de la commande
     */
    public CommandeFleurView(String password, String commande_id) {
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
        conn.GetCommandeFleur(commande_id,model);
        this.add(scrollPane);
        //Ajout de la possibilité de selectionner une ligne et une case de la table
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //Ajout de la table dans le panel
        this.add(scrollPane);
    }
}
