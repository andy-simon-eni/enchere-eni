package fr.eni.javaee.enchere.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.javaee.enchere.bo.ArticlesVendus;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Retraits;
import fr.eni.javaee.enchere.bo.Utilisateurs;

public final class ObjectBuilder {
	private ObjectBuilder() {
		
	}
	
	public static Utilisateurs getObjectUtil(ResultSet rs) throws SQLException {
		Utilisateurs util = new Utilisateurs();
		util.setNo_utilisateur(rs.getInt("no_utilisateur"));
		util.setPseudo(rs.getString("pseudo"));
		util.setNom(rs.getString("nom"));
		util.setPrenom(rs.getString("prenom"));
		util.setEmail(rs.getString("email"));
		util.setTelephone(rs.getString("telephone"));
		util.setRue(rs.getString("rue"));
		util.setCode_postal(rs.getString("code_postal"));
		util.setVille(rs.getString("ville"));
		util.setMot_de_passe(rs.getString("mot_de_passe"));
		util.setCredit(rs.getInt("credit"));
		util.setAdministrateur(rs.getBoolean("administrateur"));
		return util;
	}

	public static Categories getObjectCategories(ResultSet rs) throws SQLException {
		Categories cat = new Categories();
		cat.setNo_categorie(rs.getInt("no_categorie"));
		cat.setLibelle(rs.getString("libelle"));

		return cat;
	}

	public static Retraits getObjectRetraits(ResultSet rs) throws SQLException {
		Retraits retrait = new Retraits();
		retrait.setRue(rs.getString("rue"));
		retrait.setCode_postal(rs.getString("code_postal"));
		retrait.setVille(rs.getString("ville"));

		return retrait;
	}

	public static ArticlesVendus getObjectArticle(ResultSet rs) throws SQLException {
		ArticlesVendus art = new ArticlesVendus();
		Categories cat = getObjectCategories(rs);
		Utilisateurs util = getObjectUtil(rs);
		Retraits retrait = getObjectRetraits(rs);
		art.setNo_article(rs.getInt("no_article"));
		art.setNom_article(rs.getString("nom_article"));
		art.setDescription(rs.getString("description"));
		art.setDate_debut(rs.getDate("date_debut_encheres").toLocalDate());
		art.setDate_fin(rs.getDate("date_fin_encheres").toLocalDate());
		art.setPrix_initial(rs.getInt("prix_initial"));
		art.setPrix_vente(rs.getInt("prix_vente"));
		art.setUtil(util);
		art.setCategorie(cat);
		art.setRetrait(retrait);

		return art;
	}
	
}
