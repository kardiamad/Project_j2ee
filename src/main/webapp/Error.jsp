<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<% String error = (String) request.getAttribute("error");%>
<body>
	<h1>Error</h1>
    <h2><%= error %><br/> </h2>
    <a href="${file}?id=<c:out value='${user}' />">Go Back</a>
</body>
</html>