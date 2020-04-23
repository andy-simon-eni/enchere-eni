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
        urlPatterns= {"/profil"})
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
		boolean sessionExiste = false, showInfo = false;
		String tel = "Non renseigné", pseudo = null;
		Utilisateurs util = null;
		UtilisateursManager utilManager = new UtilisateursManager();
		
		try {
			HttpSession session = request.getSession();
			if(session.getAttribute("id") != null) {
				sessionExiste = true;
				if(request.getParameter("pseudo") != null) {
					pseudo = request.getParameter("pseudo");
					util = utilManager.getUtilByPseudo(pseudo);
				}else {
					no_util = (int)session.getAttribute("id");
					util = utilManager.getUtilByNoUtil(no_util);
					showInfo = true;
				}
				
				if(util != null) {
					request.setAttribute("pseudo", util.getPseudo());
					request.setAttribute("nom", util.getNom());
					request.setAttribute("prenom", util.getPrenom());
					request.setAttribute("email", util.getEmail());
					if(util.getTelephone() != null && !util.getTelephone().isEmpty()) {
						tel = util.getTelephone();
					}
					request.setAttribute("telephone", tel);
					request.setAttribute("rue", util.getRue());
					request.setAttribute("code_postal", util.getCode_postal());
					request.setAttribute("ville", util.getVille());
					request.setAttribute("credits", String.valueOf(util.getCredit()));
					request.setAttribute("showInfo", showInfo);
				}else {
					sessionExiste = false;
				}
			}
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		}
		if(!sessionExiste) {
			response.sendRedirect(request.getContextPath() + "/");
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/mon_compte.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
