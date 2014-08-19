<%@ taglib prefix="s" uri="/struts-tags"%>
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
	 	<link rel="stylesheet" href="../css/jquery-ui.css">
	 	<link rel="stylesheet" href="../css/jquery-ui.structure.css">
	 	<link rel="stylesheet" href="../css/jquery-ui.theme.css">

		<script src="../javascript/jquery.js"></script>		 				 
		<script src="../javascript/jquery-ui.js"></script>		
	</head>
	<body>
		<s:form action="addTask" validate="true">
			<s:textfield name="description" label="Description" />
			<s:textfield name="dueDate" label="Due Date" />			
			<s:checkbox name="isComplete" label = "Check to mark task as complete" theme = "checkbox" />			
			<s:submit value="Add Task" />
		</s:form>		
   </body>
</html>
