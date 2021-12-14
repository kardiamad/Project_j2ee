<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<% session.setAttribute("userName", request.getParameter("id")); %>
<body>
	<c:if test="${not empty user.authorizedUser or not empty userName}">
		<header>
			<nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
		        <div class="container">
		            <h2>ABC Company</h2>
		            <div class="float-left">
		                <form method="post" action="logout" style="display: flex; float: right;">
		                    <c:if test="${empty user.authorizedUser}">
		                    	<div >${userName}<input type="submit" value="Log out" /></div>
		                    </c:if>
		                    <c:if test="${empty userName}">
		                    	<div >${user.authorizedUser}<input type="submit" value="Log out" /></div>
		                    </c:if>
		                </form>
		            </div>
		        </div>
		    </nav>
		</header>
		<div align="center">
			<h1>Customer Management</h1>
	        <h2>
	        	<a href="newDeviceReg">Add New Product</a>
	        	&nbsp;&nbsp;&nbsp;
	        	<a href="listDevice">List All Products Registered</a>
	        	&nbsp;&nbsp;&nbsp;
	        </h2>
		</div>
		<div class="container">
			  <h2>Search:</h2>
			  <div class="card">
			   <div class="card-body">
			    <form action="findProductRegisteredByName" method="post">
			
			     <div class=" form-group row">
			      <label for="User Name" class="col-sm-2 col-form-label">Product</label>
			      <div class="col-sm-7">
			       <input type="text" class="form-control" name="pname"
			        placeholder="Enter Product Name">
			      </div>
			      <button type="submit" class="btn btn-primary">Find</button>
			     </div>
			   </div>
			  </div>
			 </div>
	</c:if>	
	<c:if test="${empty user.authorizedUser and empty userName}">
		<h2>Please, Login or Register first</h2>
		<p><a href="login.jsp">Login</a></p>
		<p><a href="register.jsp">Register</a></p>
	</c:if>	
</body>
</html>