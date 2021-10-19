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

	<div class="header">
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

			       	<a class="nav-link" href="/profile/${loggedUser.id}"><c:out value="${loggedUser.userName}"/></a>
			        <a class="nav-link" href="/logout">Logout</a>
			      </div>
		      	</div>
		  </div>
		</nav>
	</div>
	
	<div class="content">
		<div class="contentHeader">
			<h1>New Menu Item:</h1>
		</div>
		<p>
			<form:errors path="product.*" />
		</p>
		<div class="reg_form">
		<form:form method="POST" action="/addProduct" modelAttribute="product">
			<div class="formPad" style="">
				<p>
					<form:label path="itemName">Item Name:</form:label>
					<form:input type="text" path="itemName" class="form-control" style="width:200px"/>
				</p>
				<p>
					<form:label path="description">Description:</form:label>
					<form:textarea type="text" path="description" class="form-control" style="width:300px"/>
				</p>
				<p>
					<form:label path="makeMinutes">Cook Time:</form:label>
					<form:select path="makeMinutes" style="width:200px" class="form-select">
						<form:option value="5">5 Minutes</form:option>
						<form:option value="10">10 Minutes</form:option>
						<form:option value="15">15 Minutes</form:option>
						<form:option value="20">20 Minutes</form:option>
						<form:option value="25">25 Minutes</form:option>
						<form:option value="30">30 Minutes</form:option>
					</form:select>	
				</p>
				<p>
					<form:label path="price">Price: $</form:label>
					<form:input type="float" path="price" class="form-control" style="width:100px"/>
				</p>
				
				
			</div>
			<button type="submit" style="margin-left:110px; margin-top:20px;">Add New Item</button>
		</form:form>
		</div>
	</div>
	
	<div class ="footer">
		<img alt="logo" src="images/octopusLogo.png" class="headerLogo">
	</div>
</div>
</div>
</body>
</html>