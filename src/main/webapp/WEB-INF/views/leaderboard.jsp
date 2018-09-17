<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Leaderboard</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.1.3/darkly/bootstrap.min.css" />
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
<%@ include file="navbar.jsp" %>
	<!-- With boostrap, pages should generally be surrounded with a container element. -->
	<main class="container">
		<h1 class="page-header mt-4 mb-4">Group Leaderboard</h1>
		
		<p>${message}</p>
	
		<div class="row">
			<div class="col-lg-6">
				<table class="table">
					<thead>
						<tr>
							<th>Rank</th>
							<th>Player</th>
							<th>Challenges Completed</th>
							<th>Completion Rate</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="player" items="${ leaderboard }">
							<tr>
								<td>${ player.rank }</td>
								<td>${ player.firstName } ${ player.lastName }</td>
								<td>${ player.completed }</td>
								<td>${ player.completionRate }%</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${ empty leaderboard }">
					<h4>Sorry, no player activity yet!</h4>
				</c:if>
			</div>
		</div>	
			
	</main>
	
</body>