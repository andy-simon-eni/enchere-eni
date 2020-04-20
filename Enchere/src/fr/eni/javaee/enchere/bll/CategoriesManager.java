package fr.eni.javaee.enchere.bll;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.dal.CategoriesDAO;
import fr.eni.javaee.enchere.dal.DAOFactory;

public class CategoriesManager {

	private CategoriesDAO categoriesDAO;

	public CategoriesManager() {
		this.categoriesDAO = DAOFactory.getCategoriesDAO();
	}
	
	public List<Categories> getAllCategories() throws BusinessException{
		return this.categoriesDAO.selectAll();
	}
	
	public Categories getCategorieByNoCategorie(int no_categorie) throws BusinessException {
		return this.categoriesDAO.getCategorieByNoCategorie(no_categorie);
	}
}
