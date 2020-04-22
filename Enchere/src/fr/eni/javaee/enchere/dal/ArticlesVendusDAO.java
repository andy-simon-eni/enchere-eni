package fr.eni.javaee.enchere.dal;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.ArticlesVendus;

public interface ArticlesVendusDAO {
	public ArticlesVendus insert(ArticlesVendus article) throws BusinessException;
	public void update(ArticlesVendus article) throws BusinessException;
	public void delete(int no_article) throws BusinessException;
	public List<ArticlesVendus> selectAll();
	public ArticlesVendus getArticleByNoArticle(int no_article) throws BusinessException;
}
