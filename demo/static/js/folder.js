// API url
var URL = "http://localhost:8080/api/folders";

// Page components
var navigation;
var subfolders;
var messages;
var rules;

var FOLDER;

var accountIndex = localStorage.getItem("account");

$(document).ready(function() {
	// Initializing page components
	navigation = $("#navigation");
	subfolders = $("#subfolders");
	messages = $("#messages");
	rules = $("#rules");
	var logoutButton = $("#logoutButton");
	var createButton = $("#createButton");

	var folderId = window.location.href.split("=")[1];

	if (folderId == null) {
		$("#rules").remove();
		$("h3:eq(1)").remove();
		$("#back").attr("href", "messages.html");
		getRootFolders();

	} else {
		getFolder(folderId);
		navigation.append("<a href='#' id='apply_rules'>Apply rules</a>");

		createButton.click(function(){
			createButton.attr("href", "createFolder.html?folderid=" + folderId);
		})
		
		rules.attr("href", "rules.html?folderid=" + folderId);
		
		navigation.on("click", "#apply_rules", function() {
			applyRules();
		});
	}

	logoutButton.click(function(){
		localStorage.removeItem("account");
		window.location.replace("index.html")
	})

});

// ucitava podfoldere i poruke za folder
function getFolder(folderId) {
	
	$.ajax({
		url: URL + "/" + accountIndex + "/folders",
		type: "GET",
		success: function(folders) {
			
			for (folder of folders) {
				if(folder.parent_folder == folderId){
					subfolders.append("<a href='folder.html?id=" + folder.id + "' class='subfolder'>" + folder.name + "</a><br><br><br>"); 
				}
			}
		}
	});
	
	$.ajax({
		url: URL + "/" + folderId + "/messages",
		type: "GET",
		success: function(mess) {
			for (message of mess) {	
				messages.append("<a href='email.html?id=" + message.id + "' class='message'>" + message.subject + "</a><br><br><br>"); 
			}

		}
	});
}

// ucitava foldere
function getRootFolders() {

	$.ajax({
		url: URL + "/" + accountIndex + "/folders",
		type: "GET",
		headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
		success: function(folders) {
			
			for (folder of folders) {
				if(folder.parent_folder == null){
					subfolders.append("<a href='folder.html?id=" + folder.id + "' class='subfolder'>" + folder.name + "</a><br><br><br>"); 
				}
			}

		}
	});

}

function applyRules() {
	$.ajax({
		url: URL + "/" + accountIndex + "/folders/" + FOLDER.id + "/rules/apply",
		type: "POST",
		headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
		success: function(folders) {
			alert("Success");
			location.reload();
		}
	});
}