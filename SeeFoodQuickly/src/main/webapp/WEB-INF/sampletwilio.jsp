<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Twilio test page</title>
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
				<h3>Twilio test page</h3>
				<hr>
				<!--<form:form method="POST" action="api/v1/sms"
					modelAttribute="user">
					<p>
						<form:label path="userName">Your Name:</form:label>
						<form:input type="text" id="userName" path="userName" class="form-control" />
					</p>
					<p>
						<form:label path="userPhone">Your phone number:</form:label>
						<form:input type="text" id="userPhone" path="userPhone" class="form-control" />
					</p>
					<input type="hidden" id="phoneNumber" name="phoneNumber" />
					<input type="hidden" id="message" name="message" />
					<input type="submit" onClick="handleClick();" value="Send SMS" />
				</form:form> -->
				
				<form method="post" action="api/v1/sms">
					<input name="userName" id="userName">
					<input name="userPhone" id="userPhone">
					<input type="hidden" id="phoneNumber" name="phoneNumber" />
					<input type="hidden" id="message" name="message" />
					<button onClick="handleClick();">Test</button>
				</form>
			</div>
		</div>
	</div>
</body>
<script>
  function handleClick()
  {
	// retrieve the form values
	  var userName = document.getElementById("userName").value;
	  var userPhone = document.getElementById("userPhone").value;
	//create JSON for SMS message
	  var jsonObj = {
	      "phoneNumber" : userPhone,
	      "message" : "Hi " + userName + ", your order is ready!"
	  };
	  // convert JSON to string
	  var jsonString = JSON.stringify(jsonObj);
	  // put jsonString into hidden form field
	  document.getElementById('phoneNumber').value = userPhone;
	  document.getElementById('message').value = "Hi " + userName + ", your order is ready!";
  }
</script>
</html>
