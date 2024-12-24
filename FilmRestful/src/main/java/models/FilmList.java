package models;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
/*
 * a container class to convert a collection/array of films to XML format
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "films") //"films" is the name of the root XML element
public class FilmList {

	@XmlElement(name="film") //the name of each XML element
	private List<Film> filmsList;
	
	public FilmList() {} //default contructor
	
	public FilmList(List<Film> filmsList)  //constructs a FilmList object by passing the list of films
	{
		this.filmsList=filmsList;
	}
	
	public List<Film> getList()  //get the list of films
	{
		return this.filmsList;
	}
	
	public void setList(List<Film> filmsList)  //set the list of films
	{
		this.filmsList=filmsList;
	}
	//Author: chaimaJebri
}

