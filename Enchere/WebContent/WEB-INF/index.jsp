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
	<div class="sous-titre">
		<h1 class="title is-3">Liste des enchères</h1>
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
				<div class="panel bgPanel">
					<div class="panel-heading">
						<div class="select">
							<c:choose>
								<c:when test="${ !empty sessionScope.id}">
									<select id="selectCatConnecte">
								</c:when>
								<c:otherwise>
									<select id="selectCat">
								</c:otherwise>
							</c:choose>
							<option value="0">Toutes les catégories</option>
							<c:forEach items="${ listCat }" var="lc">
								<option value="${lc.getNo_categorie()}">${lc.getLibelle()}</option>
							</c:forEach>
							</select>
						</div>

						<div class="field has-addons searchBar">
							<div class="control">
								<input id="inputSearch" class="input" type="text"
									placeholder="Trouver une enchère">
							</div>
						</div>

					</div>
					<c:if test="${ !empty sessionScope.id}">
						<div class="panel-block">
							<div class="columns is-margin-1em">
								<div class="control column is-3">
									<label class="radio"> <input class="rbEnchere"
										id="radioA" type="radio" name="answer" value="Achat"
										data-val="Vente" checked> Achats
									</label> <br>

									<div class="is-margin-1em">
										<label class="checkbox"> <input type="checkbox"
											class="cbEnchere cbAchat" value="aOuverte"> Enchères à venir
										</label> <label class="checkbox"> <input type="checkbox"
											class="cbEnchere cbAchat" value="aEnCours"> Mes
											enchères en cours
										</label> <label class="checkbox"> <input type="checkbox"
											class="cbEnchere cbAchat" value="aRemporte"> Mes
											enchères remportées
										</label>
									</div>
								</div>


								<div class="control column is-3">
									<label class="radio"> <input class="rbEnchere"
										id="radioV" type="radio" name="answer" value="Vente"
										data-val="Achat"> Mes ventes
									</label> <br>
									<div class="is-margin-1em">
										<label class="checkbox"> <input type="checkbox"
											class="cbEnchere cbVente" value="vDebute" disabled>
											Mes ventes non débutées
										</label> <label class="checkbox"> <input type="checkbox"
											class="cbEnchere cbVente" value="vEnCours" disabled>
											Mes ventes en cours
										</label> <label class="checkbox"> <input type="checkbox"
											class="cbEnchere cbVente" value="vTermine" disabled>
											Mes ventes terminées
										</label>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<div id="list" class="columns is-multiline">
						<c:forEach items="${listEnch}" var="ench">
							<div class="column is-3">
								<div class="card is-margin-1em">
									<div class="card-image">
										<figure class="image is-4by3">
											<img src="https://bulma.io/images/placeholders/1280x960.png"
												alt="Placeholder image">
										</figure>
									</div>
									<div class="card-content">

										<div>
											<p class="titleEnchere title is-4">
											<c:choose>
													<c:when test="${ !empty sessionScope.id}">
														<a
														href="${pageContext.request.contextPath}/modifierEnchere?noArt=${ench.no_article.no_article}">
														${ench.no_article.nom_article} </a>
													</c:when>
													<c:otherwise>
														${ench.no_article.nom_article}
													</c:otherwise>
												</c:choose>
											</p>
											<p>Prix : ${ench.montant_enchere}</p>
											<span class="dateEnchere">${ench.no_article.date_debut}</span>
											<p>Fin de l'enchère : ${ench.no_article.date_fin}</p>
											<p>
												<c:choose>
													<c:when test="${ !empty sessionScope.id}">
														Vendeur :
											 			<a
															href="${pageContext.request.contextPath}/profil?pseudo=${ench.no_utilisateur.pseudo}">${ench.no_utilisateur.pseudo}</a>
													</c:when>
													<c:otherwise>
														Vendeur : ${ench.no_utilisateur.pseudo}
													</c:otherwise>
												</c:choose>
											</p>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>

				</div>
		</section>
	</div>
</body>
</html>
<script src="js/index.js"></script>
