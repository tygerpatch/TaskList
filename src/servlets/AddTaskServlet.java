package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AddTaskServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws IOException {
		
		String dueDate = servletRequest.getParameter("dueDate");
		String description = servletRequest.getParameter("description");
		
		if(dueDate == null || dueDate.isEmpty()) {
			return;
		}

		if(description == null || description.isEmpty()) {
			return;
		}

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String kind = "Task";
		String name = user.getUserId();
		Key key = KeyFactory.createKey(kind, name);
		Entity entity = new Entity("Task", key);
		
		entity.setProperty("userID", UserServiceFactory.getUserService().getCurrentUser().getUserId());
	    entity.setProperty("dueDate", dueDate);
		entity.setProperty("description", description);
		
		String isComplete = servletRequest.getParameter("isComplete");
		
		if(isComplete == null) {
			entity.setProperty("isComplete", false);
		}
		else if(isComplete.equalsIgnoreCase("on")) {
			entity.setProperty("isComplete", true);
		}
		else {
			entity.setProperty("isComplete", false);
		}
						
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastore.beginTransaction();
		datastore.put(entity);
		transaction.commit();
		
		servletResponse.sendRedirect("/taskList.jsp");
	}
}
