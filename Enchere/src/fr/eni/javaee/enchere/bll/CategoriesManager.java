package fr.eni.javaee.enchere.bll;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.dal.CategoriesDAO;
import fr.eni.javaee.enchere.dal.DAOFactory;

/* Manager des categories */

public class CategoriesManager {

	private CategoriesDAO categoriesDAO;

	// constructeur du manager, qui instancie l'objet DAO depuis la DAO Factory 
	public CategoriesManager() {
		this.categoriesDAO = DAOFactory.getCategoriesDAO();
	}
	
	// Permet de récupérer la liste des catégories
	public List<Categories> getAllCategories() throws BusinessException{
		return this.categoriesDAO.selectAll();
	}
	
	// Permet de récupérer la catégorie grace à son ID
	public Categories getCategorieByNoCategorie(int no_categorie) throws BusinessException {
		return this.categoriesDAO.getCategorieByNoCategorie(no_categorie);
	}
}
