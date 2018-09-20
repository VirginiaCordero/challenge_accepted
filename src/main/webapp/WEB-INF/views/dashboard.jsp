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
<link rel="stylesheet" href="https://bootswatch.com/4/cyborg/bootstrap.css" />
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- Custom CSS goes below Bootstrap CSS -->
<link rel="stylesheet" href="/style.css" />
</head>
<body>
	<%@ include file="navbar.jsp"%>
	<div class="container" style="margin-top: 30px;">
		<a class="btn btn-lg btn-secondary mb-3" style="width: 100%" href="/nearby-search">CREATE CHALLENGE</a>
		
		<!-- dashboard header -->
		<div>
			<c:if test="${ not empty nextChallenge }">
				<div class="card mb-3">
					<div class="card-header" style="background: url(https://maps.googleapis.com/maps/api/place/photo?maxwidth=5000&photoreference=${ nextChallengeDetails.detailResult.photos.get(0).photoReference }&key=${ apiKey });
													background-size: cover;
													color: white;
													height:600px;">
					<div style="margin-top: 440px;
								background-color: rgba(70, 70, 70, 0.6);
								border-radius: 10px;
								padding: 10px;">
						<h3>Next Challenge: ${ nextChallenge.name }</h3>
						<h5>from ${ nextChallenge.user.firstName } ${ nextChallenge.user.lastName } | to ${ nextChallenge.group.name }</h5>
					</div>
					</div>
					<div class="card-body">
						<div class="row card-header">
							<div class="text-center col-lg-4">
								<h4>LOCATION</h4>
								<h5 class="text-muted">${ nextChallengeDetails.detailResult.name}</h5>
							</div>
							<div class="text-center col-lg-4">
								<h4>REQUIREMENTS</h4>
								<h5 class="text-muted">${ nextChallenge.description }</h5>
							</div>
							<div class="text-center col-lg-4">
								<h4>DIFFICULTY</h4>
								<h5 class="text-muted">${ displayedChallengeDifficulty }/100</h5>
							</div>
						</div>
						
						<div class="row card-header" style="padding-top: 30px;">
							<div class="col-lg-2 text-center">
								<h3>${ displayedChallengeNumCompleted }</h3>
								<h5>Completed</h5>
								<ul class="text-muted" style="list-style: none; padding-left: 0;">
									<c:forEach var="userName" items="${ displayedChallengeCompleteList }">
										<li>${ userName }</li>
									</c:forEach>
								</ul>
							</div>
							<div class="col-lg-2 text-center">
								<h3>${ displayedChallengeNumAccepts }</h3>
								<h5>Accepted</h5>
								<ul class="text-muted" style="list-style: none; padding-left: 0;">
									<c:forEach var="userName" items="${ displayedChallengeAcceptList }">
										<li>${ userName }</li>
									</c:forEach>
								</ul>
							</div>
							<div class="col-lg-4 text-center">
								<c:choose>
									<c:when test="${ not empty displayedChallengeGroupRank }">
										<h3>No. ${ displayedChallengeGroupRank }</h3>
									</c:when>
									<c:otherwise>
										<h3>No. _</h3>
									</c:otherwise>
								</c:choose>
								<h5>Your Rank In</h5>
								<h5><a href="/group-leaderboard?groupId=${ nextChallenge.group.id }">${ nextChallenge.group.name }</a></h5>
							</div>
							<div class="col-lg-2 text-center">
								<h3>${ displayedChallengeNumDeclines }</h3>
								<h5>Declined</h5>
								<ul class="text-muted" style="list-style: none; padding-left: 0;">
									<c:forEach var="userName" items="${ displayedChallengeDeclineList }">
										<li>${ userName }</li>
									</c:forEach>
								</ul>
							</div>
							<div class="col-lg-2 text-center">
								<h3>${ displayedChallengeNumFailed }</h3>
								<h5>Failed</h5>
								<ul class="text-muted" style="list-style: none; padding-left: 0;">
									<c:forEach var="userName" items="${ displayedChallengeFailList }">
										<li>${ userName }</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="row card-header" style="padding-top: 30px; padding-bottom: 30px;">
							<c:choose>
								<c:when test="${ not empty acceptedChallengeExists }">
									<div class="col-lg-6 text-center">
										<a class="btn btn-info btn-lg" role="button"
											href="/challenge-response?response=completed&challengeId=${ nextChallenge.id }">Challenge
											Completed</a>
									</div>
									<div class="col-lg-6 text-center">								
										<a class="btn btn-lg btn-light" role="button"
											href="/challenge-response?response=failed&challengeId=${ nextChallenge.id }">I
											Have Failed</a>
									</div>
								</c:when>
								<c:when test="${ not empty nextChallenge }">
									<div class="col-lg-6 text-center">																	
										<a class="btn btn-info btn-lg" role="button"
											href="/challenge-response?response=accepted&challengeId=${ nextChallenge.id }">Challenge
											Accepted</a>
									</div>
									<div class="col-lg-6 text-center">
										<a class="btn btn-warning btn-lg" role="button"
											href="/challenge-response?response=declined&challengeId=${ nextChallenge.id }">Nah</a>
									</div>
								</c:when>
								<c:otherwise>
									<p>Something went wrong!</p>
								</c:otherwise>
							</c:choose>	
						</div>
						
						<div class="card-header text-center" style="padding-top: 15px;">
							<button class="btn btn-lg btn-secondary drawer">Location Details</button>
						</div>
						
						<div class="collapse" id="collapseExample">
						
								<div class="row" style="height: 450px; padding-top: 30px">
									
									<div class="col-lg-6">
									
										<ul class="list-group list-group-flush">
											<li class="list-group-item">${ nextChallengeDetails.detailResult.name}</li>
											<li class="list-group-item">${ nextChallengeDetails.detailResult.website}</li>
											<li class="list-group-item">${ nextChallengeDetails.detailResult.formattedPhoneNumber}</li>								
											<li class="list-group-item">${ nextChallengeDetails.detailResult.formattedAddress}</li>
											<li class="list-group-item">
												<ul style="list-style: none; padding-left: 0px;">
													<c:forEach var="dailyHours" items="${ nextChallengeDetails.detailResult.openingHours.weekdayText}">
														<li>${ dailyHours }</li>
													</c:forEach>
												</ul>
											</li>
										</ul>
										
									</div>
									
									<div class="col-lg-6" style="opacity: .8;">
										<iframe width="100%"
												height="100%"
												frameborder="0" style="border:0" 
												src="https://www.google.com/maps/embed/v1/place?key=${ apiKey }&q=place_id:${ nextChallengeDetails.detailResult.placeId }"></iframe>
									</div>
								</div>
							</div>
							
					</div>
				</div>
			</c:if>
			<c:if test="${ empty nextChallenge }">
				<div class="text-center">
					<h3 class="text-muted">No New Challenges.</h3>
					<h5 class="text-muted">Join More Groups or Create Your Own Challenge!</h5>
				</div>
			</c:if>
		</div>
		
			<!-- welcome -->
			<%-- <div class="card text-white bg-primary mb-3"
				style="max-width: 20rem;">
				<div class="card-header">Welcome ${ user.firstName } ${ user.lastName }</div>
				<div class="card-body">
					<div class="progress">
						<p class="card-text">Completed:</p>
						<div class="progress-bar bg-success" role="progressbar"
							style="width: ${ completed }%" aria-valuenow="25"
							aria-valuemin="0" aria-valuemax="100"></div>
					</div>
					<div class="progress">
						<p class="card-text">Accepted:</p>
						<div class="progress-bar bg-info" role="progressbar"
							style="width: ${ accepted }%" aria-valuenow="50"
							aria-valuemin="0" aria-valuemax="100"></div>
					</div>
					<div class="progress">
						<p class="card-text">Declined:</p>
						<div class="progress-bar bg-warning" role="progressbar"
							style="width: ${ declined }%" aria-valuenow="75"
							aria-valuemin="0" aria-valuemax="100"></div>
					</div>
					<div class="progress">
						<p class="card-text">Failed:</p>
						<div class="progress-bar bg-danger" role="progressbar"
							style="width: ${ failed }%" aria-valuenow="100" aria-valuemin="0"
							aria-valuemax="100"></div>
					</div>
								<h4 class="card-title">Your Stats</h4>
				<p class="card-text">Accepted: ${ accepted }:</p>
				<p class="card-text">Declined: ${ declined }:</p>
				<p class="card-text">Completed: ${ completed }</p>
				<p class="card-text">Failed: ${ failed }</p>
				<p class="card-text">Acceptability: ${ acceptDeclineRatio }</p>
				<p class="card-text">Completionistabilityness: ${ completeFailRatio }</p>
				<p class="card-text">Created: ${ created }</p>
				</div>
			</div> --%>
			
			<!-- your groups -->
			<h3 class="text-center text-muted">Your Ranking</h3>
			<div class="row d-flex justify-content-around">		
				<c:forEach items="${ usersGroupsInfo }" var="group">
					<div class="card ml-1 mr-1 mb-3" style="width: 20rem">
						<div class="card-header text-center">
							<h5>${ group.name }</h5>
							<p class="text-muted text-center">${ group.description }</p>
						</div>
						<div class="card-body text-center flush">
							<c:choose>
								<c:when test="${ not empty group.userRank }">
									<p>You are ranked</p>
									<p>No. ${ group.userRank } of ${ group.numMembers }</p>
								</c:when>
								<c:otherwise>
									<p class="text-muted">No Rank Yet<p>
								</c:otherwise>
								</c:choose>
								<a class="card-link" role="button" href="/group-leaderboard?groupId=${ group.id }">Leaderboard</a>
						</div>
					</div>
				</c:forEach>
			</div>
			
			<h3 class="text-center text-muted">Manage Your Groups</h3>
			<div class="row d-flex justify-content-around">
				
				<!-- join a group -->
				<div class="card ml-1 mr-1 mb-3 text-center" style="width: 20rem; height: 370px">
					<div class="card-header">
						<h5>Join a new group</h5>
					</div>
					<div class="card-body text-center">
						<form action="/join-group" method="post">
							<div class="form-group text-center">
								<label for="name" class="col-form-label">Group Name</label>								
								<select required id="group" name="group" class="custom-select" id="inputGroupSelect01">
									<option value="">Select a Group</option>
									<c:forEach items="${ groups }" var="group">
										<option value="${ group.id }">${ group.name }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group text-center">
								<button type="submit" class="btn btn-info">Join +</button>
							</div>
						</form>
					</div>
				</div>
				
				<!-- create a group -->
				<div class="card ml-1 mr-1 mb-3 text-center" style="width: 20rem; height: 370px">
					<div class="card-header">
						<h5>Create a new group</h5>
					</div>
					<div class="card-body text-center">
						<form action="create-group" method="post">
							<div class="form-group text-center">
								<label for="name" class="col-form-label">Group Name</label>
								<div>
									<input name="name" class="form-control">
								</div>
							</div>
							<div class="form-group">
									<label for="description" class="col-form-label">Description</label>
								<div>
									<input name="description" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<div>
									<button type="submit" class="btn btn-warning">Submit</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				
				<!-- leave a group! -->
				<div class="card ml-1 mr-1 mb-3 text-center" style="width: 20rem; height: 370px">
					<div class="card-header">
						<h5>Leave a group</h5>
					</div>
					<div class="card-body text-center">
						<form action="/leave-group" method="post">
							<div class="form-group text-center">
								<label for="name" class="col-form-label">Group Name</label>								
								<select required id="group" name="group" class="custom-select" id="inputGroupSelect01">
									<option value="">Select a Group</option>
									<c:forEach items="${ user.groups }" var="group">
										<option value="${ group.id }">${ group.name }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group text-center">
								<div>
									<button type="submit" class="btn btn-secondary">Leave</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				
			</div>
			
		</div>
		
		<script>
		    $(document).ready(() => {
		      $(document).on("click", ".drawer", () => {
		          if ($(".collapse").css("display") === "block") {
		            $(".collapse").slideUp();
		          } else {
		            $(".collapse").slideDown();
		          }
		      });
		    });
  		</script>
		
	</body>
</html>