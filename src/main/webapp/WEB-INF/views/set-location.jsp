<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>location

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Registration</title>
<link rel="stylesheet"
	href="https://bootswatch.com/4/cyborg/bootstrap.css" />
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container">
		<div class="card border-info mb-3 mx-auto" style="max-width: 20rem;">
<!-- 			<div class="card-header">Set your current Location for new searches</div>
 -->			<div class="card-body">
				<h4 class="card-title">New location</h4>
				<p class="card-text">In order to create challenges near you, we
					need you to share your location with us. Don't worry. It's in a
					safe place.</p>
				<div class="form-group row mx-auto">
				<form method="post" action="/set-location" id="location-form"
					class="form-inline my-2 my-lg-0" role="form">
					<!-- <input hidden id="location" value="" name="location"> -->
					<label for="zipcode">Zipcode</label> 
					<div class="col-sm-9">
					<input class="form-control" type="text" placeholder="1234"required id="zipcode"
						name="zipcode">
						</div>
					</div>
					<div class="form-group row mx-auto">
					<button type="button" class="btn btn-success mx-auto">Set Location</button>
					</div>		
				</form>
			</div>
			<!-- <button onclick="getLocation()" class="btn btn-outline-success my-2 my-sm-0" type="submit">Save My Current Location!</button> -->
			<script>
				let x = document.getElementById("location");

				function getLocation() {
					if (navigator.geolocation) {
						navigator.geolocation.watchPosition(showPosition);
					} else {
						x.innerHTML = "Geolocation is not supported by this browser.";
					}
				}
				function showPosition(position) {
					var coord = position.coords.latitude + ","
							+ position.coords.longitude;
					x.value = coord;
					document.getElementById("location-form").submit();

				}
			</script>
</body>
</html>