var firstName;
var lastName;
var userName;
var password;
var submit;

$(document).ready(function() {
	
	
	firstName = $("#firstname");
	userName = $("#username");
	lastName = $("#lastname")
	password = $("#password");
	submit = $("#submit");
	
	getUser();
	register();
});

function getUser(){
	
	$.ajax({
		url: "http://localhost:8080/api/users/findUser",
		type: "GET",
		success: function(user){
			firstName.val(user.firstName);
			userName.val(user.username);
			lastName.val(user.lastName);
			password.val(user.password);
		}
		
	})
	
}


function register(){
	
	submit.on("click", function(){
		
		var data={
		username: userName.val(),
		password: password.val(),
		firstName: firstName.val(),
		lastName: lastName.val()
		}
		

		$.ajax({
			url: "http://localhost:8080/api/users/update",
			type: "PUT",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function() {
				alert("User Updated");
				window.location.replace("messages.html");
			}
		});
		
	});
}