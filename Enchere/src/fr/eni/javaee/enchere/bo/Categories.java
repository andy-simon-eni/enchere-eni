package fr.eni.javaee.enchere.bo;

public class Categories {

	private int no_categorie;
	private String libelle;
	
	public Categories(int no_categorie, String libelle) {
		super();
		this.no_categorie = no_categorie;
		this.libelle = libelle;
	}
	
	public Categories(String libelle) {
		this.libelle = libelle;
	}
	public Categories() {
	}

	public int getNo_categorie() {
		return no_categorie;
	}

	public void setNo_categorie(int no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Catégories [no_categorie=");
		builder.append(no_categorie);
		builder.append(", libellé=");
		builder.append(libelle);
		builder.append("]");
		return builder.toString();
	}
	
}
