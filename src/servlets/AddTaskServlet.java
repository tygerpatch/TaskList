package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AddTaskServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws IOException {
		
		String strUserID = UserServiceFactory.getUserService().getCurrentUser().getUserId();

		Entity task = new Entity("TaskList", KeyFactory.createKey("user", strUserID));	    	    	    
		task.setProperty("dueDate", servletRequest.getParameter("dueDate"));	    
		task.setProperty("description", servletRequest.getParameter("description"));
	    
		DatastoreServiceFactory.getDatastoreService().put(task);
		servletResponse.sendRedirect("/taskList.jsp");
	}
}
