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
			String code_postal, String ville, String mdp, String verif_mdp) throws Exception {
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
			String code_postal, String ville, String mdp, String verif_mdp, BusinessException businessException) {
		Boolean valide = true;
		// TODO pour tous les champs autre que mdp et pseudo, tester si il n'y a pas des
		// caractères spéciaux
		// TODO gérer les erreurs perso
		if (pseudo == null || pseudo.isEmpty() || pseudo.length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PSEUDO_INVALIDE);
		} else {
			Utilisateurs util = this.getUtilByPseudo(pseudo);
			if (util != null) {
				businessException.ajouterErreur(CodesResultatBLL.REGLE_UTIL_PSEUDO_EXISTANT);
			}
		}

		if (nom == null || nom.isEmpty() || nom.length() > 30) {
			valide = false;
		}
		if (prenom == null || prenom.isEmpty() || prenom.length() > 30) {
			valide = false;
		}
		// TODO Tester si c'est un mail ?
		if (email == null || email.isEmpty() || email.length() > 20) {
			valide = false;
		}
		// TODO Tester si il y a que des chiffres
		if (telephone == null || telephone.isEmpty() || telephone.length() > 15) {
			valide = false;
		}
		if (code_postal == null || code_postal.isEmpty() || code_postal.length() > 10) {
			valide = false;
		}
		// TODO Tester si il n'y pas de chiffres
		if (ville == null || ville.isEmpty() || ville.length() > 30) {
			valide = false;
		}
		if (mdp == null || mdp.isEmpty() || mdp.length() > 30) {
			valide = false;
		}
		if (verif_mdp == null || verif_mdp.isEmpty() || verif_mdp.length() > 30) {
			valide = false;
		}
		if (!validerMdp(mdp, verif_mdp)) {
			valide = false;
		}
	}

	private boolean validerMdp(String mdp, String verif_mdp) {
		Boolean valide = false;
		if (mdp.equals(verif_mdp)) {
			valide = true;
		}
		return valide;
	}

	public Utilisateurs getUtilByPseudo(String pseudo) {
		Utilisateurs util = null;
		try {
			util = this.utilisateursDAO.getUtilByPseudo(pseudo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return util;
	}

}
