<%@ page language="java" contentType="text/html; charset=UTF-8"
    	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<script type="text/javascript" src=“js/app.js”></script>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src=“/webjars/bootstrap/js/bootstrap.min.js”></script>
</head>
<body>

<div class="container">
<div class="bodyStyle">

	<div class="nonUserHeader">
		<nav class="navbar navbar-expand-lg navbar-light">
		  <div class="container-fluid">
		    <a class="navbar-brand" href="#">SFQ!</a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
			    <div class="collapse navbar-collapse" id="navbarNavAltMarkup" style="display:flex; justify-content: space-between">
			      <div class="navbar-nav" style="align-items: flex-start;">
			        <a class="nav-link" href="/menu">Menu</a>
			        <c:choose>
			        	<c:when test="${loggedUser.type == 'employee' || loggedUser.type == 'admin' }">
			        		<a class="nav-link" href="/addProduct">Add Product</a>
			        		<a class="nav-link" href="/orders/open">Order Queue</a>
			        		<a class="nav-link" href="/orders">Order History</a>
			        	</c:when>
			        	<c:otherwise>
			        		<a class="nav-link" href="/my_orders">My Orders</a>
			        	</c:otherwise>
			        </c:choose>
			      </div>
			      <div class="navbar-nav" style="align-content: flex-end;">
			       	<a class="nav-link"><c:out value="${loggedUser.userName}" /></a>
			        <a class="nav-link" href="/logout">Logout</a>
			      </div>
		      	</div>
		  </div>
		</nav>
	</div>
	
	<div class="content">
		<h2><c:out value="${user.userName}"/>'s Profile</h2>
		

		<div class="reg_form">
			<div class="formPad">

			<div class="mb-3">
			  <label for="userName" class="form-label">Name:</label>
			  <input type="text" name="userName"class="form-control" placeholder="${user.userName}" disabled="disabled">
			</div>
			<div class="mb-3">
			  <label for="userEmail" class="form-label">Email:</label>
			  <input type="email" name="userEmail"class="form-control" placeholder="${user.userEmail}" disabled="disabled">
			</div>
			<div class="mb-3">
			  <label for="userPhone" class="form-label">Phone:</label>
			  <input type="text" name="userPhone"class="form-control" placeholder="${user.userPhone}" disabled="disabled">
			</div>
			<div class="mb-3">
			  <label for="type" class="form-label">User Type:</label>
			  <select disabled="disabled">
			  <option selected value="${user.type}">${user.type}</option>
		      <option value="customer">customer</option>
		      <option value="employee">employee</option>
		      <option value="admin">administrator</option>
			  </select>
			  <br>
				<c:set var="loggedUser" value="${loggedUser}"/>
				<c:set var="user" value="${user}"/>
				<c:if test="${loggedUser == user}">
				<a href="/profile/${loggedUser.id}/edit"> <button class="btn btn-outline-dark" type="button">Edit Profile</button></a>
				</c:if>
			</div>
			</div>		
		</div>

		
		
		</div>
	
	<div class ="footer">
		<img alt="logo" src="images/octopusLogo.png" class="headerLogo">
	</div>


</div>
</div>
<script>
function phoneFormat(input) {//returns (###) ###-####
    input = input.replace(/\D/g,'');
    var size = input.length;
    if (size>0) {input="("+input}
    if (size>3) {input=input.slice(0,4)+") "+input.slice(4,11)}
    if (size>6) {input=input.slice(0,9)+"-" +input.slice(9)}
    return input;
}
</script>
</body>
</html>