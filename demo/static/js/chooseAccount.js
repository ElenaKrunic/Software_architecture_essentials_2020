var AccountManager = {
	basePath: function() {return 'http://localhost:7070/api';},
	
	showAccountsList: function() {

		$.ajax({
			url : this.basePath() + '/accounts', 
			cache : false, 
			dataType: 'json', 
			success: function(accounts) {
				$('#account').empty();
				$('#account').append('<tr><th>Display name</th><th>Odaberi</th></tr>');
				$.each(accounts,function(index,account){
					$('#account tr:last').after('<tr><td>' + account.displayName + '</td> + <td><a href=\'javascript:AccountManager.showAccountDetails("' + account.id + '")\'>O nalogu</a></td></tr>');
				});
			}, 
			error: function() {
				alert('greska kod svih naloga');
			}
		});
	},
	
	showAccountDetails : function(accountId){
		if(accountId==null) return; 
		
		$.ajax({
			url: this.basePath() + '/accounts/' + accountId,
			cache:false, 
			dataType: 'json', 
			success: function(account) {
				$('#ShowAccountsDetailsPanel').show();
				$('#AccountsListPanel').hide();
				$("#addAccountBtn").hide();
				$("#accountID").val(account.id);
				$('#accountSmtpAddress').val(account.smtpAddress);
				$('#accountSmtpPort').val(account.smtpPort); 
				$('#inServerType').val(account.inServerType); 
				$('#inServerAddress').val(account.inServerAddress); 
				$('#inServerPort').val(account.inServerPort); 
				$('#accountUsername').val(account.username); 
				$('#accountPassword').val(account.password); 
				$('#accountDisplayName').val(account.displayName);
				
			}, error: function() {
				alert("doslo je do greske kod ucitavanja naloga");
			}
		});
	},
	
	dodajNalog: function() {
		$('#AccountsListPanel').slideUp("slow");
		$('#ShowAccountsDetailsPanel').show();
		$("#obrisiNalog").hide();
		$("#accountID").attr('value',null);
		$('#accountSmtpAddress').attr('value',null);
		$('#accountSmtpPort').attr('value',null); 
		$('#inServerType').attr('value',null); 
		$('#inServerAddress').attr('value',null); 
		$('#inServerPort').attr('value',null); 
		$('#accountUsername').attr('value',null); 
		$('#accountPassword').attr('value',null); 
		$('#accountDisplayName').attr('value',null);
	},
	
	povratak: function() {
		$('#ShowAccountsDetailsPanel').hide(); 
		$('#AccountsListPanel').show(); 
		$('#AccountsListPanel').focus();
	},
	
	refreshAccountList: function() {
		this.povratak(); 
		this.showAccountsList();
	},
	
	pokupiPodatke: function() {
		return JSON.stringify({
			"id" : $("#accountID").val(), 
			"smtpAddress" : $("#accountSmtpAddress").val(), 
			"smtpPort" : $("#accountSmtpPort").val(), 
			"inServerType": $("#inServerType").val() , 
			"inServerAddress": $("#inServerAddress").val(), 
			"inServerPort": $("#inServerPort").val(), 
			"username": $("#accountUsername").val(), 
			"password": $("#accountPassword").val(), 
			"displayName": $("#accountDisplayName").val()
		});
	},
	
	createAccountUrl : function(requestType) {
		if(requestType == 'POST') {
			return this.basePath() + '/accounts'; 
		}
		
		return this.basePath() + '/accounts/' + encodeURIComponent($('#accountID').val());
	}, 
	
	sacuvajNalog: function() {
		if(!confirm('Sacuvaj ulogu?')) return; 
		
		var requestType = $('#accountID').val() != '' ? 'PUT' : 'POST';
		$.ajax({
			url : this.createAccountUrl(requestType), 
			dataType: 'json', 
			type: requestType, 
			contentType: "application/json", 
			data: this.pokupiPodatke(), 
			success: function(result) {
				console.log(data);
				AccountManager.refreshAccountList();
			}, 
			error: function() {
				alert('Greska pri kreiranju novog naloga'); 
			}
		});
	}, 
	
	obrisiNalog: function() {
		if(!confirm('Da li ste sigurni? ')) return; 
		
		$.ajax({
			url : this.createAccountUrl('DELETE'), 
			type: 'DELETE', 
			success: function() {
				AccountManager.refreshAccountList(); 
			},
			error: function(error){alert(error);}
		});
	}
};

$(document).ready(function(){
	AccountManager.showAccountsList();
	AccountManager.showAccountDetails();
	$('#kontakti').show();
	$('#poruke').show();
	$("#AccountsListPanel").show();
	$("#ShowAccountsDetailsPanel").hide();
	
	$('#nazadNaAccounte').click(function(e){
		e.preventDefault();
		AccountManager.povratak();
	});
	
	$('#sacuvajIzmjeneNaloga').click(function(e){
		e.preventDefault(); 
		AccountManager.sacuvajNalog(); 
	});
	
	$('#obrisiNalog').click(function(e){
		e.preventDefault(); 
		AccountManager.obrisiNalog();
	});
	
	$("#addAccountBtn").click(function(e){
		e.preventDefault(); 
		AccountManager.dodajNalog();
	});
	
});
