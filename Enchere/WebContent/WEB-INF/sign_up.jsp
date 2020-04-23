<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="fr.eni.javaee.enchere.messages.LecteurMessage"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head><title>Créer un compte</title></head>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"></jsp:include>
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

			<div style="width: 100%; text-align: center">
				<span style="font-size: 20px">Mon profil</span>
			</div>
			<br><br>
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
				<br>
				<div style="width: 100%; text-align: center">
					<button class="button is-link">Créer</button>
					<a class="button" href="${pageContext.request.contextPath}/">Annuler</a>
				</div>
			</form>
		</div>
	</section>
</body>
</html>