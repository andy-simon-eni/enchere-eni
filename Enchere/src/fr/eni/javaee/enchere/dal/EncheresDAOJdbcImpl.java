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

	private static final String SELECT_ALL_ENCH = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere)";

	private static final String SELECT_ENCH_BY_CAT = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE c.no_categorie = ?";

	private static final String SELECT_ALL_VENTE = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE A.no_utilisateur = ?";
	
	private static final String SELECT_ALL_VENTE_BY_CAT = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE A.no_utilisateur = ? AND c.no_categorie = ?";
	
	private static final String SELECT_ENCHERES_OUVERTES = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_debut_encheres > GETDATE()";
	
	private static final String SELECT_ENCHERES_OUVERTES_BY_CAT = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_debut_encheres > GETDATE() AND c.no_categorie = ?";

	private static final String SELECT_ENCHERES_EN_COURS = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_fin_encheres > GETDATE() AND  a.date_debut_encheres <= GETDATE() AND E.no_utilisateur = ?";
	
	private static final String SELECT_ENCHERES_EN_COURS_BY_CAT = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_fin_encheres > GETDATE() AND  a.date_debut_encheres <= GETDATE() "
			+ "AND E.no_utilisateur = ? AND c.no_categorie = ?";
	
	private static final String SELECT_VENTE_OUVERTES = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_debut_encheres > GETDATE() AND A.no_utilisateur = ?";
	
	private static final String SELECT_VENTE_OUVERTES_BY_CAT = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_debut_encheres > GETDATE() AND A.no_utilisateur = ? AND c.no_categorie = ?";
	
	private static final String SELECT_VENTE_EN_COURS = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_fin_encheres > GETDATE() AND  a.date_debut_encheres <= GETDATE() AND A.no_utilisateur = ?";
	
	private static final String SELECT_VENTE_EN_COURS_BY_CATEG = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_fin_encheres > GETDATE() AND  a.date_debut_encheres <= GETDATE() AND A.no_utilisateur = ? AND"
			+ "c.no_categorie = ?";
	
	private static final String SELECT_VENTES_TERMINEES = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_fin_encheres < GETDATE() AND a.no_utilisateur = ?";
	
	private static final String SELECT_VENTES_TERMINEES_BY_CATEG = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE a.date_fin_encheres < GETDATE() AND a.no_utilisateur = ? AND c.no_categorie = ?";
	
	private static final String SELECT_ENCHERES_GAGNEES = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE A.prix_vente IS NOT NULL AND e.no_utilisateur = ?";
	
	private static final String SELECT_ENCHERES_GAGNEES_BY_CAT = "SELECT * FROM ARTICLES_VENDUS a "
			+ "LEFT JOIN ENCHERES e ON e.no_article=a.no_article "
			+ "INNER JOIN CATEGORIES c ON c.no_categorie=a.no_categorie "
			+ "INNER JOIN UTILISATEURS u ON u.no_utilisateur=a.no_utilisateur "
			+ "INNER JOIN RETRAITS R ON r.no_article=a.no_article "
			+ "INNER JOIN allEnchere AE ON AE.no_article = A.no_article AND (AE.montant = A.prix_initial OR AE.montant = E.montant_enchere) "
			+ "WHERE A.prix_vente IS NOT NULL AND e.no_utilisateur = ? AND c.no_categorie = ?";
	
	private static final String GET_PSEUDO_MAX_MONTANT_ENCHERE_BY_NO_ARTICLE = "SELECT * FROM ENCHERES E "
			+ " INNER JOIN UTILISATEURS U ON E.no_utilisateur = U.no_utilisateur "
			+ " INNER JOIN ARTICLES_VENDUS AV ON E.no_article = AV.no_article "
			+ " INNER JOIN CATEGORIES C ON AV.no_categorie = C.no_categorie "
			+ " LEFT JOIN RETRAITS R ON R.no_article=AV.no_article"
			+ " WHERE E.montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE no_article = ?) AND E.no_article = ?";
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES VALUES (?, ?, ?, ?)";
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET montant_enchere = ?, date_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";
	private static final String GET_ENCHERE_BY_NO_UTIL = "SELECT * FROM ENCHERES E "
			+ " INNER JOIN UTILISATEURS U ON E.no_utilisateur = U.no_utilisateur "
			+ " INNER JOIN ARTICLES_VENDUS AV ON E.no_article = AV.no_article "
			+ " INNER JOIN CATEGORIES C ON AV.no_categorie = C.no_categorie "
			+ " LEFT JOIN RETRAITS R ON R.no_article=AV.no_article"
			+ " WHERE E.no_utilisateur = ? AND E.no_article = ?";
	private static final String DELETE_ENCHERES_BY_NO_UTIL = "DELETE FROM ENCHERES WHERE no_utilisateur = ?";


	@Override
	public List<Encheres> selectAll() throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ENCH);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheres(int no_utilisateurAcheteurConnecte) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_VENTE);
			pstmt.setInt(1, no_utilisateurAcheteurConnecte);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresByCateg(int no_utilisateur, int idCateg) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_BY_CAT);
			pstmt.setInt(1, no_utilisateur);
			pstmt.setInt(2, idCateg);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getEncheresOuvertes() throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERES_OUVERTES);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getEncheresOuvertesByCateg(int idCateg) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERES_OUVERTES_BY_CAT);
			pstmt.setInt(1, idCateg);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getEncheresEnCours(int idUtil) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERES_EN_COURS);
			pstmt.setInt(1, idUtil);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getEncheresEnCoursByCateg(int idUtil, int idCateg) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERES_EN_COURS_BY_CAT);
			pstmt.setInt(1, idUtil);
			pstmt.setInt(2, idCateg);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresNonDebutees(int no_utilisateur) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_VENTE_OUVERTES);
			pstmt.setInt(1, no_utilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresNonDebuteesByCategorie(int no_utilisateur, int idCateg) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_VENTE_OUVERTES_BY_CAT);
			pstmt.setInt(1, no_utilisateur);
			pstmt.setInt(2, idCateg);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresEnCours(int no_utilisateur) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_VENTE_EN_COURS);
			pstmt.setInt(1, no_utilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresEnCoursByCategorie(int no_utilisateur, int idCateg) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_VENTE_EN_COURS_BY_CATEG);
			pstmt.setInt(1, no_utilisateur);
			pstmt.setInt(2, idCateg);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresTerminees(int no_utilisateur) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_VENTES_TERMINEES);
			pstmt.setInt(1, no_utilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresTermineesByCategorie(int no_utilisateur, int idCateg) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_VENTES_TERMINEES_BY_CATEG);
			pstmt.setInt(1, no_utilisateur);
			pstmt.setInt(2, idCateg);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresGagnees(int no_utilisateur) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERES_GAGNEES);
			pstmt.setInt(1, no_utilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public List<Encheres> getMesEncheresGagneesByCategorie(int no_utilisateur, int idCateg) throws BusinessException {
		List<Encheres> listEnch = new ArrayList<Encheres>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERES_GAGNEES_BY_CAT);
			pstmt.setInt(1, no_utilisateur);
			pstmt.setInt(2, idCateg);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres ench = new Encheres();
				if(rs.getDate("date_enchere") == null) {
					ench.setDate_enchere(null);
					ench.setMontant_enchere(rs.getInt("montant"));
				}else {
					ench.setDate_enchere(rs.getDate("date_enchere").toLocalDate());
					ench.setMontant_enchere(rs.getInt("montant_enchere"));
				}
				ench.setNo_utilisateur(ObjectBuilder.getObjectUtil(rs));
				ench.setNo_article(ObjectBuilder.getObjectArticle(rs));
				listEnch.add(ench);
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
	public Encheres getInfosMaxEnchereByNoArticle(int no_article) throws BusinessException {
		Encheres uneEnchere = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(GET_PSEUDO_MAX_MONTANT_ENCHERE_BY_NO_ARTICLE);
			pstmt.setInt(1, no_article);
			pstmt.setInt(2, no_article);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				uneEnchere = new Encheres();
				Utilisateurs util = ObjectBuilder.getObjectUtil(rs);
				ArticlesVendus article = ObjectBuilder.getObjectArticle(rs);
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
				Utilisateurs util = ObjectBuilder.getObjectUtil(rs);
				ArticlesVendus article = ObjectBuilder.getObjectArticle(rs);
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
