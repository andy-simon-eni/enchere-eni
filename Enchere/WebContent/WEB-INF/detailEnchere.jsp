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
				<span class="title is-5">Détail vente</span>
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

			<div class="field is-horizontal">
				<div class="field is-margin-20">
					<label class="label">Catégorie</label>
				</div>
				<div class="field-body">
					<div class="field">
						<div class="control">
							<span>${categorie}</span>
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

			<c:if test="${!showInfo}">
				<div class="field is-horizontal">
					<div class="field is-margin-20">
						<label class="label">Debut de l'enchère</label>
					</div>
					<div class="field-body">
						<div class="field">
							<div class="control">
								<span>${dateDebut}</span>
							</div>
						</div>
					</div>
				</div>
			</c:if>

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
			<c:if test="${showInfo}">
				<form method="POST"
					action="${pageContext.request.contextPath}/faire_enchere">
					<div class="field is-horizontal">
						<div class="field is-margin-20">
							<label class="label">Ma proposition</label>
						</div>
						<div class="field-body">
							<div class="field">
								<div class="control">
									<c:choose>
										<c:when test="${montantMax == null}">
											<input class="input input-is-250" type="number" min="${prix}"
												name="proposition" value="${prix}">
										</c:when>
										<c:otherwise>
											<input class="input input-is-250" type="number"
												min="${montantMax + 1}" name="proposition"
												value="${montantMax + 1}">
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
					<br> <input name="noArticle" type="hidden"
						value="${noArticle}">

					<button class="button is-link">Encherir</button>
			</c:if>
			</form>
		</div>
	</section>
</body>
</html>
<script src="js/creation_enchere.js"></script>