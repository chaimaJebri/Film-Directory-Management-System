# Overview
This repository contains the code for the Films web application which is developed with different architectural approaches (MVC and RESTful web service with a JavaScript client). 
The web application allows users to perform CRUD functionalities on a films database. Additionally, it exchanges data in 3 different formats (JSON, XML, and Text) through a RESTful web service.

# Technologies & Tools
* Java 17 (MVC Web App) / Java 11 (RESTful API)
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
* Run the project (Right click on the project – Run as – Run on server) | Upon initial launch of the project, a welcome page with an animated video is displayed. By clicking on the “Browse Films” button, you will be directed to the main web view where you can perform CRUD functionalities on films.


# MVC Web Application Screenshots
#### Project Structure in Eclipse
![MVC-Structure](https://github.com/user-attachments/assets/9e79bff1-4c67-4a24-98c2-d6beb3428581)

#### Welcome Page
![MVC-WelcomePage](https://github.com/user-attachments/assets/d5d984a9-6733-4a62-9be6-c53a2d0a61f3)

#### Get all films
![MVC-AllFilms](https://github.com/user-attachments/assets/3c6723ef-41c7-4a5f-bd5a-de1536c501f8) 

#### Search one film
![MVC-GetOneFilm](https://github.com/user-attachments/assets/b7886681-06b0-4abb-a17f-c54f526e4b36)

#### Add new film
![MVC-AddFilm01](https://github.com/user-attachments/assets/19086664-b012-409d-aab2-d0ef33e99b27)
![MVC-AddFilm02](https://github.com/user-attachments/assets/dc16fe8b-3d21-4017-83d6-1180fe6fd101)

#### Edit film
![MVC-EditFilm01](https://github.com/user-attachments/assets/62b48f6e-7260-4d99-be2c-ac62357c3a5e)
![MVC-EditFilm02](https://github.com/user-attachments/assets/a6d36650-301d-4b54-ad89-6c04f7d41940)

#### Delete Film
![MVC-DeleteFilm](https://github.com/user-attachments/assets/4e521443-f809-46f1-a7c3-9417e75f002e)


# Film RESTful API Screenshots
#### Project Structure in Eclipse
![RestApi-Structure](https://github.com/user-attachments/assets/cabd67c1-eda6-49a0-8a7f-4c39c3304154)

#### Welcome Page
![RestApi-WelcomePage](https://github.com/user-attachments/assets/a4f5c102-9c43-4f9b-addf-68c88bb09985)

#### Get all films 
* Default JSON
![RestApi-AllFilms-DefaultJSON](https://github.com/user-attachments/assets/b0767a91-32ee-462b-bffb-6054e00cd017)

* XML data format
![RestApi-AllFilms-XML](https://github.com/user-attachments/assets/5769dbe3-0b83-4c63-8778-a9237afeca55)

* JSON data format
![RestApi-AllFilms-JSON](https://github.com/user-attachments/assets/9515901b-0541-4e7d-b811-5b4b7839a1ae)

* Text data format
![RestApi-AllFilms-Text](https://github.com/user-attachments/assets/355997db-79a1-45b0-ba18-79c62815417b)

#### Search films by year - Get results in Text data format
![RestApi-GetFilmsByYear-Text](https://github.com/user-attachments/assets/35977a04-fe24-413d-a0dc-22d6caaae1c4)

#### Add new film
![Restapi-AddFilm01](https://github.com/user-attachments/assets/7cced06c-e463-4b45-92ad-06e6785aca1f)
![RestApi-AddFilm02](https://github.com/user-attachments/assets/5c05a668-8631-4ac9-a73d-5c633b84ceb0)

#### Edit film
![RestApi-EditFilm01](https://github.com/user-attachments/assets/f04ee0ab-5616-48f1-b560-55ec514e1884)
![RestApi-EditFilm02](https://github.com/user-attachments/assets/5c9b0eb9-26cd-4df9-823f-38b804a87067)

#### Delete film
![RestApi-DeleteFilm01](https://github.com/user-attachments/assets/4d40945a-3b1e-4dc7-b8dc-ae8e9f3c20df)
![RestApi-DeleteFilm02](https://github.com/user-attachments/assets/f136925c-035c-4e0f-a5f4-d4a3153fdb9d)


Author  
Chaima Jebri

