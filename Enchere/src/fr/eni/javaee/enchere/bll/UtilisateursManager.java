package fr.eni.javaee.enchere.bll;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Utilisateurs;
import fr.eni.javaee.enchere.dal.DAOFactory;
import fr.eni.javaee.enchere.dal.UtilisateursDAO;

public class UtilisateursManager {
	private UtilisateursDAO utilisateursDAO;

	public UtilisateursManager() {
		this.utilisateursDAO = DAOFactory.getUtilisateursDAO();
	}

	public void insertUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String code_postal, String ville, String mdp, String verif_mdp) throws BusinessException {
		BusinessException businessException = new BusinessException();
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

		this.validerUtil(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp, verif_mdp,
				businessException);
		if (!businessException.hasErreurs()) {

			Utilisateurs util = new Utilisateurs(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp, 0,
					false);
			this.utilisateursDAO.insert(util);

		} else {
			throw businessException;
		}

	}

	private void validerUtil(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String code_postal, String ville, String mdp, String verif_mdp, BusinessException businessException)
			throws BusinessException {
		Boolean valide = true;
		// TODO pour tous les champs autre que mdp et pseudo, tester si il n'y a pas des
		// caract�res sp�ciaux
		// TODO g�rer les erreurs perso
		if (pseudo == null || pseudo.isEmpty() || pseudo.length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PSEUDO_INVALIDE);
		} else {
			Utilisateurs util = this.getUtilByPseudo(pseudo);
			if (util != null) {
				businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PSEUDO_EXISTANT);
			}
		}

		if (nom == null || nom.isEmpty() || nom.length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_NOM_INVALIDE);
		}
		if (prenom == null || prenom.isEmpty() || prenom.length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PRENOM_INVALIDE);
		}
		// TODO Tester si c'est un mail ?
		if (email == null || email.isEmpty() || email.length() > 20) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_MAIL_INVALIDE);
		}
		// TODO Tester si il y a que des chiffres
		if (telephone == null || telephone.isEmpty() || telephone.length() > 15) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_TELEPHONE_INVALIDE);
		}
		if (code_postal == null || code_postal.isEmpty() || code_postal.length() > 10) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_CODE_POSTAL_INVALIDE);
		}
		if (rue == null || rue.isEmpty() || rue.length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_RUE_INVALIDE);
		}
		// TODO Tester si il n'y pas de chiffres
		if (ville == null || ville.isEmpty() || ville.length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_VILLE_INVALIDE);
		}
		if (mdp == null || mdp.isEmpty() || mdp.length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_MDP_INVALIDE);
		}
		if (verif_mdp == null || verif_mdp.isEmpty() || verif_mdp.length() > 30) {
			valide = false;
		}
		validerMdp(mdp, verif_mdp, businessException);
	}

	private void validerMdp(String mdp, String verif_mdp, BusinessException businessException) {
		if (verif_mdp == null || !mdp.equals(verif_mdp)) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_MDPS_DIFFERENTS);
		}
	}

	public Utilisateurs getUtilByPseudo(String pseudo) throws BusinessException {
		Utilisateurs util = null;
		util = this.utilisateursDAO.getUtilByPseudo(pseudo);
		return util;
	}

}
