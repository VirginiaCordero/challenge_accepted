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

	<p>${ user.firstName } ${ user.lastName }</p>
	<p>Your Groups:</p>
	<c:forEach items="${ user.groups }" var="group">
		<p>${ group.name }</p>
	</c:forEach>
	<p><a href="">Create Group</a></p>
	
	<form action="/dashboard/join-group" method="post">
		<p>
			<label for="group">Join a Group: </label>
			<select required id="group" name="group">
				<option value="">Select Group</option>
				
				<c:forEach items="${ groups }" var="group">
					<option value="${ group.id }">${ group.name }</option>
				</c:forEach>
			</select>
			
			<button type="submit">Join +</button>
		</p>
	</form>
	
	<form action="/dashboard/leave-group" method="post">
		<p>
			<label for="group">Leave a Group: </label>
			<select required id="group" name="group">
				<option value="">Select Group</option>
				
				<c:forEach items="${ user.groups }" var="group">
					<option value="${ group.id }">${ group.name }</option>
				</c:forEach>
			</select>
			
			<button type="submit">Leave -</button>
		</p>
	</form>
	
	<a href="/nearby-search">Select Location for a Challenge</a>
	
	<c:forEach items="${ challenges }" var="challenge">
		<p>${ challenge.name }</p>
		<p>${ challenge.description }</p>
	</c:forEach>
	
	<p>Next Challenge: ${ nextChallenge.name }</p>
	
</body>
</html>