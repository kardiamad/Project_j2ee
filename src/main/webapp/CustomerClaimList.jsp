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
    <div align="center">
    	<h1>Claims Management</h1>
		<c:set var = "claimorproduct" scope = "session" value = "claim"/>
        <table border="1" cellpadding="5">
            <caption><h2>List of Products</h2></caption>
            <tr>
                <th>User Name</th>
                <th>Product Name</th>
                <th>Description</th>
                <th>Claim Date</th>
                <th>Approval</th>
            </tr>
            <c:forEach var="claim" items="${listClaim}">
                <tr>
                    <td><c:out value="${claim.username}" /></td>
                    <td><c:out value="${claim.product_name}" /></td>
                    <td><c:out value="${claim.claim_desc}" /></td>
                    <td><c:out value="${claim.claim_date}" /></td>
                    <td><c:out value="${claim.claim_approval}" /></td>
                </tr>
            </c:forEach>
        </table>
        <a href="newDeviceReg?id=<c:out value='${claimorproduct}' />">Add New Claim</a>
    	 &nbsp;&nbsp;&nbsp;
		<a href="CustomerForm.jsp">Go Back</a>
    </div>	
</body>
</html>