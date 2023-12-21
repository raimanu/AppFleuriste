package view;

import queries.FleurViewQueries;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//Javadoc de la class FleurView
/**
 * Class permettant de gérer l'onglet fleur.
 */
public class FleurView extends JPanel {
    private static JTable table;

    String[] colonne = {"Id","Nom","Age (Jour)","Durée de vie (Jour)","Prix Unitaire (Euro)", "Vivante", "Quantité", "Fournisseur Id"};
    public static FleurViewQueries conn;
    public FleurView(String password)
    {
        conn = new FleurViewQueries(password);
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
        conn.GetFleurTable(model);
        this.add(scrollPane);
        //Ajout de la possibilité de selectionner une ligne et une case de la table
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //Ajout de la table dans le panel
        this.add(scrollPane);

        //Création du bouton pour rajouter une fleur
        JButton ajouterFleur = new JButton("Ajouter");
        boiteVertical.add(ajouterFleur);
        ajouterFleur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean correct = false;
                while(!correct) {
                    String nom = JOptionPane.showInputDialog("Nom :");

                    float age = Float.parseFloat(JOptionPane.showInputDialog("age : (Chiffre seulement)"));

                    float dureeVie = Float.parseFloat(JOptionPane.showInputDialog("duree de vie : (Chiffre seulement)"));

                    float prix = Float.parseFloat(JOptionPane.showInputDialog("prix : (Chiffre seulement)"));

                    int quantite = Integer.parseInt(JOptionPane.showInputDialog("quantite : (Chiffre seulement)"));

                    int fournisseur_id = Integer.parseInt(JOptionPane.showInputDialog("fournisseur_id : (Chiffre seulement)"));

                    if(age > dureeVie){
                        JOptionPane.showMessageDialog(null, "L'age ne peut pas être supérieur à la durée de vie");
                    }
                    else{
                        correct = true;
                        conn.ajoutFleur(nom, age, dureeVie, prix, quantite, fournisseur_id);
                        resetTable();
                    }
                }
            }
        });

        //création du bouton 'supprimer'
        JButton supprFleur = new JButton("Supprimer");
        boiteVertical.add(supprFleur);
        supprFleur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(table.getSelectedRow() != -1) {
                    // Suppression de la ligne sélectionnée dans la base de donnée
                    conn.supprFleur(table.getValueAt(table.getSelectedRow(), 0).toString());
                    // Rafraichit la table pour afficher les nouvelles données
                    resetTable();
                    JOptionPane.showMessageDialog(null, "Ligne selectionné supprimé !");
                }
            }
        });

        //création du bouton 'modifier'
        JButton modifierProduit = new JButton("Modifier");
        boiteVertical.add(modifierProduit);
        modifierProduit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1){
                    // Vérification de la colonne selectionné
                    if (table.getSelectedColumn() != -1 && table.getSelectedColumn() != 0){
                        String valeur = JOptionPane.showInputDialog("Nouvelle Valeur :");
                        String clePrim = table.getValueAt(table.getSelectedRow(), 0).toString();
                        conn.modifFleur(table.getColumnName(table.getSelectedColumn()),valeur,clePrim);
                        resetTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner une colonne valide !");
                    }
                }
            }
        });

        //Création du bouton pour rafraichir la table
        JButton rafraichir = new JButton("Rafraichir");
        boiteVertical.add(rafraichir);
        rafraichir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTable();
            }
        });
    }

    public static void resetTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        conn.GetFleurTable(model);
    }
}