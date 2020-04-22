package fr.eni.javaee.enchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bll.CategoriesManager;
import fr.eni.javaee.enchere.bll.EncheresManager;

/**
 * Servlet implementation class rechercheMotCleServlet
 */
@WebServlet("/rechercheMotCleServlet")
public class rechercheMotCleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public rechercheMotCleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String motCle;
		CategoriesManager cm = new CategoriesManager();
		EncheresManager em = new EncheresManager();
		motCle = request.getParameter("motCle");
		try {
			System.out.println("15646");
			request.setAttribute("listCat", cm.getAllCategories());
			request.setAttribute("listEnch", em.getEncheresByMotCle(motCle));
		
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/toto");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
