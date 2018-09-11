<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Challenge</title>
</head>
<body>

	<form method="post">
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