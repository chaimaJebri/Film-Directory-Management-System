<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Film</title>
<link href="./css/resultsPage.css" rel="stylesheet"/>
</head>
<body>

<c:choose>
	<c:when test="${deleted}">
		<div class="message-section">
			<h1>Yes!</h1>
			<h2>Film with ID: ${id} deleted successfully</h2>
			<a href="retrieveFilmsController">All Films</a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="message-section">
			<h1>Oops!</h1>
			<h2>Failed to delete Film! Film with ID: ${id} not found</h2>
			<a href="retrieveFilmsController">All Films</a>
		</div>
	</c:otherwise>
</c:choose>

</body>
</html>

