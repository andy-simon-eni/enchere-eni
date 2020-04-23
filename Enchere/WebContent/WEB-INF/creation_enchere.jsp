<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="fr.eni.javaee.enchere.messages.LecteurMessage"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Créer une enchère</title>
</head>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<nav class="navbar" role="navigation" aria-label="main navigation">
		<div class="navbar-brand">
			<a class="navbar-item" href="${pageContext.request.contextPath}/">
				<h1 class="title is-4">ENI - Enchères</h1>
			</a>
		</div>
	</nav>
	<section class="section">
		<img class="imgArticle" src="img/imageVide.png">
		<div class="container petitContainer">

			<div class="erreurFile"></div>

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

			<div class="sous-titre">
				<span class="title is-5">Nouvelle vente</span>
			</div>
			<br> <br>

			<c:choose>
				<c:when test="${noArticle != null}">
					<form method="POST"
						action="${pageContext.request.contextPath}/modifierEnchere">
				</c:when>
				<c:otherwise>
					<form method="POST"
						action="${pageContext.request.contextPath}/creation_enchere">
				</c:otherwise>
			</c:choose>


			<input name="noArticle" type="hidden" value="${noArticle }">
			<div class="field">
				<label class="label">Article *</label>
				<div class="control">
					<input class="input" type="text" name="article"
						value="${nomArticle}" required>
				</div>
			</div>

			<div class="field">
				<label class="label">Description *</label>
				<div class="control">
					<textarea class="textarea" name="description" required>${description}</textarea>
				</div>
			</div>

			<div class="field">
				<label class="label">Catégorie *</label>
				<div class="control">
					<div class="select">
						<select name="categorie" class="input-is-250" required>
							<option value="">Selectionner une catégorie</option>
							<c:forEach items="${listCat}" var="lc">
								<c:choose>
									<c:when test="${noCateg == lc.getNo_categorie()}">
										<option value="${lc.getNo_categorie()}" selected>${lc.getLibelle()}</option>
									</c:when>
									<c:otherwise>
										<option value="${lc.getNo_categorie()}">${lc.getLibelle()}</option>
									</c:otherwise>
								</c:choose>

							</c:forEach>
						</select>
					</div>
				</div>
			</div>

			<div class="field">
				<label class="label">Photo de l'article</label> <span
					class="button input-is-250" id="uploadImage">UPLOADER</span>
			</div>

			<img class="imgArticleMobile" src="img/imageVide.png">

			<div class="field">
				<label class="label">Mise à prix</label>
				<div class="control">
					<c:choose>
						<c:when test="${prix == null}">
							<input class="input input-is-250" type="number" name="prix" min="0"
								value="0">
						</c:when>
						<c:otherwise>
							<input class="input input-is-250" type="number" name="prix" min="0"
								value="${prix}">
						</c:otherwise>
					</c:choose>

				</div>
			</div>

			<div class="field">
				<label class="label">Début de l'enchère *</label>
				<div class="control has-icons-left">
					<input class="input input-is-250" type="text" name="dateDebut"
						value="${dateDebut}" required>
						 <span class="icon is-small is-left"><i class="far fa-calendar-alt"></i></span>
				</div>
			</div>

			<div class="field">
				<label class="label">Fin de l'enchère *</label>
				<div class="control has-icons-left">
					<input class="input input-is-250" type="text" name="dateFin"
						value="${dateFin}" required>
						<span class="icon is-small is-left"><i class="far fa-calendar-alt"></i></span>
				</div>
			</div>

			<div class="blocRetrait">
				<span class="titleRetrait">  Retrait </span> <span
					class="icon is-orange"> <i
					class="fas fa-exclamation-triangle"></i>
				</span> <span class="is-orange"> Si aucune adresse n'est renseignée,
					l'adresse insérée sera votre adresse. </span>

				<div class="field">
					<label class="label">Rue</label>
					<div class="control">
						<input class="input" type="text" name="rue" value="${rue}"
							id="rue"
							pattern="[A-Za-z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\-\s']{1,30}">
					</div>
				</div>

				<div class="field">
					<label class="label">Code postal</label>
					<div class="control">
						<input class="input" type="text" name="codePostal"
							value="${codePostal}" pattern="[0-9]{1,10}">
					</div>
				</div>

				<div class="field">
					<label class="label">Ville</label>
					<div class="control">
						<input class="input" type="text" name="ville" value="${ville}"
							pattern="[A-Za-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\-\s']{1,30}">
					</div>
				</div>
			</div>
			<br>
			<div class="is-center">
				<button class="button is-link">Enregistrer</button>
				<a class="button" href="${pageContext.request.contextPath}/">Annuler</a>
				<c:if test="${noArticle != null }">
					<button class="button is-link">Annuler la vente</button>
				</c:if>
			</div>
			<input type="file" id="imgUrl">
			</form>
		</div>
	</section>
</body>
</html>
<script src="js/creation_enchere.js"></script>