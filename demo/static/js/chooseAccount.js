// API url
var URL = "http://localhost:8080/api/accounts";
var accID;
$(document).ready(function() {

	
	
	$.ajax({
		type : 'GET',
		url : 'http://localhost:8080/api/contacts/getContactsForUser',
		success : function(response) {
			console.log(response);
		},error : function(response) {
			console.log(response);
		}
	})

	$.ajax({
		type : 'GET',
		url : URL + accID + "/messages",
		success : function(response) {
			console.log(response);
		},error : function(response) {
			console.log(response);
		}
	})

	$.ajax({
		type : 'GET',
		url : URL + accID + "/folders",
		success : function(response) {
			console.log(response);
		},error : function(response) {
			console.log(response);
		}
	})

		
	var container = $("#container");

	container.on("click", ".account", function() {
		var accID = account.id;
		localStorage.setItem("account", $(this).data("index"));

		window.location.assign("messages.html");
	});

	$.ajax({
		url: URL + '/getAccountsForUser',
		type: "GET",
		success: function(accounts) {
			var counter = 1;
			for (account of accounts) {
				container.append("<button class='account' data-index='" + counter + "'>" + account.displayName + "</button>");
				counter++;
			}
		}
	});
	

});