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
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.1.3/darkly/bootstrap.min.css" />
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
	<%@ include file="navbar.jsp"%>

	<div class="container">
		<p>In order to create challenges near you, we need you to share
			your location with us.</p>
		<p>Don't worry. It's in a safe place.</p>
		<h2>Set Your Current Location for Searches</h2>		
		<form method="post" action="/set-location" id="location-form" class="form-inline my-2 my-lg-0" role="form">
			<!-- <input hidden id="location" value="" name="location"> -->
			<label for="zipcode">Zipcode</label>
			<input required id="zipcode" name="zipcode">
			<button>Submit!</button>
		</form>
		<!-- <button onclick="getLocation()" class="btn btn-outline-success my-2 my-sm-0" type="submit">Save My Current Location!</button> -->
		<p>Sometimes this can take a few seconds...</p>
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