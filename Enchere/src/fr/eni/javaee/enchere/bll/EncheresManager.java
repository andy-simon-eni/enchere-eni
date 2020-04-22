package fr.eni.javaee.enchere.bll;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Encheres;
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
	
}
