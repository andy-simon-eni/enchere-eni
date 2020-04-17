package fr.eni.javaee.enchere.bll;

public abstract class CodesResultatBLL {
	/**
	 * Echec le pseudo de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_PSEUDO_INVALIDE=20000;
	/**
	 * Echec le pseudo de l'utilisateur existe
	 */
	public static final int REGLE_UTIL_PSEUDO_EXISTANT = 20001;
	/**
	 * Echec le nom de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_NOM_INVALIDE = 20002;
	/**
	 * Echec le prénom de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_PRENOM_INVALIDE = 20003;
	/**
	 * Echec le mail de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_MAIL_INVALIDE = 20004;
	/**
	 * Echec le téléphone de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_TELEPHONE_INVALIDE = 20005;
	/**
	 * Echec le code postal de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_CODE_POSTAL_INVALIDE = 20006;
	/**
	 * Echec la ville de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_VILLE_INVALIDE = 20007;
	/**
	 * Echec la rue de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_RUE_INVALIDE = 20008;
	/**
	 * Echec le mdp de l'utilisateur n'est pas valide
	 */
	public static final int REGLE_UTIL_MDP_INVALIDE = 20009;
	/**
	 * Echec les mdps ne sont pas identiques
	 */
	public static final int REGLE_UTIL_MDPS_DIFFERENTS = 20010;
	/**
	 * Echec l'email existe deja
	 */
	public static final int REGLE_UTIL_EMAIL_EXISTANT = 20011;
}
