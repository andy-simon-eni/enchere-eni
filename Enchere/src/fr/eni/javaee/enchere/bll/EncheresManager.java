package fr.eni.javaee.enchere.bll;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Encheres;
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
}
