var API_BASE_URL = "http://147.83.7.207:8081/rate";

$(document).ready(function(){		
	getCookie();	
});
function getCookie() {

	if($.cookie('loginid')) {
		console.log("logeado");
		var user_tag = $.cookie('loginid');
	    $('#login_info').html('<a style="color:#e52705" href="logout.html"><strong> '+ user_tag +' </strong></a>');		 
    }
	else
	{
		console.log('no logueado');
		$('#login_info').html('<a style="color:#16e7e2" href="login.html" ><strong> Iniciar sesión - Registrarse </strong></a>');			
	}

}
/*--------------------------------------------REGISTRARSE-------------------------------------------*/
$("#registrarse").click(function(e) {
	e.preventDefault();
	if($("#log_user").val() == "" || $("#log_pass").val() == "" || $("#email_user").val() == "")
	{
		if($("#log_user").val() == "")
		{
			document.getElementById('log_user').style.background='#F6B5B5';
			$('#log_user').attr('placeholder','Usuario...');
		}
		if($("#log_pass").val() == "")
		{
			document.getElementById('log_pass').style.background='#F6B5B5';
			$('#log_pass').attr('placeholder','Contraseña...');
		}

	if($("#email_user").val() == "")
		{
			document.getElementById('email_user').style.background='#F6B5B5';
			$('#email_user').attr('placeholder','email...');
		}

	}
	else
	{
		var login = new Object();
		login.loginid = $("#log_user").val();
		login.password = $("#log_pass").val();
		login.email = $("#email_user").val();
		register(login);
	}
});
function register(login){
	console.log(login);
	var url = API_BASE_URL + '/users';
	var data = $.param(login);

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		contentType : 'application/x-www-form-urlencoded',
		dataType : 'json',
		data : data
	}).done(function(data, status, jqxhr) {
        var inf = data;
		alert("¡Te has registrado como: "+login.loginid+"! Ya puedes iniciar sesión");		

  	}).fail(function() {
		alert("Error al registrarse: Nombre de usuario ya en uso");
	});
}

/*--------------------------------------------LOGIN-------------------------------------------*/
$("#login").click(function(e) {
	e.preventDefault();
	if($("#log_user").val() == "" || $("#log_pass").val() == "")
	{
		if($("#log_user").val() == "")
		{
			document.getElementById('log_user').style.background='#F6B5B5';
			$('#log_user').attr('placeholder','Usuario...');
		}
		if($("#log_pass").val() == "")
		{
			document.getElementById('log_pass').style.background='#F6B5B5';
			$('#log_pass').attr('placeholder','Contraseña...');
		}
	}
	else
	{
		var loginn = new Object();
		loginn.loginid = $("#log_user").val();
		loginn.password = $("#log_pass").val();		
		log(loginn);
	}
});
function log(loginn){
	console.log(loginn);
	var url = API_BASE_URL + '/login';
	var data = $.param(loginn);

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		contentType : 'application/x-www-form-urlencoded',
		dataType : 'json',
		data : data
	}).done(function(data, status, jqxhr) {

		var inf = data;

		/*if(inf.loginnSuccesful!= true)*/

if($("#log_user").val() == "sgr"){
			alert("¡Usuario se llama sgr!");
		}
		else{
				alert("¡Usuario y contraseña correctos!");
			var user_token= inf.token;
			var inputname = $('#log_user').val();
			var inputpass  = $('#log_pass').val();
			
			$.cookie('loginid', inputname, { expires: 1 });
			var currentusr = $.cookie('loginid');

			$.cookie('password', inputpass, { expires: 1 });
			var currentpss = $.cookie('password');

			$.cookie('token', user_token, { expires: 1 });
			var token = $.cookie('token');
			

			console.log(user_token);
			console.log(currentusr);
			console.log(currentpss);

			alert("¡Bienvenido "+loginn.loginid+", tu id es: "+inf.userid+", y tu token de acceso es: "+inf.token+"!");
			window.location = "index.html"

		}


	}).fail(function() {
		alert("Usuario y/o contraseña incorrectos");
	});
}

/*--------------------------------------------LOGOUT-------------------------------------------*/
$('#logout').on('click', function(e){
	e.preventDefault();
	if(($.removeCookie('loginid'))&&($.removeCookie('password'))&&($.removeCookie('user_id'))){
		alert("¡Hasta pronto!");		
		window.location = "portada.html"
	}
	else
	{
		alert("¡Antes debes iniciar sesión");
	}
});

/*--------------------------------------------GETS-------------------------------------------*/
$("#button_get").click(function(e) {
	e.preventDefault();
	getGame($("#mostrarjuego").val());
});
function getGame(mostrarjuego) {			
	var url = API_BASE_URL + '/game/' + mostrarjuego;
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var game = data;

				$("#result").text();
				$('<strong> ID: ' + game.id + '</strong><br>').appendTo($('#result'));
				$('<strong> Name: </strong> ' + game.name + '<br>').appendTo($('#result'));
				$('<strong> Genre: ' + game.genre + '</strong><br>').appendTo($('#result'));
				$('<strong> Year: </strong> ' + game.year + '<br>').appendTo($('#result'));
				$('<strong> creationTimestamp: ' + game.creationTimestamp + '</strong><br>').appendTo($('#result'));
				$('<strong> lastModified: </strong> ' + game.lastModified + '<br>').appendTo($('#result'));				
				
			}).fail(function() {
				$('<div class="alert alert-danger"> Has introducido mal los datos </div>').appendTo($("#result"));
	});

}
$("#button_get2").click(function(e) {
	e.preventDefault();
	getGame2($("#juego_nombre").val());	
	getScore($("#juego_nombre").val());	
});
$("#button_get3").click(function(e) {
	e.preventDefault();	
	getGame3($("#juego_nombre").val());
	getScore3($("#juego_nombre").val());	
});
$("#button_crear_score2").click(function(e) {
	e.preventDefault();		
    var newScore = new Object();    
    newScore.gameid = $("#juego_nombre").val(); 
	newScore.score = $("#score").val();	
    createScore(newScore);
	getScore($("#juego_nombre").val());	
	
});

function getGame2(juego_nombre) {
	var url = API_BASE_URL + '/game/' + juego_nombre;
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var game = data;
				$('<div class="container">' +
'<div class="thumbnail">' +
'<div class="row">' +
'  <div class="col-sm-6" >' +
'<div class="text-left">' +
'<h2 style="color:#bdff01">Año:'+ game.year +'</h2>' +
'</div></div>' +
'  <div class="col-sm-6" >' +
'<div class="text-right">' +
'<h2 style="color:#bdff01">Puntuación:'+ game.score +'</h2>' +
'</div></div></div>' +
'<div class="text-center">' +
'<h1 style="color:#bdff01">'+game.name+'</h1>' +
'<p></p>' +
'<a href="img/portada3.jpg"><img src="img/'+game.genre+'.jpg" class="img-rounded" alt="" width="600" height="600"></a></div>' +
'<p></p>'+
'<div class="row">  ' +
'  <div class="col-sm-4" ></div><div class="col-sm-6" >' +
'  <p class="text-left"><strong> ID:</strong> ' + game.id + '<br>'+
						'<strong> Name: </strong> ' + game.name + '<br>'+
						'<strong> Genre: </strong>' + game.genre + '<br>'+	
						'<strong> Score: </strong> ' + game.score + '<br>'+
						'<strong> Year: </strong>' + game.year + '<br>'+							
						'<strong> creationTimestamp: </strong>' + game.creationTimestamp + '<br>'+
						'<strong> lastModified: </strong> ' + game.lastModified + '<br></p>' +
'  </div>' +
'  <div class="col-sm-2" >' +
'  <div class="text-left">' +
'  </div>' +
'  </div>' +
'  </div>' +
' <div class="page-header">' +
'    <p class="text-left"></p>     ' +
'  </div>' +
'  <div class="row">' +
'  <div class="col-sm-1" > </div>' +
'  <div class="col-sm-11" ></div>' +
'</div>'+
'</div>' +
'</div>').appendTo($('#result'));
						

			}).fail(function() {
				$('<div class="alert alert-danger"> Has introducido mal los datos </div>').appendTo($("#result"));
	});

}
function getScore2(juego_nombre) {
	var url = API_BASE_URL + '/game/' + juego_nombre;
	$("#nota").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var game = data;

				$("#nota").text('Score:' + game.score + '<br>').appendTo($('#nota'));

			}).fail(function() {
				$('<div class="alert alert-danger"> No tiene puntuación </div>').appendTo($("#nota"));
	});

}
function getGame3(juego_nombre) {
	var url = API_BASE_URL + '/game/gname/' + juego_nombre;
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var game = data;
				$('<div class="container">' +
'<div class="thumbnail">' +
'<div class="text-center">' +
'<h1>'+ game.name +'</h1>' +
'<p></p>' +
'<div class="row">' +
'  <div class="col-sm-10" >' +
'<a href="img/portada3.jpg"><img src="img/'+game.genre+'.jpg" class="img-rounded" alt="" width="600" height="600"></a></div>' +
'<div class="col-sm-2" ></div>' +

'</div>' +
'<div class="row">  ' +
'  <div class="col-sm-10" >' +
'  <p class="text-center"><strong> ID: ' + game.id + '</strong><br>'+
						'<strong> Name: </strong> ' + game.name + '<br>'+
						'<strong> Genre: ' + game.genre + '</strong><br>'+
						'<strong> Year: </strong> ' + game.year + '<br>'+
						'<strong> creationTimestamp: ' + game.creationTimestamp + '</strong><br>'+
						'<strong> lastModified: </strong> ' + game.lastModified + '<br></p>' +
'  </div>' +
'  <div class="col-sm-2" >' +
'  <div class="text-left">' +
'  </div>' +
'  </div>' +
'  </div>' +
' <div class="page-header">' +
'    <p class="text-left"></p>     ' +
'  </div>' +
'  <div class="row">' +
'  <div class="col-sm-1" > </div>' +
'  <div class="col-sm-11" ></div>' +
'</div>'+
'</div>' +
'</div>').appendTo($('#result'));
						

			}).fail(function() {
				$('<div class="alert alert-danger"> Juego no encontrado </div>').appendTo($("#result"));
	});

}
$("#button_get_rev2").click(function(e) {
	e.preventDefault();
	getRev2($("#juego_nombre").val());
});
function getRev2(juego_nombre) {
	var url = API_BASE_URL + '/rev';
	console.log(juego_nombre)	
	$("#comentarios").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var game = data;
if (juego_nomre = gameid){
				$('<div class="row">' +
'  <div class="row">' +
'  <div class="col-sm-1" > </div>' +
'  <div class="col-sm-9" >' +
'  <h3 class="text-left">Comentarios:</h3>' +  
'<div class="jumbotron"><div class="text-left">'+
		'<strong> ID: </strong>' + game.id + '<br>'+
				'<strong> Userid: </strong> ' + game.userid + '<br>'+
				'<strong> Gameid: </strong> ' + game.gameid + '<br>'+
				'<strong> Content: </strong> ' + game.content + '<br>'+
				'</div>'+'</div>' +
'<div class="col-sm-2" > </div>' +
'</div>' +
'</div>' +
'</div>' +
'</div>' +
'</div>').appendTo($('#comentarios'));
}

			}).fail(function() {
				$('<div class="alert alert-danger"> Comentario no encontrado </div>').appendTo($("#comentarios"));
	});

}
$("#button_get_rev").click(function(e) {
	e.preventDefault();
	getRev($("#comentar").val());
});
function getRev(comentar) {
	var url = API_BASE_URL + '/rev/' + comentar;
	$("#coment").text('');
console.log('lalala');
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
console.log('laaaaa')
				var game = data;
				$('<div class="row">' +
'  <div class="row">' +
'  <div class="col-sm-1" > </div>' +
'  <div class="col-sm-9" >' +
'  <h3 class="text-left">Comentario:</h3>' +  
'<div class="jumbotron"><div class="text-left">'+
		'<strong> ID: </strong>' + game.id + '<br>'+
				'<strong> Userid: </strong> ' + game.userid + '<br>'+
				'<strong> Gameid: </strong> ' + game.gameid + '<br>'+
				'<strong> likes: </strong> ' + game.likes + '<br>'+
				'<strong> Content: </strong> ' + game.content + '<br>'+
				'<strong> creationTimestamp: </strong> ' + game.creationTimestamp + '<br>'+
				'<strong> lastModified: </strong> ' + game.lastModified + '<br>'+				
				'</div>'+'</div>' +
'<div class="col-sm-2" > </div>' +
'</div>' +
'<button type="button"  id="button_edit_comentario">Editar comentario</button>'+
'</div>' +
'</div>' +
'</div>' +
'</div>').appendTo($('#coment'));
				

			}).fail(function() {
				$('<div class="alert alert-danger"> Comentario no encontrado </div>').appendTo($("#coment"));
	});

}
$("#button_get_score").click(function(e) {
	e.preventDefault();
	getScore($("#juego_nombre").val());
});
function getScore(juego_nombre) {
	var url = API_BASE_URL + '/game/' + juego_nombre;
	$("#score").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var game = data;

				$("#score").text('<strong> Score: ' +game.score+'</strong>').appendTo($("#score"));			
				

			}).fail(function() {
				$('<div class="alert alert-danger"> No tiene puntuación </div>').appendTo($("#score"));
	});

}
$("#button_getjuegos").click(function(e) {
	e.preventDefault();
	var url = API_BASE_URL + '/game';
	getGameSinPaginación(url);
});
function getGameSinPaginación(url) {
	$("#result").text('');
	
	$.ajax({	
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var game = data;
				
				$.each(game, function(i, v) {
					var game = v;

				$("#result").text('');
				$('<div class="col-lg-3 col-md-6">' +
					'<div class="panel panel-primary"> ' +
					'<div class="panel-heading">' +
                            '<div class="row">' +
                                '<div class="col-xs-3">' +
                                   '<img class="img-rounded" src="img/portada3.jpg" width="200" height="200"> ' +
                                '</div>' +
                                '<div class="col-xs-9 text-right">' +
                                   ' <div class="huge">'+ game.name + '</div> ' +  
									' <div class="huge">'+ game.id + '</div> ' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        '<a href="juegos.html">' +
                            '<div class="panel-footer">' +
                                '<span class="pull-left">Ver</span>' +
                                '<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>' +
                                '<div class="clearfix"></div>' +
                            '</div>' +
                        '</a>' +
                    '</div>' +
                '</div>   ').appendTo($('#result'));
				});
				

	}).fail(function() {
				$('<div class="alert alert-danger"> Juegos no encontrados </div>').appendTo($("#result"));
	});
}

/*---------------------------------Get paginacion------------------------------------------*/
$("#button_gets").click(function(e) {
	e.preventDefault();
	var url = API_BASE_URL + '/game';
	getGames(url);
});
function getGames(url) {	
	$("#result").text('');	

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
        	var response = data;
		var gameCollection = new GameCollection(response);
                var linkHeader = jqxhr.getResponseHeader('Link');
                gameCollection.buildLinks(linkHeader);

		var html = gameCollection.toHTML();
		$("#result").html(html);

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});

}
function GameCollection(gameCollection){
	this.game = gameCollection;
	var instance = this;

	this.buildLinks = function(header){
		if (header != null ) {
			this.links = weblinking.parseHeader(header);
		} else {
			this.links = weblinking.parseHeader('');
		}
	}

	this.getLink = function(rel){
                return this.links.getLinkValuesByRel(rel);
	}

	this.toHTML = function(){
		var html = '';
		$.each(this.game, function(i, v) {
			var game = v;			
			html = html.concat('<div class="col-lg-3 col-md-6">' +
					'<div class="panel panel-primary"> ' +
					'<div class="panel-heading">' +
                            '<div class="row">' +
                                '<div class="col-xs-3">' +
                                   '<img class="img-rounded" src="img/portada3.jpg" width="200" height="200"> ' +
                                '</div>' +
                                '<div class="col-xs-9 text-right">' +
                                   ' <div class="huge">'+ game.name + '</div> ' +  
									' <div class="huge">'+ game.id + '</div> ' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        '<a href="juegos.html">' +
                            '<div class="panel-footer">' +
                                '<span class="pull-left">Ver</span>' +
                                '<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>' +
                                '<div class="clearfix"></div>' +
                            '</div>' +
                        '</a>' +
                    '</div>' +
                '</div>   ');
			
		});
		
		html = html.concat(' <br> ');            

 		return html;	
	}

}
$("#button_get_comentarios").click(function(e) {
	e.preventDefault();
	var url = API_BASE_URL + '/rev?per_page=3';
	getComentarios(url);
});
function getComentarios(url) {	
	$("#result").text('');	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
        	var response = data;
		var revCollection = new RevCollection(response);
                var linkHeader = jqxhr.getResponseHeader('Link');
                revCollection.buildLinks(linkHeader);

		var html = revCollection.toHTML();
		$("#result").html(html);

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});
}
function RevCollection(revCollection){
	this.rev = revCollection;
	var instance = this;

	this.buildLinks = function(header){
		if (header != null ) {
			this.links = weblinking.parseHeader(header);
		} else {
			this.links = weblinking.parseHeader('');
		}
	}

	this.getLink = function(rel){
                return this.links.getLinkValuesByRel(rel);
	}

	this.toHTML = function(){
		var html = '';
		$.each(this.rev, function(i, v) {
			var rev = v;			
			html = html.concat('<div class="jumbotron"><div class="text-left">'+
				'<strong> ID: ' + rev.id + '</strong><br>'+
				'<strong> Userid: </strong> ' + rev.userid + '<br>'+
				'<strong> Gameid: </strong> ' + rev.gameid + '<br>'+
				'<strong> Content: </strong> ' + rev.content + '<br>'+
				'</div>'+
				'</div>');
			
		});
		
		html = html.concat(' <br> ');

 		return html;	
	}

}
$("#button_get_categorias").click(function(e) {
	e.preventDefault();
	var url = API_BASE_URL + '/rev/rev?per_page=3';
	getCategorias(url);
});
function getCategorias(url) {	
	$("#result").text('');	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
        	var response = data;
		var categoriCollection = new CategoriCollection(response);
                var linkHeader = jqxhr.getResponseHeader('Link');
                categoriCollection.buildLinks(linkHeader);

		var html = categoriCollection.toHTML();
		$("#result").html(html);

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});
}
function CategoriCollection(categoriCollection){
	this.categori = categoriCollection;
	var instance = this;

	this.buildLinks = function(header){
		if (header != null ) {
			this.links = weblinking.parseHeader(header);
		} else {
			this.links = weblinking.parseHeader('');
		}
	}

	this.getLink = function(rel){
                return this.links.getLinkValuesByRel(rel);
	}

	this.toHTML = function(){
		var html = '';
		$.each(this.categori, function(i, v) {
			var categori = v;			
			html = html.concat('<div class="col-lg-3 col-md-6">' +
					'<div class="panel panel-primary"> ' +
					'<div class="panel-heading">' +
                            '<div class="row">' +
                                '<div class="col-xs-3">' +
                                   '<img class="img-rounded" src="img/portada3.jpg" width="200" height="200"> ' +
                                '</div>' +
                                '<div class="col-xs-9 text-right">' +
                                   ' <div class="huge">'+ game.name + '</div> ' +  
									' <div class="huge">'+ game.id + '</div> ' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        '<a href="juegos.html">' +
                            '<div class="panel-footer">' +
                                '<span class="pull-left">Ver</span>' +
                                '<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>' +
                                '<div class="clearfix"></div>' +
                            '</div>' +
                        '</a>' +
                    '</div>' +
                '</div>   ');
			
		});
		
		html = html.concat(' <br> ');
 		return html;	
	}

}
$("#button_get_puntuaciones").click(function(e) {
	e.preventDefault();
	var url = API_BASE_URL + '/rev/rev?per_page=3';
	getPuntuaciones(url);
});
function getPuntuaciones(url) {	
	$("#result").text('');	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
        	var response = data;
		var pointCollection = new PointCollection(response);
                var linkHeader = jqxhr.getResponseHeader('Link');
                pointCollection.buildLinks(linkHeader);

		var html = pointCollection.toHTML();
		$("#result").html(html);

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});
}
function PointCollection(categoriCollection){
	this.point = pointCollection;
	var instance = this;

	this.buildLinks = function(header){
		if (header != null ) {
			this.links = weblinking.parseHeader(header);
		} else {
			this.links = weblinking.parseHeader('');
		}
	}

	this.getLink = function(rel){
                return this.links.getLinkValuesByRel(rel);
	}

	this.toHTML = function(){
		var html = '';
		$.each(this.point, function(i, v) {
			var point = v;			
			html = html.concat('<div class="col-lg-3 col-md-6">' +
					'<div class="panel panel-primary"> ' +
					'<div class="panel-heading">' +
                            '<div class="row">' +
                                '<div class="col-xs-3">' +
                                   '<img class="img-rounded" src="img/portada3.jpg" width="200" height="200"> ' +
                                '</div>' +
                                '<div class="col-xs-9 text-right">' +
                                   ' <div class="huge">'+ game.name + '</div> ' +  
									' <div class="huge">'+ game.id + '</div> ' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        '<a href="juegos.html">' +
                            '<div class="panel-footer">' +
                                '<span class="pull-left">Ver</span>' +
                                '<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>' +
                                '<div class="clearfix"></div>' +
                            '</div>' +
                        '</a>' +
                    '</div>' +
                '</div>   ');
			
		});
		
		html = html.concat(' <br> ');

                var prev = this.getLink('prev');
		if (prev.length == 1) {
			html = html.concat(' <a onClick="getGames(\'' + prev[0].href + '\');" style="cursor: pointer; cursor: hand;">[Anteriores]</a> ');
		}
                var next = this.getLink('next');
		if (next.length == 1) {
			html = html.concat(' <a onClick="getGames(\'' + next[0].href + '\');" style="cursor: pointer; cursor: hand;">[Siguientes]</a> ');
		}

 		return html;	
	}

}

$("#button_favoritos").click(function(e) {
	e.preventDefault();
	getMisJuegos();
});
function getMisJuegos() {

	var USERNAME = $.cookie('loginid');
	var PASSWORD = $.cookie('password');

	$.ajaxSetup({
		headers: { 'Authorization': "Basic "+ btoa(USERNAME+':'+PASSWORD) }
	});

	var url = API_BASE_URL + '/likes/' +USERNAME;

	$("#mis_juegos").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json'
	}).done(function(data, status, jqxhr) {

				var likes = data;                                    
                              
                    $('<div class="col-lg-3 col-md-6">' +
					'<div class="panel panel-primary"> ' +
					'<div class="panel-heading">' +
                            '<div class="row">' +
                                '<div class="col-xs-3">' +
                                   '<img class="img-rounded" src="img/guillermo.jpg" width="200" height="200"> ' +
                                '</div>' +
                                '<div class="col-xs-9 text-right">' +
                                   ' <div class="huge">'+ likes.reviewid + '</div> ' +                                   
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        '<a href="juegos.html">' +
                            '<div class="panel-footer">' +
                                '<span class="pull-left">Ver</span>' +
                                '<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>' +
                                '<div class="clearfix"></div>' +
                            '</div>' +
                        '</a>' +
                    '</div>' +
                '</div>   ').appendTo($('#mis_juegos'));
				

	}).fail(function() {
		$("#mis_juegos").text("¡No tienes favoritos!");
	});

}
$("#button_get_por_categorias").click(function(e) {
	e.preventDefault();
    var categoria = $("#juegosporcategoria").val();
	getJuegos_categorias(categoria);
});
function getJuegos_categorias(categoria) {
    
	var url = API_BASE_URL + '/game/categorias/' +categoria;
	$("#result").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json'
	}).done(function(data, status, jqxhr) {

				var likes = data;                                    
                              
                    $('<div class="col-lg-3 col-md-6">' +
					'<div class="panel panel-primary"> ' +
					'<div class="panel-heading">' +
                            '<div class="row">' +
                                '<div class="col-xs-3">' +
                                   '<img class="img-rounded" src="img/guillermo.jpg" width="200" height="200"> ' +
                                '</div>' +
                                '<div class="col-xs-9 text-right">' +
                                   ' <div class="huge">'+ game.categoria + '</div> ' +                                   
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        '<a href="juegos.html">' +
                            '<div class="panel-footer">' +
                                '<span class="pull-left">Ver</span>' +
                                '<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>' +
                                '<div class="clearfix"></div>' +
                            '</div>' +
                        '</a>' +
                    '</div>' +
                '</div>   ').appendTo($('#result'));
				

	}).fail(function() {
		$("#result").text("¡No tienes favoritos!");
	});

}
$("#button_get_pagination").click(function(e) {
	e.preventDefault();
	var url = API_BASE_URL + '/game/games?per_page=5';
	getGamesPagination(url);
});
function getGamesPagination(url) {	
	$("#result").text('');	

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
        	var response = data;
		var gameCollection = new GameCollection(response);
                var linkHeader = jqxhr.getResponseHeader('Link');
                gameCollection.buildLinks(linkHeader);

		var html = gameCollection.toHTML();
		$("#result").html(html);

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});

}
function GameCollection(gameCollection){
	this.game = gameCollection;

	var instance = this;

	this.buildLinks = function(header){
		if (header != null ) {
			this.links = weblinking.parseHeader(header);
		} else {
			this.links = weblinking.parseHeader('');
		}
	}

	this.getLink = function(rel){
                return this.links.getLinkValuesByRel(rel);
	}

	this.toHTML = function(){
		var html = '';
		$.each(this.games, function(i, v) {
			var game = v;			
			html = html.concat('<br><strong> Name: ' + game.name + '</strong><br>'+
			'<strong> ID: </strong> ' + game.id + '<br>');
			
		});
		
		html = html.concat(' <br> ');

                var prev = this.getLink('prev');
		if (prev.length == 1) {
			html = html.concat(' <a onClick="getGamesPagination(\'' + prev[0].href + '\');" style="cursor: pointer; cursor: hand;">[Prev]</a> ');
		}
                var next = this.getLink('next');
		if (next.length == 1) {
			html = html.concat(' <a onClick="getGamesPagination(\'' + next[0].href + '\');" style="cursor: pointer; cursor: hand;">[Next]</a> ');
		}

 		return html;	
	}

}

/*--------------------------------------------POST-------------------------------------------*/
$("#button_crear_comentario").click(function(e) {
	e.preventDefault();	
    var newComent = new Object();    
    newComent.gameid = $("#juego_nombre").val(); 
    newComent.content = $("#content").val();
	console.log(newComent.gameid);
	console.log(newComent.content);
	createComent(newComent);    

});
function createComent(newComent) {
    
	var url = API_BASE_URL + '/rev';	
	var data = $.param(newComent);
	$("#create_result").text('');
	var auth_tokens = $.cookie('token');
	/*var auth_tokens = '4ddf5787be1a11e5b0d800155d077819';*/
		$.ajax({		
			url : url,
			type : 'POST',
			crossDomain : true,
			dataType : 'json',
			data : data,
			contentType : 'application/x-www-form-urlencoded',
			headers: { "X-Auth-Token":auth_tokens	}
						
		}).done(function(data, status, jqxhr) {
			$("#create_result").empty("#create_result");
			$('<div class="alert alert-success"> <strong>¡Hecho!</strong></div>').appendTo($("#create_result"));
			alert("¡Se ha creado el comentario de este juego con ID"+data.id+"");
			
		}).fail(function() {
			$("#create_result").empty("#create_result");
			$('<div class="alert alert-danger"> <strong>Oh!</strong> Error</div>').appendTo($("#create_result"));
			alert("¡No se ha creado el comentario, en el buscador tienes que tener el juego donde vas a añadir este comentario");
		});
	
}
$("#button_crear_juego").click(function(e) {
	e.preventDefault();	
    var newJuego = new Object();    
    newJuego.name = $("#nombrejuego").val(); 
	newJuego.year = $("#añojuego").val();
	newJuego.genre = $("#categoriajuego").val(); 
    createGame(newJuego);	     
	
});
function createGame(newJuego) {
    var url = API_BASE_URL + '/game';	
	var data = $.param(newJuego);	
	var auth_tokens = $.cookie('token');
	/*var auth_tokens = '4ddf5787be1a11e5b0d800155d077819';*/	
	$("#create_game").text('');
	console.log(newJuego.name);
	console.log(newJuego.year);
	console.log(newJuego.genre);
	
		$.ajax({		
			url : url,
			type : 'POST',
			crossDomain : true,
			dataType : 'json',
			data : data,
			contentType : 'application/x-www-form-urlencoded',
			headers: { "X-Auth-Token":auth_tokens	}
			
		}).done(function(data, status, jqxhr) {
			$("#create_game").empty("#create_game");
			$('ID:'+data.id+'').appendTo($("#create_game"));
			alert("¡Se ha creado el juego con ID"+data.id+"");
			
		}).fail(function() {
			$("#create_game").empty("#create_game");
			$('<div class="alert alert-danger"> <strong>Oh!</strong> Error</div>').appendTo($("#create_game"));
			alert("¡No se ha añadido el like, ya has valorado este comentario anteriormente!");
		});
	
}
$("#button_crear_score").click(function(e) {
	e.preventDefault();	
    var newScore = new Object();    
    newScore.gameid = $("#idgame").val(); 
	newScore.score = $("#score").val();	
    createScore(newScore);	     
	
});
function createScore(newScore) {
    var url = API_BASE_URL + '/score';	
	var data = $.param(newScore);	
	var auth_tokens = $.cookie('token');
	/*var auth_tokens = '4ddf5787be1a11e5b0d800155d077819';*/	
	$("#create_score").text('');
	console.log(newScore.gameid);
	console.log(newScore.score);	
		$.ajax({		
			url : url,
			type : 'POST',
			crossDomain : true,
			dataType : 'json',
			data : data,
			contentType : 'application/x-www-form-urlencoded',
			headers: { "X-Auth-Token":auth_tokens	}
			
		}).done(function(data, status, jqxhr) {
			$("#create_score").empty("#create_score");
			$('<div class="alert alert-success"> <strong>¡Hecho!</strong></div>').appendTo($("#create_score"));
			alert("¡Se ha añadido la puntuación correctamente, vuelve a buscar este juego para que se actualice!");			
		}).fail(function() {
			$("#create_score").empty("#create_score");
			$('<div class="alert alert-danger"> <strong>Oh!</strong> Error</div>').appendTo($("#create_score"));
			alert("¡No se ha añadido la puntuación, solo numeros del 0 al 10, y solo puedes puntuar 1 vez cada juego!");
		});
	
}
$("#button_crear_like").click(function(e) {
	e.preventDefault();	
    var newLike = new Object();    
    newLike.revid = $("#comentar").val(); 
	newLike.likes = ('True');	
    createLike(newLike);
	getRev($("#comentar").val());	
	
});
function createLike(newLike) {
    var url = API_BASE_URL + '/likes';	
	var data = $.param(newLike);	
	var auth_tokens = $.cookie('token');
	/*var auth_tokens = '4ddf5787be1a11e5b0d800155d077819';*/	
	$("#create_like").text('');
	console.log(newLike.revid);
	console.log(newLike.likes);	
		$.ajax({		
			url : url,
			type : 'POST',
			crossDomain : true,
			dataType : 'json',
			data : data,
			contentType : 'application/x-www-form-urlencoded',
			headers: { "X-Auth-Token":auth_tokens	}
			
		}).done(function(data, status, jqxhr) {
			$("#create_like").empty("#create_like");
			$('<div class="alert alert-success"> <strong>¡Hecho!</strong></div>').appendTo($("#create_like"));
			alert("¡Actualiza el comentario para que se actualice tu like/dislike!");	
		}).fail(function() {
			$("#create_like").empty("#create_like");
			$('<div class="alert alert-danger"> <strong>Oh!</strong> Error</div>').appendTo($("#create_like"));
			alert("¡No se ha añadido el like, ya has valorado este comentario anteriormente!");
		});
		alert("Recarga el comentario!");
	
}
$("#button_crear_dislike").click(function(e) {
	e.preventDefault();	
    var newLike = new Object();    
    newLike.revid = $("#comentar").val(); 
	newLike.likes = ('false');	
    createLike(newLike);
	getRev($("#comentar").val());	
	
});
function createDislike(newLike) {
    var url = API_BASE_URL + '/likes';	
	var data = $.param(newLike);	
	var auth_tokens = $.cookie('token');
	/*var auth_tokens = '4ddf5787be1a11e5b0d800155d077819';*/	
	$("#create_dislike").text('');
	console.log(newLike.revid);
	console.log(newLike.likes);	
		$.ajax({		
			url : url,
			type : 'POST',
			crossDomain : true,
			dataType : 'json',
			data : data,
			contentType : 'application/x-www-form-urlencoded',
			headers: { "X-Auth-Token":auth_tokens	}
			
		}).done(function(data, status, jqxhr) {
			$("#create_dislike").empty("#create_dislike");
			$('<div class="alert alert-success"> <strong>¡Hecho!</strong></div>').appendTo($("#create_dislike"));	
			alert("¡Actualiza el comentario para que se actualice tu like/dislike!");			
		}).fail(function() {
			$("#create_dislike").empty("#create_dislike");
			$('<div class="alert alert-danger"> <strong>Oh!</strong> Error</div>').appendTo($("#create_dislike"));
			alert("¡No se ha añadido el like, ya has valorado este comentario anteriormente!");
		});
	
}


/*--------------------------------------------DELETE-------------------------------------------*/
$("#button_eliminar_comentario").click(function(e) {
	e.preventDefault();	
	eliminarcomentario($("#eliminarcoment").val());
});
function eliminarcomentario(eliminarcoment) {

	var USERNAME = $.cookie('loginid');
	var PASSWORD = $.cookie('password');

	$.ajaxSetup({
		headers: { 'Authorization': "Basic "+ btoa(USERNAME+':'+PASSWORD) }
	});

	var url = API_BASE_URL + '/game/' + eliminarcoment;
	
	$("#comentarios_result").text('');

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json'
                
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Comentario eliminado</div>').appendTo($("#comentarios_result"));
		window.location.reload();
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> No hay comentarios con esta ID! </div>').appendTo($("#comentarios_result"));
	});

}
$("#button_eliminar_favoritos").click(function(e) {
	e.preventDefault();	
	eliminarfavoritos($("#favoritos_a_eliminar").val());
});
function eliminarfavoritos(favoritos_a_eliminar) {

	var USERNAME = $.cookie('loginid');
	var PASSWORD = $.cookie('password');

	$.ajaxSetup({
		headers: { 'Authorization': "Basic "+ btoa(USERNAME+':'+PASSWORD) }
	});

	var url = API_BASE_URL + '/game/' + favoritos_a_eliminar;
	
	$("#mis_juegos").text('');

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json'
                
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Se ha eliminado el juego de favoritos</div>').appendTo($("#mis_juegos"));
		window.location.reload();
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> No tienes un favorito con esta ID! </div>').appendTo($("#mis_juegos"));
	});

}
$("#button_eliminar_juego").click(function(e) {
	e.preventDefault();	
	eliminarjuego($("#eliminargame").val());
});
function eliminarjuego(eliminargame) {

	var USERNAME = $.cookie('loginid');
	var PASSWORD = $.cookie('password');

	$.ajaxSetup({
		headers: { 'Authorization': "Basic "+ btoa(USERNAME+':'+PASSWORD) }
	});

	var url = API_BASE_URL + '/game/' + eliminargame;
	
	$("#juegos_result").text('');

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json'
                
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Juego eliminado</div>').appendTo($("#juegos_result"));
		window.location.reload();
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> No hay juegos con esta ID! </div>').appendTo($("#juegos_result"));
	});

}
$("#button_eliminar_categoria").click(function(e) {
	e.preventDefault();	
	eliminarcategoria($("#categoria_a_eliminar").val());
});
function eliminarcategoria(categoria_a_eliminar) {

	var USERNAME = $.cookie('loginid');
	var PASSWORD = $.cookie('password');

	$.ajaxSetup({
		headers: { 'Authorization': "Basic "+ btoa(USERNAME+':'+PASSWORD) }
	});

	var url = API_BASE_URL + '/game/' + categoria_a_eliminar;
	
	$("#juegos_result").text('');

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json'
                
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Categoria eliminada</div>').appendTo($("#juegos_result"));
		window.location.reload();
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> No hay categoria con esta ID! </div>').appendTo($("#juegos_result"));
	});

}


/*--------------------------------------------UPDATE-------------------------------------------*/
$("#button_edit_comentario").click(function(e) {
	e.preventDefault();	
      
    var id = $("#id").val(); 
	var userid = $("#userid").val(); 
	var gameid = $("#gameid").val(); 
    var likes = $("#likes").val();
	var content = $("#content").val(); 
	var creationTimestamp = $("#creationTimestamp").val(); 
	var lastModified = $("#lastModified").val(); 
	console.log(newComent.gameid);
	console.log(newComent.content);
	updateRev(id, userid, gameid, likes, content, creationTimestamp, lastModified);
});
function updateRev(id, userid, gameid, likes, content, creationTimestamp, lastModified) {
	
	var url = API_BASE_URL + '/rev/';	
	/*var data = JSON.stringify({"id":id, "userid": userid, "gameid": gameid,"likes": likes, "content": content,"creationTimestamp": creationTimestamp, "lastModified", lastModified });
	*/$("#create_result").text('');
	var usuariojson = "application/vnd.dsa.rate.rev+json";
	var auth_tokens = '4ddf5787be1a11e5b0d800155d077819';
		$.ajax({		
			url : url,
			type : 'PUT',
			crossDomain : true,
			dataType : 'raw',
			data : data,
			contentType : 'application/raw',
			headers: { "X-Auth-Token":auth_tokens,	
					"Content-Type": usuariojson}	
	
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Comentario modificado</div>').appendTo($("#update_result"));
	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#update_result"));
	});	
}
$("#button_edit_juego").click(function(e) {
	e.preventDefault();	
	
    var newJuego = new Object();    
	newJuego.name = $("#nombre_juego").val(); 
	newJuego.genre = $("#categoriajuego").val(); 
    newJuego.usuario = user_id; 
    newJuego.year = $("#añojuego").val();        
	editarJuego(newJuego);
	
});
function editarJuego(rev) {
	
	var url = API_BASE_URL + '/game/' + rev;
	var data = JSON.stringify(rev);
	
	$("#update_result").text('');
	
	$.ajax({
		url : url,
		type : 'PATCH',
		crossDomain : true,
		dataType : 'json',
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Comentario modificado</div>').appendTo($("#update_result"));
	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#update_result"));
	});	

}
$("#button_edit_comentario").click(function(e) {
	e.preventDefault();	
	
    var rev = new Object();
	rev.game = $("#editargame").val();
	rev.user_id = $("#editarid").val();
	rev.comentario = $("#editarcomentario").val();
		
	updateRev(rev);
});
function updateRev(rev) {
	
	var url = API_BASE_URL + '/game/' + rev;
	var data = JSON.stringify(rev);
	
	$("#update_result").text('');
	
	$.ajax({
		url : url,
		type : 'PATCH',
		crossDomain : true,
		dataType : 'json',
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Comentario modificado</div>').appendTo($("#update_result"));
	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#update_result"));
	});	

}
