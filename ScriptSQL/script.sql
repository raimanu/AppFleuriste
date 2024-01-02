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