package fr.eni.javaee.enchere.bo;

public class Retraits {
	private ArticlesVendus article;
	private String rue;
	private String code_postal;
	private String ville;
	
	public Retraits() {
		super();
	}

	public Retraits(ArticlesVendus article, String rue, String code_postal, String ville) {
		super();
		this.article = article;
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	public ArticlesVendus getArticle() {
		return article;
	}

	public void setArticle(ArticlesVendus article) {
		this.article = article;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Retraits [article=");
		builder.append(article);
		builder.append(", rue=");
		builder.append(rue);
		builder.append(", code_postal=");
		builder.append(code_postal);
		builder.append(", ville=");
		builder.append(ville);
		builder.append("]");
		return builder.toString();
	}

}
