package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Utilisateurs;

public class UtilisateursDAOJdbcImpl implements UtilisateursDAO {

	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,0);";
	private static final String GET_UTILISATEUR_BY_PSEUDO = "SELECT * from UTILISATEURS WHERE pseudo = ?";

	@Override
	public void insert(Utilisateurs utilisateur) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				ResultSet rs;

				if (utilisateur.getNo_utilisateur() == 0) {
					pstmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, utilisateur.getPseudo());
					pstmt.setString(2, utilisateur.getNom());
					pstmt.setString(3, utilisateur.getPrenom());
					pstmt.setString(4, utilisateur.getEmail());
					pstmt.setString(5, utilisateur.getTelephone());
					pstmt.setString(6, utilisateur.getRue());
					pstmt.setString(7, utilisateur.getCode_postal());
					pstmt.setString(8, utilisateur.getVille());
					pstmt.setString(9, utilisateur.getMot_de_passe());
					pstmt.setInt(10, 0);
					pstmt.executeUpdate();
					rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
						utilisateur.setNo_utilisateur(rs.getInt(1));
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
	}

	@Override
	public void update(Utilisateurs utilisateur) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Utilisateurs> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateurs selectByNoUtil(int no_util) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateurs getUtilByPseudo(String pseudo) throws BusinessException {
		Utilisateurs util = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(GET_UTILISATEUR_BY_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				util = new Utilisateurs();
				util.setNo_utilisateur(rs.getInt("no_utilisateur"));
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

			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return util;
	}

}
