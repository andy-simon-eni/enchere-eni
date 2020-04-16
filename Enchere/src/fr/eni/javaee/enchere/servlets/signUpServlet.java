package fr.eni.javaee.enchere.servlets;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bll.UtilisateursManager;
import fr.eni.javaee.enchere.bo.Utilisateurs;

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
		String pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mdp,verif_mdp,url_retour;
		boolean valide = true;
		request.setCharacterEncoding("UTF-8");
		Utilisateurs util = null;
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
		try {
			util = utilManager.insertUtilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp,verif_mdp);
			HttpSession session = request.getSession();
			session.setAttribute("id", util.getNo_utilisateur());
			session.setAttribute("nom", util.getNom());
			session.setAttribute("prenom", util.getPrenom());
		} catch (BusinessException e) {
			valide = false;
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			request.setAttribute("pseudo", pseudo);
			request.setAttribute("nom", nom);
			request.setAttribute("prenom", prenom);
			request.setAttribute("email", email);
			request.setAttribute("telephone", telephone);
			request.setAttribute("rue", rue);
			request.setAttribute("code_postal", code_postal);
			request.setAttribute("ville", ville);
		}		
		
		if(valide) {
			url_retour = "bonne page";
		}else {
			url_retour = "/WEB-INF/sign_up.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url_retour);
		rd.forward(request, response);
		
	}

}
