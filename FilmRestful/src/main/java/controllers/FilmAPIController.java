package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import models.Film;
import models.FilmConverter;
import models.FilmDAOEnum;
import models.FilmValidator;


@WebServlet("/film-api")
public class FilmAPIController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FilmDAOEnum dao;
	private FilmConverter converter;
	private FilmValidator validator;
   
    public FilmAPIController() {
        super();
        dao= FilmDAOEnum.INSTANCE;
        converter = new FilmConverter();
        validator = new FilmValidator();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setHeader("Cache-Control", "no-cache"); //send the cache-control and pragma headers to prevent caching
		response.setHeader("Pragma", "no-cache");
		String format= request.getHeader("Accept");  //retrieve the accept header to get the requested format
		String id = request.getParameter("id");      // retrieve the "id" param from the request
		String search=request.getParameter("search");  // retrieve the "search" param from the request
		ArrayList<Film> films= new ArrayList<>();
		try (PrintWriter out = response.getWriter())
		{
			if ((id != null) && !(id.trim().isEmpty())){  //if "id" is provided in the request get film by id
			   try {
					Film film = dao.getFilmByID(Integer.parseInt(id));
					if(film != null){
						films.add(film);
					}
					else {
						out.write("Fim with the id : " + id  + " no found");  // if no film found with that "id" in the database
						return;
					}
				}
				catch(NumberFormatException e){  // if the "id" couldn't be parsed, return an error message
					out.write("Invalid Film ID Format : "+ id);
					return;
				}
			}
			else if((search != null) && !(search.trim().isEmpty())){ // if "search" is provided in the request get searched films
				films=dao.searchFilm(search.trim());
			}
			else {                        
				films=dao.getAllFilms();         //retrieve all films if no param is provided in the request
			}
			String convertedFilms = converter.convertFilms(films, format);  //convert films to the requested format using the FilmConverter class
			if (convertedFilms != null) {
				response.setContentType(format);  //set the Content-Type of the response
				out.write(convertedFilms);     //send the films to the client
			}
			else {
				out.write("Requested data format not supported.");  //if the client asked for a data format other than Json, Xml or Text
			}
		}
		catch (IOException e){   //handling IOException 
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		String dataFormat= request.getHeader("Content-Type");  //retrieve the content-type header from the request
		String rowData= request.getReader().lines().reduce("",(accumulator, actual) -> accumulator + actual);  // retrieve the data send in the request body 
		Film oneFilm =converter.decodeFilm(rowData, dataFormat, "Post"); //decode the row data back to a film object using the FilmConverter class
	
		if (validator.isNull(oneFilm)) //check if the request body is empty or couldn't be parsed successfully
		{
			out.write("Please provide valid Film details");
			out.close();
			return;
		}
		if(!validator.isValidYear(oneFilm.getYear())){    //year validation- year must be between 1888 and 2024           
			out.write("Failed! Year must be between 1888 & 2024");
			out.close();
			return;
		}
		if(!validator.isValidFilm(oneFilm)) //input validation to ensure all film details provided
		{
			out.write("Invalid Film details provided.");
			out.close();
			return;
		}                             
		if(dao.insertFilm(oneFilm)){     //insert the film into the database                                          
			out.write("Yes ! Film with title: "+oneFilm.getTitle()+" Inserted Successfully");
		}
		else {                           //if an error occurs while inserting the film
			out.write("Failed to insert Film with title: "+oneFilm.getTitle());
		}
		out.close();  //close the PrintWriter object
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");  //sending the cache-control header to prevent caching
		response.setHeader("Pragma", "no-cache");           //sending the pragma header to prevent caching
		String dataFormat=request.getHeader("Content-Type");
		String rowData = request.getReader().lines().reduce("",(accumulator, actual) -> accumulator + actual);
		Film oneFilm=converter.decodeFilm(rowData, dataFormat, "Delete");  //decode the row data back to a film object
		
		if(validator.isNull(oneFilm)){             //Check if the request body was empty or could not be parsed successfully
			out.write("Please provide valid Film ID");
			out.close();
			return;
		}
		int id=oneFilm.getId();
		if(dao.getFilmByID(id) != null) {	  //check if a the film is stored in the database
			if(dao.deleteFilm(id)){            //delete the film from the database
				out.write("Yes ! Film with ID: "+id+" deleted successfully.");
			}
			else {                              //if an error occurs while deleting the film this error message will be displayed
				out.write("Failed to delete film ! Please try again");
			}
		}
		else {     //if the film is not found in the database this error message will be displayed
			out.write("Failed! Film with ID: "+ id +" not found");
		}
		out.close();
	}
	//Author: chaimaJebri
	protected void doPut(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");		
		String dataFormat= request.getHeader("Content-Type");
		String rowData = request.getReader().lines().reduce("",(accumulator, actual) -> accumulator + actual);
		Film oneFilm=converter.decodeFilm(rowData, dataFormat,"Put");  //decode the data received in the request back to a film object
		
		if(validator.isNull(oneFilm)) {            //Check if the request body was empty or could not be parsed successfully
			out.write("Please provide valid Film details");
			out.close();
			return;
		}
		int id=oneFilm.getId();   // get the id of the film
		if(!validator.isValidYear(oneFilm.getYear())){         //year validation              
			out.write("Failed! Year must be between 1888 & 2024");
			out.close();
			return;
		}
		if(!validator.isValidFilm(oneFilm)){   //input validation to ensure all required details are provided
			out.write("Invalid Film details provided.");
			out.close();
			return;
		} 
		if(dao.getFilmByID(id) != null){     //checks if the film already exists in the database using the film ID
				if(dao.updateFilm(oneFilm)) {   //updating the film
					out.write("Yes ! Film with ID: "+id+" updated successfully");
				}
				else {    //if an error occurs while updating the film, this message will be displayed 
					out.write("Failed to update Film! Please try again");
				}
		}
		else {              //if film is not stored in the database
			out.write("Film with ID: "+ id +" not found");
		}
		out.close();
	}
}
