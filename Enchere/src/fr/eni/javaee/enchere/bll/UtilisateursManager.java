package fr.eni.javaee.enchere.bll;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Utilisateurs;
import fr.eni.javaee.enchere.dal.CodesResultatDAL;
import fr.eni.javaee.enchere.dal.DAOFactory;
import fr.eni.javaee.enchere.dal.UtilisateursDAO;

public class UtilisateursManager {
	private UtilisateursDAO utilisateursDAO;

	public UtilisateursManager() {
		this.utilisateursDAO = DAOFactory.getUtilisateursDAO();
	}

	public Utilisateurs insertUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mdp, String verif_mdp) throws BusinessException {
		BusinessException businessException = new BusinessException();
		Utilisateurs util = null;
		pseudo = pseudo.trim();
		nom = nom.trim();
		prenom = prenom.trim();
		email = email.trim();
		telephone = telephone.trim();
		rue = rue.trim();
		code_postal = code_postal.trim();
		ville = ville.trim();
		mdp = mdp.trim();
		verif_mdp = verif_mdp.trim();

		this.validerUtil(0, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp, verif_mdp,
				businessException);
		if (!businessException.hasErreurs()) {
			try {
				mdp = MD5Password.getEncodedPassword(mdp);
			} catch (BusinessException e) {
				businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
				throw businessException;
			}
			util = new Utilisateurs(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp, 0, false);
			util = this.utilisateursDAO.insert(util);

		} else {
			throw businessException;
		}
		return util;
	}

	private void validerUtil(int no_util, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mdp, String verif_mdp,
			BusinessException businessException) throws BusinessException {

		Pattern patternCompile_Pseudo;
		Pattern patternCompile_Nom_Prenom_Ville;
		Pattern patternCompile_Email;
		Pattern patternCompile_Telephone_CodePostal;
		Pattern patternCompile_Rue;

		String pattern_Pseudo = "[A-Za-z0-9]+";
		String pattern_Nom_Prenom_Ville = "[A-Za-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\\-\\s']+";
		String pattern_Email = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		String pattern_Telephone_CodePostal = "[0-9]{0,15}";
		String pattern_Rue = "[A-Za-z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\\-\\s']+";

		patternCompile_Pseudo = Pattern.compile(pattern_Pseudo);
		patternCompile_Nom_Prenom_Ville = Pattern.compile(pattern_Nom_Prenom_Ville);
		patternCompile_Email = Pattern.compile(pattern_Email);
		patternCompile_Telephone_CodePostal = Pattern.compile(pattern_Telephone_CodePostal);
		patternCompile_Rue = Pattern.compile(pattern_Rue);

		if (pseudo == null || pseudo.isEmpty() || pseudo.length() > 30
				|| !patternCompile_Pseudo.matcher(pseudo).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PSEUDO_INVALIDE);
		} else {
			Utilisateurs util = this.getUtilByPseudo(pseudo);
			if (util != null) {
				if (no_util != 0) {
					if (util.getNo_utilisateur() != no_util) {
						businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PSEUDO_EXISTANT);
					}
				} else {
					businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PSEUDO_EXISTANT);
				}
			}
		}

		if (nom == null || nom.isEmpty() || nom.length() > 30
				|| !patternCompile_Nom_Prenom_Ville.matcher(nom).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_NOM_INVALIDE);
		}
		if (prenom == null || prenom.isEmpty() || prenom.length() > 30
				|| !patternCompile_Nom_Prenom_Ville.matcher(prenom).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PRENOM_INVALIDE);
		}
		// TODO Tester si c'est un mail ?
		if (email == null || email.isEmpty() || email.length() > 100
				|| !patternCompile_Email.matcher(email).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_MAIL_INVALIDE);
		} else {
			Utilisateurs util = this.getUtilByEmail(email);
			if (util != null) {
				if (no_util != 0) {
					if (util.getNo_utilisateur() != no_util) {
						businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_EMAIL_EXISTANT);
					}
				} else {
					businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_EMAIL_EXISTANT);
				}
			}
		}
		// TODO Tester si il y a que des chiffres
		if (telephone.length() > 15 || !patternCompile_Telephone_CodePostal.matcher(telephone).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_TELEPHONE_INVALIDE);
		}
		if (code_postal == null || code_postal.isEmpty() || code_postal.length() > 10
				|| !patternCompile_Telephone_CodePostal.matcher(code_postal).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_CODE_POSTAL_INVALIDE);
		}
		if (rue == null || rue.isEmpty() || rue.length() > 30 || !patternCompile_Rue.matcher(rue).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_RUE_INVALIDE);
		}
		// TODO Tester si il n'y pas de chiffres
		if (ville == null || ville.isEmpty() || ville.length() > 30
				|| !patternCompile_Nom_Prenom_Ville.matcher(ville).matches()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_VILLE_INVALIDE);
		}
		if (mdp == null || mdp.isEmpty() || mdp.length() < 8) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_MDP_INVALIDE);
		}
		validerMdp(mdp, verif_mdp, businessException);
	}

	private void validerMdp(String mdp, String verif_mdp, BusinessException businessException) {
		if (verif_mdp == null || !mdp.equals(verif_mdp)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_MDPS_DIFFERENTS);
		}
	}

	public Utilisateurs verifConnexion(String identifiant, String mdp) throws BusinessException {

		BusinessException businessException = new BusinessException();
		Utilisateurs user = new Utilisateurs();

		if (identifiant == null || identifiant.isEmpty()) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PSEUDO_INVALIDE);
		} else if (mdp == null || mdp.isEmpty() || mdp.length() < 8) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_MDP_INVALIDE);
		} else {
			if (getUtilByPseudo(identifiant) != null) {
				String pseudo = identifiant;
				try {
					if (!MD5Password.testPassword(mdp, getUtilByPseudo(pseudo).getMot_de_passe())) {
						businessException.ajouterErreur(CodesResultatBLL.REGLE_CNX_UTIL_MDP_INVALIDE);
					}
				} catch (BusinessException e) {
					businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
					throw businessException;
				}
				user = getUtilByPseudo(pseudo);
			} else if (getUtilByEmail(identifiant) != null) {
				String email = identifiant;
				try {
					if (!MD5Password.testPassword(mdp, getUtilByEmail(email).getMot_de_passe())) {
						businessException.ajouterErreur(CodesResultatBLL.REGLE_CNX_UTIL_MDP_INVALIDE);
					}
				} catch (BusinessException e) {
					businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
					throw businessException;
				}
				user = getUtilByEmail(email);
			} else {
				businessException.ajouterErreur(CodesResultatBLL.REGLE_CNX_UTIL_MDP_INVALIDE);
			}
		}

		if (businessException.hasErreurs()) {
			throw businessException;
		}
		return user;
	}

	public Utilisateurs getUtilByPseudo(String pseudo) throws BusinessException {
		Utilisateurs util = null;
		util = this.utilisateursDAO.getUtilByPseudo(pseudo);
		return util;
	}

	public Utilisateurs getUtilByEmail(String email) throws BusinessException {
		Utilisateurs util = null;
		util = this.utilisateursDAO.getUtilByEmail(email);
		return util;
	}

	public Utilisateurs getUtilByNoUtil(int no_util) throws BusinessException {
		Utilisateurs util = null;
		util = this.utilisateursDAO.getUtilByNoUtil(no_util);
		return util;
	}

	public Utilisateurs updateUtilisateur(int no_utilisateur, String pseudo, String nom, String prenom, String email,
			String telephone, String rue, String code_postal, String ville, String mdp, String verif_mdp,
			String mdp_actuel) throws BusinessException {
		BusinessException businessException = new BusinessException();
		Utilisateurs util = null;
		Boolean mdpIdentique = false;
		util = this.utilisateursDAO.getUtilByNoUtil(no_utilisateur);

		pseudo = pseudo.trim();
		nom = nom.trim();
		prenom = prenom.trim();
		email = email.trim();
		telephone = telephone.trim();
		rue = rue.trim();
		code_postal = code_postal.trim();
		ville = ville.trim();
		mdp = mdp.trim();
		verif_mdp = verif_mdp.trim();
		mdp_actuel = mdp_actuel.trim();
		try {
			mdpIdentique = MD5Password.testPassword(mdp_actuel, util.getMot_de_passe());
		} catch (BusinessException e) {
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		if (mdpIdentique) {
			if ((mdp == null || mdp.isEmpty()) && (verif_mdp.isEmpty() || verif_mdp == null)) {
				mdp = util.getMot_de_passe();
				verif_mdp = util.getMot_de_passe();
			}

			this.validerUtil(no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp,
					verif_mdp, businessException);
			if (!businessException.hasErreurs()) {
				try {
					mdp = MD5Password.getEncodedPassword(mdp);
				} catch (BusinessException e) {
					businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
					throw businessException;
				}
				util = new Utilisateurs(no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville,
						mdp, util.getCredit(), util.isAdministrateur());
				this.utilisateursDAO.update(util);
				util = this.utilisateursDAO.getUtilByNoUtil(no_utilisateur);

			} else {
				throw businessException;
			}

		} else {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_MDP_ACTUEL_INVALIDE);
			throw businessException;
		}

		return util;
	}

	public void deleteUtilisateur(int no_util) throws BusinessException {
		if(!this.utilisateursDAO.isEncherisseurMax(no_util) && !this.utilisateursDAO.isVendeur(no_util)) {
			this.utilisateursDAO.delete(no_util);
		}else {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_NO_SUPPRESSION);
			throw businessException;
		}
		
	}
	
	public void ajouterCredit(int no_util, int montant) throws BusinessException{
		this.utilisateursDAO.ajouterCredit(no_util, montant);
	}
}
