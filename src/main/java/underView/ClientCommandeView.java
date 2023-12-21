package underView;

import queries.ClientViewQueries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class ClientCommandeView extends JPanel {
    private static JTable table;
    String[] colonne = {"Id" ,"Date", "Prix Total", "Client Id"};

    public static ClientViewQueries conn;

    /**
     * Constructeur de la classe ClientCommandeView
     * @param password mot de passe de la base de données
     * @param client_id id du client
     */
    public ClientCommandeView(String password, String client_id) {
        conn = new ClientViewQueries(password);
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
        conn.TableClientCommande(client_id,model);
        this.add(scrollPane);
        //Ajout de la possibilité de selectionner une ligne et une case de la table
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //Ajout de la table dans le panel
        this.add(scrollPane);
    }

}
