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
 * The UpdateFilmController class handles requests to update an existing film in the database.
 * 
 * @author Chaima Jebri
 */
@WebServlet("/updateFilmController")
public class UpdateFilmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
     * The data access object for handling operations related to film objects.
     */
	private FilmDAOEnum dao;

    /**
     * Constructs a new UpdateFilmController object and sets up the FilmDAOEnum instance.
     */
    public UpdateFilmController() 
    {
        super();
        dao=FilmDAOEnum.INSTANCE;
    }
    
    /**
     * Handles GET requests which have the ID of the film to be updated as a parameter, it searches for the film in the database
     * then passes it to a JSP file to display its details in a form where the user can made the necessary changes to it.
     * 
     * @param request The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object containing the response information.
     * @throws ServletException if the servlet encounters an error
     * @throws IOException if an I/O error occurs
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int id =Integer.parseInt(request.getParameter("id"));
		Film oneFilm=dao.getFilmByID(id);
		if(oneFilm!=null)
		{
			request.setAttribute("oneFilm", oneFilm);
		}
		else
		{
			request.setAttribute("id", id);
			request.setAttribute("filmNotFound", true);
		}
		RequestDispatcher dispatcher= request.getRequestDispatcher("updateFilmForm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Handles POST requests. The POST request has the new details of the film to be updated.
	 * After retrieving those details from the request body, the doPost method performs validation checks on the user input then proceeds with the film
	 * editing.
	 * 
     * @param request The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object containing the response information.
     * @throws ServletException if the servlet encounters an error
     * @throws IOException if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ArrayList<String> errors = new ArrayList<>();
		int id =Integer.parseInt(request.getParameter("id"));
		String title=request.getParameter("title");
		int year=0;
		try
		{
			year=Integer.parseInt(request.getParameter("year"));
		}
		catch(NumberFormatException e)
		{
			System.out.println("The year provided cannot be parsed");  //to be displayed in the console for debugging purposes 
			errors.add("Enter A Valid Year");
		}
		if(!((year>=1888)&&(year<=2024)))
		{
			errors.add("Year must be between 1888 & 2024");
		}
		String director=request.getParameter("director");
		String stars = request.getParameter("stars");
		String review =request.getParameter("review");
		if (title.trim().isEmpty() || director.trim().isEmpty() || stars.trim().isEmpty() || review.trim().isEmpty()) 
		{
		    errors.add("Enter Valid Details for Title, Director, Stars, and Review");
		}
		if(!errors.isEmpty())
		{
			request.setAttribute("errors", errors);
			request.setAttribute("id", id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("updateFilmForm.jsp");
			dispatcher.include(request, response);
			return;
		}
		
		Film oneFilm = new Film(id,title,year,director,stars,review);
	    boolean updated = dao.updateFilm(oneFilm);
	    request.setAttribute("oneFilm", oneFilm);
	    request.setAttribute("updated", updated);
	    RequestDispatcher dispatcher= request.getRequestDispatcher("updateFilmResult.jsp");
		dispatcher.forward(request, response);
	}

}
