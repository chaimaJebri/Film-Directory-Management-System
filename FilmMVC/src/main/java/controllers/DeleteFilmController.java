package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.FilmDAOEnum;

/**
 * The DeleteFilmController class handles requests to delete a film object from the database based on a provided ID
 * 
 * @author Chaima Jebri
 */
@WebServlet("/deleteFilmController")
public class DeleteFilmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The data access object for handling operations related to film objects.
	 */
    private FilmDAOEnum dao;
    
    /**
     * Constructs a new DeleteFilmController object and sets up the FilmDAOEnum instance.
     */
    public DeleteFilmController() 
    {
        super();
        dao=FilmDAOEnum.INSTANCE;
    }
    /**
     * Handles GET requests to delete a film object. It retrieves the filmID from the parameters of the request,
     * deletes the film then passes the boolean flag to a JSP page to display the result to the client.
     * 
     * @param request The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object containing the response information.
     * @throws ServletException if the servlet encounters an error
     * @throws IOException if an I/O error occurs
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int id= Integer.parseInt(request.getParameter("id"));
		boolean deleted = dao.deleteFilm(id);
		request.setAttribute("id", id);
		request.setAttribute("deleted", deleted);
		RequestDispatcher dispatcher = request.getRequestDispatcher("deleteFilmResult.jsp");
		dispatcher.include(request, response);
	}
}
