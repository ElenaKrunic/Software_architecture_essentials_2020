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

	register();
});


function register(){
	
	submit.on("click", function(){
		
		var data={
		username: userName.val(),
		password: password.val(),
		firstName: firstName.val(),
		lastName: lastName.val()
		}
		

		$.ajax({
			url: "http://localhost:8080/api/users/registration",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function() {
				alert("User Created");
				window.location.replace("index.html");
			}
		});
		
	});
	
}