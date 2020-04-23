package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.ArticlesVendus;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Retraits;
import fr.eni.javaee.enchere.bo.Utilisateurs;

public class ArticlesVendusDAOJdbcImpl implements ArticlesVendusDAO {
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie) "
			+ "VALUES (?,?,?,?,?,?,?);";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS VALUES (?,?,?,?);";
	private static final String GET_ARTICLES_VENDUS_BY_NO_ARTICLE = "SELECT AV.*, U.pseudo, U.nom, U.prenom, U.email, U.telephone, U.rue 'rue_util', U.code_postal 'cp_util', U.ville 'ville_util', U.mot_de_passe, U.credit, U.administrateur, C.libelle, R.rue 'rue_retrait', R.code_postal 'cp_retrait', R.ville 'ville_retrait' "
			+ " FROM ARTICLES_VENDUS AV INNER JOIN UTILISATEURS U ON AV.no_utilisateur = U.no_utilisateur"
			+ " INNER JOIN CATEGORIES C ON AV.no_categorie = C.no_categorie "
			+ " LEFT JOIN RETRAITS R ON AV.no_article = R.no_article WHERE AV.no_article = ?;";
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES_VENDUS "
			+ " SET nom_article = ?, description = ?, date_debut_encheres = ? ,date_fin_encheres = ?, prix_initial = ?, no_categorie = ? "
			+ " WHERE no_article = ?";
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
	private static final String DELETE_ARTICLES_BY_NO_UTIL = "DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur = ?";
	private static final String DELETE_RETRAITS_BY_NO_ARTICLE = "DELETE FROM RETRAITS WHERE no_article IN (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = ?)";

	@Override
	public ArticlesVendus insert(ArticlesVendus article) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				ResultSet rs;
				Retraits retrait;
				if (article.getNo_article() == 0) {
					pstmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, article.getNom_article());
					pstmt.setString(2, article.getDescription());
					pstmt.setDate(3, Date.valueOf(article.getDate_debut()));
					pstmt.setDate(4, Date.valueOf(article.getDate_fin()));
					pstmt.setInt(5, article.getPrix_initial());
					pstmt.setInt(6, article.getUtil().getNo_utilisateur());
					pstmt.setInt(7, article.getCategorie().getNo_categorie());
					pstmt.executeUpdate();
					rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
						article.setNo_article(rs.getInt(1));
					}
					rs.close();
					retrait = article.getRetrait();
					if (retrait != null) {
						pstmt = cnx.prepareStatement(INSERT_RETRAIT);
						pstmt.setInt(1, article.getNo_article());
						pstmt.setString(2, retrait.getRue());
						pstmt.setString(3, retrait.getCode_postal());
						pstmt.setString(4, retrait.getVille());
						pstmt.executeUpdate();
					}
					pstmt.close();
					cnx.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		return article;
	}

	@Override
	public void update(ArticlesVendus article) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				Retraits retrait;
				if (article.getNo_article() != 0) {
					pstmt = cnx.prepareStatement(UPDATE_ARTICLE);
					pstmt.setString(1, article.getNom_article());
					pstmt.setString(2, article.getDescription());
					pstmt.setDate(3, Date.valueOf(article.getDate_debut()));
					pstmt.setDate(4, Date.valueOf(article.getDate_fin()));
					pstmt.setInt(5, article.getPrix_initial());
					pstmt.setInt(6, article.getCategorie().getNo_categorie());
					pstmt.setInt(7, article.getNo_article());
					pstmt.executeUpdate();
					retrait = article.getRetrait();
					pstmt = cnx.prepareStatement(UPDATE_RETRAIT);
					pstmt.setString(1, retrait.getRue());
					pstmt.setString(2, retrait.getCode_postal());
					pstmt.setString(3, retrait.getVille());
					pstmt.setInt(4, article.getNo_article());
					pstmt.executeUpdate();
					pstmt.close();
					cnx.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			throw businessException;
		}
	}

	@Override
	public void delete(int no_article) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ArticlesVendus> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticlesVendus getArticleByNoArticle(int no_article) throws BusinessException {
		ArticlesVendus article = null;
		Retraits retrait;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(GET_ARTICLES_VENDUS_BY_NO_ARTICLE);
			pstmt.setInt(1, no_article);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				article = this.articlesBuilder(rs);
				article.setUtil(this.utilisateursBuilder(rs));
				article.setCategorie(this.categoriesBuilder(rs));

				retrait = this.retraitsBuilder(rs);

				if (retrait != null) {
					article.setRetrait(retrait);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return article;
	}

	private ArticlesVendus articlesBuilder(ResultSet rs) throws SQLException {
		ArticlesVendus article;
		article = new ArticlesVendus();
		article.setNo_article(rs.getInt("no_article"));
		article.setNom_article(rs.getString("nom_article"));
		article.setDescription(rs.getString("description"));
		article.setDate_debut(LocalDate.parse(rs.getString("date_debut_encheres")));
		article.setDate_fin(LocalDate.parse(rs.getString("date_fin_encheres")));
		article.setPrix_initial(rs.getInt("prix_initial"));
		article.setPrix_vente(rs.getInt("prix_vente"));
		return article;
	}

	private Utilisateurs utilisateursBuilder(ResultSet rs) throws SQLException {
		Utilisateurs util;
		util = new Utilisateurs();
		util.setNo_utilisateur(rs.getInt("no_utilisateur"));
		util.setPseudo(rs.getString("pseudo"));
		util.setNom(rs.getString("nom"));
		util.setPrenom(rs.getString("prenom"));
		util.setEmail(rs.getString("email"));
		util.setTelephone(rs.getString("telephone"));
		util.setRue(rs.getString("rue_util"));
		util.setCode_postal(rs.getString("cp_util"));
		util.setVille(rs.getString("ville_util"));
		util.setMot_de_passe(rs.getString("mot_de_passe"));
		util.setCredit(rs.getInt("credit"));
		util.setAdministrateur(rs.getBoolean("administrateur"));
		return util;
	}

	private Categories categoriesBuilder(ResultSet rs) throws SQLException {
		Categories categ;
		categ = new Categories();
		categ.setNo_categorie(rs.getInt("no_categorie"));
		categ.setLibelle(rs.getString("libelle"));
		return categ;
	}

	private Retraits retraitsBuilder(ResultSet rs) throws SQLException {
		Retraits retrait = null;
		String rue, cp, ville;
		rue = rs.getString("rue_retrait");
		cp = rs.getString("cp_retrait");
		ville = rs.getString("ville_retrait");
		if (rue != null && !rue.isEmpty() && cp != null && !cp.isEmpty() && ville != null && !ville.isEmpty()) {
			retrait = new Retraits();
			retrait.setRue(rue);
			retrait.setCode_postal(cp);
			retrait.setVille(ville);
		}
		return retrait;
	}
	@Override
	public void deleteArticlesRetraits(int no_util) throws BusinessException{
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(DELETE_RETRAITS_BY_NO_ARTICLE);
				pstmt.setInt(1, no_util);
				pstmt.executeUpdate();
				pstmt = cnx.prepareStatement(DELETE_ARTICLES_BY_NO_UTIL);
				pstmt.setInt(1, no_util);
				pstmt.executeUpdate();
				cnx.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		}catch(SQLException e){
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw businessException;
		}
		
	}

}
