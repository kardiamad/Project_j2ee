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
<body>
	<c:if test="${not empty product[0].authorizedUser}">
		<header>
			<nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
		        <div class="container">
		            <h2>ABC Company</h2>
		            <div class="float-left">
		                <form method="post" action="logout" style="display: flex; float: right;">
		                    <div >${product[0].authorizedUser}<input type="submit" value="Log out" /></div>
		                </form>
		            </div>
		        </div>
		    </nav>
		</header>
	    <div align="center">
	        <h1>Add New Claim</h1>
			
			<form action="insertClaim" method="post">
		        <table border="1" cellpadding="5">
		            <tr>
		                <th>Product Name: </th>
		                <td><c:out value="${product.product_name}" /></td>
	            	</tr>
		            <tr>
		                <th>Claim Description: </th>
		                <td>
		                	<input type="text" name="desc" size="155"/>
		                </td>
		            </tr>
		            
		            <tr>
		            	<td colspan="2" align="center">
		            		<input type="submit" value="Save" />
		            	</td>
		            </tr>
		        </table>
	        </form>
	    </div>	
	</c:if>	
	<c:if test="${empty product[0].authorizedUser}">
		<h2>Please, Login or Register first</h2>
		<p><a href="login.jsp">Login</a></p>
		<p><a href="register.jsp">Register</a></p>
	</c:if> 
</body>
</html>