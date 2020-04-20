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
import fr.eni.javaee.enchere.bll.UtilisateursManager;
import fr.eni.javaee.enchere.bo.ArticlesVendus;
import fr.eni.javaee.enchere.bo.Utilisateurs;

/**
 * Servlet implementation class creationEnchereServlet
 */
@WebServlet(
        urlPatterns= {"/creation_enchere"})
public class creationEnchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public creationEnchereServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriesManager cm = new CategoriesManager();
		try {
			
			request.setAttribute("listCat", cm.getAllCategories());
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creation_enchere.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomArticle,description,codePostal,ville,rue;
		LocalDate dateDebut, dateFin;
		int noCateg, prixInitial;
		boolean valide = true;
		request.setCharacterEncoding("UTF-8");
		ArticlesVendus article = null;
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
			article = articlesManager.insertArticleVendu(nomArticle, description, dateDebut, dateFin, prixInitial, (int)session.getAttribute("id"), noCateg, rue, codePostal, ville);
		} catch (BusinessException e) {
			valide = false;
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("dateDebut", dateDebut);
			request.setAttribute("dateFin", dateFin);
			request.setAttribute("prixInitial", prixInitial);
			request.setAttribute("rue", rue);
			request.setAttribute("codePostal", codePostal);
			request.setAttribute("ville", ville);
			request.setAttribute("noCateg", noCateg);
		}		
		
		if(valide) {
			response.sendRedirect(request.getContextPath() + "/");
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sign_up.jsp");
			rd.forward(request, response);
		}		
	}

}
