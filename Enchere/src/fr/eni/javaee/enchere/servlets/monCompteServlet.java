package fr.eni.javaee.enchere.servlets;

import java.io.IOException;

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
 * Servlet implementation class monCompteServlet
 */
@WebServlet(
        urlPatterns= {"/mon-compte"})
public class monCompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public monCompteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no_util = 0;
		boolean sessionExiste = false;
		String pseudo, nom, prenom, email, tel, rue, cp, ville, credits, url_retour;
		Utilisateurs util = null;
		UtilisateursManager utilManager = new UtilisateursManager();
		
		pseudo = "Non renseigné";
		nom = "Non renseigné";
		prenom = "Non renseigné";
		email = "Non renseigné";
		tel = "Non renseigné";
		rue = "Non renseigné"; 
		cp = "Non renseigné";
		ville = "Non renseigné";
		credits = "Non renseigné";
		
		try {
			HttpSession session = request.getSession();
			if(session.getAttribute("id") != null) {
				sessionExiste = true;
				no_util = (int)session.getAttribute("id");
				util = utilManager.getUtilByNoUtil(no_util);
				
				if(util != null) {
					pseudo = util.getPseudo();
					nom = util.getNom();
					prenom = util.getPrenom();
					email = util.getEmail();
					if(util.getTelephone() != null && !util.getTelephone().isEmpty()) {
						tel = util.getTelephone();
					}
					rue = util.getRue();
					cp = util.getCode_postal();
					ville = util.getVille();
					credits = String.valueOf(util.getCredit());
				}
				
				request.setAttribute("pseudo", pseudo);
				request.setAttribute("nom", nom);
				request.setAttribute("prenom", prenom);
				request.setAttribute("email", email);
				request.setAttribute("telephone", tel);
				request.setAttribute("rue", rue);
				request.setAttribute("code_postal", cp);
				request.setAttribute("ville", ville);
				request.setAttribute("credits", credits);
			}
						
			
			
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		}
		if(!sessionExiste) {
			url_retour = "/WEB-INF/index.jsp";
		}else {
			url_retour = "/WEB-INF/mon_compte.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(url_retour);
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
