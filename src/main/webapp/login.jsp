<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<% String error = (String) request.getAttribute("error");%>
<body>
 	
 <div class="container">
  <c:if test="${not empty error}">
 	<h3 style="color:red">${error}</h3>
  </c:if>
  <h1>Customer Login Form:</h1>
  <div class="card">
   <div class="card-body">
    <form action="login" method="post">

     <div class=" form-group row">
      <label for="User Name" class="col-sm-2 col-form-label">User
       Name</label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="uname" value="${user.username}" 
        placeholder="Enter User Name">
      </div>
     </div>

     <div class="form-group row">
      <label for="Password" class="col-sm-2 col-form-label">Password</label>
      <div class="col-sm-7">
       <input type="password" class="form-control" name="upassword" value="${user.password}" 
        placeholder="Enter Password">
      </div>
     </div>
     
     <button type="submit" class="btn btn-primary">Login</button>
    </form>
   </div>
  </div>
 </div>
</body>
</html>