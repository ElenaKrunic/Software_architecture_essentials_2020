var value;
var operation;
var condition;

var rule;

var create;

var accountIndex = localStorage.getItem("account");

$(document).ready(function(){
	value = $("#value");
	operation = $("#operation");
	condition = $("#condition");
	create = $("#create");
	
	var folderId = window.location.href.split("=")[1];

	createRule(folderId);
	
});

function createRule(folderId){
	
	create.click(function(){
		
		var data = {
				
				value : value.val(),
				operation : operation.val(),
				condition : condition.val()
		}
		
		console.log(operation.val());
		
		$.ajax({
			url : "http://localhost:8080/api/rules/saveRule/" + folderId + "/" + folderId,
			type : "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(){
				alert("Added rule!");
			}
		});
	});
	
}