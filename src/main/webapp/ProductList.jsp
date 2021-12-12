<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
	<title>ABC</title>
	<meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<% session.setAttribute("userName", request.getParameter("id")); %>
<body>
	<c:if test="${not empty listProduct[0].authorized_username or not empty userName}">
		<header>
			<nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
		        <div class="container">
		            <h2>ABC Company</h2>
		            <div class="float-left">
		                <form method="post" action="logout" style="display: flex; float: right;">
		                	<c:if test="${empty listProduct[0].authorized_username}">
		                    	<div >${userName}<input type="submit" value="Log out" /></div>
		                    </c:if>
		                    <c:if test="${empty userName}">
		                    	<div >${listProduct[0].authorized_username}<input type="submit" value="Log out" /></div>
		                    </c:if>
		                </form>
		            </div>
		        </div>
		    </nav>
		</header>
	    <div align="center" class="p-3 mb-2 bg-gradient-warning text-dark">
	    	<h1>Products Management</h1>
	    	
	        <table border="1" cellpadding="5">
	            <caption><h2>List of Products</h2></caption>
	            <tr>
	                <th>Product ID</th>
	                <th>Product Name</th>
	                <th>Brand</th>
	                <th>Category</th>
	                <th>Actions</th>
	            </tr>
	            <c:forEach var="product" items="${listProduct}">
	                <tr>
	                    <td><c:out value="${product.product_id}" /></td>
	                    <td><c:out value="${product.product_name}" /></td>
	                    <td><c:out value="${product.product_brand}" /></td>
	                    <td><c:out value="${product.product_category}" /></td>
	                    <td>
	                    	<a href="edit?id=<c:out value='${product.product_id}' />">Edit</a>
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
	        <a href="AddNewProduct.jsp?id=<c:out value='${listProduct[0].authorized_username}' />">Add New Product</a>
	        	&nbsp;&nbsp;&nbsp;
	        <a href="AdminForm.jsp?id=<c:out value='${listProduct[0].authorized_username}' />">Go Back</a>
	    </div>	
	</c:if>	
	<c:if test="${empty listProduct[0].authorized_username and empty userName}">
		<h2>Please, Login or Register first</h2>
		<p><a href="login.jsp">Login</a></p>
		<p><a href="register.jsp">Register</a></p>
	</c:if>    
</body>
</html>