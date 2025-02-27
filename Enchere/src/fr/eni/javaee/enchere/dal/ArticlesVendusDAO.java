package fr.eni.javaee.enchere.dal;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.ArticlesVendus;

//Interface contenant toutes les méthodes de ARTICLESVENDUS
public interface ArticlesVendusDAO {
	public ArticlesVendus insert(ArticlesVendus article) throws BusinessException;
	public void update(ArticlesVendus article) throws BusinessException;
	public ArticlesVendus getArticleByNoArticle(int no_article) throws BusinessException;
	public void deleteArticlesRetraits(int no_util) throws BusinessException;
	public void updateArticleRemporte() throws BusinessException;
}