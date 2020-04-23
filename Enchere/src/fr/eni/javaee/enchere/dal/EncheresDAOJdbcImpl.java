package fr.eni.javaee.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Retraits;
import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.bo.Categories;
import fr.eni.javaee.enchere.bo.ArticlesVendus;
import fr.eni.javaee.enchere.bo.Utilisateurs;

public class EncheresDAOJdbcImpl implements EncheresDAO {

	private static final String SELECT_ALL_ENCH = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS R ON r.no_article=a.no_article";

	private static final String SELECT_ENCH_BY_CAT = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS R ON r.no_article=a.no_article " + "WHERE c.no_categorie = ?";

	private static final String RECHERCHE_ENCH = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS r ON r.no_article=a.no_article " + "WHERE a.nom_article LIKE ?";

	private static final String SELECT_ENCHERES_OUVERTES = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS R ON r.no_article=a.no_article " + "WHERE a.date_debut_encheres > GETDATE()";

	private static final String SELECT_MES_ENCHERES_EN_COURS = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "WHERE a.date_fin_encheres > GETDATE() AND  a.date_debut_encheres <= GETDATE() AND e.no_utilisateur = ?";

	private static final String SELECT_MES_ENCHRES_REMPORTEES = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "WHERE a.date_fin_encheres < GETDATE() AND e.no_utilisateur = ?";

	private static final String SELECT_MES_VENTES_NON_DEBUTEES = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "WHERE a.date_debut_encheres > GETDATE() AND a.no_utilisateur = ?";

	private static final String SELECT_MES_VENTES_EN_COURS = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "WHERE a.date_fin_encheres > GETDATE() AND  a.date_debut_encheres <= GETDATE() AND a.no_utilisateur = ?";

	private static final String SELECT_MES_VENTES_TERMINEES = "SELECT * FROM ENCHERES e "
			+ "INNER JOIN ARTICLES_VENDUS a ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=e.no_utilisateur "
			+ "LEFT JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "WHERE a.date_fin_encheres < GETDATE() AND a.no_utilisateur = ?";

	private static final String GET_PSEUDO_MAX_MONTANT_ENCHERE_BY_NO_ARTICLE = "SELECT * FROM ENCHERES "
			+ " WHERE montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE no_article = ?) AND no_article = ?";
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES VALUES (?, ?, ?, ?)";
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET montant_enchere = ?, date_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";
	private static final String GET_ENCHERE_BY_NO_UTIL = "SELECT * FROM ENCHERES E "
			+ " INNER JOIN UTILISATEURS U ON E.no_utilisateur = U.no_utilisateur "
			+ " INNER JOIN ARTICLES_VENDUS AV ON E.no_article = AV.no_article "
			+ " WHERE no_utilisateur = ? AND no_article = ?";
	private static final String DELETE_ENCHERES_BY_NO_UTIL = "DELETE FROM ENCHERES WHERE no_utilisateur = ?";

	@Override
	public List<Encheres> getEnchereByMesVentesnNonDebutes(int no_utilisateurVendeurConnecte) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_MES_VENTES_NON_DEBUTEES);
			pstmt.setInt(1, no_utilisateurVendeurConnecte);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
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
	public List<Encheres> getEnchereByMesVentesEnCours(int no_utilisateurVendeurConnecte) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_MES_VENTES_EN_COURS);
			pstmt.setInt(1, no_utilisateurVendeurConnecte);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
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
	public List<Encheres> getEnchereByMesVentesTerminees(int no_utilisateurVendeurConnecte) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_MES_VENTES_TERMINEES);
			pstmt.setInt(1, no_utilisateurVendeurConnecte);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
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
	public List<Encheres> getEnchereByEncheresOuvertes() throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERES_OUVERTES);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
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
	public List<Encheres> getEnchereByMesEncheresEnCours(int no_utilisateurAcheteurConnecte) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_MES_ENCHERES_EN_COURS);
			pstmt.setInt(1, no_utilisateurAcheteurConnecte);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
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
	public List<Encheres> getEnchereByMesEncheresRemportees(int no_utilisateurAcheteurConnecte)
			throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_MES_ENCHRES_REMPORTEES);
			pstmt.setInt(1, no_utilisateurAcheteurConnecte);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
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
	public List<Encheres> getEnchereByMotCle(String motCle) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		String mot = "%" + motCle + "%";
		System.out.println(mot);
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(RECHERCHE_ENCH);
			pstmt.setString(1, mot);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
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
	public List<Encheres> selectAll() throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ENCH);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
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
	public List<Encheres> getEnchereByCategorie(int cat) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCH_BY_CAT);
			pstmt.setInt(1, cat);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
				ench.setMontant_enchere(rs.getInt("montant_enchere"));
				listEnch.add(ench);
				ench.setNo_utilisateur(getObjectUtil(rs));
				ench.setNo_article(getObjectArticle(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_ECHEC);
			throw businessException;
		}
		return listEnch;
	}

	private Utilisateurs getObjectUtil(ResultSet rs) throws SQLException {
		Utilisateurs util = new Utilisateurs();
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
		return util;
	}

	private Categories getObjectCategories(ResultSet rs) throws SQLException {
		Categories cat = new Categories();
		cat.setNo_categorie(rs.getInt("no_categorie"));
		cat.setLibelle(rs.getString("libelle"));

		return cat;
	}

	private Retraits getObjectRetraits(ResultSet rs) throws SQLException {
		Retraits retrait = new Retraits();
		retrait.setRue(rs.getString("rue"));
		retrait.setCode_postal(rs.getString("code_postal"));
		retrait.setVille(rs.getString("ville"));

		return retrait;
	}

	private ArticlesVendus getObjectArticle(ResultSet rs) throws SQLException {
		ArticlesVendus art = new ArticlesVendus();
		Categories cat = getObjectCategories(rs);
		Utilisateurs util = getObjectUtil(rs);
		Retraits retrait = getObjectRetraits(rs);
		art.setNo_article(rs.getInt("no_article"));
		art.setNom_article(rs.getString("nom_article"));
		art.setDescription(rs.getString("description"));
		art.setDate_debut(rs.getDate("date_debut_encheres").toLocalDate());
		art.setDate_fin(rs.getDate("date_fin_encheres").toLocalDate());
		art.setPrix_initial(rs.getInt("prix_initial"));
		art.setPrix_vente(rs.getInt("prix_vente"));
		art.setUtil(util);
		art.setCategorie(cat);
		art.setRetrait(retrait);

		return art;
	}

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
				Utilisateurs util = this.getObjectUtil(rs);
				ArticlesVendus article = this.getObjectArticle(rs);
				uneEnchere.setNo_utilisateur(util);
				uneEnchere.setNo_article(article);
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
				pstmt.setInt(1, uneEnchere.getNo_utilisateur().getNo_utilisateur());
				pstmt.setInt(2, uneEnchere.getNo_article().getNo_article());
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
				pstmt.setInt(3, uneEnchere.getNo_utilisateur().getNo_utilisateur());
				pstmt.setInt(4, uneEnchere.getNo_article().getNo_article());
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
				Utilisateurs util = this.getObjectUtil(rs);
				ArticlesVendus article = this.getObjectArticle(rs);
				uneEnchere.setNo_utilisateur(util);
				uneEnchere.setNo_article(article);
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
	public void deleteByNoUtil(int no_util) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ENCHERES_BY_NO_UTIL);
			pstmt.setInt(1, no_util);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw businessException;
		}
	}

}
