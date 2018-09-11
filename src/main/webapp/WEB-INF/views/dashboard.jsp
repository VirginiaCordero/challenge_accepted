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

<<<<<<< Updated upstream
	<p>${ user.firstName } ${ user.lastName }</p>
	<c:forEach items="${ user.groups }" var="group">
		<p>${ group.name }</p>
	</c:forEach>
	
=======
	<h1>Psssssst...</h1>
	<p>${ user.firstName }</p>
	<p>${ user.groups }</p>
	<p>If you made it here, you must be logged in. Congratulations here's your reward...</p>
>>>>>>> Stashed changes
	
</body>
</html>