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
		      <a class="nav-link" href="/about">About See Food Quickly</a>
		    </button>
			    <div class="collapse navbar-collapse" id="navbarNavAltMarkup" style="display:flex; justify-content: space-between">
			      <div class="navbar-nav" style="align-items: flex-start;">
			        <a class="nav-link">About</a>
			        <a class="nav-link">Menu</a>
			        <a class="nav-link">Download</a>
			      </div>
			      <div class="navbar-nav" style="align-content: flex-end;">
			        <a class="nav-link" href="/login">Sign In SFQ!</a>
			      </div>
		      	</div>
		  </div>
		</nav>
	</div>
	
	<div class="content">
		<h2>Register</h2>
		
			<c:out value="${regError}" />
		<p>
			<form:errors path="user.*" />
		</p>
		<div class="reg_form">
		<form:form method="POST" action="/register" modelAttribute="user">
			<div class="formPad">
				<p>
					<form:label path="userName">Name:</form:label>
					<form:input type="text" path="userName" class="form-control" style="width:200px"/>
				</p>
				<p>
					<form:label path="userPhone">Phone:</form:label>
					<form:input type="text" path="userPhone" class="form-control" style="width:200px" id="phone" onInput="this.value = phoneFormat(this.value)"/>
				</p>
				<p>
					<form:label path="userEmail">Email:</form:label>
					<form:input type="text" path="userEmail" class="form-control" style="width:200px"/>
				</p>
				<p>
					<form:label path="password">Password:</form:label>
					<form:password path="password" class="form-control" style="width:200px" />
				</p>
				<p>
					<form:label path="confirmPassword">Confirm Password:</form:label>
					<form:password path="confirmPassword" class="form-control" style="width:200px"/>
			</div>
				</p>
			<button type="submit" style="margin-left:140px">Submit</button>
		</form:form>
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