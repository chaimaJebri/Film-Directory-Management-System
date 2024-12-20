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
 * The InsertFilmController class handles requests to insert a new film into the database
 * 
 * @author Chaima Jebri
 */
@WebServlet("/insertFilmController")
public class InsertFilmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
     * The data access object for handling operations related to film objects.
     */
	private FilmDAOEnum dao;
   
    /**
     * Constructs a new InsertFilmController object and sets up the FilmDAOEnum instance.
     */
    public InsertFilmController() 
    {
        super();
        dao=FilmDAOEnum.INSTANCE;
    }
    
    /**
     * Handles GET requests to pass the user to a JSP file where he can fill a form with the details 
     * of the new film to be added.
     * 
     * @param request The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object containing the response information.
     * @throws ServletException if the servlet encounters an error
     * @throws IOException if an I/O error occurs
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("insertFilmForm.jsp");
		dispatcher.include(request, response);
	}
	
	/**
	 * Handles POST requests that have the film details to be added into the database.
	 * It retrieves the details from the request body, performs some validation checks on the user inputs then adds the film 
	 * to the database. The result of the insertion will be forwarded to a JSP file to be displayed to the client.
	 * 
     * @param request The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object containing the response information.
     * @throws ServletException if the servlet encounters an error
     * @throws IOException if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ArrayList<String> errors = new ArrayList<>();
		String title = request.getParameter("title");
		int year=0;
		try
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
		catch(NumberFormatException e)
		{
			errors.add("Enter A Valid Year");
		}
		if(!((year>=1888)&&(year<=2024)))
		{
			errors.add("Year must be between 1888 & 2024");
		}
		String director = request.getParameter("director");
		String stars = request.getParameter("stars");
		String review = request.getParameter("review");
		if (title.trim().isEmpty() || director.trim().isEmpty() || stars.trim().isEmpty() || review.trim().isEmpty()) 
		{
		    errors.add("Enter Valid Details for Title, Director, Stars, and Review");
		}
		if(!errors.isEmpty())
		{
			request.setAttribute("errors", errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher("insertFilmForm.jsp");
			dispatcher.include(request, response);
			return;
		}
		Film oneFilm = new Film(title,year,director,stars,review);
		boolean inserted = dao.insertFilm(oneFilm);
		
		request.setAttribute("oneFilm", oneFilm);
		request.setAttribute("inserted", inserted);
		RequestDispatcher dispatcher = request.getRequestDispatcher("insertFilmResult.jsp");
		dispatcher.include(request, response);
	}

}
