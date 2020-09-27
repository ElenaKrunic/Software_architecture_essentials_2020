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
			success:function(ajdi) {
				alert("Uspjesan login"); 
				location.assign("chooseAccount.html");
				//ovde da dodijelim neki parametar u localStorage 
				localStorage.setItem("ajdi", ajdi);
				//localStorage['ajdi'] = ajdi;
			}, 
			error: function() {
				alert("Ne postoji korisnik sa datim kredencijalima!"); 
			}
		}); 
	}); 
}