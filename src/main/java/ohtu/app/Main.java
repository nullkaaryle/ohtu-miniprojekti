package ohtu.app;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ohtu.data.FakeDatabase;
import ohtu.data.ReferenceDAO;
import ohtu.views.AddPresenter;
import ohtu.views.AddView;
import ohtu.views.AddViewImpl;
import ohtu.views.MainPresenter;
import ohtu.views.MainView;
import ohtu.views.MainViewImpl;

@SuppressWarnings("serial")
@PreserveOnRefresh
public class Main extends UI {

        private Server server;
    
	@Override
	protected void init(VaadinRequest vaadinRequest) {
            
                server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		
		context.addServlet(new ServletHolder(new MyUIServlet()), "/*");
		
		try {
			server.start();
		} catch (Exception e) {}
            
		getPage().setTitle("Main page - Title");
		
		ReferenceDAO dao = new ReferenceDAO(new FakeDatabase());

		MainView mainView = new MainViewImpl();
		new MainPresenter(mainView, dao);
		
		AddView addView = new AddViewImpl();
		new AddPresenter(addView, dao);
	
		Navigator navigator = new Navigator(this, this);
		navigator.addView("", mainView);
		navigator.addView("addRefs", addView);
	}

	@WebServlet(urlPatterns = "/*", asyncSupported = true)
	@VaadinServletConfiguration(ui = Main.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

}