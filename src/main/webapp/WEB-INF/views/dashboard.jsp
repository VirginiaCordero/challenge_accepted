<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Dashboard</title>
<link rel="stylesheet"
	href="https://bootswatch.com/4/cyborg/bootstrap.css" />
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container">
		<!-- dashboard header -->
		<div class="card text-white bg-primary mb-4" style="width: 36rem;">
			<div class="card-body">
				<h3 class="card-title">Dashboard</h3>
				<img class="card-img-top" src="https://via.placeholder.com/360x100"
					alt="Card image cap">
			</div>
		</div>
		<!-- welcome -->
		<div class="card text-white bg-primary mb-3" style="max-width: 20rem;">
			<div class="card-header">Welcome ${ user.firstName } ${ user.lastName }</div>
			<div class="card-body">
				<h4 class="card-title">Your Stats</h4>
				<p class="card-text">Accepted: ${ accepted }:</p>
				<p class="card-text">Declined: ${ declined }:</p>
				<p class="card-text">Completed: ${ completed }</p>
				<p class="card-text">Failed: ${ failed }</p>
				<p class="card-text">Acceptability: ${ acceptDeclineRatio }</p>
				<p class="card-text">Completionistabilityness: ${ completeFailRatio }</p>
				<p class="card-text">Created: ${ created }</p>
			</div>
		</div>
		<!-- displayed challenge statistics -->
		<div class="card text-white bg-primary mb-4" style="width: 20rem;">
			<div class="card-header">Displayed Challenge Statistics</div>
			<div class="card-body">
				<p class="card-text">Accepted: ${ displayedChallengeNumAccepts }
					${ displayedChallengeAcceptList }</p>
				<p class="card-text">Declined: ${ displayedChallengeNumDeclines }
					${ displayedChallengeDeclineList }</p>
				<p class="card-text">Completed: ${ displayedChallengeNumCompleted }
					${ displayedChallengeCompleteList }</p>
				<p class="card-text">Failed: ${ displayedChallengeNumFailed } ${ displayedChallengeFailList }</p>
			</div>
		</div>
		<!-- your groups -->
		<div class="card text-white bg-primary mb-4" style="width: 20rem;">
			<div class="card-header">Your groups</div>
			<div class="card-body">
				<c:forEach items="${ usersGroupsInfo }" var="group">
					<p class="card-text">${ group.name }:${ group.description }</p>
					<p class="card-text">Rank: ${ group.userRank } out of ${ group.numMembers }</p>
					<p>
						<a href="/group-leaderboard?groupId=${ group.id }">See Group
							Leaderboard!</a>
				</c:forEach>
			</div>
		</div>
		<!--  create a new group -->
		<div class="card text-white bg-primary mb-4" style="width: 20rem;">
			<div class="card-header">Create a new group</div>
			<div class="card-body">
				<form action="create-group" method="post">
					<div class="form-group row">
						<label for="name" class="col-sm-9 col-form-label">Group</label>
						<div class="col-sm-9">
							<input name="name" class="form-control"
								placeholder="What is the group name?">
						</div>
					</div>
					<div class="form-group row">
						<label for="description" class="col-sm-9 col-form-label">Description</label>
						<div class="col-sm-9">
							<input name="description" class="form-control"
								placeholder="What is your group about?">
						</div>
					</div>
					<div class="form-group row">
						<div class="col-sm-7">
							<button type="submit" class="btn btn-warning">Submit</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--  join a group -->
		<div class="card text-white bg-primary mb-4" style="width: 20rem;">
			<div class="card-header">Join a new group</div>
			<div class="card-body">
				<form action="/join-group" method="post">
					<div class="form-group row">
						<div class="input-group col-sm-9">
							<select required id="group" name="group" class="custom-select"
								id="inputGroupSelect01">
								<option value="">Select Group</option>
								<c:forEach items="${ groups }" var="group">
									<option value="${ group.id }">${ group.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-sm-7">
							<button type="submit" class="btn btn-success">Join +</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--  leave a group -->
		<div class="card text-white bg-primary mb-4" style="width: 20rem;">
			<div class="card-header">Leave a group</div>
			<div class="card-body">
				<form action="/leave-group" method="post">
					<div class="form-group row">
						<div class="col-sm-9">
							<select required id="group" name="group" class="custom-select" id="inputGroupSelect01">
								<option value="">Select Group</option>
								<c:forEach items="${ user.groups }" var="group">
									<option value="${ group.id }">${ group.name }</option>
								</c:forEach>
							</select>
						</div>
						</div>
						<div class="form-group row">
						<div class="col-sm-7">
							<button type="submit" class="btn btn-danger">Leave -</button>
						</div>
						</div>
				</form>
			</div>
		</div>	
	<!-- your statistics -->
		<div class="card text-white bg-success mb-4" style="width: 36rem;">
			<img class="card-img-top"
				src="https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=${ nextChallengeDetails.detailResult.photos.get(0).photoReference }&key=${ apiKey }"
				alt="Card image cap">
			<div class="card-body">
				<h3 class="card-title">Next Challenge</h3>
				<p class="card-text">${ nextChallenge.name }</p>
				<p class="card-text">${ nextChallenge.description }</p>
				<h3 class="card-title">Challenge Details</h3>
				<p class="card-text">${ nextChallengeDetails.detailResult.name}</p>
				<p class="card-text">${ nextChallengeDetails.detailResult.formattedAddress}</p>
				<p class="card-text">${ nextChallengeDetails.detailResult.formattedPhoneNumber}</p>
				<p class="card-text">${ nextChallengeDetails.detailResult.openingHours.weekdayText}</p>
				<h3 class="card-title">Challenge Accepted</h3>
				<c:choose>
					<c:when test="${ not empty acceptedChallengeExists }">
						<a
							href="/challenge-response?response=completed&challengeId=${ nextChallenge.id }">Challenge
							Completed</a>
						<a
							href="/challenge-response?response=failed&challengeId=${ nextChallenge.id }">I
							Have Failed</a>
					</c:when>
					<c:when test="${ not empty nextChallenge }">
						<a
							href="/challenge-response?response=accepted&challengeId=${ nextChallenge.id }">Challenge
							Accepted</a>
						<a
							href="/challenge-response?response=declined&challengeId=${ nextChallenge.id }">Nah</a>
					</c:when>
					<c:otherwise>
						<p>Join more groups, loser.</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
</div>
		<!--last div-->

		<!-- ==================== Legacy Code, but functional ==================== -->
		<%-- <p>${ user.firstName }${ user.lastName }</p>
>>>>>>> Stashed changes
	<p>Your Groups:</p>

	<c:forEach items="${ user.groups }" var="group">
		<p>${ group.name }</p>
	</c:forEach>

	<form action="create-group" method="post">
		<fieldset>
			<legend>Create a new group</legend>
			<label for="name">Group Name: </label> <input name="name"> <label
				for="description">Description: </label> <input name="description">
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
	</form> --%>

		<%-- <p>Next Challenge: ${ nextChallenge.name } -- ${ nextChallenge.description }</p>
	<h3>Challenge Details:</h3>
	<p>${ nextChallengeDetails.detailResult.formattedAddress}</p>
	<p>${ nextChallengeDetails.detailResult.formattedPhoneNumber}</p>
	<p>${ nextChallengeDetails.detailResult.openingHours.weekdayText}</p> --%>

		<%-- <c:choose>
		<c:when test="${ not empty acceptedChallengeExists }">
			<a
				href="/challenge-response?response=completed&challengeId=${ nextChallenge.id }">Challenge
				Completed</a>
			<a
				href="/challenge-response?response=failed&challengeId=${ nextChallenge.id }">I
				Have Failed</a>
		</c:when>
		<c:when test="${ not empty nextChallenge }">
			<a
				href="/challenge-response?response=accepted&challengeId=${ nextChallenge.id }">Challenge
				Accepted</a>
			<a
				href="/challenge-response?response=declined&challengeId=${ nextChallenge.id }">Nah</a>
		</c:when>
		<c:otherwise>
			<p>Join more groups, loser.</p>
		</c:otherwise>
	</c:choose> --%>
		<!-- <p>
		<a href="/nearby-search">Select Location for a Challenge</a>
	</p> -->
</body>
</html>