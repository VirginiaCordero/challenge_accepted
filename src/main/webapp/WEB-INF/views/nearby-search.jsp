<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Nearby Search</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.1.3/darkly/bootstrap.min.css" />
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
<%@ include file="navbar.jsp" %>
	<!-- With boostrap, pages should generally be surrounded with a container element. -->
	<main class="container">
		<h1 class="page-header mt-4 mb-4">Events List</h1>
		
		<p>${message}</p>
	
		<form class="form-inline" autocomplete="off">
	
			<label class="sr-only" for="keyword">Keyword</label> <input
				type="text" value="${param.keyword}"
				class="form-control mb-2 mr-sm-2" id="keyword" name="keyword"
				placeholder="Keyword">
	
			<button type="submit" class="btn btn-primary mb-2 mr-2">Search</button>
	
			<c:if test="${not empty param.keyword}">
				<a href="/" class="btn btn-secondary mb-2">Clear</a>
			</c:if>
	
		</form>
		<div class="row">
			<div class="col-lg-6">
				<table class="table">
					<thead>
						<tr>
							<th>Name</th>
							<th>Rating</th>
							<th>Create Challenge</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${ nearbySearchResults.results }">
							<tr>
								<td>${ result.name }</td>
								<td>${ result.rating }</td>
								<td><a href="/create-challenge?placeId=${ result.placeId }">Challenge!</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${ empty nearbySearchResults }">
					<h4>Sorry, your search did not return any results :(</h4>
				</c:if>
			</div>
			
			<div class="col-lg-6">
				<iframe width="550" height="550" frameborder="0" style="border: 0"
					src="${ embeddedMapUrl }"
					allowfullscreen>
				</iframe>
			</div>
			
		</div>
		
		
		<p>
			<a href="">Previous 20 Results</a> -- <a href="">Next 20 Results</a>
		<p>
		
	</main>
	
</body>