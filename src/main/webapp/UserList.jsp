<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
<meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<%
String name = (String)request.getParameter("id");
%>
<body>
	<c:if test="${not empty listUser[0].authorizedUser or not empty param.name}">
		<header>
			<nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
		        <div class="container">
		            <h2>ABC Company</h2>
		            <div class="float-left">
		                <form method="post" asp-action="logout" style="display: flex; float: right;">
		                    <div >${listUser[0].authorizedUser}<input type="submit" value="Log out" /></div>
		                </form>
		            </div>
		        </div>
		    </nav>
		</header>
		<div align="center">
			<h1>Users Management</h1>
	    </div>
	    <div align="center">
	        <table border="1" cellpadding="5">
	            <caption><h2>List of Users</h2></caption>
	            <tr>
	            	<th>User ID</th>
	                <th>User Name</th>
	                <th>Email</th>
	                <th>Password</th>
	                <th>Phone Number</th>
	                <th>Address</th>
	                <th>Role</th>
	                <th>Action</th>
	            </tr>
	            <c:forEach var="user" items="${listUser}">
	                <tr>
	                	<td><c:out value="${user.user_id}" /></td>
	                    <td><c:out value="${user.username}" /></td>
	                    <td><c:out value="${user.email}" /></td>
	                    <td><c:out value="${user.password}" /></td>
	                    <td><c:out value="${user.phoneno}" /></td>
	                    <td><c:out value="${user.address}" /></td>
	                    <c:if test="${user.role == 0}">
	                    	<td>Customer</td>
	                    </c:if>
	                    <c:if test="${user.role == 1}">
	                    	<td>Admin</td>
	                    </c:if>
	                    <td>
		            		<a href="changePermission?id=<c:out value='${user.user_id}' />">Change Permission</a>
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
	        <a href="AdminForm.jsp?id=<c:out value='${listUser[0].authorizedUser}' />">Go Back</a>
	    </div>	
	</c:if>	
	<c:if test="${empty listUser[0].authorizedUser and empty param.name}">
		<h2>Please, Login or Register first</h2>
		<p><a href="login.jsp">Login</a></p>
		<p><a href="register.jsp">Register</a></p>
	</c:if>    
</body>
</html>