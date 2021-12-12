<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
	<title>ABC</title>
</head>
<body>
	<c:if test="${not empty listClaim[0].authorizedUser}">
		<header>
			<nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
		        <div class="container">
		            <h2>ABC Company</h2>
		            <div class="float-left">
		                <form method="post" action="logout" style="display: flex; float: right;">
		                    <div >${listClaim[0].authorizedUser}<input type="submit" value="Log out" /></div>
		                </form>
		            </div>
		        </div>
		    </nav>
		</header>
		<center>
			<h1>Claims Management</h1>
	    </center>
	    <div align="center">
	        <table border="1" cellpadding="5">
	            <caption><h2>List of Products</h2></caption>
	            <tr>
	           		<th>ID</th>
	                <th>User Name</th>
	                <th>Product Name</th>
	                <th>Description</th>
	                <th>Claim Date</th>
	                <th>Approval</th>
			   		<th>Actions</th>
	            </tr>
	            <c:forEach var="claim" items="${listClaim}">
	                <tr>
	                	<td><c:out value="${claim.claim_id}" /></td>
	                    <td><c:out value="${claim.username}" /></td>
	                    <td><c:out value="${claim.product_name}" /></td>
	                    <td><c:out value="${claim.claim_desc}" /></td>
	                    <td><c:out value="${claim.claim_date}" /></td>
	                    <td><c:out value="${claim.claim_approval}" /></td>
			   			<td>
		            		<c:if test="${claim.claim_approval == 'N'}">
		            			<a href="approve?id=<c:out value='${claim.claim_id}' />">Approve</a>
		            		</c:if>
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
	   		<a href="AdminForm.jsp">Go Back</a>
	    </div>	
	</c:if>	
	<c:if test="${empty listClaim.authorizedUser}">
		<h2>Please, Login or Register first</h2>
		<p><a href="login.jsp">Login</a></p>
		<p><a href="register.jsp">Register</a></p>
	</c:if>    
</body>
</html>