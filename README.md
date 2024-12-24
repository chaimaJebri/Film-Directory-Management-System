# Overview
This repository contains the code for the Films web application which is developed with different architectural approaches (MVC and RESTful web service with a JavaScript client). 
The web application allows users to perform CRUD functionalities on a films database. Additionally, it exchanges data in 3 different formats (JSON, XML, and Text) through a RESTful web service.

# Technologies & Tools
* Java 11
* Apache Tomcat v9.0
* MySQL Database
* Eclipse IDE

# HTTP/MVC Web Application
The MVC web application is implemented using various Java controllers (e.g. RetrieveFilmsController) and JSP views that allow users to browse, search, insert, update, or delete films.

## Usage: 
* Download the FilmMVC folder.
* Import the project into Eclipse IDE – If you are using different versions of Java or Tomcat, you may need to configure a Build Path and a Target Runtimes (Right click on the project - Properties – Java Build Path / Target Runtimes).
* Configure MySQL database.
* Run the project (Right click on the project – Run as – Run on server).

# The Film RESTful API
The Restful API uses HTTP methods (Get, Post, Put, Delete) to enable full CRUD functionalities on the film database. It is built to exchange data in JSON, XML, or Text.
## Endpoint URL: 
http://localhost:8080/FilmRestful/film-api  
Any REST client can be used to test the REST API (e.g. Postman, Postcode, CURL.).

## JavaScript Web Application
The JavaScript application is developed to consume the Restful API in order to let users browse and search the films database using a browser. Users can also add, edit, and delete films. The application also incorporates pagination functionality. 
Using the web view and through a drop-down menu, users can choose in which format the data can be exchanged between the client and the server (JSON, XML, or Text).
The web application is implemented using HTML, CSS, and JavaScript, alongside jQuery and Bootstrap frameworks.

## Usage:
* Download the FilmRestful folder.
* Import the project into Eclipse.
* Configure MySQL database.
* Run the project (Right click on the project – Run as – Run on server) | Upon initial launch of the project, a welcome page with an animated video is displayed. By clicking on the “Browse Films” button, you will be directed to the main web view where you can perform CRUD functionalities on the films.

Author  
Chaima Jebri
