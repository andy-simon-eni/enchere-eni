-- Script de cr�ation de la base de donn�es ENCHERES
--   type :      SQL Server 2012
--
-- Modification : passage de varchar(20) � varchar(100) le champ email dans la table UTILISATEURS
-- Modification : erreur de table pour la contrainte encheres_utilisateurs_fk qui s'ajoutait sur ARTICLES_VENDUS et non sur ENCHERES
-- Modification : passage de varchar(30) à varchar(35) le champ mot de passe car il est crypté en base
-- Modification : création table qui va contenir la date de la dernière maj des articles. Pour mettre a jour les articles où la date de fin est passée.
-- Modification : ajout de deux vues, afin de récuperer les no_article ainsi que les montants maximums ou prix_initial des articles

CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
)

ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)

CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL

)

ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article)

CREATE TABLE RETRAITS (
	no_article         INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)

ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_article)

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(100) NOT NULL,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(35) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL
)

ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur)


CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL
)

ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)

CREATE TABLE MAJ_ARTICLES (
	date_derniere_maj			DATE NOT NULL,
);
ALTER TABLE MAJ_ARTICLES ADD constraint maj_articles_pk PRIMARY KEY (date_derniere_maj)

INSERT INTO MAJ_ARTICLES VALUES(convert(varchar(10), GETDATE(), 120))


ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateurs_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_utilisateurs_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 
    
CREATE VIEW MaxMontantUtilisateur AS
SELECT no_article, MAX(montant_enchere)'montantMax'
FROM ENCHERES
GROUP BY no_article

CREATE VIEW allEnchere AS
SELECT AV.no_article, ISNULL(MMU.montantMax, AV.prix_initial)'montant'
FROM ARTICLES_VENDUS AV 
LEFT JOIN MaxMontantUtilisateur MMU ON AV.no_article = MMU.no_article
