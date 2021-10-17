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
			       	<a class="nav-link">Signed in as: []USERNAME[]</a>
			        <a class="nav-link" href="/logout">Logout</a>
			      </div>
		      	</div>
		  </div>
		</nav>
	</div>
	
	<div class="content">
		<div class="contentHeader">
			<h1>Menu:</h1>
		</div>
		<div class="menuBody">
			<c:forEach items="${allProducts}" var="product">
				<div class="card" style="width: 15rem; display:flex; flex-direction: column; padding:5px; align-items: center; height:300px; margin-bottom:15px; border-color:rgb(64, 194, 198);">
  					<img src="images/productImage.png" class="card-img-top" alt="productImage" style="width:60px; height:60px">
  					<div class="card-body" style="display:flex; flex-direction:column; align-items: center">
   						 <h6 class="card-title"> <c:out value="${product.itemName}"/> </h6>
    					 <p class="card-text" style="font-size: small;"> <c:out value="${product.description}"/></p>
   						 <form action="/addItemToCart/${product.id}" method="POST">
   						 	<label style="font-size: small;">Choose Amount:</label>
   						 	<select name="quantity" class="form-select" aria-label="Default select example" style="width:75px; font-size: small;">
							  <option value="1">1</option>
							  <option value="2">2</option>
							  <option value="3">3</option>
							  <option value="4">4</option>
							  <option value="5">5</option>
							  <option value="6">6</option>
							  <option value="7">7</option>
							  <option value="8">8</option>
							  <option value="9">9</option>
							  <option value="10">10</option>
							</select>
							<br>
							<button type="submit" style="font-size: small">Add To Cart</button>
   						 </form>
  					</div>
				</div>
			</c:forEach>
		</div>
		<div class="contentFooter">
		<h3><a href="/cart">Proceed to cart</a></h3>	 
		</div>
	</div>
	
	<div class ="footer">
		<img alt="logo" src="images/octopusLogo.png" class="headerLogo">
	</div>
</div>
</div>
</body>
</html>