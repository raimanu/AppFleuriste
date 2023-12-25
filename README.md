# AppFleuriste

Pour se connecter à la base de donnée via POSTGRESQL :

- Ouvrir PgAdmin et ensuite ouvrir base de donnée nommer Fleuriste.
- Créer la base de donnée et insérer les données si pas encore fait, avec le code suivant :
```sql
DROP TABLE IF EXISTS FLEUR CASCADE;
DROP TABLE IF EXISTS COMPOSE CASCADE;
DROP TABLE IF EXISTS COMMANDE CASCADE;
DROP TABLE IF EXISTS CLIENT CASCADE;
DROP TABLE IF EXISTS FOURNISSEUR CASCADE;

CREATE TABLE CLIENT (
PRIMARY KEY (client_id),
client_id SERIAL NOT NULL,
nom TEXT,
prenom TEXT,
adresse TEXT
);

CREATE TABLE FOURNISSEUR (
PRIMARY KEY (fournisseur_id),
fournisseur_id SERIAL NOT NULL,
nom TEXT,
adresse TEXT
);

CREATE TABLE FLEUR (
PRIMARY KEY (fleur_id),
fleur_id SERIAL NOT NULL,
nom TEXT,
age FLOAT,
duree_vie FLOAT,
prix_unitaire FLOAT,
quantite INTEGER,
fournisseur_id SERIAL NOT NULL,
FOREIGN KEY (fournisseur_id) REFERENCES FOURNISSEUR (fournisseur_id)
);

CREATE TABLE COMMANDE (
PRIMARY KEY (commande_id),
commande_id SERIAL NOT NULL,
date_commande DATE,
montant_total FLOAT,
client_id SERIAL NOT NULL,
FOREIGN KEY (client_id) REFERENCES CLIENT (client_id)
);

CREATE TABLE COMPOSE (
PRIMARY KEY (commande_id, fleur_id),
commande_id SERIAL NOT NULL,
fleur_id SERIAL NOT NULL,
quantite FLOAT,
FOREIGN KEY (commande_id) REFERENCES COMMANDE (commande_id),
FOREIGN KEY (fleur_id) REFERENCES FLEUR (fleur_id)
);


ALTER TABLE COMPOSE ADD FOREIGN KEY (fleur_id) REFERENCES FLEUR (fleur_id);
ALTER TABLE COMPOSE ADD FOREIGN KEY (commande_id) REFERENCES COMMANDE (commande_id);
ALTER TABLE COMMANDE ADD FOREIGN KEY (client_id) REFERENCES CLIENT (client_id);
ALTER TABLE FLEUR ADD FOREIGN KEY (fournisseur_id) REFERENCES FOURNISSEUR (fournisseur_id);

INSERT INTO CLIENT (nom, prenom, adresse) VALUES ('DUPONT', 'Jean', '1 rue de la Paix');
INSERT INTO CLIENT (nom, prenom, adresse) VALUES ('DURAND', 'Pierre', '2 rue de la Paix');
INSERT INTO CLIENT (nom, prenom, adresse) VALUES ('DUPUIS', 'Paul', '3 rue de la Paix');
INSERT INTO CLIENT (nom, prenom, adresse) VALUES ('DURANT', 'Jacques', '4 rue de la Paix');
INSERT INTO CLIENT (nom, prenom, adresse) VALUES ('PAIN', 'Robert', '3 rue de la Gloire');
INSERT INTO CLIENT (nom, prenom, adresse) VALUES ('REMAT', 'Jean', '3 rue de la Gloire');
INSERT INTO CLIENT (nom, prenom, adresse) VALUES ('PURUT', 'Pierre', '3 rue de la Gloire');

INSERT INTO FOURNISSEUR (nom, adresse) VALUES ('Fournisseur 1', '1 rue de la Paix');
INSERT INTO FOURNISSEUR (nom, adresse) VALUES ('Fournisseur 2', '2 rue de la Paix');
INSERT INTO FOURNISSEUR (nom, adresse) VALUES ('Fournisseur 3', '3 rue de la Paix');

INSERT INTO FLEUR (nom, age, duree_vie, prix_unitaire, quantite, fournisseur_id) VALUES ('Rose', 3, 5, 3, 10, 1);
INSERT INTO FLEUR (nom, age, duree_vie, prix_unitaire, quantite, fournisseur_id) VALUES ('Tulipe', 1, 2, 2, 10, 2);
INSERT INTO FLEUR (nom, age, duree_vie, prix_unitaire, quantite, fournisseur_id) VALUES ('Lys', 10, 20, 1, 5, 3);
INSERT INTO FLEUR (nom, age, duree_vie, prix_unitaire, quantite, fournisseur_id) VALUES ('Orchidée', 13, 15, 5, 5, 1);
INSERT INTO FLEUR (nom, age, duree_vie, prix_unitaire, quantite, fournisseur_id) VALUES ('Muguet', 10, 15, 10, 15, 2);
INSERT INTO FLEUR (nom, age, duree_vie, prix_unitaire, quantite, fournisseur_id) VALUES ('Pâquerette', 1, 20, 7, 3, 3);
INSERT INTO FLEUR (nom, age, duree_vie, prix_unitaire, quantite, fournisseur_id) VALUES ('Pivoine', 1, 20, 20, 5, 1);

INSERT INTO COMMANDE (date_commande, montant_total, client_id) VALUES ('2018-01-01', 1, 1);

INSERT INTO COMPOSE (commande_id, fleur_id, quantite) VALUES (1, 1, 1);

INSERT INTO COMMANDE (date_commande, montant_total, client_id) VALUES ('2018-01-01', 1, 2);

INSERT INTO COMPOSE (commande_id, fleur_id, quantite) VALUES (2, 2, 1);
```
- Entrer ensuite votre mot de passe dans la console après avoir lancé le Main.

---

Ce que nous devrons faire :
-

Les outils que nous avons utilisés pour ce projet sont :
-
- Trello : ```https://trello.com/invite/b/VuLQQJi7/ATTIf3d5bce9894fbebf3c882027004a9ff816052C87/kanban-application-fleuriste```
- Miro : ```https://miro.com/app/board/uXjVNZv4Xlk=/?share_link_id=706278503164```


Le backlog de notre projet :
-
- Pouvoir ajouter, modifier et supprimer une commande (Fait ; À refaire)
- Pouvoir ajouter, modifier et supprimer une fleur (Fait ; À refaire)
- Pouvoir ajouter, modifier et supprimer un client (En cours)
- Pouvoir ajouter, modifier et supprimer un fournisseur (En cours)
- Pouvoir être notifié lorsqu'une fleur est en rupture de stock (À faire)
- Pouvoir être notifié lorsqu'une fleur est en fin de vie (À faire)
- Pouvoir voir les fleurs composant une commande dans une nouvelle fenêtre (À faire) 
- Pouvoir voir les commandes d'un client dans une nouvelle fenêtre (À faire)
- Pouvoir voir les fleurs d'un fournisseur dans une nouvelle fenêtre (À faire)

TO DO LIST :
- 
- Faire les tests unitaires
- Faire les tests d'intégration
- Finir la base de donnée
- Finir l'interface graphique
  - Faire la view commande
  - Faire la view fleur
  - Faire la view client
  - Faire la view fournisseur
- Lier le Type de fleur avec la durée de vie de celle çi
- Penser aux évolutions du projet
- Revoir le modèle conceptuel de donnée
- Bien définir les contraintes pour les fleurs et les commandes etc

 
requete sql pour avoir les fleurs d'une commande :
-
```sql
SELECT * FROM FLEUR WHERE fleur_id IN (SELECT fleur_id FROM COMPOSE WHERE commande_id = 1);
```

requete sql pour avoir les fleurs qui sont a moins de 3 jours par rapport a la durée de vie moins l'age :
-
```sql
SELECT * FROM FLEUR WHERE duree_vie - age <= 7;
```
