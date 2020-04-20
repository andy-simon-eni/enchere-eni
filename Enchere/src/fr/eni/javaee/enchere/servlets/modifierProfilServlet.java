package fr.eni.javaee.enchere.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bll.UtilisateursManager;
import fr.eni.javaee.enchere.bo.Utilisateurs;

/**
 * Servlet implementation class modifierProfilServlet
 */
@WebServlet(
        urlPatterns= {"/modifier_profil"})
public class modifierProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modifierProfilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no_util = 0;
		boolean sessionExiste = false;
		Utilisateurs util = null;
		UtilisateursManager utilManager = new UtilisateursManager();
		
		try {
			HttpSession session = request.getSession();
			if(session.getAttribute("id") != null) {
				sessionExiste = true;
				no_util = (int)session.getAttribute("id");
				util = utilManager.getUtilByNoUtil(no_util);
				
				if(util != null) {
					request.setAttribute("pseudo", util.getPseudo());
					request.setAttribute("nom", util.getNom());
					request.setAttribute("prenom", util.getPrenom());
					request.setAttribute("email", util.getEmail());
					request.setAttribute("telephone", util.getTelephone());
					request.setAttribute("rue", util.getRue());
					request.setAttribute("code_postal", util.getCode_postal());
					request.setAttribute("ville", util.getVille());
					request.setAttribute("credits", String.valueOf(util.getCredit()));
				}
			}
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		}
		if(!sessionExiste) {
			response.sendRedirect(request.getContextPath() + "/");
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifier_profil.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mdp,verif_mdp, mdp_actuel, resultButton;
		int no_util = 0;
		boolean valide = true, sessionExiste = false;
		Utilisateurs util = null;
		request.setCharacterEncoding("UTF-8");
		UtilisateursManager utilManager = new UtilisateursManager();
		
		resultButton = request.getParameter("button");
		
		HttpSession session = request.getSession();
		if(session.getAttribute("id") != null) {
			sessionExiste = true;
			no_util = (int)session.getAttribute("id");
		}
		
		if(sessionExiste) {
			if(resultButton.equals("update")) {
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
				mdp_actuel = request.getParameter("mdp_actuel");
				
				try {
					util = utilManager.updateUtilisateur(no_util, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mdp,verif_mdp, mdp_actuel);
					session.setAttribute("nom", util.getNom());
					session.setAttribute("prenom", util.getPrenom());
				} catch (BusinessException e) {
					valide = false;
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
					response.sendRedirect(request.getContextPath() + "/profil");
				}else {
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifier_profil.jsp");
					rd.forward(request, response);
				}
			}else if(resultButton.equals("delete")) {
				try {
					utilManager.deleteUtilisateur(no_util);
				} catch (BusinessException e) {
					request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				}
				Cookie cookie = new Cookie("identifiant", "");
		    	cookie.setMaxAge(0);
		    	response.addCookie(cookie);
				response.sendRedirect(request.getContextPath() + "/logOut");
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

}
