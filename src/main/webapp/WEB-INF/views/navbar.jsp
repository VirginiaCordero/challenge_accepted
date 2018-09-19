<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="/dashboard">
	<img src="/src/main/resources/static/grin.svg" 
	width="30" height="30" class="d-inline-block align-top" alt="">
    Challenge Accepted
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor02" aria-controls="navbarColor02"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse justify-content-between" id="navbarColor02">
	<span class="navbar-text font-italic">
      Get outside!
    </span>
		<c:if test="${not empty user}">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link"
					href="/dashboard">Dashboard <span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item active"><a class="nav-link"
					href="set-location">Change my location <span class="sr-only">(current)</span>
				</a></li>
			</ul>
		</c:if>
		<div class="form-inline my-2 my-lg-0">
		<c:choose>
			<c:when test="${not empty user}">  
				<form action="/logout" class="form-inline my-2 my-lg-0">
					<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout</button>
				</form>
			</c:when>
			<c:otherwise>
				<form action="/registration" class="form-inline my-2 my-lg-0">
					<button class="btn btn-outline-success my-2 my-sm mr-md-2 "
						type="submit">Register</button>
				</form>
				<form action="/login" class="form-inline my-2 my-lg-0">
					<button class="btn btn-sm btn-outline-secondary" type="submit">Login</button>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</nav>