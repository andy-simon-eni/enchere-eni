package fr.eni.javaee.enchere.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bll.EncheresManager;
import fr.eni.javaee.enchere.bo.Encheres;
import fr.eni.javaee.enchere.bo.Utilisateurs;

/**
 * Servlet implementation class recherchesConnecté
 */
@WebServlet("/recherchesConnecté")
public class recherchesConnecté extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public recherchesConnecté() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager em = new EncheresManager();
		HttpSession session = request.getSession();
		int user = (int) session.getAttribute("id");
		
		boolean cka1 = Boolean.parseBoolean(request.getParameter("cka1"));
		boolean cka2 = Boolean.parseBoolean(request.getParameter("cka2"));
		boolean cka3 = Boolean.parseBoolean(request.getParameter("cka3"));
		
		List<Encheres> listEnch = null;
		List<Encheres> listEnch1 = null;
		List<Encheres> listEnch2 = null;
		List<Encheres> listEnch3 = null;
		
		List<Integer> listNumArt = new ArrayList<Integer>();
		
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		
			try {
				if(cka1) {
					listEnch1 = em.getEnchereByEncheresOuvertes();
					
					for(Encheres ench : listEnch1) {
						boolean ajout = true;
						for(Integer i : listNumArt) {
							if(i ==  ench.getNo_article().getNo_article()) {
								ajout=false;
							}
						}
						if(ajout) {
							JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
							objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
							objectBuilder.add("montant", ench.getMontant_enchere());
							objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
							objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
							objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
							
							arrayBuilder.add(objectBuilder);
							
							listNumArt.add(ench.getNo_article().getNo_article());
						}	
					
					}
				} 
				if(cka2) {
					listEnch2 = em.getEnchereByMesEncheresEnCours(user);

					for(Encheres ench : listEnch2) {
						boolean ajout = true;
						for(Integer i : listNumArt) {
							if(i ==  ench.getNo_article().getNo_article()) {
								ajout=false;
							}
						}
						if(ajout) {
							JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
							objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
							objectBuilder.add("montant", ench.getMontant_enchere());
							objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
							objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
							objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
							
							arrayBuilder.add(objectBuilder);
							
							listNumArt.add(ench.getNo_article().getNo_article());
						}	
					
					}
				}
				if(cka3) {
					listEnch3 = em.getEnchereByMesEncheresRemportees(user);
					
					for(Encheres ench : listEnch3) {
						boolean ajout = true;
						for(Integer i : listNumArt) {
							if(i ==  ench.getNo_article().getNo_article()) {
								ajout=false;
							}
						}
						if(ajout) {
							JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
							objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
							objectBuilder.add("montant", ench.getMontant_enchere());
							objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
							objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
							objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
							
							arrayBuilder.add(objectBuilder);
							
							listNumArt.add(ench.getNo_article().getNo_article());
						}	
					
					}
				}
				if(!cka1 && !cka2 && !cka3) {
					listEnch = em.getAllEncheres();
					for(Encheres ench : listEnch) {
						JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
						objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
						objectBuilder.add("montant", ench.getMontant_enchere());
						objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
						objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
						objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
						
						arrayBuilder.add(objectBuilder);
					}
				}
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(arrayBuilder.build().toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager em = new EncheresManager();
		HttpSession session = request.getSession();
		int user = (int) session.getAttribute("id");
		
		boolean ckv1 = Boolean.parseBoolean(request.getParameter("ckv1"));
		boolean ckv2 = Boolean.parseBoolean(request.getParameter("ckv2"));
		boolean ckv3 = Boolean.parseBoolean(request.getParameter("ckv3"));
		
		List<Encheres> listEnch = null;
		List<Encheres> listEnch1 = null;
		List<Encheres> listEnch2 = null;
		List<Encheres> listEnch3 = null;
		
		List<Integer> listNumArt = new ArrayList<Integer>();
		
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		
			try {
				if(ckv1) {
					listEnch1 = em.getEnchereByMesVentesnNonDebutes(user);
					
					for(Encheres ench : listEnch1) {
						boolean ajout = true;
						for(Integer i : listNumArt) {
							if(i ==  ench.getNo_article().getNo_article()) {
								ajout=false;
							}
						}
						if(ajout) {
							JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
							objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
							objectBuilder.add("montant", ench.getMontant_enchere());
							objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
							objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
							objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
							
							arrayBuilder.add(objectBuilder);
							
							listNumArt.add(ench.getNo_article().getNo_article());
						}	
					}
				} 
				if(ckv2) {
					listEnch2 = em.getEnchereByMesVentesEnCours(user);

					for(Encheres ench : listEnch2) {
						boolean ajout = true;
						for(Integer i : listNumArt) {
							if(i ==  ench.getNo_article().getNo_article()) {
								ajout=false;
							}
						}
						if(ajout) {
							JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
							objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
							objectBuilder.add("montant", ench.getMontant_enchere());
							objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
							objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
							objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
							
							arrayBuilder.add(objectBuilder);
							
							listNumArt.add(ench.getNo_article().getNo_article());
						}	
					
					}
				}
				if(ckv3) {
					listEnch3 = em.getEnchereByMesVentesTerminees(user);
					
					for(Encheres ench : listEnch3) {
						boolean ajout = true;
						for(Integer i : listNumArt) {
							if(i ==  ench.getNo_article().getNo_article()) {
								ajout=false;
							}
						}
						if(ajout) {
							JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
							objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
							objectBuilder.add("montant", ench.getMontant_enchere());
							objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
							objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
							objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
							
							arrayBuilder.add(objectBuilder);
							
							listNumArt.add(ench.getNo_article().getNo_article());
						}	
					
					}
				}
				if(ckv1 == false && ckv2==false && ckv3==false) {
					listEnch = em.getAllEncheres();
					for(Encheres ench : listEnch) {
						JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
						objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
						objectBuilder.add("montant", ench.getMontant_enchere());
						objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
						objectBuilder.add("nomUtil", ench.getNo_utilisateur().getNom());
						objectBuilder.add("prenomUtil", ench.getNo_utilisateur().getPrenom());
						
						arrayBuilder.add(objectBuilder);
					}
				}
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(arrayBuilder.build().toString());
	}

}
