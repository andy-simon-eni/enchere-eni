package fr.eni.javaee.enchere.dal;


public abstract class DAOFactory {
	
	public static UtilisateursDAO getUtilisateursDAO()
	{
		return new UtilisateursDAOJdbcImpl();
	}
}
