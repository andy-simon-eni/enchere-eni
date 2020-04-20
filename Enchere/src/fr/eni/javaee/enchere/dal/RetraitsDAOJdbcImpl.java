package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Retraits;

public class RetraitsDAOJdbcImpl implements RetraitsDAO {
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS VALUES(?,?,?,?)";

	@Override
	public void insert(Retraits retrait) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				pstmt = cnx.prepareStatement(INSERT_RETRAIT);
				pstmt.setInt(1, retrait.getArticle().getNo_article());
				pstmt.setString(2, retrait.getRue());
				pstmt.setString(2, retrait.getCode_postal());
				pstmt.setString(2, retrait.getVille());
				pstmt.executeUpdate();
				pstmt.close();
				cnx.commit();
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

	}

}
