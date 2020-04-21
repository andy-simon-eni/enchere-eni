package fr.eni.javaee.enchere.bo;

import java.time.LocalDate;

public class ArticlesVendus {
	private int no_article;
	private String nom_article;
	private String description;
	private LocalDate date_debut;
	private LocalDate date_fin;
	private int prix_initial;
	private int prix_vente;
	private Utilisateurs util;
	private Categories categorie;
	private Retraits retrait;
	
	public ArticlesVendus(int no_article, String nom_article, String description, LocalDate date_debut,
			LocalDate date_fin, int prix_initial, int prix_vente, Utilisateurs util, Categories categorie, Retraits retrait) {
		this(nom_article, description, date_debut, date_fin, prix_initial, prix_vente, util, categorie, retrait);
		this.no_article = no_article;
	}
	
	public ArticlesVendus() {
		super();
	}

	public ArticlesVendus(String nom_article, String description, LocalDate date_debut, LocalDate date_fin,
			int prix_initial, int prix_vente, Utilisateurs util, Categories categorie, Retraits retrait) {
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.util = util;
		this.categorie = categorie;
		this.retrait = retrait;
	}

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public String getNom_article() {
		return nom_article;
	}

	public void setNom_article(String nom_article) {
		this.nom_article = nom_article;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(LocalDate date_debut) {
		this.date_debut = date_debut;
	}

	public LocalDate getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(LocalDate date_fin) {
		this.date_fin = date_fin;
	}

	public int getPrix_initial() {
		return prix_initial;
	}

	public void setPrix_initial(int prix_initial) {
		this.prix_initial = prix_initial;
	}

	public int getPrix_vente() {
		return prix_vente;
	}

	public void setPrix_vente(int prix_vente) {
		this.prix_vente = prix_vente;
	}

	public Utilisateurs getUtil() {
		return util;
	}

	public void setUtil(Utilisateurs util) {
		this.util = util;
	}

	public Categories getCategorie() {
		return categorie;
	}

	public void setCategorie(Categories categorie) {
		this.categorie = categorie;
	}

	public Retraits getRetrait() {
		return retrait;
	}

	public void setRetrait(Retraits retrait) {
		this.retrait = retrait;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Articles_vendus [no_article=");
		builder.append(no_article);
		builder.append(", nom_article=");
		builder.append(nom_article);
		builder.append(", description=");
		builder.append(description);
		builder.append(", date_debut=");
		builder.append(date_debut);
		builder.append(", date_fin=");
		builder.append(date_fin);
		builder.append(", prix_initial=");
		builder.append(prix_initial);
		builder.append(", prix_vente=");
		builder.append(prix_vente);
		builder.append(", util=");
		builder.append(util);
		builder.append(", categorie=");
		builder.append(categorie);
		builder.append(", retrait=");
		//builder.append(retrait);
		builder.append("]");
		return builder.toString();
	}
	
}
