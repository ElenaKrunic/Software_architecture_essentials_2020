var to;
var cc;
var bcc;
var content;
var subject;
var send;

var blabla;

$(document).ready(function(){
	
	to = $("input[name=to]");
	cc = $("input[name=cc]");
	bcc = $("input[name=bcc]");
	content = $("#textfield");
	subject = $("input[name=subject]");
	send = $("#sendButton");
	var logoutButton = $("#logoutButton");
		
	createEmail();
	
	logoutButton.click(function(){
		localStorage.removeItem("account");
		window.location.replace("login.html")
	})
	
});

function createEmail(){
	
	send.click(function(){
		
		stringCC = cc.val();
		stringBCC = bcc.val();
		stringTO = to.val();
		
		var file = document.querySelector('input[type="file"]').files[0];
		if (file != null) {
			let reader = new FileReader();
			reader.readAsDataURL(file);

			reader.onload = function () {
				var attachment = {
					name: file.name,
					data: reader.result.split(",")[1],
					mimeType: reader.result.split(";")[0].split(":")[1],
				};

				var data = {	
					to: stringTO,
					cc: stringCC,
					bcc: stringBCC,
					subject: subject.val(),
					content: content.val(),
					tags: [],
					attachments: [attachment]
				};
				
				console.log(data);
				sendMessage(data);
			};

		} else {
			var data = {	
				to: stringTO,
				cc: stringCC,
				bcc: stringBCC,
				subject: subject.val(),
				content: content.val(),
				tags: [],
				attachments: []
			};

			sendMessage(data);
		}
	});
}

function sendMessage(data) {

	var accountIndex = localStorage.getItem("account");

	$.ajax({
		url : "http://localhost:8080/api/messages/"+ accountIndex,
		type : "POST",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(){
			alert("Sent!");
			window.location.replace("messages.html");
		}
	});
}