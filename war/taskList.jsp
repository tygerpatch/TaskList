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
			<form action="/servlets/AddTask" method = "POST">
				<table>
	  				<tr>
	  					<td><label for="description">Description</label></td>
	  					<td><label for="dueDate">Date Due</label></td>
	  					<td></td>
					</tr>
		
	  				<tr>
	  					<td><input type="text" name="description" /></td>
	  					<td><input type="text" name="dueDate" id="dueDate" /></td>
	  					<td><input type="submit" value="Add" /></td> 
					</tr>				
	  			</table>
			</form>
			
			<div style = "height: 100px; width: 300px; border: 1px solid; overflow: auto">
				<table id = "taskList"></table>
			</div>
			
			<script type="text/javascript">
				$(function() {
					$( "#dueDate" ).datepicker();
				});

				var row, checkBox;
				var table = document.getElementById("taskList");

				<%				
					String propertyName = "userID";
					FilterOperator operator = FilterOperator.EQUAL;
					String value = user.getUserId();	
					Filter filter = new FilterPredicate(propertyName, operator, value);
		
					Query query = new Query("Task");
					query.setFilter(filter);
		
					DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
					PreparedQuery preparedQuery = datastore.prepare(query);
		
					for (Entity entity : preparedQuery.asIterable()) {
				%>
						checkBox = document.createElement('input');
						checkBox.type = "checkbox";
						checkBox.checked = <%= entity.getProperty("isComplete") %>;				
		
						checkBox.onclick = function() {
							jQuery.ajax({
								url: "./servlets/UpdateTask",
								type: 'POST',
								data: { 
									isComplete: (this.checked ? "true" : "false"),
									description: "<%= entity.getProperty("description") %>"
								}
							});
						}
					
						row = table.insertRow(-1); // insert at last position
				
						row.insertCell(0).appendChild(checkBox);
						row.insertCell(1).appendChild(document.createTextNode("<%= entity.getProperty("description") %> <%= entity.getProperty("dueDate") %>"));
				<%
					}
				}
				%>

		</script>						
	</body>
</html>
