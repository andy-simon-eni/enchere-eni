package fr.eni.javaee.enchere;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import fr.eni.javaee.enchere.bll.ArticlesVendusManager;
@WebListener
public class listenerMajEnchereFini implements ServletContextListener {

	public listenerMajEnchereFini() {

	}

	public void contextInitialized(ServletContextEvent sce) {
		try {
			ArticlesVendusManager articleManager = new ArticlesVendusManager();
			articleManager.updateArticleRemporte();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}
