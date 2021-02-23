var rulesList;
var rulesOperations;
var rulesCondition;
var value;
var operation;
var condition;
var createRule;

var rule;

var updateButton;


var accountIndex = localStorage.getItem("account");

$(document).ready(function(){
	rulesList = $("#rulesList");
	rulesOperation = $("#rulesOperation");
	rulesCondition = $("#rulesCondition");
	createRule = $("#createRule");
	value = $("#value");
	operation = $("#operation");
	condition = $("#condition");
	updateButton = $("#updateButton");
	var logoutButton = $("#logoutButton");
	
	var folderId = window.location.href.split("=")[1];
	
	getRules(folderId);

	rulesList .on("click", ".deleteButton", function(){
		
		var id = $(this).data("ruleid");
		
		var data = {
				id : id			
		}
		
		$.ajax({
			url: "http://localhost:8080/api/rules/" + id,
			type: "DELETE",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function() {
				alert("Deleted!");
			}
		});
		
	});

	logoutButton.click(function(){
		localStorage.removeItem("account");
		window.location.replace("login.html")
	})
	
});

function getRules(folderId){
	$.ajax({
		url: "http://localhost:8080/api/rules/getRules",
		type: "GET",
		success: function(rules){
			createRule.append("<a class='btn btn-success col-2' href= 'createRule.html?folderId=" + folderId + "'> Create Rule</a>");
			for(rule of rules){				
				rulesList.append("<input type='text' value = '" + rule.value + "'><input type='text' value = '" + rule.operation + "'><input type='text' value = '" + rule.condition + "'>");
				rulesList.append("<button class='deleteButton' data-ruleid = '" + rule.id + "'>X</button>");
			}
		}
	});
}