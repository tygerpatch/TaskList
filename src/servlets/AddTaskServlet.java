package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AddTaskServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {

		String userID = UserServiceFactory.getUserService().getCurrentUser().getUserId();
		Entity entity = new Entity("Task", KeyFactory.createKey("Task", userID));
		entity.setProperty("userID", userID);
		
		String dueDate = servletRequest.getParameter("dueDate");
		
		if(dueDate == null || dueDate.isEmpty()) {
			return;
		}
		
		entity.setProperty("dueDate", dueDate);
		
		String description = servletRequest.getParameter("description");

		if(description == null || description.isEmpty()) {
			return;
		}
		
		entity.setProperty("description", description);
		
		String strIsComplete = servletRequest.getParameter("isComplete");
		entity.setProperty("isComplete", false);

		if ((strIsComplete != null) && (strIsComplete.equalsIgnoreCase("on"))) {
			entity.setProperty("isComplete", true);
		}

		DatastoreServiceFactory.getDatastoreService().put(entity);
		servletResponse.sendRedirect("/taskList.jsp");
	}
}
