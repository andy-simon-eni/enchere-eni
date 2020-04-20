package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;

public class CategoriesDAOJdbcImpl implements CategoriesDAO {
	
	private static final String SELECT_ALL_CAT = "SELECT * from CATEGORIES";

	@Override
	public List<Categories> selectAll() throws BusinessException {
		List<Categories> listCat = new ArrayList<Categories>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_CAT);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				listCat.add(new Categories(rs.getString("libelle")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return listCat;
	}

}
