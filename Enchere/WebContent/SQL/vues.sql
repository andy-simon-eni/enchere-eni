-- Modification : ajout de deux vues, afin de récuperer les no_article ainsi que les montants maximums ou prix_initial des articles

-- Permet de récupérer le no_article ainsi que le montant le plus élevé pour chaque article avec une enchere
CREATE VIEW MaxMontantUtilisateur AS
SELECT no_article, MAX(montant_enchere)'montantMax'
FROM ENCHERES
GROUP BY no_article

-- Permet de récupérer le no_article ainsi que le montant le plus élevé pour chaque article avec une enchere si elle existe, sinon affiche le prix initial de l'article
CREATE VIEW allEnchere AS
SELECT AV.no_article, ISNULL(MMU.montantMax, AV.prix_initial)'montant'
FROM ARTICLES_VENDUS AV 
LEFT JOIN MaxMontantUtilisateur MMU ON AV.no_article = MMU.no_article