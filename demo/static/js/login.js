var link = "http://localhost:7070/users";
var prijaviSeDugme; 
var username; 
var password;

$(document).ready(function(){
	 prijaviSeDugme = $("#loginButton"); 
	 username= $("#username"); 
	 password = $("#password"); 
	
	login(); 
})

function login() {
	
	prijaviSeDugme.click(function(){
		
		var data = {
				"username" : username.val(), 
				"password" : password.val()
		}
				
		$.ajax({
			url : link + '/login', 
			type: "POST", 
			contentType: "application/json", 
			data: JSON.stringify(data),
			success:function() {
				location.assign("chooseAccount.html");
			}, 
			error: function() {
				alert("Ne postoji korisnik sa datim kredencijalima!"); 
			}
		}); 
	}); 
}