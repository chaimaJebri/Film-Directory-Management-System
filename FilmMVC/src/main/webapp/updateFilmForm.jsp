<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Film</title>
<link href="./css/formStyles.css" rel="stylesheet"/>
</head>
<body>

<c:choose>
	<c:when test="${not empty errors}">
	<div class="error-section">
		<h1>Oops</h1>
		<ul>
			<c:forEach var="error" items="${errors}">
				<li><b> ${error} </b></li>
			</c:forEach>
		</ul>
		<a  href="updateFilmController?id=${id}">Try Again</a>
		<a  href="retrieveFilmsController">All Films</a>
	</div>
	</c:when>
	<c:when test="${filmNotFound}">
	<div class="error-section">
		<h1>Oops!</h1>
		<h2>Failed to edit Film! Film with ID: ${id} not found</h2>
		<a href="retrieveFilmsController">All Films</a>
	</div>
	</c:when>
	<c:otherwise>
	<div class="form-container">
		<div>
				<h1>Edit Film</h1>
				<p>Make adjustments to the Film details as required</p>
			
				<form method="post" action="updateFilmController">
				
					<input type="hidden" id="id" name="id" value="${oneFilm.id}">
				
					<label for="title" class="top-bottom-fields"> Title </label>
					<input type="text" id="title" name="title" placeholder="Enter Title" value="${oneFilm.title}" required>
					
					<label for="year"> Year </label>
					<input type="number" id="year" name="year" placeholder="Enter Year" value="${oneFilm.year}" required>
				
					<label for="director"> Director </label>
					<input type="text" id="director" name="director" placeholder="Enter Director" value="${oneFilm.director}" required>
					
					<label for="stars"> Stars </label>
					<input type="text" id="stars" name="stars" placeholder="Enter Stars" value="${oneFilm.stars}" required>
					
					<label for="review"> Review </label>
					<textarea id="review" name="review" placeholder="Enter Review" required>${oneFilm.review}</textarea>
					
					<button type="submit" class="top-bottom-fields">Save Changes</button>
				
				</form>
		</div>
		<form method="get" action="retrieveFilmsController" class="top-left">
		
			<button type="submit">Cancel</button>
			
		</form>
	</div>
	</c:otherwise>
	
</c:choose>

</body>
</html>