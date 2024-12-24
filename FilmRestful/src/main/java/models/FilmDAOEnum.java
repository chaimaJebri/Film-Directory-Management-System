package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public enum FilmDAOEnum {

	INSTANCE;   // the Singleton instance
	
		//Mudfoot
		private String user = "your_db_username";
	    private String password = "your_db_password";
	    private String url = "jdbc:mysql://MySQL_server_address:6306/"+user;

		//AWS
		//private String user = "your_AWS_username";
		//private String password = "your_AWS_password";
		//private String url = "your_AWS_RDS_URL";
		
	    //Azure
	    //private String user = "your_Azure_username";
	    //private String password = "your_Azure_password";
	    //private String url = "your_Azure_MySQL_URL";
	
	   
	    //open the database connection
	   private Connection openConnection()
		{
			Connection dbConn=null; //initialize a database connection 
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
		 
			return dbConn; //returns the established database connection
	    }
		
	   //getting the next film stored in the database
	   private Film getNextFilm(ResultSet rslt)
		{
	    	Film Film=null;
	    	
			try 
			{  //extracting the film details from the resultset and creating a film object
				Film = new Film(
						rslt.getInt("id"),
						rslt.getString("title"),
						rslt.getInt("year"),
						rslt.getString("director"),
						rslt.getString("stars"),
						rslt.getString("review"));
			} 
			catch (SQLException e) //handling SQL exceptions
			{
				e.printStackTrace();
			}
			//return the film object
	    	return Film;		
		}
		//retrieving all films from the database
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

	   //retrieving a film from the database based on the provided ID
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
		   return oneFilm; //if the film is found return it otherwise return null
	   }
	  //delete a film from the database using it's id
	   //returns true if the film is deleted false if not
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
				   System.out.println("Yes ! Film deleted successfully");//if the film is deleted 
			   }
			   else
			   { //if the film couldn't be deleted e.g. not found in the database
				   System.out.println("Failed !");
			   }
		   }
		   catch (SQLException e) //handling SQL exception
		   {
			   System.out.println(e.getMessage());
		   }
		   return deleted;
	   }

	   //inserting a new film in the database 
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
			   if(rows>0) //if a row or more in the database table is affected means the film is added
			   {
				   inserted=true;
				   System.out.println("Yes ! Film inserted successfully");
			   }
			   else
			   {
				   System.out.println("Failed !"); //if an error occurs while inserting the film
			   }
		   }
		   catch (SQLException e)
		   {
			   System.out.println(e.getMessage());
		   }
		   
		   return inserted; //true if film added, false if not
	   }
	   
	   //updating an existing film in the database based on it's ID
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
			   if (rows>0) //if a row or more are affected means the film is updated
			   {
				   updated=true;
				   System.out.println("YES! film updated");
			   }
			   else
			   {
				   System.out.println("Failed!"); //if an error occurs while updating the film
			   }
		   }
		   catch (SQLException e)
		   {
			   System.out.println(e.getMessage());
		   }
		   return updated; //true if film updated, false if not
	   }
	   
	   //searching for films by title, year or director using a dynamic SQL statement
	   public ArrayList<Film> searchFilm(String searchStr)
	   {
		   ArrayList<Film> searchedFilms = new ArrayList<>();
		   String searchSQL ="select * from films where title like ? OR director like ?";
		   int year= -1; //initializing the year to -1 to indicate later if the search string can be parsed or not
		   try
		   {
			   year =Integer.parseInt(searchStr); //parsing the search string
			   searchSQL=searchSQL+" OR year = ? "; //if it's parsed, append it to the SQL query
		   }
		   catch(NumberFormatException e)
		   {  //if the year couldn't be parsed to an integer
			   System.out.println("the search input: "+searchStr+" cannot be parsed to an integer"); 
		   }
		   try(Connection dbConn = openConnection();
			   PreparedStatement stmnt = dbConn.prepareStatement(searchSQL))
		   {  //setting the params for the SQL statement
			   stmnt.setString(1, "%"+searchStr+"%");
			   stmnt.setString(2, "%"+searchStr+"%");
			   if (year != -1) { stmnt.setInt(3, year);} //if the year is valid set it in the SQL stmnt
			   try(ResultSet rslt=stmnt.executeQuery())  //execute the query
			   {
				   while (rslt.next())
				   {      //get the searched films from the database
					   Film oneFilm=getNextFilm(rslt);
					   searchedFilms.add(oneFilm);
				   }
			   }
		   }
		   catch(SQLException e)
		   { //handling SQL exceptions
			   System.out.println(e.getMessage());
		   }
		   return searchedFilms; //return the searched films
	   }
	 //Author: chaimaJebri
}
