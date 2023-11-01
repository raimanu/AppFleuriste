# AppFleuriste

Pour se connecter à la base de donnée via POSTGESQL :

- Ouvrir PgAdmin et ensuite ouvrir base de donnée nommer Fleuriste.
- Créer la base de donnée et insérer les données si pas encore fait, avec le code suivant :
- ```sql
  DROP TABLE IF EXISTS Fleur CASCADE;
  CREATE TABLE Fleur (nom text PRIMARY KEY, prix int, age int, dureeVie int, vivante BOOLEAN, quantite int);
  Insert into Fleur (nom, prix, age, dureeVie, vivante, quantite) values
  ('Rose', 2.5, 2, 5, true, 10),
  ('Tulipe', 1.5, 1, 5, true, 10),
  ('Lys', 3.5, 3, 5, true, 10),
  ('Orchidée', 4.5, 4, 5, true, 10),
  ('Marguerite', 5.5, 5, 5, true, 10);
  ```
- Entrer ensuite votre mot de passe dans la console après avoir lancé le main.

 
