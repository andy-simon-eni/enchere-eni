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
	/**
	 * Echec mdp ou identifiant incorrect
	 */
	public static final int REGLE_CNX_UTIL_MDP_INVALIDE = 30011;
	/**
	 * mdp actuel incorrect lors de la modification de compte
	 */
	public static final int REGLE_UTIL_MDP_ACTUEL_INVALIDE = 40000;
	/**
	 * Nom de l'articles_vendus non valide
	 */
	public static final int REGLE_ARTICLES_VENDUS_NOM_INVALIDE = 50000;
	/**
	 * Description de l'articles_vendus non valide
	 */
	public static final int REGLE_ARTICLES_VENDUS_DESCRIPTION_INVALIDE = 50001;
	/**
	 * Date de début d'enchère est inférieur à la date d'aujourd'hui
	 */
	public static final int REGLE_ARTICLES_VENDUS_DATE_DEBUT_INVALIDE = 50002;
	/**
	 * Date de fin d'enchère est inférieur a la date de début
	 */
	public static final int REGLE_ARTICLES_VENDUS_DATE_FIN_INVALIDE = 50003;
	/**
	 * Prix Initial inférieur à 0
	 */
	public static final int REGLE_ARTICLES_VENDUS_PRIX_INITIAL_INVALIDE = 50004;
	/**
	 * Utilisateur invalide
	 */
	public static final int REGLE_ARTICLES_VENDUS_UTIL_INVALIDE = 50005;
	/**
	 * Catégorie Invalide
	 */
	public static final int REGLE_ARTICLES_VENDUS_CATEG_INVALIDE = 50006;
	/**
	 * Echec la rue de retrait n'est pas valide
	 */
	public static final int REGLE_ARTICLES_VENDUS_RUE_INVALIDE = 20008;
	/**
	 * Echec le code postal de retrait n'est pas valide
	 */
	public static final int REGLE_ARTICLES_VENDUS_CODE_POSTAL_INVALIDE = 20006;
	/**
	 * Echec la ville de retrait n'est pas valide
	 */
	public static final int REGLE_ARTICLES_VENDUS_VILLE_INVALIDE = 20007;

}
