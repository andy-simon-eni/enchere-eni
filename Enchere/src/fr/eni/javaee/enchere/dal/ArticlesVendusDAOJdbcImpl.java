package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.ArticlesVendus;

public class ArticlesVendusDAOJdbcImpl implements ArticlesVendusDAO {
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie) VALUES (?,?,?,?,?,?,?);";

	@Override
	public ArticlesVendus insert(ArticlesVendus article) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				ResultSet rs;

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
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		return article;
	}

	@Override
	public void update(ArticlesVendus article) throws BusinessException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

}
