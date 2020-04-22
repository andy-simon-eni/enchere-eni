package fr.eni.javaee.enchere.bll;

import fr.eni.javaee.enchere.dal.DAOFactory;
import fr.eni.javaee.enchere.dal.EncheresDAO;

public class EncheresManager {

	private EncheresDAO encheresDAO;
	
	public EncheresManager() {
		this.encheresDAO = DAOFactory.getEncheresDAO();
	}
	
}
