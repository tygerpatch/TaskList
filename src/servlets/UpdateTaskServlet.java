package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UpdateTaskServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws IOException {

		String propertyName = "description";
		FilterOperator operator = FilterOperator.EQUAL;
		String value = servletRequest.getParameter("description");
		Filter filter = new FilterPredicate(propertyName, operator, value);

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		String kind = "Task";
		String name = user.getUserId();
		Key key = KeyFactory.createKey(kind, name);
		
		Query query = new Query("Task", key);
		query.setFilter(filter);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery preparedQuery = datastore.prepare(query);

		boolean isComplete = servletRequest.getParameter("isComplete").equalsIgnoreCase("true") ? true : false;

		Transaction transaction;
		
		for (Entity entity : preparedQuery.asIterable()) {
			transaction = datastore.beginTransaction();
			entity.setProperty("isComplete", isComplete);
			datastore.put(entity);
			transaction.commit();			
		}		
	}
}

