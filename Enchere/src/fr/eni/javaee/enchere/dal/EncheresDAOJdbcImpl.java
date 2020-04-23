package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.bo.Retraits;

public class EncheresDAOJdbcImpl implements EncheresDAO {

	private EncheresDAO encheresDAO;
	private static final String GET_PSEUDO_MAX_MONTANT_ENCHERE_BY_NO_ARTICLE = "SELECT * FROM ENCHERES "
			+ " WHERE montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE no_article = ?) AND no_article = ?";
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES VALUES (?, ?, ?, ?)";
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET montant_enchere = ?, date_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";
	private static final String GET_ENCHERE_BY_NO_UTIL = "SELECT * FROM ENCHERES WHERE no_util = ? AND no_article = ?";

	@Override
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException {
		Encheres uneEnchere = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(GET_PSEUDO_MAX_MONTANT_ENCHERE_BY_NO_ARTICLE);
			pstmt.setInt(1, no_article);
			pstmt.setInt(2, no_article);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				uneEnchere = new Encheres();
				uneEnchere.setNo_utilisateur(rs.getInt("no_utilisateur"));
				uneEnchere.setNo_article(rs.getInt("no_article"));
				uneEnchere.setMontant_enchere(rs.getInt("montant_enchere"));
				uneEnchere.setDate_enchere((rs.getDate("date_enchere")).toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		
		return uneEnchere;
	}

	@Override
	public void insertEnchere(Encheres uneEnchere) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				pstmt = cnx.prepareStatement(INSERT_ENCHERE);
				pstmt.setInt(1, uneEnchere.getNo_utilisateur());
				pstmt.setInt(2, uneEnchere.getNo_article());
				pstmt.setDate(3, Date.valueOf(uneEnchere.getDate_enchere()));
				pstmt.setInt(4, uneEnchere.getMontant_enchere());
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
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
	}
	
	@Override
	public void updateEnchere(Encheres uneEnchere) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				pstmt = cnx.prepareStatement(UPDATE_ENCHERE);
				pstmt.setInt(1, uneEnchere.getMontant_enchere());
				pstmt.setDate(2, Date.valueOf(uneEnchere.getDate_enchere()));
				pstmt.setInt(3, uneEnchere.getNo_utilisateur());
				pstmt.setInt(4, uneEnchere.getNo_article());
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

	@Override
	public Encheres getEnchereByNoUtil(int no_util, int no_article) throws BusinessException {
		Encheres uneEnchere = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(GET_ENCHERE_BY_NO_UTIL);
			pstmt.setInt(1, no_util);
			pstmt.setInt(2, no_article);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				uneEnchere = new Encheres();
				uneEnchere.setNo_utilisateur(rs.getInt("no_utilisateur"));
				uneEnchere.setNo_article(rs.getInt("no_article"));
				uneEnchere.setMontant_enchere(rs.getInt("montant_enchere"));
				uneEnchere.setDate_enchere((rs.getDate("date_enchere")).toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		
		return uneEnchere;
	}


}
