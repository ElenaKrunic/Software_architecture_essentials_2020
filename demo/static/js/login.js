var URL = "http://localhost:8080";
var prijaviSeDugme; 
var username; 
var password; 
var userID;

$(document).ready(function(){
	
	var containerLogin = $("#containerLogin");
	
	containerLogin.on("click", ".login", function() {
		username = user.username; 
		localStorage.setItem("user", $(this).data("index")); 
		window.location.assign("chooseAccount.html")

	});
	
	
	 prijaviSeDugme = $("#loginButton"); 
	 username= $("#username"); 
	 password = $("#password");
	
	 login(); 
})




function login() {
	
	prijaviSeDugme.click(function(){
				
		var data = {
				"username" : username.val(), 
				"password" : password.val(), 
		}
						
		$.ajax({
			url : URL + '/api/users/login', 
			type: "POST", 
			contentType: "application/json", 
			data: JSON.stringify(data),
			success: function(){
				location.assign("chooseAccount.html")
			}, 
			error: function() {
				alert("Ne postoji korisnik sa datim kredencijalima!"); 
			}
		}); 
	}); 
}
