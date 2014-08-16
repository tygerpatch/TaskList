package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.users.UserServiceFactory;

 @SuppressWarnings("serial")
public class AddTaskServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws IOException {
		
		Entity entity = new Entity("Task");
				
		entity.setProperty("userID", UserServiceFactory.getUserService().getCurrentUser().getUserId());
	    entity.setProperty("dueDate", servletRequest.getParameter("dueDate"));
		entity.setProperty("description", servletRequest.getParameter("description"));
		entity.setProperty("isComplete", false);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastore.beginTransaction();		
		datastore.put(entity);
		transaction.commit();
		
		servletResponse.sendRedirect("/taskList.jsp");
	}
}
