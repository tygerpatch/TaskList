package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
public class UpdateTaskServlet extends HttpServlet {

	// UserServiceFactory.getUserService().getCurrentUser().getUserId()
	// DatastoreServiceFactory.getDatastoreService().put(entity);
	
	public void doPost(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws IOException {

		String propertyName = "description";
		FilterOperator operator = FilterOperator.EQUAL;
		String value = servletRequest.getParameter("description");
		Filter filter = new FilterPredicate(propertyName, operator, value);

		Query query = new Query("Task");
		query.setFilter(filter);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery preparedQuery = datastore.prepare(query);

		boolean isComplete = servletRequest.getParameter("isComplete").equalsIgnoreCase("true") ? true : false;

		Transaction transaction = datastore.beginTransaction();
		
		for (Entity entity : preparedQuery.asIterable()) {
			entity.setProperty("isComplete", isComplete);
			datastore.put(entity);
		}
		
		transaction.commit();
	}
}


// ~ Original ~

//String strUserID = UserServiceFactory.getUserService().getCurrentUser().getUserId();
//Query query = new Query("TaskList", KeyFactory.createKey("user", strUserID));
//List<Entity> taskList = DatastoreServiceFactory.getDatastoreService().prepare(query).asList(FetchOptions.Builder.withDefaults());		
//int indx = -1;
//
//for(int i = 0; i < taskList.size(); i++) {			
//	if(servletRequest.getParameter("description").equalsIgnoreCase(taskList.get(i).getProperty("description").toString())) {
//		indx = i;
//		i = taskList.size();
//	}
//}
//
//if(indx != -1) {
//
//	Entity oldTask = taskList.get(indx);
//    Entity newTask = new Entity("TaskList", KeyFactory.createKey("user", strUserID));	    						
//				
//	newTask.setProperty("dueDate", oldTask.getProperty("dueDate"));
//	newTask.setProperty("description", oldTask.getProperty("description"));
//	newTask.setProperty("isComplete", (servletRequest.getParameter("isComplete").equalsIgnoreCase("true") ? true : false));
//	
//	// BUG: instead of overwriting old task, it adds another new task
//	//DatastoreServiceFactory.getDatastoreService().put(newTask);
//}		
