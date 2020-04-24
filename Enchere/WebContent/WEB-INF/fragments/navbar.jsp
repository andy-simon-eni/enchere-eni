<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar" role="navigation" aria-label="main navigation">
		<div class="navbar-brand">
			<a class="navbar-item" href="${pageContext.request.contextPath}">
				<h1 style="font-size: 25px">ENI - Enchères</h1>
			</a>
			<div class="navbar-item">
				<button class="button is-small is-link is-rounded"
					value="Rafraichir" id="refresh"
					onclick="document.location.reload(false)">
					<i class="fas fa-undo-alt"></i>
				</button>
			</div>
			<a role="button" class="navbar-burger burger" aria-label="menu"
				aria-expanded="false" data-target="navbarBasicExample"> <span
				aria-hidden="true"></span> <span aria-hidden="true"></span> <span
				aria-hidden="true"></span>
			</a>
		</div>

		<c:if test="${ !empty sessionScope.id}">
			<div id="navbarBasicExample" class="navbar-menu">
				<div class="navbar-end">
					<div class="navbar-item">
					<div style="padding-right: 10px">
						
							<a class="button is-link"
								href="${pageContext.request.contextPath}/creation_enchere"> <strong>Créer
									une enchère</strong>
							</a>
						</div>	
						<div style="padding-right: 10px">
							<a href="${pageContext.request.contextPath}/profil"> <strong>${ sessionScope.prenom }
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
							<a class="button is-link"
								href="${pageContext.request.contextPath}/signIn"> <strong>Créer
									un compte / Se Connecter</strong>
							</a>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</nav>