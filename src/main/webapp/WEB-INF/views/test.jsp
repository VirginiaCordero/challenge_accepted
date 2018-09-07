<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:forEach var="result" items="${ nearbySearchResults.results }">
	<p>Name: ${ result.name }</p>
	<p>Rating: ${ result.rating }</p>
	<p>Address: ${ result.vicinity }</p>
	<p>Latitude: ${ result.geometry.location.lat }</p>
	<p>Longitude: ${ result.geometry.location.lng }</p>
	<p>Keywords: ${ result.types }</p>

</c:forEach>

</body>
</html>