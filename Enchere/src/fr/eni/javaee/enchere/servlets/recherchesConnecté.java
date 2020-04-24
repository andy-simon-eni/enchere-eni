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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EncheresManager em = new EncheresManager();
		List<Encheres> listEnch = null;
		String search;
		int idCateg;

		HttpSession session = request.getSession();
		int user = (int) session.getAttribute("id");
		
		idCateg = Integer.parseInt(request.getParameter("categorie"));
		search = request.getParameter("search");

		try {
			switch (search) {
			case "Achat":
				if(idCateg != 0) {
					listEnch = em.getEncheresByCategorie(idCateg);
				}else {
					listEnch = em.getAllEncheres();
				}
				break;
			case "Vente":
				if(idCateg != 0) {
					listEnch = em.getMesEncheresByCategorie(user, idCateg);
				}else {
					listEnch = em.getMesEncheres(user);
				}
				break;
			case "aOuverte":
				if(idCateg != 0) {
					listEnch = em.getEncheresOuvertesByCategorie(idCateg);
				}else {
					listEnch = em.getEncheresOuvertes();
				}
				break;
			case "aEnCours":
				if(idCateg != 0) {
					listEnch = em.getEncheresEnCoursByCategorie(user, idCateg);
				}else {
					listEnch = em.getEncheresEnCours(user);
				}
				break;
			case "aRemporte":
				if(idCateg != 0) {
					listEnch = em.getMesEncheresGagneesByCategorie(user, idCateg);
				}else {
					listEnch = em.getMesEncheresGagnees(user);
				}
				break;
			case "vDebute":
				if(idCateg != 0) {
					listEnch = em.getMesEncheresNonDebuteesByCategorie(user, idCateg);
					
				}else {
					listEnch = em.getMesEncheresNonDebutees(user);
				}
				break;
			case "vEnCours":
				if(idCateg != 0) {
					listEnch = em.getMesEncheresEnCoursByCategorie(user, idCateg);
				}else {
					listEnch = em.getMesEncheresEnCours(user);
				}
				break;
			case "vTermine":
				if(idCateg != 0) {
					listEnch = em.getMesEncheresTermineesByCategorie(user, idCateg);
				}else {
					listEnch = em.getMesEncheresTerminees(user);
				}
				break;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (Encheres ench : listEnch) {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
			objectBuilder.add("noArticle", ench.getNo_article().getNo_article());
			objectBuilder.add("nomArticle", ench.getNo_article().getNom_article());
			objectBuilder.add("montant", ench.getMontant_enchere());
			objectBuilder.add("dateFinEnch", ench.getNo_article().getDate_fin().toString());
			objectBuilder.add("pseudo", ench.getNo_utilisateur().getPseudo());

			arrayBuilder.add(objectBuilder);
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(arrayBuilder.build().toString());
	}

}
