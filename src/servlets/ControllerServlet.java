package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class ControllerServlet  extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws ServletException, IOException {

		process(servletRequest, servletResponse);
	}

	@Override
	protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws ServletException, IOException {

		process(servletRequest, servletResponse);
	}

	private void process(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws ServletException, IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = UserServiceFactory.getUserService().getCurrentUser();
		
		String requestURI = servletRequest.getRequestURI();
		
		// assume user is not logged in
		String url = "/login.jsp";
		String link = userService.createLoginURL(requestURI);
		
		// if user IS logged in
		if(user != null) { 
			url = "/taskList.jsp";
			link = userService.createLogoutURL(requestURI);
			
			servletRequest.setAttribute("userID", user.getUserId());
		}
				
		servletRequest.setAttribute("link", link);
		servletRequest.getRequestDispatcher(url).forward(servletRequest, servletResponse);
	}
}
