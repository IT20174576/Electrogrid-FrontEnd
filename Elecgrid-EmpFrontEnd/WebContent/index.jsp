<%@page import="com.emplyeeServices"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/employee.js"></script>

<meta charset="ISO-8859-1">
<title>Employee Management</title>
</head>
<body>

<div class="container"><div class="row"><div class="col-8">
 <h1 class="m-3">Employee Management</h1>
 <form id="formEmployee" name = "formEmployee">
 
  Employee name:
 <input id="e_name" name="e_name" type="text"
 class="form-control form-control-sm">
 <br> Employee mobile:
 <input id="e_mobile" name="e_mobile" type="text"
 class="form-control form-control-sm">
 <br> Designation:
 <input id="designation" name="designation" type="text"
 class="form-control form-control-sm"><br>
 
 <input id="btnSave" name="btnSave" type="button" value="Save"
 class="btn btn-primary">
 <input type="hidden" id="hidemployeeIDSave"
 name="hidemployeeIDSave" value="">
 
 </form>

<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>

<div id="divEmpGrid">
 <%
 emplyeeServices empObj = new emplyeeServices();
 out.print(empObj.viewEmp());
 %>
</div>
</div> </div> </div>

</body>
</html>