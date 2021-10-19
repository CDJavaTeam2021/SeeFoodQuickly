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
			       	<a class="nav-link">Signed in as: ${sessionScope.userName }</a>
			        <a class="nav-link" href="/logout">Logout</a>
			      </div>
		      	</div>
		  </div>
		</nav>
	</div>
	
	<div class="content">
		<div class="contentHeader">
			<h1>New Product</h1>
		</div>
		<div class="contentBody">
					<form:form action="/products/new" method="post" modelAttribute="newProduct">
						<p>
							<form:label path="itemName">Product Name</form:label>
							<form:errors path="itemName" />
							<form:input path="itemName" />
						</p>
						<p>
							<form:label path="description">Description</form:label>
							<form:errors path="description" />
							<form:textarea path="description"/>
						</p>
						<p>
							<form:label path="price">Price</form:label>
							<form:errors path="price" />
							<form:input path="price" type="number" min="0.01" step="0.01"/>
						</p>
						<p>
							<form:label path="makeMinutes">Time to Make in Minutes:</form:label>
							<form:errors path="makeMinutes" />
							<form:input path="makeMinutes" type="number" min="1" step=".5"/>
						</p>
						<form:button>Save Item!</form:button>

					</form:form>
					
					<form action="/test/upload" method="post" enctype="multipart/form-data">
						<input type="file" name="image">
						<button>Upload!</button>
					</form>
					
					<c:forEach items="${pictures}" var="picture">
						<img src="${picture.url}" alt="${picture.description}">
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