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
 * Servlet implementation class signInServlet
 */
@WebServlet(
        urlPatterns= {"/signIn"})
public class signInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Permet d'afficher la page de connexion avec les cookies si ils existent
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Cookie[] cookies = request.getCookies();
	        if(cookies != null) {
	        	for(Cookie ck : cookies) {
	        		if(ck.getName().equals("identifiant")) {
	        			request.setAttribute("identifiant", ck.getValue());
	        		}
	        	}
	        }   
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sign_in.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Permet de se connecter
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identifiant, mdp;
		request.setCharacterEncoding("UTF-8");
		boolean success = true;
		identifiant = request.getParameter("identifiant");
		mdp = request.getParameter("mdp");
		
		UtilisateursManager utilManager = new UtilisateursManager();
		HttpSession session = request.getSession();
		
		try {
			Utilisateurs util = utilManager.verifConnexion(identifiant, mdp);
		
			session.setAttribute("id", util.getNo_utilisateur());
		    session.setAttribute("nom", util.getNom());
		    session.setAttribute("prenom", util.getPrenom());
		    if(request.getParameter("cbRemenber_id") != null) {
			    	Cookie cookie = new Cookie("identifiant", "");
			    	cookie.setMaxAge(0);
			    	response.addCookie(cookie);
		    	
			       Cookie cookie2 = new Cookie("identifiant",identifiant);
			       cookie2.setHttpOnly(true);
			       cookie2.setMaxAge(7 * 24 * 60 * 60);
			       response.addCookie(cookie2);
		    } 
		} catch(BusinessException e) {
			success = false;
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			
		}
		 if(success) {
			 response.sendRedirect(request.getContextPath() + "/");   
		 }else {
			 RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sign_in.jsp");
			 rd.forward(request, response);
		 }
//		
//		if(utilManager.getUtilByPseudo(identifiant) != null) {
//			if(identifiant.equals(utilManager.getUtilByPseudo(identifiant).getPseudo()) && mdp.equals(utilManager.getUtilByPseudo(identifiant).getMot_de_passe())){
//	
//		        session.setAttribute("id", utilManager.getUtilByPseudo(identifiant).getNo_utilisateur());
//		        session.setAttribute("nom", utilManager.getUtilByPseudo(identifiant).getNom());
//		        session.setAttribute("prenom", utilManager.getUtilByPseudo(identifiant).getPrenom());
//		   
//		        if(request.getParameter("cbRemenber_id") != null) {
//			        Cookie cookie = new Cookie("identifiant",identifiant);
//			        cookie.setHttpOnly(true);
//			        cookie.setMaxAge(7 * 24 * 60 * 60);
//			        response.addCookie(cookie);
//		        }
//		 
//		        response.sendRedirect(request.getContextPath() + "/accueil");   
//
//			}
//		}
//		else if(utilManager.getUtilByEmail(identifiant) != null) {
//			if(identifiant.equals(utilManager.getUtilByEmail(identifiant).getEmail()) && mdp.equals(utilManager.getUtilByEmail(identifiant).getMot_de_passe())) {
//	
//		        session.setAttribute("id", utilManager.getUtilByEmail(identifiant).getNo_utilisateur());
//		        session.setAttribute("nom", utilManager.getUtilByEmail(identifiant).getNom());
//		        session.setAttribute("prenom", utilManager.getUtilByEmail(identifiant).getPrenom());
//		        response.sendRedirect(request.getContextPath() + "/accueil");
//			}
//		}else {
//			request.setAttribute("erreur","Erreur de saisie" );
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sign_in.jsp");
//			rd.forward(request, response); 
//		}
	}
}


