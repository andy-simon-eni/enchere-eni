package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Encheres;

public class EncheresDAOJdbcImpl implements EncheresDAO {

	private static final String SELECT_ALL_ENCH = "SELECT * from ENCHERES";
	private static final String SELECT_ENCH_BY_CAT = "SELECT * from ENCHERES INNER JOIN SELECT * FROM ENCHERES " + 
			"INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article " + 
			"INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie WHERE c.no_categorie = ?";

	@Override
	public List<Encheres> selectAll() throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ENCH);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				listEnch.add(new Encheres(
						rs.getInt("no_article"),
						rs.getDate("date_enchere").toLocalDate(),
						rs.getInt("montant_enchere")
						));
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
				listEnch.add(new Encheres(
						rs.getInt("no_article"),
						rs.getDate("date_enchere").toLocalDate(),
						rs.getInt("montant_enchere")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_ECHEC);
			throw businessException;
		}
		return listEnch;
	}


	
}
