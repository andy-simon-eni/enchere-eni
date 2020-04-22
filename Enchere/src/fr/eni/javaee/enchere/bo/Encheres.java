package fr.eni.javaee.enchere.bo;

import java.time.LocalDate;

public class Encheres {

	private int no_utilisateur;
	private int no_article;
	private LocalDate date_enchere;
	private int montant_enchere;
	
	public Encheres(int no_article, LocalDate date_enchere, int montant_enchere) {
		this.no_article = no_article;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
	}
	
	public Encheres(int no_utilisateur, int no_article, LocalDate date_enchere, int montant_enchere) {
		super();
		this.no_utilisateur = no_utilisateur;
		this.no_article = no_article;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
	}
	
	public Encheres() {
	}

	public int getNo_utilisateur() {
		return no_utilisateur;
	}

	public void setNo_utilisateur(int no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Encheres [no_utilisateur=");
		builder.append(no_article);
		builder.append(", date_enchere=");
		builder.append(date_enchere);
		builder.append(", montant_enchere=");
		builder.append(montant_enchere);
		builder.append("]");
		return  builder.toString();
	}
	
	
	
}
