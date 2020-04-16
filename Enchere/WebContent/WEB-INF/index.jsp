<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<nav class="navbar" role="navigation" aria-label="main navigation">
		<div class="navbar-brand">
			<a class="navbar-item"
				href="${pageContext.request.contextPath}/accueil">
				<h1 style="font-size: 25px">ENI - Enchères</h1>
			</a> <a role="button" class="navbar-burger burger" aria-label="menu"
				aria-expanded="false" data-target="navbarBasicExample"> <span
				aria-hidden="true"></span> <span aria-hidden="true"></span> <span
				aria-hidden="true"></span>
			</a>
		</div>

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
	</nav>
	<section class="section">
		<div class="container">
			<h1 class="title">A faire</h1>
			<p class="subtitle">Afficher la liste des enchères</p>
		</div>
	</section>
</body>
</html>