<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js"></script>
<script>
	function search() {
		$
				.ajax({
					type : "POST",
					url : "./recherchesNonConnecté",
					data : "motCle=" + $('#inputSearch').val(),
					success : function(result) {
						$('#list').empty();
						$
								.each(
										result,
										function(index, value) {
											var nomArticle = value.nomArticle;
											var montant = value.montant;
											var dateFinEnch = value.dateFinEnch;
											var nomUtil = value.nomUtil;
											var prenomUtil = value.prenomUtil;
											console.log(nomArticle, montant,
													dateFinEnch, nomUtil,
													prenomUtil);
											$('#list')
													.append(
															'<div class="column is-2"><div class="card" style="margin: 1em"><div class="card-image">'
																	+ '<figure class="image is-4by3"><img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">'
																	+ '</figure></div><div class="card-content"><div><p class="title is-4">'
																	+ nomArticle
																	+ '</p><p>Prix : '
																	+ montant
																	+ '</p>'
																	+ '<p>Fin de l\'enchère : '
																	+ dateFinEnch
																	+ '</p><p>Vendeur : '
																	+ nomUtil
																	+ ' '
																	+ prenomUtil
																	+ '  </p></div></div></div></div>');
										});
					}
				});
	}
	function searchByCat() {
		$
				.ajax({
					type : "GET",
					url : "./recherchesNonConnecté",
					data : "codeCat=" + $("#selectCat option:selected").val(),
					success : function(result) {
						$('#list').empty();
						$
								.each(
										result,
										function(index, value) {
											var nomArticle = value.nomArticle;
											var montant = value.montant;
											var dateFinEnch = value.dateFinEnch;
											var nomUtil = value.nomUtil;
											var prenomUtil = value.prenomUtil;
											console.log(nomArticle, montant,
													dateFinEnch, nomUtil,
													prenomUtil);
											$('#list')
													.append(
															'<div class="column is-2"><div class="card" style="margin: 1em"><div class="card-image">'
																	+ '<figure class="image is-4by3"><img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">'
																	+ '</figure></div><div class="card-content"><div><p class="title is-4">'
																	+ nomArticle
																	+ '</p><p>Prix : '
																	+ montant
																	+ '</p>'
																	+ '<p>Fin de l\'enchère : '
																	+ dateFinEnch
																	+ '</p><p>Vendeur : '
																	+ nomUtil
																	+ ' '
																	+ prenomUtil
																	+ '  </p></div></div></div></div>');
										});
					}
				});
	}
	function disabledCk() {
		if ($('#radioA').is(':checked')) {
			$('#ckv1').attr('disabled', 'disabled');
			$('#ckv2').attr('disabled', 'disabled');
			$('#ckv3').attr('disabled', 'disabled');
			$('#cka1').removeAttr('disabled');
			$('#cka2').removeAttr('disabled');
			$('#cka3').removeAttr('disabled');
		} else {
			$('#ckv1').removeAttr('disabled');
			$('#ckv2').removeAttr('disabled');
			$('#ckv3').removeAttr('disabled');
			$('#cka1').attr('disabled', 'disabled');
			$('#cka2').attr('disabled', 'disabled');
			$('#cka3').attr('disabled', 'disabled');
		}
	}
	function check() {
		var cka1 = false;
		var cka2 = false;
		var cka3 = false;
		var ckv1 = false;
		var ckv2 = false;
		var ckv3 = false;

		if ($('#radioA').is(':checked')) {
			if ($('#cka1').is(':checked')) {
				cka1 = true;
			}
			if ($('#cka2').is(':checked')) {
				cka2 = true;
			}
			if ($('#cka3').is(':checked')) {
				cka3 = true;
			}
			$
					.ajax({
						type : "GET",
						url : "./recherchesConnecté",
						data : {
							cka1 : cka1,
							cka2 : cka2,
							cka3 : cka3,
						},
						success : function(result) {
							$('#list').empty();
							$
									.each(
											result,
											function(index, value) {
												var nomArticle = value.nomArticle;
												var montant = value.montant;
												var dateFinEnch = value.dateFinEnch;
												var nomUtil = value.nomUtil;
												var prenomUtil = value.prenomUtil;
												console.log(nomArticle,
														montant, dateFinEnch,
														nomUtil, prenomUtil);
												$('#list')
														.append(
																'<div class="column is-2"><div class="card" style="margin: 1em"><div class="card-image">'
																		+ '<figure class="image is-4by3"><img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">'
																		+ '</figure></div><div class="card-content"><div><p class="title is-4">'
																		+ nomArticle
																		+ '</p><p>Prix : '
																		+ montant
																		+ '</p>'
																		+ '<p>Fin de l\'enchère : '
																		+ dateFinEnch
																		+ '</p><p>Vendeur : '
																		+ nomUtil
																		+ ' '
																		+ prenomUtil
																		+ '  </p></div></div></div></div>');
											});
						}
					});
		} else {
			if ($('#ckv1').is(':checked')) {
				ckv1 = true;
			}
			if ($('#ckv2').is(':checked')) {
				ckv2 = true;
			}
			if ($('#ckv3').is(':checked')) {
				ckv3 = true;
			}
			console.log(ckv1, ckv2, ckv3);
			$
					.ajax({
						type : "POST",
						url : "./recherchesConnecté",
						data : {
							ckv1 : ckv1,
							ckv2 : ckv2,
							ckv3 : ckv3,
						},
						success : function(result) {
							$('#list').empty();
							$
									.each(
											result,
											function(index, value) {
												var nomArticle = value.nomArticle;
												var montant = value.montant;
												var dateFinEnch = value.dateFinEnch;
												var nomUtil = value.nomUtil;
												var prenomUtil = value.prenomUtil;
												console.log(nomArticle,
														montant, dateFinEnch,
														nomUtil, prenomUtil);
												$('#list')
														.append(
																'<div class="column is-2"><div class="card" style="margin: 1em"><div class="card-image">'
																		+ '<figure class="image is-4by3"><img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">'
																		+ '</figure></div><div class="card-content"><div><p class="title is-4">'
																		+ nomArticle
																		+ '</p><p>Prix : '
																		+ montant
																		+ '</p>'
																		+ '<p>Fin de l\'enchère : '
																		+ dateFinEnch
																		+ '</p><p>Vendeur : '
																		+ nomUtil
																		+ ' '
																		+ prenomUtil
																		+ '  </p></div></div></div></div>');
											});
						}
					});
		}

	}
</script>
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
			<div class="panel" style="background-color: #f7f7f7">
				<div class="panel-heading">
					<div class="select">
						<select id="selectCat" onchange="searchByCat()">
							<option value="0">Toutes les catégories</option>
							<c:forEach items="${ listCat }" var="lc">
								<option value="${lc.getNo_categorie()}">${lc.getLibelle()}</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="field has-addons" style="float:right">
						<div class="control">

							<input id="inputSearch" class="input" type="text"
								placeholder="Trouver une enchère">
						</div>
						<div class="control" onclick="search()">
							<a class="button is-primary"><i class="fas fa-search"></i></a>
						</div>
					</div>

				</div>
				<c:if test="${ !empty sessionScope.id}">
					<div class="panel-block" style="display: block">
						<div class="columns" style="margin: 1em">
							<div class="control column is-2">
								<label class="radio"> <input id="radioA" type="radio"
									name="answer" checked onclick="disabledCk(),check()">
									Achats
								</label> <br>

								<div style="margin: 1em">
									<label class="checkbox"> <input id="cka1"
										type="checkbox" onclick="check()"> Enchères ouvertes
									</label> <label class="checkbox"> <input id="cka2"
										type="checkbox" onclick="check()"> Mes enchères en
										cours
									</label> <label class="checkbox"> <input id="cka3"
										type="checkbox" onclick="check()"> Mes enchères
										remportées
									</label>
								</div>
							</div>


							<div class="control column is-2">
								<label class="radio"> <input id="radioV" type="radio"
									name="answer" onclick="disabledCk(),check()"> Mes
									ventes
								</label> <br>
								<div style="margin: 1em">
									<label class="checkbox"> <input id="ckv1"
										type="checkbox" disabled onclick="check()"> Mes ventes
										débutées
									</label> <label class="checkbox"> <input id="ckv2"
										type="checkbox" disabled onclick="check()"> Mes ventes
										en cours
									</label> <label class="checkbox"> <input id="ckv3"
										type="checkbox" disabled onclick="check()"> Mes ventes
										terminées
									</label>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div id="list" class="columns is-multiline">
					<c:forEach items="${listEnch}" var="ench">
						<div class="column is-2">
							<div class="card" style="margin: 1em">
								<div class="card-image">
									<figure class="image is-4by3">
										<img src="https://bulma.io/images/placeholders/1280x960.png"
											alt="Placeholder image">
									</figure>
								</div>
								<div class="card-content">

									<div>
										<p class="title is-4">${ench.no_article.nom_article}</p>
										<p>Prix : ${ench.montant_enchere}</p>
										<p>Fin de l'enchère : ${ench.no_article.date_fin}</p>
										<p>Vendeur : ${ench.no_utilisateur.nom}
											${ench.no_utilisateur.prenom}</p>
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
