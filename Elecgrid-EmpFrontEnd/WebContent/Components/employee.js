/**
 * 
 */
 
 $(document).ready(function() 
{ 
	if ($("#alertSuccess").text().trim() == "") 
	 { 
	 	$("#alertSuccess").hide(); 
	 } 
	 $("#alertError").hide(); 
});

// SAVE ============================================
	$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide();
 
 // Form validation-------------------
var status = validateEmployeeForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
 
 // If valid------------------------
var type = ($("#hidemployeeIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
	 url : "employeeAPI", 
	 type : type, 
	 data : $("#formEmployee").serialize(), 
	 dataType : "text", 
	 
 complete : function(response, status) 
 { 
 	onEmployeeSaveComplete(response.responseText, status); 
 } 
 }); 
});  


function onEmployeeSaveComplete(response, status)
{ 
if (status == "success") 
 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
 { 
	 $("#alertSuccess").text("Successfully saved."); 
	 $("#alertSuccess").show(); 
	 $("#divEmpGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
	 $("#alertError").text("Error while saving."); 
	 $("#alertError").show(); 
 } else
 { 
	 $("#alertError").text("Unknown error while saving.."); 
	 $("#alertError").show(); 
 } 
 
	 $("#hidemployeeIDSave").val(""); 
	 $("#formEmployee")[0].reset(); 
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
 $("#hidemployeeIDSave").val($(this).closest("tr").find('#employeeUpdate').val()); 
 $("#e_name").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#e_mobile").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#designation").val($(this).closest("tr").find('td:eq(2)').text()); 
  
}); 

  // CLIENT-MODEL================================================================
	function validateEmployeeForm() 
	{ 
		// NAME
		if ($("#e_name").val().trim() == "") 
 		{ 
			 return "Insert Employee name."; 
 		} 
		// MOBILE
			if ($("#e_mobile").val().trim() == "") 
			 { 
 				return "Insert Employee mobile."; 
 			} 
 			
 			// is numerical value
			var emobile = $("#e_mobile").val().trim(); 
			if (!$.isNumeric(emobile)) 
	 		{ 
	 			return "Insert a numerical value for mobile."; 
	 		} 
 			
		// DESIGNATION
			if ($("#designation").val().trim() == "") 
			 { 
 				return "Insert Employee designation."; 
 			} 
 			return true;
		}
		
	//DELETE
	$(document).on("click", ".btnRemove", function(event) 
{ 
 $.ajax( 
 { 
 url : "employeeAPI", 
 type : "DELETE", 
 data : "e_id=" + $(this).data("e_id"),
 dataType : "text", 
 complete : function(response, status) 
 { 
 onEmployeeDeleteComplete(response.responseText, status); 
 } 
 }); 
});	
		
function onEmployeeDeleteComplete(response, status) 
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
 }


