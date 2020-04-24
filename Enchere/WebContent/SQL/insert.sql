INSERT INTO MAJ_ARTICLES VALUES('23-04-2020');

INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) 
VALUES ('EtienneC','Cassin','Etienne','etienne.cassin@campus-eni.fr','0607080910',' 60 rue de la chevalerie','44300','Nantes','7e1878f24a48433a0eb245bde2b09f01',1000,1);

INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) 
VALUES ('BG','Bill','Gates','bill.gates@microsoft.fr','0607080911',' 60 rue de la tour','35600','Redon','8dc69c58a5422bafd19e2fa92195bbf4',1000,1);

INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) 
VALUES ('JB','Jeff','Bezos','jeff.bezos@amazon.fr','0607080912',' 61 rue de la livraison','44800','Saint Herblain','3f380f861fae4f500d27a47bfbdf7a3b',200,1);


INSERT INTO CATEGORIES (libelle) VALUES ('SportLoisirs')
INSERT INTO CATEGORIES (libelle) VALUES ('Informatique')
INSERT INTO CATEGORIES (libelle) VALUES ('Ameublement')
INSERT INTO CATEGORIES (libelle) VALUES ('Vêtements');


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Ballon','Idéal pour jouer au foot','20/04/2020','27/04/2020',20,1,1)
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (2,1,'21/04/2020',30)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Canne à pêche','Résistante en fibre de carbone','20/04/2020','27/04/2020',30,1,1);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (2,2,'26/04/2020',50)

INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('T-shirt blanc','taille 16 ans','20/04/2020','27/04/2020',25,1,4);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,3,'25/04/2020',35)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Polo','Marque Ralph Lauren','20/04/2020','27/04/2020',105,1,4);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,4,'25/04/2020',120)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('PC Gamer','I7','20/04/2020','27/04/2020',100,1,2);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,5,'26/04/2020',600)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Meuble','Couleur noir','20/04/2020','27/04/2020',10,2,3);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,6,'26/04/2020',20)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Lave vaisselle','De 2018','15/04/2020','18/04/2020',60,2,3);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,7,'16/04/2020',80)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Ballon de basket','NBA 2019','15/04/2020','18/04/2020',30,2,1);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,8,'16/04/2020',40)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Gants de gardien de but','Mousse renforcée','15/04/2020','18/04/2020',35,1,1);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,9,'16/04/2020',45)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Jean Noir','taille 40','22/05/2020','30/05/2020',50,1,4);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,10,'23/05/2020',60)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Jean Bleu','taille 42','22/05/2020','30/05/2020',120,1,4);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (3,11,'23/05/2020',130)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Jean Gris','taille 44','22/05/2020','30/05/2020',60,3,4);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (1,12,'23/05/2020',70)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Casque audio','Razer','22/05/2020','30/05/2020',30,3,2);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (1,13,'23/05/2020',40)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Souris de PC','Logitech','20/04/2020','30/04/2020',60,3,2);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (1,14,'21/04/2020',80)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Table de salon de jardin','Pour 6 personnes','20/04/2020','30/04/2020',20,3,3);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (1,15,'21/04/2020',150)


INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie)
VALUES('Maillot de football','Equipe du FCNantes','16/04/2020','17/04/2020',10,3,4);
INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (1,16 ,'17/04/2020',50)

INSERT INTO RETRAITS VALUES (1, '60 rue de la chevalerie','44300','Nantes')
INSERT INTO RETRAITS VALUES (2, '60 rue de la chevalerie','44300','Nantes')
INSERT INTO RETRAITS VALUES (3, '60 rue de la chevalerie','44300','Nantes')
INSERT INTO RETRAITS VALUES (4, '60 rue de la chevalerie','44300','Nantes')
INSERT INTO RETRAITS VALUES (5, '60 rue de la chevalerie','44300','Nantes')
INSERT INTO RETRAITS VALUES (6, ' 60 rue de la tour','35600','Redon')
INSERT INTO RETRAITS VALUES (7, ' 60 rue de la tour','35600','Redon')
INSERT INTO RETRAITS VALUES (8, ' 60 rue de la tour','35600','Redon')
INSERT INTO RETRAITS VALUES (9, '60 rue de la chevalerie','44300','Nantes')
INSERT INTO RETRAITS VALUES (10, '60 rue de la chevalerie','44300','Nantes')
INSERT INTO RETRAITS VALUES (11, '60 rue de la chevalerie','44300','Nantes')
INSERT INTO RETRAITS VALUES (12, '61 rue de la livraison','44800','Saint Herblain')
INSERT INTO RETRAITS VALUES (13, '61 rue de la livraison','44800','Saint Herblain')
INSERT INTO RETRAITS VALUES (14, '61 rue de la livraison','44800','Saint Herblain')
INSERT INTO RETRAITS VALUES (15, '61 rue de la livraison','44800','Saint Herblain')
INSERT INTO RETRAITS VALUES (16, '61 rue de la livraison','44800','Saint Herblain')