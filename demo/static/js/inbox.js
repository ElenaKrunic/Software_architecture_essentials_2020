var url = "http://localhost:2222/account"; 
var account = localStorage.getItem("account");

$document.ready(function(){
	
	$("#kontakti").show(); 
	$("#accounts").show();
	
	//sve poruke
	var messages = $("#messages"); 
	//osvjezi kad ucitavas 
	var refresh = $("#reloadMailButton");
	//div za sortiranje 
	var sorting = $("#sort"); 
	var ascDesc = $("#ascDesc"); 
	var sortButton = $("#sortButton"); 
	//div za pretragu 
	var search = $("#searchMailButton"); 
	var searchInput=$("#searchInput"); 
	var refreshButtonAscDesc=$("#refreshMailButtonByAscDesc");
	
	getMessages(); 
	
	refreshButtonAscDesc.click(function(){
		$.ajax({
			url: URL + "/" + account + "/messages/refresh", 
			type: "POST", 
			success: function() {
				messages.empty(); 
				getMessages(); 
				alert("Lista mejlova je osvjezena!"); 
			}
		});
	});
	
	getMessages(); 
}); 

function getMessages() {
	$.ajax({
		url: URL + "/" + account + "/messages",
		type: "GET", 
		success: function(messages) {
			for(message of messages) {
				alert("Implementirati tek posto implementiram korisnika!"); 
			}
		}
	}); 
}