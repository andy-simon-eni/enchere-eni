package fr.eni.javaee.enchere.bll;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.dal.DAOFactory;
import fr.eni.javaee.enchere.dal.EncheresDAO;

public class EncheresManager {

	private EncheresDAO encheresDAO;
	
	public EncheresManager() {
		this.encheresDAO = DAOFactory.getEncheresDAO();
	}
	
	public List<Encheres> getAllEncheres() throws BusinessException{
		return this.encheresDAO.selectAll();
	}
	
	public List<Encheres> getEncheresByCategorie(int no_categorie) throws BusinessException{
		return this.encheresDAO.getEnchereByCategorie(no_categorie);
	}
	
	public List<Encheres> getEncheresByMotCle(String motCle) throws BusinessException{
		return this.encheresDAO.getEnchereByMotCle(motCle);
	}
	
	public List<Encheres> getEnchereByMesEncheresRemportees(int no_utilisateurAcheteurConnecte) throws BusinessException{
		return this.encheresDAO.getEnchereByMesEncheresRemportees(no_utilisateurAcheteurConnecte);
	}
	
	List<Encheres> getEnchereByMesEncheresEnCours(int no_utilisateurAcheteurConnecte)throws BusinessException{
		return this.encheresDAO.getEnchereByMesEncheresEnCours(no_utilisateurAcheteurConnecte);
	}
	
	List<Encheres> getEnchereByEncheresOuvertes()  throws BusinessException{
		return this.encheresDAO.getEnchereByEncheresOuvertes();

	}
	
	List<Encheres> getEnchereByMesVentesTerminees(int no_utilisateurVendeurConnecte) throws BusinessException{
		return this.encheresDAO.getEnchereByMesVentesTerminees(no_utilisateurVendeurConnecte);
	}
	
	List<Encheres> getEnchereByMesVentesEnCours(int no_utilisateurVendeurConnecte) throws BusinessException{
		return this.encheresDAO.getEnchereByMesVentesEnCours(no_utilisateurVendeurConnecte);
	}
	
	List<Encheres> getEnchereByMesVentesnNonDebutes(int no_utilisateurVendeurConnecte) throws BusinessException{
		return this.encheresDAO.getEnchereByMesVentesnNonDebutes(no_utilisateurVendeurConnecte);
	}
	
}
