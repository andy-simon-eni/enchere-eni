package fr.eni.javaee.enchere.dal;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Encheres;

public interface EncheresDAO {
	public List<Encheres> selectAll() throws BusinessException;
	List<Encheres> getEnchereByCategorie(int no_categorie) throws BusinessException;
	public List<Encheres> getMesEncheres(int no_utilisateurAcheteurConnecte) throws BusinessException;
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException;
	public void insertEnchere(Encheres uneEnchere) throws BusinessException;
	public void updateEnchere(Encheres uneEnchere) throws BusinessException;
	public Encheres getEnchereByNoUtil(int no_util, int no_article) throws BusinessException;
	public void deleteByNoUtil(int no_util) throws BusinessException;
}
