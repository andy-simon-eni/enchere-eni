package fr.eni.javaee.enchere.dal;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Encheres;

public interface EncheresDAO {

	public List<Encheres> selectAll() throws BusinessException;
	List<Encheres> getEnchereByCategorie(int no_categorie) throws BusinessException;
	List<Encheres> getEnchereByMotCle(String motCle) throws BusinessException;
}
