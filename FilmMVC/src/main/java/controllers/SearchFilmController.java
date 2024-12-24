package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import models.Film;
import models.FilmDAOEnum;

/**
 * The SearchFilmController class handles requests to search for specific films by title, year or director.
 * It retrieves the searched films from the database then forward them to a JSP page to be displayed.
 * 
 * @author Chaima Jebri
 */
@WebServlet("/searchFilmController")
public class SearchFilmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
     * The data access object for handling operations related to film objects.
     */
	private FilmDAOEnum dao;
    
    /**
     * Constructs a new SearchFilmController object and sets up the FilmDAOEnum instance.
     */
    public SearchFilmController() 
    {
        super();
        dao=FilmDAOEnum.INSTANCE;
    }
    
    /**
     * Handles GET requests to search for films by title, director or year. This method retrieves the search input from the request
     * parameters, ensures that the input is valid (not an empty input) then gets the specific films from the database.
     * Those films are stored in an ArrayList then passed to a JSP page to display them.
     * 
     * @param request The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object containing the response information.
     * @throws ServletException if the servlet encounters an error
     * @throws IOException if an I/O error occurs
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String searchStr= request.getParameter("searchStr").trim();	
		if(searchStr.isEmpty())
		{
			request.setAttribute("errorMessage1", "Please type something to search!");
			RequestDispatcher dispatcher =request.getRequestDispatcher("searchFilmResults.jsp");
			dispatcher.forward(request, response);
			return;
		}	
		ArrayList<Film> searchResults = dao.searchFilm(searchStr);	
		if (!searchResults.isEmpty())
		{
			request.setAttribute("searchResults", searchResults);
		}
		else
		{
			request.setAttribute("errorMessage2", "No Films Found with those details!");
		}
		RequestDispatcher dispatcher =request.getRequestDispatcher("searchFilmResults.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * Handles POST requests by delegating to the doGet method
	 * 
     * @param request The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object containing the response information.
     * @throws ServletException if the servlet encounters an error
     * @throws IOException if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request,response);
	}
}
