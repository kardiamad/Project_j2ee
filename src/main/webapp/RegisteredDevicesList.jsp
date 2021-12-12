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
<% String error = (String) request.getAttribute("error");%>
<body>
	<c:if test="${not empty productRegistered[0].authorizedUser or not empty userName}">
		<header>
			<nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
		        <div class="container">
		            <h2>ABC Company</h2>
		            <div class="float-left">
		                <form method="post" action="logout" style="display: flex; float: right;">
		                	<c:if test="${empty productRegistered[0].authorizedUser}">
		                    	<div >${userName}<input type="submit" value="Log out" /></div>
		                    </c:if>	
		                    <c:if test="${empty userName}">
		                    	<div >${productRegistered[0].authorizedUser}<input type="submit" value="Log out" /></div>
		                    </c:if>
		                </form>
		            </div>
		        </div>
		    </nav>
		</header>
	    <div align="center">
	        <c:if test="${not empty error}">
		 	  <h3 style="color:red">${error}</h3>
		    </c:if>
	    	<h1>Registered Devices</h1>
	        <table border="1" cellpadding="5">
	            <caption><h2>List of Registered Devices</h2></caption>
	            <tr>
	                <th>User Name</th>
	                <th>Product Name</th>
	                <th>Serial No</th>
	                <th>Purchase Date</th>
	                <th>Approval</th>
	                <th></th>
	            </tr>
	            <c:forEach var="product" items="${productRegistered}">
	                <tr>
	                    <td><c:out value="${product.username}" /></td>
	                    <td><c:out value="${product.product_name}" /></td>
	                    <td><c:out value="${product.serialno}" /></td>
	                    <td><c:out value="${product.purchase_date}" /></td>
	                    <td></td>
	                    <c:if test="${product.role == 0}">
	                    	<td><a href="newClaim?id=<c:out value='${product.product_reg_id}' />">Add New Claim</a></td>
	                    </c:if>
	                </tr>
	                <c:if test="${product.claim != null}">
	        			<c:forEach var="claim" items="${product.claim}">
	        				<c:if test="${product.product_name == claim.product_name}">
			               		<tr>
				               		<td><c:out value="${claim.claim_id}" /></td>
				               		<td><c:out value="${claim.product_name}" /></td>
				                    <td><c:out value="${claim.claim_desc}" /></td>
				                    <td><c:out value="${claim.claim_date}" /></td>
				                    <td><c:out value="${claim.claim_approval}" /></td>
				                    <c:if test="${claim.role == 1}">
				                    	<c:if test="${claim.claim_approval == 'N'}">
				                    		<td><a href="approve?id=<c:out value='${claim.claim_id}' />">Approve</a></td>
				                    	</c:if>
				                    </c:if>
			                    </tr>
			                </c:if>
		                </c:forEach>
	        		</c:if>
	            </c:forEach>
	        </table>
	        <c:if test="${productRegistered[0].role == 1}">
	        	<a href="AdminForm.jsp?id=<c:out value='${productRegistered[0].authorizedUser}' />">Go Back</a>
	        </c:if>	
	        <c:if test="${productRegistered[0].role == 0}">
	        	<a href="CustomerForm.jsp?id=<c:out value='${productRegistered[0].authorizedUser}' />">Go Back</a>
	        </c:if>
	    </div>	
	</c:if>	
	<c:if test="${empty productRegistered[0].authorizedUser and empty userName}">
		<h2>Please, Login or Register first</h2>
		<p><a href="login.jsp">Login</a></p>
		<p><a href="register.jsp">Register</a></p>
	</c:if>    
</body>
</html>