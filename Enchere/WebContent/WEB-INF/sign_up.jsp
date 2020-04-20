<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="fr.eni.javaee.enchere.messages.LecteurMessage"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head><title>Créer un compte</title></head>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<nav class="navbar" role="navigation" aria-label="main navigation">
		<div class="navbar-brand">
			<a class="navbar-item"
				href="${pageContext.request.contextPath}/accueil">
				<h1 style="font-size: 25px">ENI - Enchères</h1>
			</a>
		</div>
	</nav>
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

			<form method="POST"
				action="${pageContext.request.contextPath}/signUp">
				<div class="field is-horizontal">
					<div class="field-body">
						<div class="field">
							<label class="label">Pseudo *</label>
							<div class="control">
								<input class="input" type="text" placeholder="Pseudo"
									name="pseudo" value="${pseudo}" required
									pattern="[A-Za-z0-9]{1,30}">
							</div>
						</div>
						<div class="field">
							<label class="label">Nom *</label>
							<div class="control">
								<input class="input" type="text" placeholder="Nom" name="nom"
									value="${nom}" required
									pattern="[A-Za-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\-\s]{1,30}">
							</div>
						</div>
					</div>
				</div>

				<div class="field is-horizontal">
					<div class="field-body">
						<div class="field">
							<label class="label">Prénom *</label>
							<div class="control">
								<input class="input" type="text" placeholder="Prénom"
									name="prenom" value="${prenom}" required
									pattern="[A-Za-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\-\s]{1,30}">
							</div>
						</div>
						<div class="field">
							<label class="label">Email *</label>
							<div class="control">
								<input class="input" type="email" placeholder="Email"
									name="email" value="${email}" required pattern=".{1,100}">
							</div>
						</div>
					</div>
				</div>

				<div class="field is-horizontal">
					<div class="field-body">
						<div class="field">
							<label class="label">Téléphone</label>
							<div class="control">
								<input class="input" type="text" placeholder="Téléphone"
									name="telephone" value="${telephone}" pattern="[0-9]{0,15}">
							</div>
						</div>
						<div class="field">
							<label class="label">Rue</label>
							<div class="control">
								<input class="input" type="text" placeholder="Rue" name="rue"
									value="${rue}" required
									pattern="[A-Za-z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\-\s']{1,30}">
							</div>
						</div>
					</div>
				</div>

				<div class="field is-horizontal">
					<div class="field-body">
						<div class="field">
							<label class="label">Code postal</label>
							<div class="control">
								<input class="input" type="text" placeholder="Code postal"
									name="code_postal" value="${code_postal}" required
									pattern="[0-9]{1,10}">
							</div>
						</div>
						<div class="field">
							<label class="label">Ville</label>
							<div class="control">
								<input class="input" type="text" placeholder="Ville"
									name="ville" value="${ville}" required
									pattern="[A-Za-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\-\s']{1,30}">
							</div>
						</div>
					</div>
				</div>

				<div class="field is-horizontal">
					<div class="field-body">
						<div class="field">
							<label class="label">Mot de passe</label>
							<div class="control">
								<input class="input" type="password" placeholder="Mot de passe"
									name="mdp" required pattern=".{8,30}">
							</div>
						</div>
						<div class="field">
							<label class="label">Confirmation</label>
							<div class="control">
								<input class="input" type="password" placeholder="Confirmation"
									name="verif_mdp" required pattern=".{8,30}">
							</div>
						</div>
					</div>
				</div>

				<div class="field is-horizontal">
					<div class="field">
						<div class="control">
							<button class="button is-link">Connexion</button>
						</div>
					</div>
					            
					<div class="field">
						<div class="control">
							<a class="button"
								href="${pageContext.request.contextPath}/accueil">Annuler</a>
						</div>
					</div>
				</div>
			</form>
		</div>
	</section>
</body>
</html>