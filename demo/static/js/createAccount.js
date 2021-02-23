var username;
var password;
var displayName;
var smtpAddress;
var smtpPort;
var imapAddress;
var imapType;
var imapPort;
var confirmButton;
var deleteButton;

var accountIndex = localStorage.getItem("account");

$(document).ready(function() {
	
	username = $("input[name=username]");
	password = $("input[name=password]");
	displayName = $("input[name=display_name]");
	smtpAddress = $("input[name=smtp_address]");
	smtpPort = $("input[name=smtp_port]");
	imapAddress = $("input[name=imap_address]");
	imapType = $("input[name=imap_type]");
	imapPort = $("input[name=imap_port]");
	confirmButton = $("#confirm_button");
	deleteButton = $("#delete_button");
	
	if(accountIndex == null){
		create();
	}else{
		edit(accountIndex);
	}
});
	
function create(){
	
	deleteButton.remove();
	
	confirmButton.click(function(){
		
	var data = {
			username: username.val(),
			password: password.val(),
			displayName: displayName.val(),
			smtpAddress: smtpAddress.val(),
			smtpPort: smtpPort.val(),
			inServerAddress: imapAddress.val(),
			inServerType: imapType.val(),
			inServerPort: imapPort.val()
		}
	
	$.ajax({
		url: "http://localhost:8080/api/accounts/saveAccount",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function() {
			alert("Account Created");
			window.location.replace("chooseAccount.html");
		}
		});
	});
	
}

function edit(accountIndex){
	
	$.ajax({
		url: "http://localhost:8080/api/accounts/" + accountIndex,
		type: "GET",
		success: function(account) {

			username.val(account.username),
			password.val(account.password),
			displayName.val(account.displayName),
			smtpAddress.val(account.smtpAddress),
			smtpPort.val(account.smtpPort),
			imapAddress.val(account.inServerAddress),
			imapType.val(account.inServerType),
			imapPort.val(account.inServerPort)
			
			confirmButton.click(function(){
				
			var data = {
					id: account.id,
					username: username.val(),
					password: password.val(),
					displayName: displayName.val(),
					smtpAddress: smtpAddress.val(),
					smtpPort: smtpPort.val(),
					inServerAddress: imapAddress.val(),
					inServerType: imapType.val(),
					inServerPort: imapPort.val()
				}

					$.ajax({
						url: "http://localhost:8080/api/accounts/updateAccount/" + accountIndex,
						type: "PUT",
						contentType: "application/json",
						data: JSON.stringify(data),
						success: function() {
							alert("Edit Successful");
							window.location.replace("messages.html")
						}
					});
				});


				deleteButton.click(function() {

					var data = {
						id: account.id
					}

					$.ajax({
						url: "http://localhost:8080/api/accounts/deleteAccount/" + accountIndex,
						type: "DELETE",
						contentType: "application/json",
						data: JSON.stringify(data),
						success: function() {
							alert("Account deleted");
							localStorage.removeItem("account");
							window.location.replace("index.html")
						}
					});
				});

			}
		});


	}