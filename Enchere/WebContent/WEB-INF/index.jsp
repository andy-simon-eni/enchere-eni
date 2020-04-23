<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Accueil</title>
</head>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"></jsp:include>
	<div>
		<h1 class="title" style="text-align: center">Liste des enchères</h1>
	</div>
	<div id="enchere">
		<section class="section">
			<div class="container">
				<c:if test="${!empty listeCodesErreur}">
					<div role="alert">
						<c:forEach var="code" items="${listeCodesErreur}">
							<div class="notification is-danger">
								<button class="delete"></button>
								<span>${LecteurMessage.getMessageErreur(code)}</span>
							</div>
						</c:forEach>
					</div>
					<br>
					<br>
				</c:if>
				<h2 class="subtitle">Filtre :</h2>
				<div class="field has-addons">
					<div class="control">
						<input class="input" type="text" placeholder="Trouvé une enchère">
					</div>
					<div class="control">
						<a class="button is-primary"> Search </a>
					</div>
				</div>
			</div>


			<div class="container">
				<h2 class="subtitle">Catégories :</h2>

				<div class="dropdown is-active">
					<div class="dropdown-trigger">
						<button class="button" aria-haspopup="true"
							aria-controls="dropdown-menu">
							<span>Toutes</span> <span class="icon is-small"> <i
								class="fas fa-angle-down" aria-hidden="true"></i>
							</span>
						</button>
					</div>
					<div class="dropdown-menu" id="dropdown-menu" role="menu">
						<div class="dropdown-content">
							<c:forEach items="${ listCat }" var="lc">
								<a class="dropdown-item"> ${lc.getLibelle()} </a>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>


			<div class="card" style="width: 20%; margin: 1em">
				<div class="card-image">
					<figure class="image is-4by3">
						<img src="https://bulma.io/images/placeholders/1280x960.png"
							alt="Placeholder image">
					</figure>
				</div>
				<div class="card-content">

					<div>
						<p class="title is-4">Audi RS6</p>
						<p>Prix :</p>
						<p>Fin de l'enchère :</p>
						<p>Vendeur :</p>
					</div>
				</div>
			</div>
		</section>
	</div>







</body>
</html>
