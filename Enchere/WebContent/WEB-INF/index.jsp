<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js"></script>
<!DOCTYPE html>
<html>
<head><title>Accueil</title></head>
  <jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
  <body>
  <nav class="navbar" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <a class="navbar-item" href="${pageContext.request.contextPath}">
      <h1 style="font-size: 25px">ENI - Enchères</h1>
    </a>
    <div class="navbar-item">
    	<button class="button is-small is-primary is-rounded" value="Rafraichir" id="refresh" onclick="document.location.reload(false)"><i class="fas fa-undo-alt"></i></button>
    </div>
    <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
    </a>
  </div>

		<c:if test="${ !empty sessionScope.id}">
			<div id="navbarBasicExample" class="navbar-menu">
				<div class="navbar-end">
					<div class="navbar-item">
						<div style="padding-right: 10px">
							<a href="${pageContext.request.contextPath}/showProfil"> <strong>${ sessionScope.prenom }
									${ sessionScope.nom }</strong>
							</a>
						</div>
						<div class="buttons">
							<a class="button is-light"
								href="${pageContext.request.contextPath}/logOut">
								Déconnexion </a>
						</div>
					</div>
				</div>
			</div>
		</c:if>




		<c:if test="${ empty sessionScope.id}">
			<div id="navbarBasicExample" class="navbar-menu">
				<div class="navbar-end">
					<div class="navbar-item">
						<div class="buttons">
							<a class="button is-primary"
								href="${pageContext.request.contextPath}/signUp"> <strong>Créer
									un compte</strong>
							</a> <a class="button is-light"
								href="${pageContext.request.contextPath}/signIn"> Se
								connecter </a>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</nav>
	<div>
		<h1 class="title" style="text-align: center">Liste des enchères</h1>
	</div>
	<div id="enchere">
		<section class="section">
			<div class="container">
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


			<div class="card" style="width:20%; margin:1em">
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
