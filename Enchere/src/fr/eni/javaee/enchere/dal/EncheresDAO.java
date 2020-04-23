package fr.eni.javaee.enchere.dal;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Encheres;

public interface EncheresDAO {
	public List<Encheres> selectAll() throws BusinessException;
	List<Encheres> getEnchereByCategorie(int no_categorie) throws BusinessException;
	List<Encheres> getEnchereByMotCle(String motCle) throws BusinessException;
	List<Encheres> getEnchereByMesEncheresRemportees(int no_utilisateurAcheteur) throws BusinessException;
	List<Encheres> getEnchereByMesEncheresEnCours(int no_utilisateurAcheteurConnecte) throws BusinessException;
	List<Encheres> getEnchereByEncheresOuvertes() throws BusinessException;
	List<Encheres> getEnchereByMesVentesTerminees(int no_utilisateurVendeurConnecte) throws BusinessException;
	List<Encheres> getEnchereByMesVentesEnCours(int no_utilisateurVendeurConnecte) throws BusinessException;
	List<Encheres> getEnchereByMesVentesnNonDebutes(int no_utilisateurVendeurConnecte) throws BusinessException;
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException;
	public void insertEnchere(Encheres uneEnchere) throws BusinessException;
	public void updateEnchere(Encheres uneEnchere) throws BusinessException;
	public Encheres getEnchereByNoUtil(int no_util, int no_article) throws BusinessException;
	public void deleteByNoUtil(int no_util) throws BusinessException;
}
