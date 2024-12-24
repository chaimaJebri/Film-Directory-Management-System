<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Films</title>
<link href="./css/retrieveFilmsStyles.css" rel="stylesheet"/>
</head>
<body>

<div class="container">
	<form method="get" action="insertFilmController">
		<button class="top-button">Add New Film</button>
	</form>
	<h1>List of Films</h1>
</div>

<div class="top-right">
		<form method="post" action="searchFilmController">
	    	<input type="text" id="searchStr" name="searchStr" placeholder="Search Films &#128269;" required>
	    	<button type="submit">Search</button>
		</form>
</div>

<table>
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
		<c:forEach var="film" items="${films}">
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

</body>
</html>