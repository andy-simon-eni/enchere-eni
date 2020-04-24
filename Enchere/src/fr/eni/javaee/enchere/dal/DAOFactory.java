package fr.eni.javaee.enchere.dal;

//Permet de récupérer toutes les DAO
public abstract class DAOFactory {
	
	public static UtilisateursDAO getUtilisateursDAO()
	{
		return new UtilisateursDAOJdbcImpl();
	}
	
	public static CategoriesDAO getCategoriesDAO()
	{
		return new CategoriesDAOJdbcImpl();
	}
	
	public static EncheresDAO getEncheresDAO()
	{
		return new EncheresDAOJdbcImpl();
	}
	
	public static ArticlesVendusDAO getArticlesVendusDAO()
	{
		return new ArticlesVendusDAOJdbcImpl();
	}
}
