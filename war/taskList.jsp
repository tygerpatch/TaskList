<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Query.Filter" %>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator" %>
<%@ page import="com.google.appengine.api.datastore.Query.FilterPredicate" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
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
		<h3>Task List</h3>
		<%
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			
			if(user == null) {
		%>
				<p>
					<a href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign in</a>
				</p>
		<%
			}				
			else {
		%>
			<p>
				<a href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign out</a>
			</p>
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
					String propertyName = "userID";
					FilterOperator operator = FilterOperator.EQUAL;
					String value = user.getUserId();	
					Filter filter = new FilterPredicate(propertyName, operator, value);
		
					String kind = "Task";
					String name = user.getUserId();
					Key key = KeyFactory.createKey(kind, name);

					Query query = new Query("Task", key);
					query.setFilter(filter);
		
					DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
					PreparedQuery preparedQuery = datastore.prepare(query);	

					for (Entity entity : preparedQuery.asIterable()) {
						if((Boolean)entity.getProperty("isComplete")) {
				%>
							<div style="text-decoration: line-through;">
				<%
						}
						else  {
				%>
							<div>
				<%
						}
				%>
							<%= entity.getProperty("description") %> <%= entity.getProperty("dueDate") %>
						</div>					
				<%
					}
				%>
				</div>
				<%
			}
				%>			
	</body>
</html>
