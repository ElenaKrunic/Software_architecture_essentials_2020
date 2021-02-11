var URL = "http://localhost:8080/api/folders";

var folderName;
var confirmButton;
var deleteButton;

var FOLDER;

var accountIndex = localStorage.getItem("account");
//alert(accountIndex); ispise id naloga 

$(document).ready(function() {
	
	title = $("h1");
	folderName = $("input[name=folder_name]");
	confirmButton = $("#confirm_button");
	deleteButton = $("#delete_button");
	var logoutButton = $("#logoutButton");
	
	var folderId = window.location.href.split("=")[1];
	
	if (folderId == null) {
		title.text("Create Folder");
		createFolder();
	} else {
		title.text("Edit Folder");
		editFolder(folderId);
	}
	
	logoutButton.click(function(){
		localStorage.removeItem("account");
		window.location.replace("index.html")
	})
	
});
//izmena i brisanje
function editFolder(folderId) {

	confirmButton.text("Sacuvaj izmene");

	$.ajax({
		url: URL + "/" + folderId,
		type: "GET",
		success: function(folder) {
			// varijable
			FOLDER = folder;
			folderName.val(FOLDER.name);

			// izmena
			confirmButton.click(function() {
				var data = {
					id: FOLDER.id,
					name: folderName.val()
				}

				$.ajax({
					url: URL + "/updateFolder/" + FOLDER.id,
					type: "PUT",
					contentType: "application/json",
					data: JSON.stringify(data),
					success: function() {
						location.replace("folder.html");
					}
				});
			});

			// brisanje
			deleteButton.click(function() {

				$.ajax({
					url: URL + "/deleteFolder/" + FOLDER.id,
					type: "DELETE",
					success: function() {
						location.assign("folder.html");
					}
				});
			});
		}
	});
}

// kreiranje foldera
function createFolder() {

	confirmButton.text("Dodaj folder");
	deleteButton.remove();
	confirmButton.click(function() {

		var data = {
			name: folderName.val()
		}

		$.ajax({
			url: URL + "/addFolder/" + accountIndex,
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function() {
				location.assign("folder.html");
			}
		});
	});
}