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
<title>SFQ Checkout</title>
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
				<div class="checkoutPad">
					<h3>Your Order:</h3>
					<hr>
					<div class="checkout_table" style="border-style:solid; border-width: 2px; border-color:orange; ;padding:15px; height: 300px;border-radius:5px; overflow-y:scroll " >
						<table>
							<tbody>
							<c:forEach items="${sessionScope.myCart}" var="item">
								<tr style="display:flex; flex-direction: row; justify-content: space-around; margin-bottom: 5px">
									<td>(${item.quantity}) </td>
									<td style="width:270px">${item.itemProduct.itemName}</td>
									<td><fmt:formatNumber value = "${item.lineTotal}" type = "currency"/></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<hr>
					<h6 style="display:flex; flex-direction:row; width:350px;justify-content: flex-end">Sub Total: <fmt:formatNumber value = "${sessionScope.cartTotal}" type = "currency"/></h6>
					<h6 style="display:flex; flex-direction:row; width:350px;justify-content: flex-end">Taxes: <fmt:formatNumber value = "${sessionScope.cartTotal * 0.0925}" type = "currency"/></h6>
					<h5 style="display:flex; flex-direction:row; width:350px;justify-content: flex-end">Total Charge: <fmt:formatNumber value = "${sessionScope.cartTotal * 1.0925}" type = "currency"/></h5>
					<hr>
					<!-- 							CREDIT CARD PROCESSING    -->
					
					<form action='/charge' method='POST' id='checkout-form' xmlns:th="http://www.w3.org/1999/xhtml">
<%-- 					<input type='hidden' th:value='${sessionScope.cartTotal * 1.0925}' name='amount' />  --%>
					    <input type='hidden' value='${sessionScope.cartTotal * 1.0925}' name='amount' />
					    <script
					            src='https://checkout.stripe.com/checkout.js'
					            class='stripe-button'
					            data-key="pk_test_51JkGb9LiLrBml3YZDf9jKApvu5SDJnA3DMAnJYWNnNMLVAt2kygpBCM6GQtJXnILH0k68HMl8Uv86Mx7TaXt3wtS00AIvnnaWm"
					         	data-amount='${sessionScope.cartTotal * 109.25}'
					            data-name='SFQ'
					            data-description='Checkout'
					            data-image='images/octopusLogo.png'
					            data-locale='auto'
					            data-zip-code='false'>
					    </script>
					</form>

				</div>
		<div class="contentFooter">
		</div>
	</div>
	
	<div class ="footer">
		<img alt="logo" src="images/octopusLogo.png" class="headerLogo">
	</div>
</div>
</div>
</body>
</html>