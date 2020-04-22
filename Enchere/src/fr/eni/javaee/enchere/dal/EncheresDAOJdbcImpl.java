package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Encheres;

public class EncheresDAOJdbcImpl implements EncheresDAO {

	private EncheresDAO encheresDAO;
	private static final String GET_PSEUDO_MAX_MONTANT_ENCHERE_BY_NO_ARTICLE = "SELECT * FROM ENCHERES "
			+ " WHERE montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE no_article = ?) AND no_article = ?";

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
				uneEnchere.setDate_enchere(LocalDate.parse((rs.getString("date_enchere"))));
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
