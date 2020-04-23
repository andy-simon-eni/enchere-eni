package fr.eni.javaee.enchere.bll;

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
	public List<Encheres> getEncheresByMotCle(String motCle) throws BusinessException{
		return this.encheresDAO.getEnchereByMotCle(motCle);
	}
	public List<Encheres> getEnchereByMesEncheresRemportees(int no_utilisateurAcheteurConnecte) throws BusinessException{
		return this.encheresDAO.getEnchereByMesEncheresRemportees(no_utilisateurAcheteurConnecte);
	}
	public List<Encheres> getEnchereByMesEncheresEnCours(int no_utilisateurAcheteurConnecte)throws BusinessException{
		return this.encheresDAO.getEnchereByMesEncheresEnCours(no_utilisateurAcheteurConnecte);
	}
	public List<Encheres> getEnchereByEncheresOuvertes()  throws BusinessException{
		return this.encheresDAO.getEnchereByEncheresOuvertes();

	}
	public List<Encheres> getEnchereByMesVentesTerminees(int no_utilisateurVendeurConnecte) throws BusinessException{
		return this.encheresDAO.getEnchereByMesVentesTerminees(no_utilisateurVendeurConnecte);
	}
	public List<Encheres> getEnchereByMesVentesEnCours(int no_utilisateurVendeurConnecte) throws BusinessException{
		return this.encheresDAO.getEnchereByMesVentesEnCours(no_utilisateurVendeurConnecte);
	}
	public List<Encheres> getEnchereByMesVentesnNonDebutes(int no_utilisateurVendeurConnecte) throws BusinessException{
		return this.encheresDAO.getEnchereByMesVentesnNonDebutes(no_utilisateurVendeurConnecte);
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
					uneEnchere = new Encheres(no_utilisateur, no_article, java.time.LocalDate.now(), montant_enchere);
					if(this.encheresDAO.getEnchereByNoUtil(no_utilisateur, no_article) != null) {
						this.encheresDAO.updateEnchere(uneEnchere);
					}else {
						this.encheresDAO.insertEnchere(uneEnchere);
					}									
					if(enchereMax != null) {
						utilManager.ajouterCredit(enchereMax.getNo_utilisateur(), enchereMax.getMontant_enchere());
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
