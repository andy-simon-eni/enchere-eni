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
				href="${pageContext.request.contextPath}/">
				<h1 style="font-size: 25px">ENI - Enchères</h1>
			</a>
		</div>
	</nav>
	<section class="section">
		<div class="container">

			<div style="width: 100%; text-align: center">
				<span style="font-size: 20px">Nouvelle vente</span>
			</div>
			<br><br>
			<form method="POST"	action="${pageContext.request.contextPath}/creation_enchere">
				
				<div class="field">
					<label class="label">Article</label>
					<div class="control">
						<input class="input" type="text" name="article">
					</div>
				</div>
				
				<div class="field">
					<label class="label">Description</label>
					<div class="control">
						<textarea class="textarea" name="desciption"></textarea>
					</div>
				</div>

				<div class="field">
					<label class="label">Catégorie</label>
					<div class="control">
						<div class="select" name="categorie">
						  <select>
						    <option>Select dropdown</option>
						    <option>With options</option>
						  </select>
						</div>
					</div>
				</div>
				
				<div class="field">
					<label class="label">Photo de l'article</label>
					<div class="control">
						<button class="button">UPLOADER</button>
					</div>
				</div>

				<div class="field">
					<label class="label">Mise à prix</label>
					<div class="control">
						<input class="input" type="number" name="prix">
					</div>
				</div>
				
				<div class="field">
					<label class="label">Début de l'enchère</label>
					<div class="control">
						<input class="input" type="date" name="dateDebut">
					</div>
				</div>
				
				<div class="field">
					<label class="label">Fin de l'enchère</label>
					<div class="control">
						<input class="input" type="date" name="dateFin">
					</div>
				</div>

				<div class="field">
					<label class="label">Rue</label>
					<div class="control">
						<input class="input" type="text" name="rue">
					</div>
				</div>
				
				<div class="field">
					<label class="label">Code postal</label>
					<div class="control">
						<input class="input" type="text" name="codePostal">
					</div>
				</div>
				
				<div class="field">
					<label class="label">Ville</label>
					<div class="control">
						<input class="input" type="text" name="ville">
					</div>
				</div>
				
				<br>
				<div style="width: 100%; text-align: center">
					<button class="button is-link">Enregistrer</button>
					<a class="button" href="${pageContext.request.contextPath}/">Annuler</a>
				</div>
			</form>
		</div>
	</section>
</body>
</html>