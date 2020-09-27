var link = "http://localhost:8080/account";
var brojac = 0; 

$(document).ready(function(){
	
	var odaberiNalogDiv = $("#odaberiNalogDiv"); 
	
	odaberiNalogDiv.on("click", ".account", function(){
		alert("klik na div"); 
		
		localStorage.setItem("account", $(this).data("index")); 
		
		window.location.assing("inbox.html");
	});
	
	$.ajax({
		url : link, 
		type: "GET", 
		success: function(accounts) {
			for(account of accounts) {
				odaberiNalogDiv.append("<button class='account' data-index='" + brojac + "'>" + account.displayName + "</button>");
				brojac++;
			}
		}
	});
	
});