<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Login Page</title>
<meta name="description" content="Login to account">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h1>Welcome to Grandma's Octopus Shack!</h1>
		<div class="row">
			<div class="col">
				<h3>Register</h3>
				<hr>
				<p>
					<c:out value="${regError}" />
				</p>
				<p>
					<form:errors path="user.*" />
				</p>
				<form:form method="POST" action="/register"
					modelAttribute="user">
					<p>
						<form:label path="firstName">First Name:</form:label>
						<form:input type="firstName" path="firstName" class="form-control" />
					</p>
					<p>
						<form:label path="lastName">Last Name:</form:label>
						<form:input type="lastName" path="lastName" class="form-control" />
					</p>
					<p>
						<form:label path="email">Email:</form:label>
						<form:input type="email" path="email" class="form-control" />
					</p>
					<p>
						<form:label path="password">Password:</form:label>
						<form:password path="password" class="form-control" />
					</p>
					<p>
						<form:label path="passwordConfirmation">Confirm Password:</form:label>
						<form:password path="passwordConfirmation" class="form-control" />
					</p>
					<input type="submit" value="Register" />
				</form:form>
			</div>
			<div class="col">
				<h3>Login</h3>
				<hr>
				<p>${authError}</p>
				<form method="POST" action="/login">
					<label>Email:</label>
					<div class="form-control">
						<input type="email" name="loginEmail">
					</div>
					<br> <label>Password:</label>
					<div class="form-control">
						<input type="password" name="loginPassword">
					</div>
					<br>
					<button>Login</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
