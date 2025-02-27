package fr.eni.javaee.enchere.bll;

import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.bo.Utilisateurs;
import fr.eni.javaee.enchere.dal.CodesResultatDAL;
import fr.eni.javaee.enchere.BusinessException;
import java.time.LocalDate;
import java.util.List;
import fr.eni.javaee.enchere.dal.DAOFactory;
import fr.eni.javaee.enchere.dal.EncheresDAO;

/* Manager des encheres */

public class EncheresManager {

	private EncheresDAO encheresDAO;
	
	// constructeur du manager, qui instancie l'objet DAO depuis la DAO Factory
	public EncheresManager() {
		this.encheresDAO = DAOFactory.getEncheresDAO();
	}
	
	//Permet de récuperer toutes les enchères
	public List<Encheres> getAllEncheres() throws BusinessException{
		return this.encheresDAO.selectAll();
	}
	//Permet de récuperer toutes les enchères d'une catégorie
	public List<Encheres> getEncheresByCategorie(int no_categorie) throws BusinessException{
		return this.encheresDAO.getEnchereByCategorie(no_categorie);
	}
	//Permet de récuperer toutes les ventes d'un utilisateur
	public List<Encheres> getMesEncheres(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheres(idUtilisateur);
	}
	//Permet de récuperer toutes les ventes d'un utilisateur dans une catégorie
	public List<Encheres> getMesEncheresByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresByCateg(idUtilisateur, idCateg);
	}
	//Permet de récuperer toutes les enchères à venir
	public List<Encheres> getEncheresOuvertes() throws BusinessException{
		return this.encheresDAO.getEncheresOuvertes();
	}
	//Permet de récuperer toutes les enchères à venir d'une catégorie
	public List<Encheres> getEncheresOuvertesByCategorie(int idCateg) throws BusinessException{
		return this.encheresDAO.getEncheresOuvertesByCateg(idCateg);
	}
	//Permet de récuperer toutes les enchères en cours
	public List<Encheres> getEncheresEnCours(int idUtil) throws BusinessException{
		return this.encheresDAO.getEncheresEnCours(idUtil);
	}
	//Permet de récuperer toutes les enchères en cours d'une catégorie
	public List<Encheres> getEncheresEnCoursByCategorie(int idUtil, int idCateg) throws BusinessException{
		return this.encheresDAO.getEncheresEnCoursByCateg(idUtil, idCateg);
	}
	//Permet de récuperer toutes les ventes non débutées d'un utilisateur
	public List<Encheres> getMesEncheresNonDebutees(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheresNonDebutees(idUtilisateur);
	}
	//Permet de récuperer toutes les ventes non débutées d'un utilisateur par catégorie
	public List<Encheres> getMesEncheresNonDebuteesByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresNonDebuteesByCategorie(idUtilisateur, idCateg);
	}
	//Permet de récuperer toutes les ventes en cours d'un utilisateur
	public List<Encheres> getMesEncheresEnCours(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheresEnCours(idUtilisateur);
	}
	//Permet de récuperer toutes les ventes en cours d'un utilisateur par catégorie
	public List<Encheres> getMesEncheresEnCoursByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresEnCoursByCategorie(idUtilisateur, idCateg);
	}
	//Permet de récuperer toutes les ventes terminées d'un utilisateur
	public List<Encheres> getMesEncheresTerminees(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheresTerminees(idUtilisateur);
	}
	//Permet de récuperer toutes les ventes terminées d'un utilisateur par catégorie
	public List<Encheres> getMesEncheresTermineesByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresTermineesByCategorie(idUtilisateur, idCateg);
	}
	//Permet de récuperer toutes les encheres gagnées d'un utilisateur
	public List<Encheres> getMesEncheresGagnees(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheresGagnees(idUtilisateur);
	}
	//Permet de récuperer toutes les encheres gagnées d'un utilisateur par catégorie
	public List<Encheres> getMesEncheresGagneesByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresGagneesByCategorie(idUtilisateur, idCateg);
	}
	//Permet de récupérer l'enchère la plus haute sur un article
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException {
		Encheres uneEnchere = null;
		uneEnchere = this.encheresDAO.getInfosMaxEnchereByNoArticle(no_article);
		return uneEnchere;
	}
	
	//Permet d'ajouter des enchères dans la BDD
	public void insertEnchere(int no_utilisateur, int no_article, int montant_enchere) throws BusinessException{
		BusinessException businessException = new BusinessException();
		UtilisateursManager utilManager = new UtilisateursManager();
		Encheres uneEnchere = null, enchereMax = null;
		ArticlesVendusManager articleManager = new ArticlesVendusManager();
		if(no_utilisateur > 0 && no_article > 0) {
			if(utilManager.getUtilByNoUtil(no_utilisateur).getCredit() >= montant_enchere) {
				enchereMax = this.getInfosMaxEnchereByNoArticle(no_article);
				if((enchereMax != null && montant_enchere > enchereMax.getMontant_enchere()) || (enchereMax == null && montant_enchere > 0 && montant_enchere >= articleManager.getArticleByNoArticle(no_article).getPrix_initial()) ) {
					uneEnchere = new Encheres(utilManager.getUtilByNoUtil(no_utilisateur), articleManager.getArticleByNoArticle(no_article), java.time.LocalDate.now(), montant_enchere);
					if(this.encheresDAO.getEnchereByNoUtil(no_utilisateur, no_article) != null) {
						this.encheresDAO.updateEnchere(uneEnchere);
					}else {
						this.encheresDAO.insertEnchere(uneEnchere);
					}									
					if(enchereMax != null) {
						utilManager.ajouterCredit(enchereMax.getNo_utilisateur().getNo_utilisateur(), enchereMax.getMontant_enchere());
					}
					utilManager.ajouterCredit(no_utilisateur, -montant_enchere);
				}else {
					businessException.ajouterErreur(CodesResultatBLL.REGLE_CREDITS_INVALIDE);
					throw businessException;
				}								
			}else {
				businessException.ajouterErreur(CodesResultatBLL.REGLE_CREDITS_INSUFFISANTS);
				throw businessException;
			}
		}else {
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
	}
	
	//Permet de supprimer les enchères d'un utilisateur de la BDD 
	public void deleteEncheresByNoUtil(int no_util) throws BusinessException {
		this.encheresDAO.deleteByNoUtil(no_util);
	}
	
}
