package fr.eni.javaee.enchere.dal;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Encheres;

public interface EncheresDAO {
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException;
	public void insertEnchere(Encheres uneEnchere) throws BusinessException;
}
