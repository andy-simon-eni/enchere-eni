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
	private static final String GET_UTILISATEUR_BY_NO_UTIL = "SELECT * from UTILISATEURS WHERE no_utilisateur = ?";
	private static final String GET_UTILISATEUR_BY_EMAIL = "SELECT * from UTILISATEURS WHERE email = ?";
	private static final String GET_UTILISATEUR_BY_PSEUDO = "SELECT * from UTILISATEURS WHERE pseudo = ?";
	private static final String UPDATE_UTILISATEUR_BY_NO_UTIL = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = ?";
	private static final String DELETE_UTILISATEUR_BY_NO_UTIL = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
	private static final String AJOUTER_CREDIT_UTILISATEUR = "UPDATE UTILISATEURS SET credit = credit + (?) WHERE no_utilisateur = ?";
	private static final String IS_ENCHERISSEUR_MAX = "SELECT * FROM ENCHERES E "
			+ " INNER JOIN ARTICLES_VENDUS AV ON E.no_article = AV.no_article "
			+ " INNER JOIN (SELECT MAX(montant_enchere) 'maxMontant' FROM ENCHERES GROUP BY no_article) g1 ON g1.maxMontant = E.montant_enchere "
			+ " WHERE E.no_utilisateur = ? AND AV.date_fin_encheres >= convert(varchar(10), getdate(), 120) ";
	private static final String IS_VENDEUR = " SELECT TOP(1) 1 FROM ARTICLES_VENDUS WHERE no_utilisateur = ? AND date_fin_encheres >= convert(varchar(10), getdate(), 120) ";

	@Override
	public Utilisateurs insert(Utilisateurs utilisateur) throws BusinessException {
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
		return utilisateur;
	}

	@Override
	public void update(Utilisateurs utilisateur) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				ResultSet rs;

				pstmt = cnx.prepareStatement(UPDATE_UTILISATEUR_BY_NO_UTIL);
				pstmt.setString(1, utilisateur.getPseudo());
				pstmt.setString(2, utilisateur.getNom());
				pstmt.setString(3, utilisateur.getPrenom());
				pstmt.setString(4, utilisateur.getEmail());
				pstmt.setString(5, utilisateur.getTelephone());
				pstmt.setString(6, utilisateur.getRue());
				pstmt.setString(7, utilisateur.getCode_postal());
				pstmt.setString(8, utilisateur.getVille());
				pstmt.setString(9, utilisateur.getMot_de_passe());
				pstmt.setInt(10, utilisateur.getNo_utilisateur());
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
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			throw businessException;
		}
	}

	@Override
	public void delete(int no_util) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_UTILISATEUR_BY_NO_UTIL);
			pstmt.setInt(1, no_util);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw businessException;
		}
	}

	@Override
	public List<Utilisateurs> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateurs getUtilByNoUtil(int no_util) throws BusinessException {
		Utilisateurs util = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(GET_UTILISATEUR_BY_NO_UTIL);
			pstmt.setInt(1, no_util);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				util = new Utilisateurs();
				util.setNo_utilisateur(rs.getInt("no_utilisateur"));
				util.setPseudo(rs.getString("pseudo"));
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
				util.setPseudo(rs.getString("pseudo"));
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
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return util;
	}

	@Override
	public Utilisateurs getUtilByEmail(String email) throws BusinessException {
		Utilisateurs util = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(GET_UTILISATEUR_BY_EMAIL);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				util = new Utilisateurs();
				util.setNo_utilisateur(rs.getInt("no_utilisateur"));
				util.setPseudo(rs.getString("pseudo"));
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
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return util;
	}

	@Override
	public void ajouterCredit(int no_util, int montant) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				pstmt = cnx.prepareStatement(AJOUTER_CREDIT_UTILISATEUR);
				pstmt.setInt(1, montant);
				pstmt.setInt(2, no_util);
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
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			throw businessException;
		}

	}

	@Override
	public boolean isEncherisseurMax(int no_util) throws BusinessException {
		Boolean isEncherisseur = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(IS_ENCHERISSEUR_MAX);
			pstmt.setInt(1, no_util);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				isEncherisseur = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return isEncherisseur;
	}

	@Override
	public boolean isVendeur(int no_util) throws BusinessException {
		Boolean isVendeur = false;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(IS_VENDEUR);
			pstmt.setInt(1, no_util);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				isVendeur = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return isVendeur;
	}

}
