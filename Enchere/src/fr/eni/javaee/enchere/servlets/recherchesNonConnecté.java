package fr.eni.javaee.enchere.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bll.CategoriesManager;
import fr.eni.javaee.enchere.bll.EncheresManager;
import fr.eni.javaee.enchere.bo.Encheres;

/**
 * Servlet implementation class rechercheMotCleServlet
 */
@WebServlet("/recherchesNonConnecté")
public class recherchesNonConnecté extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public recherchesNonConnecté() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager em = new EncheresManager();
		int codeCat = Integer.parseInt(request.getParameter("codeCat"));
		List<Encheres> listEnch = null;
			try {
				if(codeCat==0) {
					listEnch =em.getAllEncheres();
				}else {
					listEnch = em.getEncheresByCategorie(codeCat);
				}	
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		
		for(Encheres ench : listEnch) {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
			objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
			objectBuilder.add("montant", ench.getMontant_enchere());
			objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
			objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
			objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
	
			arrayBuilder.add(objectBuilder);
		}
		response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(arrayBuilder.build().toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager em = new EncheresManager();
		
		String motCle = request.getParameter("motCle");
		List<Encheres> listEnch = null;
		try {
			listEnch = em.getEncheresByMotCle(motCle);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		
		for(Encheres ench : listEnch) {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
			objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
			objectBuilder.add("montant", ench.getMontant_enchere());
			objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
			objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
			objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
	
			arrayBuilder.add(objectBuilder);
		}
		response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(arrayBuilder.build().toString());
				 
	}

}
