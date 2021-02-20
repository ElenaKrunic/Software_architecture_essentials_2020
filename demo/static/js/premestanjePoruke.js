var URL = "http://localhost:8080/api";

var container;
var messageId;


var accountIndex = localStorage.getItem("account");

$(document).ready(function() {

	messageId = window.location.href.split("=")[1];

	container = $("#container");
	var logoutButton = $("#logoutButton");
	

	container.on("click", ".folder", function() {
		moveTo($(this).data("id"));
	});

	getRootFolders();
	logoutButton.click(function(){
		localStorage.removeItem("account");
		window.location.replace("login.html")
	})

});


function moveTo(folderId) {

	var data = {
		messageId: parseInt(messageId),
		folderId: parseInt(folderId)
	};

	console.log(data);

	$.ajax({
		url: "http://localhost:8080/api/messages/" + messageId + "/moveTo/" + folderId,
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(data) {
			location.assign("messages.html");
		}
	});
}

// Obtains info about root folders
function getRootFolders() {

	$.ajax({
		url: "http://localhost:8080/api/folders/" + accountIndex + "/folders",
		type: "GET",
		success: function(folders) {
			
			for (folder of folders) {	
				container.append("<button data-id='" + folder.id + "' class='folder btn btn-dark'>" + folder.name + "</button>"); 

				/*for (subFolder of folder.subFolders) {
					container.append("<button data-id='" + subFolder.id + "' class='folder btn btn-dark'>" + subFolder.name + "</button>"); 
				}*/
			}

		}
	});

}