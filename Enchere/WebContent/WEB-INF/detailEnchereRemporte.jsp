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
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"></jsp:include>
	<section class="section">
		<img class="imgArticle" src="img/imageVide.png">
		<div class="container petitContainer">


			<div class="sous-titre">
				<c:choose>
					<c:when test="${detailVendeur}">
						<span class="title is-5">${PseudoMontantMax } a remporté
							l'enchere</span>
					</c:when>
					<c:otherwise>
						<span class="title is-5">Vous avez remporté l'enchere</span>
					</c:otherwise>
				</c:choose>
			</div>
			<br> <br>

			<div class="field">
				<span class="title is-5">${nomArticle}</span>
			</div>
			<br>
			<div class="field is-horizontal">
				<div class="field is-margin-20 is-relative">
					<label class="label label-description">Description</label>
				</div>
				<div class="field-body">
					<div class="field">
						<div class="control">
							<textarea class="textarea" name="description" readonly>${description}</textarea>
						</div>
					</div>
				</div>
			</div>


			<img class="imgArticleMobile" src="img/imageVide.png">

			<div class="field is-horizontal">
				<div class="field is-margin-20">
					<label class="label">Meileure offre</label>
				</div>
				<div class="field-body">
					<div class="field">
						<div class="control">
							<c:choose>
								<c:when test="${montantMax != null}">
									<span>${montantMax} crédits par <a
										href="${pageContext.request.contextPath}/profil?pseudo=${PseudoMontantMax}">${PseudoMontantMax}</a></span>
								</c:when>
								<c:otherwise>
									<span>Aucune offre !</span>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field is-margin-20">
					<label class="label">Mise a prix</label>
				</div>
				<div class="field-body">
					<div class="field">
						<div class="control">
							<span>${prix}</span>
						</div>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field is-margin-20">
					<label class="label">Fin de l'enchère</label>
				</div>
				<div class="field-body">
					<div class="field">
						<div class="control">
							<span>${dateFin}</span>
						</div>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field is-margin-20">
					<label class="label">Retrait</label>
				</div>
				<div class="field-body">
					<div class="field">
						<div class="control">
							<span>${rue}, ${codePostal} ${ville}</span>
						</div>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field is-margin-20">
					<label class="label">Vendeur</label>
				</div>
				<div class="field-body">
					<div class="field">
						<div class="control">
							<span><a
								href="${pageContext.request.contextPath}/profil?pseudo=${pseudoVendeur}">${pseudoVendeur}</a></span>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${not detailVendeur}">
				<c:choose>
					<c:when test="${telVendeur != null && not empty telVendeur}">
						<div class="field is-horizontal">
							<div class="field is-margin-20">
								<label class="label">Téléphone du vendeur</label>
							</div>
							<div class="field-body">
								<div class="field">
									<div class="control">
										<span>${telVendeur}</span>
									</div>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="field is-horizontal">
							<div class="field is-margin-20">
								<label class="label">Mail du vendeur</label>
							</div>
							<div class="field-body">
								<div class="field">
									<div class="control">
										<span>${mailVendeur}</span>
									</div>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>
			<br> <input name="noArticle" type="hidden" value="${noArticle}">
			<c:choose>
				<c:when test="${detailVendeur}">
					<button class="button is-link">Retrait effectué</button>
				</c:when>
				<c:otherwise>
					<a class="button is-link" href="${pageContext.request.contextPath}/">Retour</a>
				</c:otherwise>
			</c:choose>
		</div>
	</section>
</body>
</html>
<script src="js/creation_enchere.js"></script>