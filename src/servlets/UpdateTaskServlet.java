package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import java.util.List;

@SuppressWarnings("serial")
public class UpdateTaskServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws IOException {
		
		String strUserID = UserServiceFactory.getUserService().getCurrentUser().getUserId();
		Query query = new Query("TaskList", KeyFactory.createKey("user", strUserID));
		List<Entity> taskList = DatastoreServiceFactory.getDatastoreService().prepare(query).asList(FetchOptions.Builder.withDefaults());		
		int indx = -1;
		
		for(int i = 0; i < taskList.size(); i++) {			
			if(servletRequest.getParameter("description").equalsIgnoreCase(taskList.get(i).getProperty("description").toString())) {
				indx = i;
				i = taskList.size();
			}
		}
		
		if(indx != -1) {

			Entity oldTask = taskList.get(indx);
		    Entity newTask = new Entity("TaskList", KeyFactory.createKey("user", strUserID));	    						
						
			newTask.setProperty("dueDate", oldTask.getProperty("dueDate"));
			newTask.setProperty("description", oldTask.getProperty("description"));
			newTask.setProperty("isComplete", (servletRequest.getParameter("isComplete").equalsIgnoreCase("true") ? true : false));
			
			// BUG: instead of overwriting old task, it adds another new task
			//DatastoreServiceFactory.getDatastoreService().put(newTask);
		}		
	}
}
