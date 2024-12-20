package models;

/**
 * The Film class is a blueprint to create film objects. It has setters and getters methods for the different 
 * attributes also a method to generate a string representation for a film object.
 * 
 * @author Chaima Jebri
 */
public class Film {
	
	private int id;             //Primary Key & Auto-increment in the database
	private String title;
	private int year;
	private String director;
	private String stars;
	private String review;
	
	/**
	 * Default constructor to create a film object
	 */
	public Film() 
	{
		super();
	}
	
	/**
	 * Constructs a film object with the given title, year, director, stars and review.
	 * 
	 * @param title  the title of the film
	 * @param year  the release year of the film 
	 * @param director  the director of the film
	 * @param stars the stars of the film
	 * @param review  the review of the film
	 */
	public Film(String title, int year, String director, String stars,String review)
	{
		this.title = title;
		this.year = year;
		this.director = director;
		this.stars = stars;
		this.review = review;
	}
	
	/**
	 * Constructs a film object with the given ID, title, year, director, stars and review.
	 * 
	 * @param id the unique identifier of the film object which is auto-increment
	 * @param title the title of the film
	 * @param year the release year of the film
	 * @param director the director of the film
	 * @param stars the stars of the film
	 * @param review the review of the film
	 */
	public Film(int id, String title, int year, String director, String stars,String review) 
	   {
			super();
			this.id = id;
			this.title = title;
			this.year = year;
			this.director = director;
			this.stars = stars;
			this.review = review;
		}

	/**
	 * gets the unique identifier of the film
	 * 
	 * @return the unique identifier of the film
	 */
	public int getId() 
	{
		return id;
	}
	
	/**
	 * Sets the ID of the film
	 * 
	 * @param id the ID of the film
	 */
	public void setId(int id) 
	{
		this.id = id;
	}
	
	/**
	 * gets the title of the film
	 * 
	 * @return the title of the film
	 */
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * Sets the title of the film
	 * 
	 * @param title the title of the film
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}
	/**
	 * gets the release year of the film
	 * 
	 * @return the year of the film
	 */
	public int getYear() 
	{
		return year;
	}

	/**
	 * Sets the release year of the film 
	 * 
	 * @param year the year of the film
	 */
	public void setYear(int year) 
	{
		this.year = year;
	}
	
	/**
	 * gets the director of the film
	 * 
	 * @return the director of the film
	 */
	public String getDirector() 
	{
		return director;
	}
	
	/**
	 * Sets the director of the film
	 * 
	 * @param director the director of the film
	 */
	public void setDirector(String director) 
	{
		this.director = director;
	}
	
	/**
	 * gets the stars of the film
	 * 
	 * @return the stars of the film
	 */
	public String getStars() 
	{
		return stars;
	}
	
	/**
	 * Sets the stars of the film
	 * 
	 * @param stars the stars of the film
	 */
	public void setStars(String stars) 
	{
		this.stars = stars;
	}
	/**
	 * gets the review of the film
	 * 
	 * @return the review of the film
	 */
	public String getReview() 
	{
		return review;
	}
	
	/**
	 * Sets the review of the film
	 * 
	 * @param review the review of the film
	 */
	public void setReview(String review) 
	{
		this.review = review;
	}
	
	/**
	 * Generates a string representation for a film object
	 * 
	 * @return a string representing the film
	 */
	@Override
	public String toString() 
	{
		return "Film [ id= " + id + ", title= " + title + ", year= " + year
				+ ", director= " + director + ", stars= " + stars + ", review= "
				+ review + " ]";
	}   
}
