<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Film</title>
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
			<a  href="insertFilmController">Try Again</a>
			<a  href="retrieveFilmsController">All Films</a>
		</div>
	</c:when>
	<c:otherwise>
	<div class="form-container">
		<div>
			<h1>ADD New Film</h1>
			<p>Kindly fill the details below to add a new Film</p>
			
			<form method="post" action="insertFilmController">
			
				<label for="title" class="top-bottom-fields"> Title </label>
				<input type="text" id="title" name="title" placeholder="Enter Title" required>
				
				<label for="year"> Year </label>
				<input type="number" id="year" name="year" placeholder="Enter Year" required>
			
				<label for="director"> Director </label>
				<input type="text" id="director" name="director" placeholder="Enter Director" required>
				
				<label for="stars"> Stars </label>
				<input type="text" id="stars" name="stars" placeholder="Enter Stars" required>
				
				<label for="review"> Review </label>
				<textarea id="review" name="review" placeholder="Enter Review" required></textarea>
				
				<button type="submit" class="top-bottom-fields" >ADD Film</button>
				
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