<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Create Challenge</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.1.3/darkly/bootstrap.min.css" />
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
<%@ include file="navbar.jsp" %>
	<form action="/create-challenge" method="post">
		<p>
			<label for="name">Name: </label>
			<input type="text" id="name" name="name">
		</p>
		<p>
			<label for="group">Group: </label>
			<select required id="group" name="group">
				<option value="">Select Group</option>
				
				<c:forEach items="${ groups }" var="group">
					<option value="${ group.id }">${ group.name }</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<label for="description">Description: </label>
			<input type="text" id="description" name="description">
		</p>
		<p>
			<label for="type">Type: </label>
			<input type="text" id="type" name="type">
		</p>
		<p>
			<label for="placeId">Place Id: </label>
			<input type="text" id="placeId" name="placeId" value="${ placeDetailResult.detailResult.placeId }">
		</p>
		<p>
			<label for="location">Location: </label>
			<input id="location" name="location" value="${ placeDetailResult.detailResult.geometry.location.lat },${ placeDetailResult.detailResult.geometry.location.lng }">
		</p>
		 
		 <button type="submit">Create +</button>
	</form>

	
	<p>${ placeDetailResult.detailResult.formattedAddress }<p>
	<p>${ placeDetailResult.detailResult.formattedPhoneNumber}<p>
	<p>${ placeDetailResult.detailResult.name }<p>

</body>
</html>