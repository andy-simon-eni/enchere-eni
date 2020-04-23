package fr.eni.javaee.enchere.dal;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Encheres;

public interface EncheresDAO {
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException;
	public void insertEnchere(Encheres uneEnchere) throws BusinessException;
	public void updateEnchere(Encheres uneEnchere) throws BusinessException;
	public Encheres getEnchereByNoUtil(int no_util, int no_article) throws BusinessException;
}
