var link = "http://localhost:8080/users";
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
		
		alert('klik na dugme');
		
		var data = {
				"username" : username.val(), 
				"password" : password.val()
		}
		
		console.log(username); 
		
		
		$.ajax({
			url : link + '/login', 
			type: "POST", 
			contentType: "application/json", 
			data: JSON.stringify(data),
			success:function() {
				alert("Uspjesan login"); 
				location.assign("chooseAccount.html");
			}, 
			error: function() {
				alert("Ne postoji korisnik sa datim kredencijalima!"); 
			}
		}); 
	}); 
}