package models;

public class FilmValidator {

	//returns True is the film object is null, False if not
	public boolean isNull(Film film){
		return film == null;
	}
	
	//returns True if the year is between 1888 and 2024, False if not
	//1888 is the year when the first film was  recorded
	public boolean isValidYear(int year){
		return (year>=1888)&&(year<=2024);
	}
	
	//checks if a string is not null and not empty(white spaces for example)
	//returns True if the string is valid, False if not
	public boolean isValidString(String str){
		return (str != null) && !(str.trim().isEmpty());
	}
	
	//returns True is all film details provided are valid, otherwise it returns False
	public boolean isValidFilm(Film film){
		return (
				isValidString (film.getTitle())
				&& isValidYear(film.getYear())
				&& isValidString (film.getDirector())
				&&	isValidString (film.getStars())
				&& isValidString (film.getReview())
				);
	}
	//Author: chaimaJebri
}
