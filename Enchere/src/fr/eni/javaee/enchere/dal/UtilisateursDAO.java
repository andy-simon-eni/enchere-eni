package fr.eni.javaee.enchere.dal;

import java.util.List;

import fr.eni.javaee.enchere.bo.Utilisateurs;

public interface UtilisateursDAO {
	public void insert(Utilisateurs utilisateur) throws Exception;
	public void update(Utilisateurs utilisateur);
	public void delete(int id);
	public List<Utilisateurs> selectAll();
	public Utilisateurs selectByNoUtil(int no_util);
	public Utilisateurs getUtilByPseudo(String pseudo) throws Exception;
}
