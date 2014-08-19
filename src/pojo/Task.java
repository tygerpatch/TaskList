package pojo;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class Task  extends ActionSupport {
	// *** due date property
	private Date dueDate;

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		System.out.println("dueDate = " + dueDate);
		this.dueDate = dueDate;
	}

	// *** description property
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		System.out.println("description = " + description);
		this.description = description;
	}

	// *** isComplete property
	private boolean isComplete = false;

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		System.out.println("isComplete = " + isComplete);
		this.isComplete = isComplete;
	}

	@Override
	public String execute() throws Exception {
		String userID = UserServiceFactory.getUserService().getCurrentUser().getUserId();
		
		Entity entity = new Entity("Task", KeyFactory.createKey("Task", userID));
		entity.setProperty("userID", userID);		
		entity.setProperty("dueDate", dueDate);		
		entity.setProperty("description", description);

		DatastoreServiceFactory.getDatastoreService().put(entity);
		
		return super.execute();
	}	
}
