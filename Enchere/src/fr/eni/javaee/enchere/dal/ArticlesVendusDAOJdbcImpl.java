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
	private static final String GET_ARTICLES_VENDUS_BY_NO_ARTICLE = "SELECT *"
			+ " FROM ARTICLES_VENDUS AV INNER JOIN UTILISATEURS U ON AV.no_utilisateur = U.no_utilisateur"
			+ " INNER JOIN CATEGORIES C ON AV.no_categorie = C.no_categorie "
			+ " INNER JOIN RETRAITS R ON AV.no_article = R.no_article WHERE AV.no_article = ?;";
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES_VENDUS "
			+ " SET nom_article = ?, description = ?, date_debut_encheres = ? ,date_fin_encheres = ?, prix_initial = ?, no_categorie = ? "
			+ " WHERE no_article = ?";
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
	private static final String DELETE_ARTICLES_BY_NO_UTIL = "DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur = ?";
	private static final String DELETE_RETRAITS_BY_NO_ARTICLE = "DELETE FROM RETRAITS WHERE no_article IN (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = ?)";
	private static final String UPDATE_PRIX_VENTE = "MERGE INTO ARTICLES_VENDUS AV "
			+ " USING (SELECT AV.no_article, ISNULL(MMU.montantMax, AV.prix_initial) 'prix_vente' "
			+ " FROM ARTICLES_VENDUS AV " + "LEFT JOIN MaxMontantUtilisateur MMU ON AV.no_article = MMU.no_article "
			+ " where (SELECT DATEDIFF(day, date_derniere_maj, GETDATE()) FROM MAJ_ARTICLES) > 0 "
			+ " AND DATEDIFF(day, date_fin_encheres, GETDATE()) > 0) AS source "
			+ " ON AV.no_article = source.no_article " + " WHEN MATCHED THEN "
			+ " UPDATE SET AV.prix_vente = source.prix_vente; ";
	private static final String UPDATE_DATE_MAJ_ARTICLE = "UPDATE MAJ_ARTICLES SET date_derniere_maj = convert(varchar(10), GETDATE(), 120)";

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
				article = ObjectBuilder.getObjectArticle(rs);
				article.setUtil(ObjectBuilder.getObjectUtil(rs));
				article.setCategorie(ObjectBuilder.getObjectCategories(rs));

				retrait = ObjectBuilder.getObjectRetraits(rs);

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

	@Override
	public void deleteArticlesRetraits(int no_util) throws BusinessException {
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
		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw businessException;
		}

	}

	@Override
	public void updateArticleRemporte() throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				PreparedStatement pstmt;
				cnx.setAutoCommit(false);
				pstmt = cnx.prepareStatement(UPDATE_PRIX_VENTE);
				pstmt.executeUpdate();
				pstmt = cnx.prepareStatement(UPDATE_DATE_MAJ_ARTICLE);
				pstmt.executeUpdate();
				pstmt.close();
				cnx.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		} catch (SQLException e) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			throw businessException;
		}
	}

}
