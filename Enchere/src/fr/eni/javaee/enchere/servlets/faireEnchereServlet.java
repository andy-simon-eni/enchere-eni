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
import fr.eni.javaee.enchere.bll.EncheresManager;
import fr.eni.javaee.enchere.bll.UtilisateursManager;
import fr.eni.javaee.enchere.bo.ArticlesVendus;
import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.bo.Utilisateurs;

/**
 * Servlet implementation class faireEnchereServlet
 */
@WebServlet(
        urlPatterns= {"/faire_enchere"})
public class faireEnchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public faireEnchereServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int proposition, no_article;
		request.setCharacterEncoding("UTF-8");
		boolean valide = true;
		ArticlesVendus article = null;
		
		no_article = Integer.parseInt(request.getParameter("noArticle"));
		proposition = Integer.parseInt(request.getParameter("proposition"));

		EncheresManager enchereManager = new EncheresManager();
		UtilisateursManager utilManager = new UtilisateursManager();
		ArticlesVendusManager articleManager = new ArticlesVendusManager();
		try {
			HttpSession session = request.getSession();
			int no_util = (int) session.getAttribute("id");
			enchereManager.insertEnchere(no_util, no_article, proposition);
		} catch (BusinessException e) {
			valide = false;
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		}

		if (valide) {
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			try {
				article = articleManager.getArticleByNoArticle(no_article);
				Encheres uneEnchere;
				uneEnchere = enchereManager.getInfosMaxEnchereByNoArticle(no_article);
				request.setAttribute("noArticle", request.getParameter("noArticle"));
				request.setAttribute("nomArticle", article.getNom_article());
				request.setAttribute("description", article.getDescription());
				request.setAttribute("categorie", article.getCategorie().getLibelle());
				if(uneEnchere != null) {
					request.setAttribute("montantMax", uneEnchere.getMontant_enchere());
					request.setAttribute("PseudoMontantMax", utilManager.getUtilByNoUtil(uneEnchere.getNo_utilisateur()).getPseudo());
				}
				request.setAttribute("prix", article.getPrix_initial());
				request.setAttribute("dateFin", article.getDate_fin());
				request.setAttribute("rue", article.getRetrait().getRue());
				request.setAttribute("codePostal", article.getRetrait().getCode_postal());
				request.setAttribute("ville", article.getRetrait().getVille());
				request.setAttribute("pseudoVendeur", article.getUtil().getPseudo());
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detailEnchere.jsp");
				rd.forward(request, response);
			} catch (BusinessException e) {
				response.sendRedirect(request.getContextPath() + "/");
			}
		}
	}

}
