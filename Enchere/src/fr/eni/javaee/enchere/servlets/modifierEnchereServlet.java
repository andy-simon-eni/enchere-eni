package fr.eni.javaee.enchere.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bll.ArticlesVendusManager;
import fr.eni.javaee.enchere.bll.CategoriesManager;
import fr.eni.javaee.enchere.bll.EncheresManager;
import fr.eni.javaee.enchere.bll.UtilisateursManager;
import fr.eni.javaee.enchere.bo.ArticlesVendus;
import fr.eni.javaee.enchere.bo.Encheres;

/**
 * Servlet implementation class modifierEnchere
 */
@WebServlet(urlPatterns = { "/modifierEnchere" })
public class modifierEnchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public modifierEnchereServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoriesManager cm = new CategoriesManager();
		EncheresManager encheresManager = new EncheresManager();
		UtilisateursManager utilManager = new UtilisateursManager();
		Encheres uneEnchere = null;
		Boolean showInfo = true, detailVendeur = true;
		String paramNoArticle = request.getParameter("noArt");
		if (paramNoArticle == null) {
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			try {
				int no_article = Integer.parseInt(paramNoArticle);
				ArticlesVendus article = null;
				ArticlesVendusManager articlesManager = new ArticlesVendusManager();

				article = articlesManager.getArticleByNoArticle(no_article);
				if(article != null) {
					request.setAttribute("noArticle", article.getNo_article());
					request.setAttribute("nomArticle", article.getNom_article());
					request.setAttribute("description", article.getDescription());
					request.setAttribute("noCateg", article.getCategorie().getNo_categorie());
					request.setAttribute("prix", article.getPrix_initial());
					request.setAttribute("dateDebut", article.getDate_debut());
					request.setAttribute("dateFin", article.getDate_fin());
					request.setAttribute("rue", article.getRetrait().getRue());
					request.setAttribute("codePostal", article.getRetrait().getCode_postal());
					request.setAttribute("ville", article.getRetrait().getVille());
					request.setAttribute("listCat", cm.getAllCategories());
					LocalDate dateActuelle = LocalDate.now();
					String url;
					if(article.getDate_debut().isAfter(dateActuelle)) {
						url = "/WEB-INF/creation_enchere.jsp";
						HttpSession session = request.getSession();
						int no_util = (int)session.getAttribute("id");
						if(article.getUtil().getNo_utilisateur() != no_util) {
							url = "/WEB-INF/detailEnchere.jsp";
							showInfo = false;
							uneEnchere = encheresManager.getInfosMaxEnchereByNoArticle(article.getNo_article());
							if(uneEnchere != null) {
								request.setAttribute("montantMax", uneEnchere.getMontant_enchere());
								request.setAttribute("PseudoMontantMax", uneEnchere.getNo_utilisateur().getPseudo());
							}
							request.setAttribute("categorie", article.getCategorie().getLibelle());
							request.setAttribute("pseudoVendeur", article.getUtil().getPseudo());
						}
						
					}else if(article.getDate_fin().isBefore(dateActuelle)){
						url = "/WEB-INF/detailEnchereRemporte.jsp";
						HttpSession session = request.getSession();
						int no_util = (int)session.getAttribute("id");
						uneEnchere = encheresManager.getInfosMaxEnchereByNoArticle(article.getNo_article());
						if(uneEnchere != null) {
							request.setAttribute("montantMax", uneEnchere.getMontant_enchere());
							request.setAttribute("PseudoMontantMax", uneEnchere.getNo_utilisateur().getPseudo());
						}
						request.setAttribute("categorie", article.getCategorie().getLibelle());
						request.setAttribute("pseudoVendeur", article.getUtil().getPseudo());
						if(article.getUtil().getNo_utilisateur() != no_util) {
							detailVendeur = false;
							
							request.setAttribute("telVendeur", article.getUtil().getTelephone());
							request.setAttribute("mailVendeur", article.getUtil().getEmail());
						}
					}else {
						url = "/WEB-INF/detailEnchere.jsp";
						uneEnchere = encheresManager.getInfosMaxEnchereByNoArticle(article.getNo_article());
						if(uneEnchere != null) {
							request.setAttribute("montantMax", uneEnchere.getMontant_enchere());
							request.setAttribute("PseudoMontantMax", uneEnchere.getNo_utilisateur().getPseudo());
						}
						request.setAttribute("categorie", article.getCategorie().getLibelle());
						request.setAttribute("pseudoVendeur", article.getUtil().getPseudo());
					}
					request.setAttribute("showInfo", showInfo);
					request.setAttribute("detailVendeur", detailVendeur);
					RequestDispatcher rd = request.getRequestDispatcher(url);
					rd.forward(request, response);
					
				}else {
					response.sendRedirect(request.getContextPath() + "/");
				}
				
			} catch (BusinessException e) {
				request.getSession().setAttribute("ListeErreurAfficherEnchere", e.getListeCodesErreur());
				response.sendRedirect(request.getContextPath() + "/");				
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nomArticle,description,codePostal,ville,rue;
		LocalDate dateDebut, dateFin;
		int noArticle, noCateg, prixInitial;
		boolean valide = true;
		request.setCharacterEncoding("UTF-8");
		noArticle = Integer.parseInt(request.getParameter("noArticle"));
		nomArticle = request.getParameter("article");
		description = request.getParameter("description");
		dateDebut = LocalDate.parse(request.getParameter("dateDebut"));
		dateFin = LocalDate.parse(request.getParameter("dateFin"));
		prixInitial = Integer.parseInt(request.getParameter("prix"));
		rue = request.getParameter("rue");
		codePostal = request.getParameter("codePostal");
		ville = request.getParameter("ville");
		noCateg = Integer.parseInt(request.getParameter("categorie"));
		ArticlesVendusManager articlesManager = new ArticlesVendusManager();
		try {
			HttpSession session = request.getSession();
			int no_util = (int)session.getAttribute("id");
			articlesManager.updateArticleVendu(noArticle, nomArticle, description, dateDebut, dateFin, prixInitial, no_util, noCateg, rue, codePostal, ville);
		} catch (BusinessException e) {
			valide = false;
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			request.setAttribute("noArticle", noArticle);
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebut", dateDebut);
			request.setAttribute("dateFin", dateFin);
			request.setAttribute("prixInitial", prixInitial);
			request.setAttribute("rue", rue);
			request.setAttribute("codePostal", codePostal);
			request.setAttribute("ville", ville);
			request.setAttribute("noCateg", noCateg);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(valide) {
			response.sendRedirect(request.getContextPath() + "/");
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creation_enchere.jsp");
			rd.forward(request, response);
		}
	}

}
