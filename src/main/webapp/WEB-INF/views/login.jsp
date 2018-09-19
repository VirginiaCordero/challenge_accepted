<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Log In</title>
<link rel="stylesheet"
	href="https://bootswatch.com/4/cyborg/bootstrap.css" />
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
<%@ include file="navbar.jsp" %>
	<div class="container">
		<div class="row">
			<div class="col-md-6 mx-auto">
				<form action="/login" method="post" id="fileForm" role="form">
					<fieldset>
						<legend class="text-center">
							<h2>Challenge Accepted</h2> <span class="req"></span>
							<h6>Challenge your friends to fun and simple competitions.</h6>
						</legend>
						<div class="form-group">
							<label for="email"><span class="req">* </span> Email
								Address: </label> <input
								class="form-control" required type="text" name="email" value="${ param.email }" 
								id="email" />
							<div class="status" id="status"></div>
						</div>
						<div class="form-group">
							<label for="password"><span class="req">* </span>
								Password: </label> <input required name="password" type="password"
								class="form-control inputpass" minlength="4" maxlength="16"
								id="pass1" /> <label for="password"></label>
						</div>
						<div align=center class="form-group">
							<button class="btn btn-success">Log In</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</body>
</html>