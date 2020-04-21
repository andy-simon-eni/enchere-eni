package fr.eni.javaee.enchere.bo;

import java.time.LocalDate;

public class Encheres {
	
	private Utilisateurs no_utilisateur;
	private ArticlesVendus no_article;
	private LocalDate date_enchere;
	private int montant_enchere;
	
	
	
	public Encheres(Utilisateurs no_utilisateur, ArticlesVendus no_article, LocalDate date_enchere,
			int montant_enchere) {
		super();
		this.no_utilisateur = no_utilisateur;
		this.no_article = no_article;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
	}

	public Encheres(LocalDate date_enchere,
			int montant_enchere) {
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
	}

	public Encheres() {
		// TODO Auto-generated constructor stub
	}

	public LocalDate getDate_enchere() {
		return date_enchere;
	}

	public void setDate_enchere(LocalDate date_enchere) {
		this.date_enchere = date_enchere;
	}

	public int getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public Utilisateurs getNo_utilisateur() {
		return no_utilisateur;
	}

	public void setNo_utilisateur(Utilisateurs no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	public ArticlesVendus getNo_article() {
		return no_article;
	}

	public void setNo_article(ArticlesVendus no_article) {
		this.no_article = no_article;
	}
	
	

	
	
	
	
}
