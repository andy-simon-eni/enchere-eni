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

/**
 * Servlet implementation class uploadImageServlet
 */
@WebServlet(
        urlPatterns= {"/upload_image"})
public class uploadImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                           
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        String name;
        int size, taille_max;
        Boolean extOk = false, sizeOk = false;
        List<String> extensions;
        
        extensions = new ArrayList<String>();
        extensions.add("jpg");
        extensions.add("jpeg");
        extensions.add("png");
        name = request.getParameter("name");
        size = Integer.parseInt(request.getParameter("size"));
        taille_max = 3000000;
                
        for (String extension : extensions) {
			if(name.equals(extension)) {
				extOk = true;
			}
		}
        
        if(extOk) {
        	if(size <= taille_max) {
        		objectBuilder.add("result", "OK");
        	}else {
        		objectBuilder.add("result", "erreurSize");
        	}
        }else {
        	objectBuilder.add("result", "erreurExt");
        }
        
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectBuilder.build().toString());
	}

}
