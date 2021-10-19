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
<title>See Food Quickly Login</title>
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
			        <a class="nav-link" href="/about">About See Food Quickly</a>
			        <a class="nav-link">Menu</a>
			        <a class="nav-link">Download</a>
			      </div>
			      <div class="navbar-nav" style="align-content: flex-end;">
			        <a class="nav-link" href="/registration">Register for SFQ!</a>
			      </div>
		      	</div>
		  </div>
		</nav>
	</div>
	
	<div class="content">
		<img alt="logo" src="images/octopusLogo.png" class="contentLogo">
		
			<h1>See Food Quickly</h1>
			<p>${authError}</p>
			<form method="POST" action="/login">
				<div style="display:flex; flex-direction: column; align-items: center; margin-top:5px">
					<div class="form-floating mb-3" style="background-color: white; border-style:solid; border-color:rgb(251, 205, 0);border-width:4px; border-radius:5px">
					  <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" name="email">
					  <label for="floatingInput">Email address</label>
					</div>
					<div class="form-floating" style="background-color: white; border-style:solid; ; border-color:rgb(251, 205, 0);border-width:4px; border-radius:5px">
					  <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
					  <label for="floatingPassword">Password</label>
					</div>
					<br>
					<button type="submit" style="background-color: white; border-style:solid; border-width:3px; border-radius:10px">Login</button>
				</div>
			</form>
		
		
	</div>
	
	<div class ="footer">
	
	</div>


</div>
</div>

</body>
</html>