package fr.eni.javaee.enchere.bll;

import java.time.LocalDate;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.bo.Utilisateurs;
import fr.eni.javaee.enchere.dal.CodesResultatDAL;
import fr.eni.javaee.enchere.dal.DAOFactory;
import fr.eni.javaee.enchere.dal.EncheresDAO;

public class EncheresManager {

	private EncheresDAO encheresDAO;
	
	public EncheresManager() {
		this.encheresDAO = DAOFactory.getEncheresDAO();
	}
	
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException {
		Encheres uneEnchere = null;
		uneEnchere = this.encheresDAO.getInfosMaxEnchereByNoArticle(no_article);
		return uneEnchere;
	}
	
	public void insertEnchere(int no_utilisateur, int no_article, int montant_enchere) throws BusinessException{
		BusinessException businessException = new BusinessException();
		UtilisateursManager utilManager = new UtilisateursManager();
		Encheres uneEnchere = null;
		no_utilisateur = no_utilisateur;
		no_article = no_article;
		montant_enchere = montant_enchere;

		if(no_utilisateur > 0 && no_article > 0 && montant_enchere > 0) {
			if(utilManager.getUtilByNoUtil(no_utilisateur).getCredit() >= montant_enchere) {
				uneEnchere = new Encheres(no_utilisateur, no_article, java.time.LocalDate.now(), montant_enchere);
				this.encheresDAO.insertEnchere(uneEnchere);
			}else {
				businessException.ajouterErreur(CodesResultatBLL.REGLE_CREDITS_INSUFFISANTS);
				throw businessException;
			}
		}else {
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
	}
	
}
