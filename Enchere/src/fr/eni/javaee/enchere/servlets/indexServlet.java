package fr.eni.javaee.enchere.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bll.ArticlesVendusManager;
import fr.eni.javaee.enchere.bll.CategoriesManager;
import fr.eni.javaee.enchere.bll.EncheresManager;
import fr.eni.javaee.enchere.bo.Encheres;

/**
 * Servlet implementation class indexServlet
 */
@WebServlet(
        urlPatterns= {""})
public class indexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public indexServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Affiche la page d'accueil ainsi que les enchères
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriesManager cm = new CategoriesManager();
		EncheresManager em = new EncheresManager();
		ArticlesVendusManager articleManager = new ArticlesVendusManager();
		try {
			articleManager.updateArticleRemporte();
			Object paramSessionErreur = request.getSession().getAttribute("ListeErreurAfficherEnchere");
			if(paramSessionErreur != null) {
				List<Integer> listeErreurs = (List<Integer>) paramSessionErreur;
				request.setAttribute("listeCodesErreur", listeErreurs);
				request.getSession().removeAttribute("ListeErreurAfficherEnchere");
			}
			request.setAttribute("listCat", cm.getAllCategories());
			request.setAttribute("listEnch", em.getAllEncheres());
		
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
