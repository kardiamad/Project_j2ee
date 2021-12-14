<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
<meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script
	  src="https://code.jquery.com/jquery-3.6.0.min.js"
	  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	  crossorigin="anonymous"></script>
	<script type="text/javascript" src="scripts/ajaxCalls.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<% session.setAttribute("userName", request.getParameter("id")); %>
<% String error = (String) request.getAttribute("error");%>
<% String auth_user = (String) request.getAttribute("auth_user");%>

<body>
	<c:if test="${not empty listProduct[0].authorized_username or not empty userName or not empty auth_user}">
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
		                    <div >${listProduct[0].authorized_username}<input type="submit" value="Log out" /></div>
		                </form>
		            </div>
		        </div>
		    </nav>
		</header>
	
	    <div align="center">
	    	  <c:if test="${not empty error}">
			 	<h3 style="color:red">${error}</h3>
			  </c:if>
	        <h1>Add New Device Registration</h1>
			<form action="insertDevice" method="post">
		        <table border="1" cellpadding="5">
		            <tr>
		                <th>Product Name: </th>
		                <td>
		                	<select name="pname" >
							  <c:forEach items="${listProduct}" var="product">
							    <option value="${product.product_name}" >
							        ${product.product_name}
							    </option>
							  </c:forEach>
							</select>
		                </td>
	            	</tr>
		            <tr>
		                <th>Serial No: </th>
		                <td>
		                	<input type="text" name="serialno" size="45" value="${productRegistered.serialno}"/>
		                </td>
		            </tr>
		            <tr>
		                <th>Purchase Date: </th>
		                <td>
		                	<div class="mb-3">
		                		<input type="date" name="purchase_date" id="purchase_date" placeholder="yyyy-MM-dd" value="${productRegistered.purchase_date}"/>
		                	</div>
		                	
		                </td>
		            </tr>
		            <tr>
		            	<td colspan="2" align="center">
		            		<input type="submit" value="Save" />
		            	</td>
		            </tr>
		        </table>
	        </form>
	        <a href="CustomerForm.jsp?id=<c:out value='${listProduct[0].authorized_username}' />">Go Back</a>
	    </div>	
		</c:if>	

		<c:if test="${empty listProduct[0].authorized_username and empty userName and empty auth_user}">
			<h2>Please, Login or Register first</h2>
			<p><a href="login.jsp">Login</a></p>
			<p><a href="register.jsp">Register</a></p>
		</c:if>
		<script type="text/javascript">
			$(document).ready(function(){
				var today = new Date();
				var dd = today.getDate();
				var mm = today.getMonth() + 1; //January is 0!
				var yyyy = today.getFullYear();
		
				if (dd < 10) {
				   dd = '0' + dd;
				}
		
				if (mm < 10) {
				   mm = '0' + mm;
				} 
				    
				var maxDate = yyyy + '-' + mm + '-' + dd;
				$('#purchase_date').attr('max', maxDate); //disable future dates
			})
		</script>
</body>
</html>