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
 * The RetrieveFilmsController class handles requests to get all film instances stored in the database
 * 
 * @author Chaima Jebri
 */
@WebServlet("/retrieveFilmsController")
public class RetrieveFilmsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	/**
    * The data access object for handling operations related to film objects.
    */
	private FilmDAOEnum dao;
    
    /**
     * Constructs a new RetrieveFilmsController object and sets up the FilmDAOEnum instance.
     */
    public RetrieveFilmsController() {
        super();
        dao=FilmDAOEnum.INSTANCE;
    }
    
    /**
     * Handles GET requests to fetch all film instances from the database. It stores them in an ArrayList
     * then passes the ArrayList to a JSP Page so they can be displayed to the user
     * 
     * @param request The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object containing the response information.
     * @throws ServletException if the servlet encounters an error
     * @throws IOException if an I/O error occurs
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ArrayList<Film> films = dao.getAllFilms();
		request.setAttribute("films", films);
		RequestDispatcher dispatcher= request.getRequestDispatcher("allFilms.jsp");
		dispatcher.include(request, response);
	}
}


