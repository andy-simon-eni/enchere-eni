package fr.eni.javaee.enchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.enchere.bll.UtilisateursManager;

/**
 * Servlet implementation class signUpServlet
 */
@WebServlet(
        urlPatterns= {"/signUp"})
public class signUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sign_up.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mdp,verif_mdp;
		request.setCharacterEncoding("UTF-8");
		
		pseudo = request.getParameter("pseudo");
		nom = request.getParameter("nom");
		prenom = request.getParameter("prenom");
		email = request.getParameter("email");
		telephone = request.getParameter("telephone");
		rue = request.getParameter("rue");
		code_postal = request.getParameter("code_postal");
		ville = request.getParameter("ville");
		mdp = request.getParameter("mdp");
		verif_mdp = request.getParameter("verif_mdp");
		UtilisateursManager utilManager = new UtilisateursManager();
		utilManager.insertUtilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp,verif_mdp);		
		
	}

}
