// API url
var URL = "http://localhost:8080/api/messages";

var navigation;
var messagesList;
var syncButton;
var logoutButton;

var sorting;
var ascDesc;
var sortButton;

var search;
var searchInput;
var searchButton;
var reload;

var message;

var accountIndex = localStorage.getItem("account");

$(document).ready(function(){
	
	navigation = $("#navigation");
	messagesList = $("#messages");
	syncButton = $("#sync");
	logoutButton = $("#logoutButton");
	
	sorting = $("#sorting");
	ascDesc = $("#ascDesc");
	sortButton = $("#sort");
	
	search = $("#search");
	searchInput = $("#searchInput");
	searchButton = $("#searchButton");
	reloadButton = $("#reload");
	var messageId = window.location.href.split("=")[1];
	
	
	messagesList.on("click", ".deleteButton", function(){
		var id = $(this).data("messageid");
		
		$.ajax({
			url: URL + "/" + id,
			type: "DELETE",
			contentType: "application/json",
			success: function() {
				alert("Deleted!");
				getReloadMessages();
				window.location.reload()
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('greska kod sinhronizacije contacts'); 
			}
		});
	});
	
	logoutButton.click(function(){
		localStorage.removeItem("account");
		window.location.replace("index.html")
	})
	
	getMessages();

	syncButton.click(function() {
		$.ajax({
			url: URL + "/" + accountIndex + "/sync",
			type: "POST",
			success: function(){
				alert("Zapoceta sihnronizacija!");
				messagesList.empty();
				getMessages();
				alert("Sync complete");
				window.location.reload()
			},
			error: function() {
				alert("Greska kod sinhronizacije poruka");
			}
		});
	});

	getReloadMessages();

});

function getMessages(){
	
	$.ajax({
		url: "http://localhost:8080/api/accounts/" + accountIndex + "/messages",
		type: "GET",
		success: function(messages){
			
			for(message of messages){
				
				if(message.unread){
					messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'>" + message.subject + "</a><dd><b> " + message.content + "</b></dd><button class='deleteButton' data-messageid = '"+ message.id + "'>Obrisi</button>");
				}else{
					messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'>" + message.subject + "</a><dd>" + message.content + "</dd><button class='deleteButton' data-messageid = '"+ message.id + "'>Obrisi</button>");	
				}				
			}
		}
	});
}

function getReloadMessages(){
	
	reloadButton.click(function(){
		$.ajax({
			url: "http://localhost:8080/api/accounts/" + accountIndex + "/messages",
			type: "GET",
			success: function(messages){
				messagesList.empty();

				getMessages();				
				}
			
		})
	})
}
