package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The FilmDAOEnum class is the data access object that provides an interface to the films table in MySQL database.
 * It contains methods to retrieve, add, update, delete and search films.
 * This class uses the Singleton ENUM design pattern to ensure it has only one instance throughout the application.
 * 
 * @author Chaima Jebri
 */
public enum FilmDAOEnum {

	/**
	 * The Singleton Instance
	 */
	INSTANCE;
	
	private String user = "your_db_username";
    private String password = "you_db_password";
    private String url = "jdbc:mysql://MySQL_server_address:6306/"+user;


   /**
    * Establishes the data base connection 
    * 
    * @return the database connection object
    */
   private Connection openConnection()
	{
		Connection dbConn=null;
			try
			{
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch(Exception e) 
			{ 
				System.out.println(e); 
		    }
			try
			{
	 			dbConn = DriverManager.getConnection(url, user, password);
			} 
			catch(SQLException se) 
			{ 
				System.out.println(se); 
			}	  
	 
		return dbConn;
    }

   /**
    * Retrieves the next film object from the database
    * 
    * @param rslt  the ResultSet containing the film data
    * @return A film object representing the next film in the database
    */
   private Film getNextFilm(ResultSet rslt)
	{
    	Film Film=null;
    	
		try 
		{
			Film = new Film(
					rslt.getInt("id"),
					rslt.getString("title"),
					rslt.getInt("year"),
					rslt.getString("director"),
					rslt.getString("stars"),
					rslt.getString("review"));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
    	return Film;		
	}
	
   /**
    * Retrieves all film objects from the films table in the database
    * 
    * @return an ArrayList containing all film objects
    */
   public ArrayList<Film> getAllFilms()
   {
		ArrayList<Film> allFilms = new ArrayList<Film>();
	    String selectSQL = "select * from films";
	    
		try (Connection dbConn =openConnection();
			 PreparedStatement stmnt=dbConn.prepareStatement(selectSQL);
			 ResultSet rslt = stmnt.executeQuery())
		{
		    
		    while(rslt.next())
		    {
		    	Film oneFilm = getNextFilm(rslt);
		    	allFilms.add(oneFilm);
		    }
		    
		} 
		catch(SQLException se) 
		{ 
			System.out.println(se); 
		}

	   return allFilms;
   }

   /**
    * Retrieves a film object from the database based on its ID
    * 
    * @param id  the ID of the film to be retrieved
    * @return The film object which can be null if it's not found in the database
    */
   public Film getFilmByID(int id)
   {
	   String selectSQL = "select * from films where id=?";
	   Film oneFilm=null;
	   
	   try (Connection dbConn = openConnection();
			PreparedStatement stmnt = dbConn.prepareStatement(selectSQL))
	   {
		   stmnt.setInt(1, id);
		   
		   try (ResultSet rslt = stmnt.executeQuery())
		   {
			   while(rslt.next())
			   {
				   oneFilm=getNextFilm(rslt);
			   }
		   }
	   }
	   catch(SQLException e)
	   {
		   System.out.print(e.getMessage());
	   }
	   return oneFilm;
   }
  
   /**
    * Deletes a film from the database based on the given ID
    * 
    * @param id  the ID of the film to be deleted
    * @return true if the film is successfully deleted, false if not
    */
   public boolean deleteFilm (int id)
   {
	   boolean deleted =false;
	   String deleteSQL ="delete from films where id=?";
	   
	   try(Connection dbConn =openConnection();
		   PreparedStatement stmnt = dbConn.prepareStatement(deleteSQL))
	   {
		   stmnt.setInt(1, id);
		   int rows =stmnt.executeUpdate();
		   if(rows>0)
		   {
			   deleted=true;
			   System.out.println("Yes ! Film deleted successfully");
		   }
		   else
		   {
			   System.out.println("Failed to delete Film!");
		   }
	   }
	   catch (SQLException e)
	   {
		   System.out.println(e.getMessage());
	   }
	   return deleted;
   }

   /**
    * Inserts a new film object in the database
    * 
    * @param film  the film object to be added
    * @return true if added successfully, false if not
    */
   public boolean insertFilm(Film film)
   {
	   boolean inserted=false;
	   String insertSQL="insert into films (title,year,director,stars,review) values(?,?,?,?,?)";
	   
	   try(Connection dbConn =openConnection();
		   PreparedStatement stmnt=dbConn.prepareStatement(insertSQL))
	   {
		   stmnt.setString(1, film.getTitle());
		   stmnt.setInt(2, film.getYear());
		   stmnt.setString(3, film.getDirector());
		   stmnt.setString(4, film.getStars());
		   stmnt.setString(5, film.getReview());
		   
		   int rows=stmnt.executeUpdate();
		   if(rows>0)
		   {
			   inserted=true;
			   System.out.println("Yes ! Film inserted successfully");
		   }
		   else
		   {
			   System.out.println("Failed to insert Film!");
		   }
	   }
	   catch (SQLException e)
	   {
		   System.out.println(e.getMessage());
	   }
	   
	   return inserted;
   }
  
   /**
    * Updates a film object in the database
    * 
    * @param film  the new details of the film to be updated including its ID
    * @return true if film updated successfully, false if not
    */
   public boolean updateFilm(Film film)
   {
	   boolean updated=false;
	   String updateSQL="update films set title=? , year=?, director=? , stars=?, review=? where id=?";
	   
	   try (Connection dbConn =openConnection();
		   PreparedStatement stmnt=dbConn.prepareStatement(updateSQL))
	   {
		   stmnt.setString(1, film.getTitle());
		   stmnt.setInt(2, film.getYear());
		   stmnt.setString(3, film.getDirector());
		   stmnt.setString(4, film.getStars());
		   stmnt.setString(5, film.getReview());
		   stmnt.setInt(6, film.getId());
		   
		   int rows=stmnt.executeUpdate();
		   if (rows>0)
		   {
			   updated=true;
			   System.out.println("YES! film updated");
		   }
		   else
		   {
			   System.out.println("Failed to update Film!");
		   }
	   }
	   catch (SQLException e)
	   {
		   System.out.println(e.getMessage());
	   }
	   return updated;
   }
   
   /**
    * Searches for specific films by title, year or director
    * 
    * @param searchStr  the search string that may match films title, director or year
    * @return an Arraylist containing the searched films, can be empty if no films found
    */
   public ArrayList<Film> searchFilm(String searchStr)
   {
	   ArrayList<Film> searchedFilms = new ArrayList<>();
	   String searchSQL ="select * from films where title like ? OR director like ?";
	   int year= -1;
	   try
	   {
		   year =Integer.parseInt(searchStr);
		   searchSQL=searchSQL+" OR year = ? ";
	   }
	   catch(NumberFormatException e)
	   { 
		   System.out.println("the search input: "+searchStr+" cannot be parsed to an integer"); 
	   }
	   try(Connection dbConn = openConnection();
		   PreparedStatement stmnt = dbConn.prepareStatement(searchSQL))
	   {
		   stmnt.setString(1, "%"+searchStr+"%");
		   stmnt.setString(2, "%"+searchStr+"%");
		   if (year != -1) { stmnt.setInt(3, year);}
		   try(ResultSet rslt=stmnt.executeQuery())
		   {
			   while (rslt.next())
			   {
				   Film oneFilm=getNextFilm(rslt);
				   searchedFilms.add(oneFilm);
			   }
		   }
	   }
	   catch(SQLException e)
	   {
		   System.out.println(e.getMessage());
	   }
	   return searchedFilms;
   }

}
