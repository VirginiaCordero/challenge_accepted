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
	href="https://bootswatch.com/4/cyborg/bootstrap.css" />
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
<%@ include file="navbar.jsp" %>
	<div class="container text-center" style="max-width: 650px;">
		<h3>Create a Challenge!</h3>
		<form action="/create-challenge" method="post">
		
			<div class="form-group">
    			<label for="name" class="col-sm-2 col-form-label col-form-label-lg">Title</label>
    			<div>
      				<input required type="text" name="name" class="form-control form-control-lg" id="name" placeholder="Challenge Title">
    			</div>
  			</div>
  			
			<div class="form-group">
    			<label for="description" class="col-sm-2 col-form-label col-form-label-lg">Description</label>
    			<div>
      				<textarea required type="text" name="description" class="form-control form-control-lg" id="description" rows="3" placeholder="Challenge Description"></textarea>
    			</div>
  			</div>
  			
  			<div class="form-group">
    			<label for="group" class="col-sm-2 col-form-label col-form-label-lg">Group</label>
    			<div>
      				<select class="form-control" required id="group" name="group">
						<option value="">Select Group</option>	
						<c:forEach items="${ groups }" var="group">
							<option value="${ group.id }">${ group.name }</option>
						</c:forEach>
					</select>		
    			</div>
  			</div>
  			
  			<input hidden type="text" id="placeId" name="placeId" value="${ placeDetailResult.detailResult.placeId }">
  			
  			<input hidden id="location" name="location" value="${ placeDetailResult.detailResult.geometry.location.lat },${ placeDetailResult.detailResult.geometry.location.lng }">
  			
  			<button type="submit" class="btn btn-lg btn-info mt-3">Issue Challenge</button>
		
		</form>
	
		
		<p>${ placeDetailResult.detailResult.formattedAddress }<p>
		<p>${ placeDetailResult.detailResult.formattedPhoneNumber}<p>
		<p>${ placeDetailResult.detailResult.name }<p>
	</div>

</body>
</html>