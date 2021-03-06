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
<% session.setAttribute("userName", request.getParameter("id")); %>
<% String auth_user = (String) request.getAttribute("auth_user");%>
<% String error = (String) request.getAttribute("error");%>
<body>
	<c:if test="${not empty product.authorized_username or not empty userName or not empty auth_user}">
		<header>
			<nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
		        <div class="container">
		            <h2>ABC Company</h2>
		            <div class="float-left">
		                <form method="post" action="logout" style="display: flex; float: right;">
		                	<c:if test="${empty product.authorized_username}">
		                    	<div >${userName}<input type="submit" value="Log out" /></div>
		                    </c:if>
		                    <c:if test="${empty userName}">
		                    	<div >${product.authorized_username}<input type="submit" value="Log out" /></div>
		                    </c:if>
		                    <c:if test="${empty userName and empty product.authorized_username}">
		                    	<div >${auth_user}<input type="submit" value="Log out" /></div>
		                    </c:if>
		                </form>
		            </div>
		        </div>
		    </nav>
		</header>
	    <div align="center">
	        <h1>Add New Product</h1>
			 <c:if test="${not empty error}">
			 	<h3 style="color:red">${error}</h3>
			  </c:if>
			<form action="insertProduct" method="post">
		        <table border="1" cellpadding="5">
		            <tr>
		                <th>Product Name: </th>
		                <td>
		                	<input type="text" name="pname" size="45" value="<c:out value='${product.product_name}' />"/>
		                </td>
	            	</tr>
		            <tr>
		                <th>Product Brand: </th>
		                <td>
		                	<input type="text" name="pbrand" size="45" value="<c:out value='${product.product_brand}' />"/>
		                </td>
		            </tr>
		            <tr>
		                <th>Product Category: </th>
		                <td>
		                	<input type="text" name="pcat" size="5" value="<c:out value='${product.product_category}' />"/>
		                </td>
		            </tr>
		            <tr>
		            	<td colspan="2" align="center">
		            		<input type="submit" value="Save" />
		            	</td>
		            </tr>
		        </table>
	        </form>
	        <a href="AdminForm.jsp?id=<c:out value='${userName}' />">Go Back</a>
	    </div>
	</c:if>	
	<c:if test="${empty product.authorized_username and empty userName and empty auth_user}">
		<h2>Please, Login or Register first</h2>
		<p><a href="login.jsp">Login</a></p>
		<p><a href="register.jsp">Register</a></p>
	</c:if>	
</body>
</html>