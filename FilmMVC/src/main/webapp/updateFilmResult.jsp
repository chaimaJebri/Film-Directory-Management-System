<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Film</title>
<link href="./css/resultsPage.css" rel="stylesheet"/>
</head>
<body>
<c:choose>
	<c:when test="${updated}">
	<div class="message-section">
	<h1>Film with ID: ${oneFilm.id} updated successfully</h1>
	<h2>Film Details</h2>
		<table>
				<thead>
					<tr>
						<th>Title</th>
						<th>Year</th>
						<th>Director</th>
						<th>Stars</th>
						<th>Review</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${oneFilm.title}</td>
						<td>${oneFilm.year}</td>
						<td>${oneFilm.director}</td>
						<td>${oneFilm.stars}</td>
						<td>${oneFilm.review}</td>
					</tr>
				</tbody>
		</table>
		<br>
		<a  href="retrieveFilmsController">All Films</a>
		</div>
	</c:when>
	<c:otherwise>
	<div class="message-section">
		<h1>Oops!</h1>
		<h2>Failed to edit film with ID: ${oneFilm.id}. Please Try Again</h2>
        <a href="updateFilmController?id=${oneFilm.id}">Try again</a>
        <a  href="retrieveFilmsController">All Films</a>
	</div>
	</c:otherwise>
</c:choose>
</body>
</html>