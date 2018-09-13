<%@page import="java.util.Random"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CA</title>
<link rel="stylesheet" href="/style.css" />
</head>
<body>

	<p>${ user.firstName }${ user.lastName }</p>
	<p>Your Groups:</p>
	<c:forEach items="${ user.groups }" var="group">
		<p>${ group.name }</p>
	</c:forEach>

	<form action="create-group" method="post">
		<fieldset>
			<legend>Create a new group</legend>
			<label for="name">Group Name: </label> <input name="name">
			<label for="description">Description: </label> <input
				name="description">
			<button type="submit">Submit</button>
		</fieldset>
	</form>
	
	<form action="/join-group" method="post">
		<p>
			<label for="group">Join a Group: </label> <select required id="group"
				name="group">
				<option value="">Select Group</option>

				<c:forEach items="${ groups }" var="group">
					<option value="${ group.id }">${ group.name }</option>
				</c:forEach>
			</select>

			<button type="submit">Join +</button>
		</p>
	</form>

	<form action="/leave-group" method="post">
		<p>
			<label for="group">Leave a Group: </label> <select required
				id="group" name="group">
				<option value="">Select Group</option>

				<c:forEach items="${ user.groups }" var="group">
					<option value="${ group.id }">${ group.name }</option>
				</c:forEach>
			</select>

			<button type="submit">Leave -</button>
		</p>
	</form>
	
	<p>Next Challenge: ${ nextChallenge.name }: ${ nextChallenge.description }</p>

	<h3>Challenge Details:</h3>
	<p>${ nextChallengeDetails.detailResult.formattedAddress}</p>
	<p>${ nextChallengeDetails.detailResult.formattedPhoneNumber}</p>
	<p>${ nextChallengeDetails.detailResult.openingHours.weekdayText}</p>
	
	<c:choose>
  		<c:when test="${ not empty acceptedChallengeExists }">
  			<a href="/challenge-response?response=completed&challengeId=${ nextChallenge.id }">Challenge Completed</a>
			<a href="/challenge-response?response=failed&challengeId=${ nextChallenge.id }">I Have Failed</a>
  		</c:when>
  		<c:when test="${ not empty nextChallenge }">
			<a href="/challenge-response?response=accepted&challengeId=${ nextChallenge.id }">Challenge Accepted</a>
			<a href="/challenge-response?response=declined&challengeId=${ nextChallenge.id }">Nah</a>
		</c:when>
		<c:otherwise>
			<p> Join more groups, loser.</p>
		</c:otherwise>
	</c:choose>
	
	<p><a href="/nearby-search">Select Location for a Challenge</a></p>
	
	</body>
</html>