var container;
var from;
var cc;
var bcc;
var content;
var subject;
var send;

var MESSAGE;


var accountIndex = localStorage.getItem("account");

$(document).ready(function(){
	
	container = $("#container");
	from = $("input[name=from]");
	cc = $("input[name=cc]");
	bcc = $("input[name=bcc]");
	content = $("#content");
	subject = $("input[name=subject]");
	var logoutButton = $("#logoutButton");

	var messageId = window.location.href.split("=")[1];
	
	getEmail(messageId);
	
	logoutButton.click(function(){
		localStorage.removeItem("account");
		window.location.replace("login.html")
	})
});

function getEmail(messageId){
		
		$.ajax({
			url: "http://localhost:8080/api/messages/" + messageId,
			type: "GET",
			success: function(mess){
				
				MESSAGE = mess;
				from.val(MESSAGE.from.email);
				
				from.val(MESSAGE.from);
				/*ccString = "";
				for(contact of mess.cc){
					ccString += contact.email + ", ";
				}*/
				
				cc.val(MESSAGE.cc);
				
				/*bccString = "";
				for(contact of mess.bcc){
					bccString += contact.email + ", ";
				}*/
				
				bcc.val(MESSAGE.bcc);
				subject.val(MESSAGE.subject);
				content.text(MESSAGE.content);

				for (attachment of mess.attachments) {
					container.append("<div><a href='data:" + attachment.mimeType + ";base64," + attachment.data + "' download='" + attachment.name + "'>" + attachment.name + "</a></div>");
				}

				container.append("<a href='premestanjePoruke.html?id=" + MESSAGE.id + "'>Move to folder</a>");
			}
			
		});

		$.ajax({
			url: "http://localhost:8080/api/messages/" + messageId + "/markAsRead",
			type: "PUT",
			contentType: "application/json",
			success: function(){
				console.log("Message is now read!");
			}
		});
}