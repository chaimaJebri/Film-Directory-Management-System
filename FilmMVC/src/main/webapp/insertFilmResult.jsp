<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Film</title>
<link href="./css/resultsPage.css" rel="stylesheet"/>
</head>
<body>

<c:choose>
	<c:when test="${inserted}">
	<div class="message-section">
				<h1>Film Added Successfully</h1>
				<h2>Added Film Details</h2>
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
		<a  href="retrieveFilmsController">Back To All Films</a>
	</div>
	</c:when>
	<c:otherwise>
	<div class="message-section">
		<h1>Oops!</h1>
		<h2>Failed to add the film. Please Try Again</h2>
        <a href="insertFilmController">Try again</a>
        <a  href="retrieveFilmsController">All Films</a>
     </div>
	</c:otherwise>
</c:choose>


</body>
</html>