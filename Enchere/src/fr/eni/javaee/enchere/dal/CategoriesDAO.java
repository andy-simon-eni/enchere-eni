package fr.eni.javaee.enchere.dal;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;

//Interface contenant toutes les méthodes de CATEGORIE
public interface CategoriesDAO {

	public List<Categories> selectAll() throws BusinessException;
	public Categories getCategorieByNoCategorie(int no_categorie) throws BusinessException;
}
