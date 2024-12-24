$(document).ready(function()
{
	var endPoint = './film-api';
	var defaultFormat='json'; //When the dropdown doesn't have a value yet, JSON will be the default format

	//Update Film
	$(document).on('click', '.updateButton', function() {
		
		var filmId=$(this).val();
		var row=$(this).closest('tr');
        
        $('#id-update').val(filmId);         //fill the modal inputs with the current film details (id will be a hidden input)
        $('#title-updated').val(row.find('td:eq(1)').text());
        $('#year-updated').val(row.find('td:eq(2)').text());
        $('#director-updated').val(row.find('td:eq(3)').text());
        $('#stars-updated').val(row.find('td:eq(4)').text());
        $('#review-updated').val(row.find('td:eq(5)').text());
        $('#updateFilmModal').modal('show');                    //display the modal with the current film details    
	});	
	
	$('#saveChangesButton').on('click', function(){
		
		var selectedFormat = $('#format').val()|| defaultFormat;      //better to keep it as a local variable to reflect the current value of the dropdown menu otherwise it can lead to difficulties in tracking changes
		var updatedFilm= {
			id:$('#id-update').val(),
			title:$('#title-updated').val(),
			year: $('#year-updated').val(),
			director:$('#director-updated').val(),
			stars:$('#stars-updated').val(),
			review:$('#review-updated').val()
		};
		
		$.ajax({
			url:endPoint,
			type:'PUT',
			cache:false,
			data: filmData(selectedFormat,updatedFilm,'update'),
			headers: {'Content-Type': appropriateHeader(selectedFormat)},
			success: function(response) 
            { 
				$('#updateFilmModal').modal('hide');     //model will be hidden then after 3s the result from the server will popup in an alert box
                setTimeout(function(){
					alert(response);
				},300);
				retrieveFilms(selectedFormat);    //refresh the page to get all films including the updated one
            }, 
            error: function(error) 
            {
                console.log("Error occurred while updating a film: " + error);
            }
		});
	});
	
	//Delete Film
	$(document).on('click', '.deleteButton', function() {
		
		var filmId=$(this).val();
		$('#deleteFilmModal').data('filmId', filmId);
		$('#deleteFilmModal').modal('show');
	});	
		
	$('#deleteButtonModel').on('click', function(){	
		
		var selectedFormat = $('#format').val()|| defaultFormat;
		var filmId=$('#deleteFilmModal').data('filmId');       //retrieving the filmId from the modal data
		var Film={ id:filmId };
		
		$.ajax({
			url:endPoint,
			type:'DELETE',
			cache:false,
			data:filmData(selectedFormat,Film,'delete'),
			headers:{'Content-Type': appropriateHeader(selectedFormat)},
			success: function(response) 
            {             
				$('#deleteFilmModal').modal('hide');              //model will be hidden then after 3s the result from the server will popup in an alert box
                setTimeout(function(){
					alert(response);
				},300);
				retrieveFilms(selectedFormat);                 //refresh the page
            }, 
            error: function(error) 
            {
                console.log("Error occurred while deleting a film: " + error);
            }
		});
	});
	
	//Add new film
	$('#addFilmModal').on('show.bs.modal', function () {
		$(this).find('input[type=text], input[type=number]').val(''); //clear the addFilmModal inputs for next use
	 });
	 
	$('#addButtonModel').on('click', function(){
		
		var selectedFormat = $('#format').val()|| defaultFormat;
		var newFilm={                                            //read user input from the DOM
			title: $('#title-added').val(),
			year:$('#year-added').val(),
			director:$('#director-added').val(),
			stars:$('#stars-added').val(),
			review:$('#review-added').val()
		};
		
		$.ajax({
			url:endPoint,
			type:'POST',
			cache:false,
			data: filmData(selectedFormat,newFilm,'add'),
			headers: {'Content-Type': appropriateHeader(selectedFormat)},
			success: function(response) 
            { 
				$('#addFilmModal').modal('hide');    //model will be hidden then after 3s the result 
                setTimeout(function(){                //from the server will popup in an alert box
					alert(response);
				},300);
				retrieveFilms(selectedFormat);   //refresh the page to get all films including the new one
            }, 
            error: function(error) 
            {
                console.log("Error occurred while inserting a new film: " + error);
            }
		});
	});
	
	//Formating the film details before sending it to the server based on the selectedFormat
	function filmData(format,film, operation)
	{
		var result;
		switch(format)
		{
			case 'json':
					result=JSON.stringify(film);
					break;
			case 'xml':        //for the not provided elements it'll be undefined (we can do it based on the operation as we did with the text format, but this one is brief and also works)
					result='<film><id>'+film.id+'</id><title>'+film.title+'</title><year>'+film.year+'</year><director>'
					+film.director+'</director><stars>'+film.stars+'</stars><review>'+film.review+'</review></film>';
					break;
			case 'text':  //formatting the text data based on the operation
				{
					if(operation==='add') //to add a film no need to specify the ID as it is auto-increment
					{
						result=film.title+'#'+film.year+'#'+film.director+'#'+film.stars+'#'+film.review;
					}
					else if(operation==='update') // to update a film we need to specify the ID
					{
						result=film.id+'#'+film.title+'#'+film.year+'#'+film.director+'#'+film.stars+'#'+film.review;
					}
					else if(operation==='delete') // we delete a film using the ID only
					{
						result=film.id;
					}
					break;
				}
			default:
				result=null;
				break;
		}
		console.log("The details sent to the server: "+result); //logging the data send to the server to the console
		return result;
	}
	
	var filmsPerPage=20; //the limit of films to display in a page
	var currentPage=1;
	
	//Get all films
	function retrieveFilms(format) 
	{
        $.ajax({
            url: endPoint,
            type: 'GET',
            crossDomain: true,
            cache:false,
            headers: {'Accept': appropriateHeader(format) },
            success: function(response) 
            { 
				totalFilms = filmsNumber(response,format);     //the number of films received in the response
				displayFilms(response,format);                 //display the first part of films initially
                startPagination(response,format);     //get the correspond part of films when clicking on pagination pages
            }, 
            error: function(error) 
            {
                console.log("Error occurred while retrieving films: " + error);
            }
        });
     }
     
    retrieveFilms(defaultFormat); //get all films initially by JSON format
     
    $('#format').change(function() {  //everytime the value of the dropdown menu changes, another get request 
		                               //is sent to the server to get all films in that format
        var selectedFormat = $(this).val();
        retrieveFilms(selectedFormat);
      });
    
    //Search for Films(by title/director/year) //result will be on the first page of the pagination
	$('#searchString').on('input', function() {      
	        var searchString = $(this).val();
	        var selectedFormat = $('#format').val()|| defaultFormat; //If the dropdown menu doesn't have a value,json will be the default format
	        var params = {
	            'search': searchString
	        };
	        $.ajax({
	            url: endPoint,
	            type: 'GET',
	            crossDomain: true,
	            cache: false,
	            data: params,
	            headers: { 'Accept': appropriateHeader(selectedFormat) },
	            success: function(response) 
	            {
					totalFilms = filmsNumber(response,selectedFormat);      //number of films received in the response
					displayFilms(response,selectedFormat);
             	    startPagination(response,selectedFormat); 
	            },
	            error: function(error) 
	            {
	                console.log("Error occurred while searching films: " + error);
	            }
	        });
	    });
	
	//Pagination
	//pagination only client side, so caution, if you click any pagination page you're not sending a request to the server
	function displayFilms(response,format) {
		 
	    var startIndex = (currentPage - 1) * filmsPerPage;
	    var endIndex = startIndex + filmsPerPage;
	    parseResponse(response,startIndex,endIndex,format);
	 }
	 
	function startPagination(response,format) {
		 
	    var pagesNumber = Math.ceil(totalFilms / filmsPerPage);
	    $('.pagination').twbsPagination(
			 {
	       		totalPages: pagesNumber,
	            visiblePages: 3,
	            onPageClick: function(event,page) 
	            {
	                currentPage = page; // value of currentPage(initialized at 1),will be updated with the page you click on it
	                displayFilms(response,format);  //films of current page will be displayed
	            }
	    });
	 } 
	 
    //the number of films received from the server (totalFilms)
	function filmsNumber(response,format)     
	{
		 if (format === 'xml') //in the case of xml & text we will receive a documentt from the server
		{
	      return $(response).find('film').length; //the number of films is the number of the XML element "film" in the response
	    } 
	    else if (format === 'json') //in the case of JSON we will receive an array from the server
	    {
	       return response.length; // the number of films is the length of the response/array
	    }
	    else if (format === 'text') 
	    {
	       return response.split('\n').length;//each film will be in a seperate line, so the number of films is the number of lines in the response
	    }
	}      
	 
	 //getting the appropriate header based on the value of the dropdown menu
	function appropriateHeader(format) 
	{
		switch (format) 
		{
            case 'xml':
                return 'text/xml';
            case 'json':
                return 'application/json';
            case 'text':
                return 'text/plain';
            default:
                return; 
        }
	}
	
	//passing the response received from the server to the appropriate method to parse it based on its format
	function parseResponse(response,startIndex,endIndex, format) 
	{
		$('table tbody').empty();
		if (format === 'xml') 
		{
	       parseXmlResponse(response,startIndex,endIndex);
	    } 
	    else if (format === 'json') 
	    {
	       parseJsonResponse(response,startIndex,endIndex);
	    }
	    else if (format === 'text') 
	    {
	       parseTextResponse(response,startIndex,endIndex);
	    }
    }
    
    //the method responsible for parsing XML data
	function parseXmlResponse(response,startIndex,endIndex) 
	{
    	var i = 0;
        $(response).find('film').each(function() //finding each film element in the response
        {
			if (i >= startIndex && i < endIndex)
			{
			   var film = $(this);
         	   var row = '<tr>' +
	                '<td>' + film.find('id').text() + '</td>' +
	                '<td>' + film.find('title').text() + '</td>' +
	                '<td>' + film.find('year').text() + '</td>' +
	                '<td>' + film.find('director').text() + '</td>' +
	                '<td>' + film.find('stars').text() + '</td>' +
	                '<td>' + film.find('review').text() + '</td>' +
	                '<td>' + '<button type="button" title="Edit" class="updateButton btn btn-outline-primary" value="'+film.find('id').text()+'"><i class="bi bi-pencil-square"></i></button>' + '</td>' +                                                                                                   //update film
                	'<td>' + '<button type="button" title="Delete" class="deleteButton btn btn-outline-danger" value="'+film.find('id').text()+'"><i class="bi bi-trash3-fill"></i></button>' + '</td>' +  
                '</tr>';
          	  $('table tbody').append(row); //append the film object to the table body
			}
			i++;
        });
    }
	
	//the method responsible for parsing JSON data
	function parseJsonResponse(response,startIndex,endIndex) 
	{
		var filmstoDisplay=response.slice(startIndex,endIndex);
        filmstoDisplay.forEach(function(film) {
            var row = '<tr>' +
                '<td>' + film.id + '</td>' +
                '<td>' + film.title + '</td>' +
                '<td>' + film.year + '</td>' +
                '<td>' + film.director + '</td>' +
                '<td>' + film.stars + '</td>' +
                '<td>' + film.review + '</td>' +
                '<td>' + '<button type="button" title="Edit" class="updateButton btn btn-outline-primary" value="'+film.id+'"><i class="bi bi-pencil-square"></i></button>' + '</td>' +                                                                                   //update film
                '<td>' + '<button type="button" title="Delete" class="deleteButton btn btn-outline-danger" value="'+film.id+'"><i class="bi bi-trash3-fill"></i></button>' + '</td>' +       //delete film
                '</tr>';
            $('table tbody').append(row);  //append the film object to the table body
        });
    }
    
    //the method responsible for parsing Text data
    function parseTextResponse(response,startIndex,endIndex) 
    {
        var lines = response.split('\n'); //split the response and store each line in the lines table
   	    var i = 0;
        lines.forEach(function(line) {
			if (i >= startIndex && i < endIndex) 
			{
				var fields = line.split('#');   //split each line using the # delimiter
          		var row = '<tr>' +
                '<td>' + fields[0] + '</td>' + //the id of the film
                '<td>' + fields[1] + '</td>' + //the title of the film
                '<td>' + fields[2] + '</td>' +  //the year of the film
                '<td>' + fields[3] + '</td>' +  //the director of the film
                '<td>' + fields[4] + '</td>' +  //the stars of the film
                '<td>' + fields[5] + '</td>' +   // the review of the film
                '<td>' + '<button type="button" title="Edit" class="updateButton btn btn-outline-primary" value="'+fields[0]+'"><i class="bi bi-pencil-square"></i></button>' + '</td>' +                                                                                      //update film
                '<td>' + '<button type="button" title="Delete" class="deleteButton btn btn-outline-danger" value="'+fields[0]+'"><i class="bi bi-trash3-fill"></i></button>' + '</td>' +       //delete film
                '</tr>';
           		 $('table tbody').append(row);  //append the film object to the table body
			}
			i++;
        });
    }
    
});

