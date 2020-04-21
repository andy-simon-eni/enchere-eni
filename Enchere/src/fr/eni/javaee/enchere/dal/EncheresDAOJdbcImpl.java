package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.ArticlesVendus;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.bo.Retraits;
import fr.eni.javaee.enchere.bo.Utilisateurs;

public class EncheresDAOJdbcImpl implements EncheresDAO {

	private static final String SELECT_ALL_ENCH = "SELECT * FROM ENCHERES e " + 
			"INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article " + 
			"INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie " + 
			"INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur " + 
			"LEFT JOIN RETRAITS R ON r.no_article=a.no_article";
	private static final String SELECT_ENCH_BY_CAT = "SELECT * FROM ENCHERES e " + 
			"INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article " + 
			"INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie " + 
			"INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur " + 
			"LEFT JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "WHERE c.no_categorie = ?";

	@Override
	public List<Encheres> selectAll() throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ENCH);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);	
				ench.setNo_utilisateur(getObjectUtil(rs));
				System.out.println(ench);
				ench.setNo_article(getObjectArticle(rs));
				System.out.println(ench);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_ECHEC);
			throw businessException;
		}
		return listEnch;
	}

	@Override
	public List<Encheres> getEnchereByCategorie(Categories cat) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCH_BY_CAT);
			pstmt.setInt(1, cat.getNo_categorie());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);	
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_ECHEC);
			throw businessException;
		}
		return listEnch;
	}

	private Utilisateurs getObjectUtil(ResultSet rs) throws SQLException {
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
	
	private Categories getObjectCategories(ResultSet rs) throws SQLException {
		Categories cat = new Categories();
		cat.setNo_categorie(rs.getInt("no_categorie"));
		cat.setLibelle(rs.getString("libelle"));
		
		return cat;
	}
	
	private Retraits getObjectRetraits(ResultSet rs) throws SQLException {
		Retraits retrait = new Retraits();
		retrait.setRue(rs.getString("rue"));
		retrait.setCode_postal(rs.getString("code_postal"));
		retrait.setVille(rs.getString("ville"));
		
		return retrait;
	}
	
	private ArticlesVendus getObjectArticle(ResultSet rs) throws SQLException {
		ArticlesVendus art = new ArticlesVendus();
		Categories cat =  getObjectCategories(rs);
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
