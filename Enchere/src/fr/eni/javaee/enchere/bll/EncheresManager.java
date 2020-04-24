package fr.eni.javaee.enchere.bll;

import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.bo.Utilisateurs;
import fr.eni.javaee.enchere.dal.CodesResultatDAL;
import fr.eni.javaee.enchere.BusinessException;
import java.time.LocalDate;
import java.util.List;
import fr.eni.javaee.enchere.dal.DAOFactory;
import fr.eni.javaee.enchere.dal.EncheresDAO;

public class EncheresManager {

	private EncheresDAO encheresDAO;
	
	public EncheresManager() {
		this.encheresDAO = DAOFactory.getEncheresDAO();
	}
	
	public List<Encheres> getAllEncheres() throws BusinessException{
		return this.encheresDAO.selectAll();
	}
	public List<Encheres> getEncheresByCategorie(int no_categorie) throws BusinessException{
		return this.encheresDAO.getEnchereByCategorie(no_categorie);
	}
	public List<Encheres> getMesEncheres(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheres(idUtilisateur);
	}
	public List<Encheres> getMesEncheresByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresByCateg(idUtilisateur, idCateg);
	}
	public List<Encheres> getEncheresOuvertes() throws BusinessException{
		return this.encheresDAO.getEncheresOuvertes();
	}
	public List<Encheres> getEncheresOuvertesByCategorie(int idCateg) throws BusinessException{
		return this.encheresDAO.getEncheresOuvertesByCateg(idCateg);
	}
	public List<Encheres> getEncheresEnCours(int idUtil) throws BusinessException{
		return this.encheresDAO.getEncheresEnCours(idUtil);
	}
	public List<Encheres> getEncheresEnCoursByCategorie(int idUtil, int idCateg) throws BusinessException{
		return this.encheresDAO.getEncheresEnCoursByCateg(idUtil, idCateg);
	}
	public List<Encheres> getMesEncheresNonDebutees(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheresNonDebutees(idUtilisateur);
	}
	public List<Encheres> getMesEncheresNonDebuteesByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresNonDebuteesByCategorie(idUtilisateur, idCateg);
	}
	public List<Encheres> getMesEncheresEnCours(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheresEnCours(idUtilisateur);
	}
	public List<Encheres> getMesEncheresEnCoursByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresEnCoursByCategorie(idUtilisateur, idCateg);
	}
	public List<Encheres> getMesEncheresTerminees(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheresTerminees(idUtilisateur);
	}
	public List<Encheres> getMesEncheresTermineesByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresTermineesByCategorie(idUtilisateur, idCateg);
	}
	public List<Encheres> getMesEncheresGagnees(int idUtilisateur) throws BusinessException{
		return this.encheresDAO.getMesEncheresGagnees(idUtilisateur);
	}
	public List<Encheres> getMesEncheresGagneesByCategorie(int idUtilisateur, int idCateg) throws BusinessException{
		return this.encheresDAO.getMesEncheresGagneesByCategorie(idUtilisateur, idCateg);
	}
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException {
		Encheres uneEnchere = null;
		uneEnchere = this.encheresDAO.getInfosMaxEnchereByNoArticle(no_article);
		return uneEnchere;
	}
	
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
	
	public void deleteEncheresByNoUtil(int no_util) throws BusinessException {
		this.encheresDAO.deleteByNoUtil(no_util);
	}
	
}
