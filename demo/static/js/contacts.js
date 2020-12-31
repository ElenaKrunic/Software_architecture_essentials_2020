// ucitava listu kontakata 
var ContactManager = {
	//vraca url 
		basePath: function() { return 'http://localhost:8080/api'; }, 
		
	showContactsList: function() {
			$.ajax({
				url : this.basePath() + '/contacts', 
				cache : false, 
				dataType : 'json', 
				success: function(list) {	
					console.log(list);
					$('#contact').empty();
					$('#contact').append('<tr><th>Ime</th><th>Prezime</th><th>Odaberi</th></tr>');
					$.each(list,function(index,contact){
						$('#contact tr:last').after('<tr><td>' + contact.firstName + '</td><td>' + contact.lastName + '</td><td><a href=\'javascript:ContactManager.showContactDetails("' + contact.id + '")\'>O kontaktu</a></td></tr>');
					});
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert('greska kod getAll contacts'); 
				}
			});
		},
	
	showContactDetails : function(contactId) {
		if(contactId==null) return;
		$('#ContactListPanel').hide(); 
		$('#obrisiKontakt').show(); 
		
		$.ajax({
			url: this.basePath() + '/contacts/' + contactId, 
			cache: false, 
			dataType: 'json', 
			success: function(contact) {
				$('#ShowContactDetailsPanel').show();
				$('#contactID').val(contact.id);
				$('#contactFirstName').val(contact.firstName);
				$('#contactLastName').val(contact.lastName);
				$('#contactFirstName').focus();
				$("#contactDisplayName").val(contact.displayName); 
				$("#contactEmail").val(contact.email);
				$("#contactNote").val(contact.note);
			},
			error: function (jqXHR, textStatus, errorThrown) {  
				alert('greska kod show contact details');
			}
		}); 
	}, 
	
	dodajKontakt: function() {
		$("#ContactListPanel").slideUp("slow"); 
		$("#obrisiKontakt").hide();
		$("#ShowContactDetailsPanel").show();
		$("#contactID").attr('value',null); 
		$("#contactFirstName").attr('value', 'Dodaj kontakt').focus().select();
		$("#contactLastName").attr('value',null);
		$("#contactDisplayName").attr('value', null); 
		$("#contactEmail").attr('value',null);
		$("#contactNote").attr('value',null);
	},
	
	povratak: function() {
		$("#ShowContactDetailsPanel").hide(); 
		$("#ContactListPanel").show(); 
		$("#ContactListPanel").focus(); 
	},
	
	refreshContactList: function() {
		this.povratak(); 
		this.showContactsList();
	}, 
	
	pokupiPodatke: function() {
		return JSON.stringify({
			"id" : $("#contactID").val(),
			"firstName": $("#contactFirstName").val() , 
			"lastName" :$("#contactLastName").val(),
			"displayName" : $("#contactDisplayName").val(), 
			"email" : $("#contactEmail").val(),
			"note" : $("#contactNote").val()
		});
	}, 
	
	createContactUrl: function (requestType) {
		if(requestType == 'POST') {
			return this.basePath() + '/contacts'; 
		}
		
		return this.basePath() + '/contacts/' + encodeURIComponent($("#contactID").val());
	},
	
	//za sacuvaj promjene ide put,ako je korisnik vec postojao 
	//ide post ako ne postoji,nego se nanovo kreira 
	sacuvajKontakt: function() {
		if(!confirm('Sacuvaj?')) return;
		var requestType= $("#contactID").val() != '' ? 'PUT' : 'POST';
		$.ajax({
			url: this.createContactUrl(requestType), 
			dataType : 'json', 
			type: requestType, 
			contentType: "application/json",
			data : this.pokupiPodatke(), 
			success: function(result) {
				ContactManager.refreshContactList(); 
			}, 
			error: function(error) {
				alert('Greska pri kreiranju novog kontakta');
			}
		});
	}, 
	
	obrisiKontakt: function() {
		if(!confirm('Da li ste sigurni? ')) return; 
		$.ajax({
			url : this.createContactUrl('DELETE'), 
			type: 'DELETE', 
			success: function() {
				ContactManager.refreshContactList(); 
			}, 
			error: function(error) {alert(error);}
		});
	}

};


$(document).ready(function(){
	ContactManager.showContactsList();
	ContactManager.showContactDetails();
	$('#ContactListPanel').show();
	$('#ShowContactDetailsPanel').hide();
	
	$('#nazadNaKontakte').click(function (e) {
		e.preventDefault();
		ContactManager.povratak();
	});
	
	$('#sacuvajIzmjene').click(function(e){
		e.preventDefault(); 
		ContactManager.sacuvajKontakt();
	});
	
	$("#obrisiKontakt").click(function(e){
		e.preventDefault();
		//dodai 
		ContactManager.obrisiKontakt();
	});
	
	$("#addContactBtn").click(function(e){
		e.preventDefault(); 
		ContactManager.dodajKontakt();
	});
	
	$("#accounts").show(); 
	$("#poruke").show();
	
}); 
