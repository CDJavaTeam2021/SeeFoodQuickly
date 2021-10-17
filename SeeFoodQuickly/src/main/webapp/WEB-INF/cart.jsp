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
			        <a class="nav-link" href="/orders">View Orders</a>
			        <a class="nav-link" href="/addProduct">Add Product</a>
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
		<div class="contentHeader">
		</div>
		<div class="cartBody">
			<h4>Order for: {UserName}</h4>
			<h6>Contact: {PhoneNumber}</h6>
			<h6>Date: {CurrDate}</h6>
			<br>
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Item</th>
						<th>Quantity</th>
						<th>Charge</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
				<%-- <c:forEach items="" var=""> --%>
					<tr>
						<td></td>
						<td></td>
						<td>$</td>
						<td><a>Remove</a></td>
					</tr>
				<%-- </c:forEach> --%>
				</tbody>
			</table>
			
			<br>
			<h6>Sub Total: $ {{Sum}}</h6>
			<h6>Taxes: $ {{Tax}}</h6>
			<hr>
			<h5>Total Charge: $ {{Total Charge}}</h5>
			
			
			
		</div>
		<div class="contentFooter">
		<a href="/checkout">Proceed To Checkout</a>
		</div>
	</div>
	
	<div class ="footer">
		<img alt="logo" src="images/octopusLogo.png" class="headerLogo">
	</div>
</div>
</div>
</body>
</html>