package fr.eni.javaee.enchere.bll;

import java.time.LocalDate;
import java.util.regex.Pattern;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.ArticlesVendus;
import fr.eni.javaee.enchere.bo.Retraits;
import fr.eni.javaee.enchere.bo.Utilisateurs;
import fr.eni.javaee.enchere.dal.ArticlesVendusDAO;
import fr.eni.javaee.enchere.dal.DAOFactory;

/* Manager des articlesVendus */

public class ArticlesVendusManager {
	private ArticlesVendusDAO articlesVendusDAO;

	// constructeur du manager, qui instancie l'objet DAO depuis la DAO Factory
	public ArticlesVendusManager() {
		this.articlesVendusDAO = DAOFactory.getArticlesVendusDAO();
	}
	
	// Permet d'inserer les données dans la table
	public ArticlesVendus insertArticleVendu(String nom_article, String description, LocalDate date_debut, LocalDate date_fin, int prix_initial, int no_util, int no_categorie, String rue, String code_postal, String ville) throws BusinessException {
		BusinessException businessException = new BusinessException();
		UtilisateursManager utilManager = new UtilisateursManager();
		CategoriesManager categManager = new CategoriesManager();
		ArticlesVendus article = null;
		Boolean createRetrait;
		nom_article = nom_article.trim();
		description = description.trim();
		rue = rue.trim();
		code_postal = code_postal.trim();
		ville = ville.trim();
		validerArticle(nom_article, description, date_debut, date_fin, prix_initial, no_util, no_categorie, businessException);
		if((rue == null || rue.isEmpty()) && (code_postal == null || code_postal.isEmpty()) && (ville == null || ville.isEmpty())) {
			Utilisateurs util = utilManager.getUtilByNoUtil(no_util);
			rue = util.getRue();
			code_postal = util.getCode_postal();
			ville = util.getVille();
		}
		validerRetrait(rue, code_postal, ville, businessException);
		if (!businessException.hasErreurs()) {
			 article = new ArticlesVendus(nom_article, description, date_debut, date_fin, prix_initial, 0, utilManager.getUtilByNoUtil(no_util), categManager.getCategorieByNoCategorie(no_categorie), null);			 
			 Retraits retrait = new Retraits(rue, code_postal, ville);
			 article.setRetrait(retrait);
			 article = this.articlesVendusDAO.insert(article);
		} else {
			throw businessException;
		}
		return article;
	}
	
	// Permet les champs d'un article, avant l'insertion
	private void validerArticle(String nom_article, String description, LocalDate date_debut, LocalDate date_fin, int prix_initial, int no_util, int no_categorie, BusinessException businessException) throws BusinessException {
		UtilisateursManager utilManager = new UtilisateursManager();
		CategoriesManager categManager = new CategoriesManager();
		if(nom_article == null || nom_article.isEmpty() || nom_article.length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_NOM_INVALIDE);
		}
		if(description == null || description.isEmpty() || description.length() > 300) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_DESCRIPTION_INVALIDE);
		}
		if(date_debut.isBefore(java.time.LocalDate.now())) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_DATE_DEBUT_INVALIDE);
		}
		if(date_debut.isAfter(date_fin)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_DATE_FIN_INVALIDE);
		}
		if(prix_initial < 0) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_PRIX_INITIAL_INVALIDE);
		}
		if(utilManager.getUtilByNoUtil(no_util) == null) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_UTIL_INVALIDE);
		}
		if(categManager.getCategorieByNoCategorie(no_categorie) == null) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_CATEG_INVALIDE);
		}
	}
	
	// Permet les champs d'un retrait, avant l'insertion
	private void validerRetrait(String rue, String code_postal, String ville, BusinessException businessException) throws BusinessException {
		UtilisateursManager utilManager;
		Pattern patternCompile_Ville;
		Pattern patternCompile_CodePostal;
		Pattern patternCompile_Rue;
		
		String pattern_Rue = "[A-Za-z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\\-\\s']+";
		String pattern__Ville = "[A-Za-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\\-\\s']+";
		String pattern_CodePostal = "[0-9]{0,15}";
		
		patternCompile_Ville = Pattern.compile(pattern__Ville);
		patternCompile_CodePostal = Pattern.compile(pattern_CodePostal);
		patternCompile_Rue = Pattern.compile(pattern_Rue);
		
		if(rue == null || rue.isEmpty() || rue.length() > 30 || !patternCompile_Rue.matcher(rue).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_RUE_INVALIDE);
		}
		if(code_postal == null || code_postal.isEmpty() || !patternCompile_CodePostal.matcher(code_postal).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_CODE_POSTAL_INVALIDE);
		}
		if(ville == null || ville.isEmpty() || ville.length() > 30 || !patternCompile_Ville.matcher(ville).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLES_VENDUS_VILLE_INVALIDE);
		}	
		
	}
	
	// Permet de recuperer un article grace à son ID
	public ArticlesVendus getArticleByNoArticle(int no_article) throws BusinessException {
		ArticlesVendus article = null;
		article = this.articlesVendusDAO.getArticleByNoArticle(no_article);
		return article;
	}
	
	// Permet de mettre à jour un article
	public void updateArticleVendu(int noArticle, String nom_article, String description, LocalDate date_debut, LocalDate date_fin, int prix_initial, int no_util, int no_categorie, String rue, String code_postal, String ville) throws BusinessException {
		BusinessException businessException = new BusinessException();
		UtilisateursManager utilManager = new UtilisateursManager();
		CategoriesManager categManager = new CategoriesManager();
		ArticlesVendus article = null;
		Boolean createRetrait;
		nom_article = nom_article.trim();
		description = description.trim();
		rue = rue.trim();
		code_postal = code_postal.trim();
		ville = ville.trim();
		validerArticle(nom_article, description, date_debut, date_fin, prix_initial, no_util, no_categorie, businessException);
		if((rue == null || rue.isEmpty()) && (code_postal == null || code_postal.isEmpty()) && (ville == null || ville.isEmpty())) {
			Utilisateurs util = utilManager.getUtilByNoUtil(no_util);
			rue = util.getRue();
			code_postal = util.getCode_postal();
			ville = util.getVille();
		}
		validerRetrait(rue, code_postal, ville, businessException);
		if (!businessException.hasErreurs() && noArticle > 0) {
			 article = new ArticlesVendus(noArticle, nom_article, description, date_debut, date_fin, prix_initial, 0, utilManager.getUtilByNoUtil(no_util), categManager.getCategorieByNoCategorie(no_categorie), null);			 
			 Retraits retrait = new Retraits(rue, code_postal, ville);
			 article.setRetrait(retrait);
			 this.articlesVendusDAO.update(article);
		} else {
			throw businessException;
		}
	}
	
	// Permet de supprimer un article
	public void deleteArticlesRetraits(int no_util) throws BusinessException {
		this.articlesVendusDAO.deleteArticlesRetraits(no_util);
	}
	
	//Permet de mettre la derniere enchere dans le prix de vente lorsque l'enchere est finie
	public void updateArticleRemporte() throws BusinessException {
		this.articlesVendusDAO.updateArticleRemporte();
	}
	
}
