<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
<link href="./css/resultsPage.css" rel="stylesheet"/>
</head>
<body>

<c:choose>
	<c:when test="${not empty errorMessage1}">
	<div class="message-section">
		<h1>Oops!</h1>
		<h2>${errorMessage1}</h2>
		<a href="retrieveFilmsController">Back To All Films</a>
	</div>
	</c:when>
	<c:when test="${not empty searchResults}">
	<div class="search-section">
	<div class="container">
		<form method="get" action="retrieveFilmsController">
			<button class="top-button">Back To All Films</button>
		</form>
		<h1>Search Films</h1>
	</div>
	<table border="1">
	<thead>
		<tr>
			<th>Film ID</th>
			<th>Title</th>
			<th>Year</th>
			<th>Director</th>
			<th>Stars</th>
			<th>Review</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="film" items="${searchResults}">
			<tr>
				<td>${film.id}</td>
				<td>${film.title}</td>
				<td>${film.year}</td>
				<td>${film.director}</td>
				<td>${film.stars}</td>
				<td>${film.review}</td>
				<td><a href="updateFilmController?id=${film.id}">Edit</a></td>
				<td><a href="deleteFilmController?id=${film.id}">Delete</a></td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
	</c:when>
	<c:otherwise>
	<div class="message-section">
		<h1>Oops!</h1>
		<h2>${errorMessage2}</h2>
		<a href="retrieveFilmsController">Back To All Films</a>
	</div>
	</c:otherwise>

</c:choose>

</body>
</html>