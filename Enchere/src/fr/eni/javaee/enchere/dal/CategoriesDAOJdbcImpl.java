package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.Utilisateurs;

public class CategoriesDAOJdbcImpl implements CategoriesDAO {

	private static final String SELECT_ALL_CAT = "SELECT * from CATEGORIES";
	private static final String SELECT_CATAGORIE_BY_NO_CATEGORIE = "SELECT * from CATEGORIES WHERE no_categorie = ?";

	@Override
	public List<Categories> selectAll() throws BusinessException {
		List<Categories> listCat = new ArrayList<Categories>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_CAT);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				listCat.add(ObjectBuilder.getObjectCategories(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return listCat;
	}

	@Override
	public Categories getCategorieByNoCategorie(int no_categorie) throws BusinessException {
		Categories categ = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATAGORIE_BY_NO_CATEGORIE);
			pstmt.setInt(1, no_categorie);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				categ = ObjectBuilder.getObjectCategories(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return categ;
	}

}
