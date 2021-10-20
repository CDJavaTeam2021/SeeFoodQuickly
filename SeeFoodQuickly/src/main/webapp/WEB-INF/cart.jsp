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
<title>SFQ Cart</title>
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
		</div>
		<div class="cartBody">
			<h4>Order for: <c:out value="${loggedUser.userName}"/> </h4>
			<h6>Contact: <c:out value="${loggedUser.userPhone}"/></h6>
			<h6>Date: <fmt:formatDate type = "date" dateStyle = "long"  value = "${currentDate}" /></h6>
			<br>
			<div style="height: 200px;width:100%; overflow-y: scroll">
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
					<c:forEach items="${sessionScope.myCart}" var="item">
						<tr>
							<td>${item.itemProduct.itemName}</td>
							<td>${item.quantity}</td>
							<td><fmt:formatNumber value = "${item.lineTotal}" type = "currency"/></td>
	 						<td><a href="/remove/${item.cartIndex}">Remove</a></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<br>
			<div style="width:100%; display:flex; flex-direction: column; align-items: flex-end; padding-right:15px">
				<h6>Sub Total: <fmt:formatNumber value = "${sessionScope.cartTotal}" type = "currency"/></h6>
				<h6>Taxes: <fmt:formatNumber value = "${sessionScope.cartTotal * 0.0925}" type = "currency"/></h6>
				<hr>
				<h5>Total Charge: <fmt:formatNumber value = "${sessionScope.cartTotal * 1.0925}" type = "currency"/></h5>
			</div>
				<form action="/update/contact" method="post" style="display:flex; align-items:center; width:100%; justify-content:flex-end">
					<label for="myPhone">Confirm Phone#:</label>
					<input style="margin-left:10px; margin-right:10px" id="myPhone" name="myPhone" value="${sessionScope.myPhone}">	
					<button type="submit" style="height: 30px; margin-left:15px">Update</button>
				</form>
			
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