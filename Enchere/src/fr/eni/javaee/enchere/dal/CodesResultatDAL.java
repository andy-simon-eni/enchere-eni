package fr.eni.javaee.enchere.dal;

public abstract class CodesResultatDAL {
	/**
	 * Echec de l'insert
	 */
	public static final int INSERT_OBJET_ECHEC=10000;
	/**
	 * Echec du select
	 */
	public static final int SELECT_OBJET_ECHEC=10001;
	/**
	 * Echec du delete
	 */
	public static final int DELETE_OBJET_ECHEC=10002;
	/**
	 * Echec du update
	 */
	public static final int UPDATE_OBJET_ECHEC=10003;
	/**
	 * Echec du select all
	 */
	public static final int SELECT_ALL_ECHEC=10004;
	
}
