package models;

import java.io.StringWriter;
import java.util.ArrayList;
import java.io.StringReader;

import javax.servlet.ServletException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class FilmConverter {

	//to decode the data received from the client which can be JSON, XML, or Text to a film object
	public Film decodeFilm(String rowData, String dataFormat, String operation) throws ServletException {
		switch (dataFormat){
			case "application/json":
				return decodeJson(rowData);
			case "text/xml" :
				return decodeXml(rowData);
			case "text/plain":
				if ("Post".equals(operation))
					return decodeTextForPost(rowData);
				else if ("Put".equals(operation))
					return decodeTextForPut(rowData);
				else if("Delete".equals(operation))
					return decodeTextForDelete(rowData);
			default :
				throw new ServletException("Unsupported request format: "+dataFormat);
		}
	}
	
	//decode JSON data received in the request body to a film object
	public Film decodeJson(String rowData){
		try {
			Gson gson = new Gson();
			return gson.fromJson(rowData, Film.class);
		}
		catch (JsonSyntaxException e){
			System.out.println("Invalid Film details provided.");
			e.printStackTrace();
			return null;
		}
	}
	
	//decode XML data received in the request body to a film object
	public Film decodeXml(String rowData){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Film.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return(Film) unmarshaller.unmarshal(new StringReader(rowData));
		}
		catch(JAXBException e){
			System.out.println("Invalid Film details provided.");
			e.printStackTrace();
			return null;
		}
	}
	
	//decode TEXT data received in the post request to a film object
	public Film decodeTextForPost(String rowData){
		String[] filmData = rowData.split("#");
		if (filmData.length >= 5){
			try {
				String title= filmData[0].trim();
				int year=Integer.parseInt(filmData[1].trim());
				String director=filmData[2].trim();
				String stars=filmData[3].trim();
				String review=filmData[4].trim();
				return new Film(title,year,director,stars,review);
			}
			catch (NumberFormatException e){                   // in case a failure to parse the year input
				System.out.println("The Year provided couldn't be parsed");
				return null;
			}
		}
		else {
			System.out.println("Not enough data provided to insert a new film in Text format");  //if the client didn't provide all data for the text format
			return null;
		}
	}
	
/*
 * I'm using 3 different methods for the Text format as for the post request the details received from the client will not include 
 * the ID of the film, but in the put request all film details will be provided including the ID
 * For the delete request only the ID of the film will be provided
 */
	//decode TEXT data received in the put request to a film object
	public Film decodeTextForPut(String rowData)
	{
		String [] filmData= rowData.split("#");  //ensuring i got from the client id#title#year#director#stars#review
		if (filmData.length >= 6){
			try {
				int id=Integer.parseInt(filmData[0].trim());
				String title= filmData[1].trim();
				int year=Integer.parseInt(filmData[2].trim());
				String director=filmData[3].trim();
				String stars=filmData[4].trim();
				String review=filmData[5].trim();
				return new Film(id,title,year,director,stars,review);
			}
			catch (NumberFormatException e){     //in case a failure to parse the ID or the year input
				System.out.println("The ID or Year provided couldn't be parsed");
				return null;
			}
		}
		else {                  //client didn't provide all data needed to update a film in text format
			System.out.println("Not enough data provided to update a film in Text format");  //if the client didn't provide all data for the text format
			return null;
		}
	}
	
	//decode TEXT data received in the delete request to a film object
	public Film decodeTextForDelete(String rowData)
	{
		try{
			Film film = new Film();
			film.setId(Integer.parseInt(rowData.trim()));
			return film;
		}
		catch (NumberFormatException e){
			System.out.println("Invalid ID Format provided");
			return null;
		}
	}
	
	//to convert the Films arrayList to JSON, XML, or Text
	public String convertFilms(ArrayList<Film> films, String format){
		switch (format){
			case "application/json" :
				return filmsToJson(films);
			case "text/xml" :
				return filmsToXml(films);
			case "text/plain" :
				return filmsToText(films);
			default :
				return null;
		}
	}
	
	//encoding the list of films to JSON format
	public String filmsToJson (ArrayList<Film> films){
		Gson gson = new Gson();
		return gson.toJson(films);
	}
	
	//encoding the list of films to XML format
	public String filmsToXml(ArrayList<Film> films){
		try {
			FilmList filmsList = new FilmList(films);
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(FilmList.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			marshaller.marshal(filmsList,writer);
			return writer.toString();
		}
		catch (JAXBException e){
			e.printStackTrace();
			return "Failed to get all films in XML format";
		}
	}
	//Author: chaimaJebri
	//encoding the list of films to Text format
	public String filmsToText(ArrayList<Film> films){
		StringBuilder plainText = new StringBuilder();
		for(Film film : films){
			plainText.append(film.getId()+"#"+film.getTitle()+"#"+film.getYear()+"#"+ film.getDirector()
			+"#"+film.getStars()+"#"+film.getReview()+"\n");
		}
		return plainText.toString();
	}
	
}
