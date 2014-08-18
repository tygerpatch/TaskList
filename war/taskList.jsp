<%@ page import="com.google.appengine.api.datastore.Query.Filter" %>
<%@ page import="com.google.appengine.api.datastore.Query.FilterPredicate" %>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<html>
	<head>
		<title>Task List</title>
	 	<link rel="stylesheet" href="/css/jquery-ui.css">
	 	<link rel="stylesheet" href="/css/jquery-ui.structure.css">
	 	<link rel="stylesheet" href="/css/jquery-ui.theme.css">

		<script src="/javascript/jquery.js"></script>		 				 
		<script src="/javascript/jquery-ui.js"></script>		
	</head>
	<body>
		<%
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
		%>	
		<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign Out</a>
		<script type="text/javascript">
			function validateForm() {
				var form = document.forms["myForm"];
			    var description = form["description"].value;
			    var dueDate = form["dueDate"].value;

			    if (description == null || description == "") {
			        alert("Task must have a description.");
			        return false;
			    }

			    if (dueDate == null || dueDate == "") {
			        alert("Task must have a due date");
			        return false;
			    }				    
			}
						
			$(function() {
				$( "#dueDate" ).datepicker();
			});
		</script>			
		<form action="/servlets/AddTask" method = "POST" onsubmit="return validateForm()" name="myForm">
			<table>
				<tr>
					<td><label for="description">Description</label></td>
					<td><input type="text" name="description" /></td>
				</tr>
				<tr>
					<td><label for="dueDate">Date Due</label></td>
					<td><input type="text" name="dueDate" id="dueDate" /></td>
				</tr>
			</table>
			<label for="isComplete">Mark task as complete?</label>
			<input type="checkbox" name="isComplete" />
			<br />

  			<input type="submit" value="Add" />
		</form>
		<div style = "height: 100px; width: 300px; border: 1px solid; overflow: auto" id="taskList">
			<%				
				Filter filter = new FilterPredicate("userID", FilterOperator.EQUAL, user.getUserId());

				Query query = new Query("Task", KeyFactory.createKey("Task", user.getUserId()));
				query.setFilter(filter);
	
				PreparedQuery preparedQuery = DatastoreServiceFactory.getDatastoreService().prepare(query);	

				for (Entity entity : preparedQuery.asIterable()) {
					if((Boolean)entity.getProperty("isComplete")) {
						out.println("<div style='text-decoration: line-through;'>");
						
					}
					else  {
						out.println("<div>");
					}
					
					out.println(entity.getProperty("description") + " " + entity.getProperty("dueDate"));					
					out.println("</div>");					
				}
			%>		
		</div>
	</body>
</html>
