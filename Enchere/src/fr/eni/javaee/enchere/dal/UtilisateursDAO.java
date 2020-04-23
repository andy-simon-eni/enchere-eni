package fr.eni.javaee.enchere.dal;

import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Utilisateurs;

public interface UtilisateursDAO {
	public Utilisateurs insert(Utilisateurs utilisateur) throws BusinessException;
	public void update(Utilisateurs utilisateur) throws BusinessException;
	public void delete(int no_util) throws BusinessException;
	public List<Utilisateurs> selectAll();
	public Utilisateurs getUtilByNoUtil(int no_util) throws BusinessException;
	public Utilisateurs getUtilByPseudo(String pseudo) throws BusinessException;
	public Utilisateurs getUtilByEmail(String email) throws BusinessException;
	public void ajouterCredit(int no_util, int montant) throws BusinessException;
}
