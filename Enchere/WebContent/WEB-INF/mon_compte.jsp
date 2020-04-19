<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="fr.eni.javaee.enchere.messages.LecteurMessage"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
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
		<div class="container" style="max-width: 650px !important; text-align: center">
			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Pseudo :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${pseudo}</label>
					</div>
				</div>
			</div>
			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Nom :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${nom}</label>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Prénom :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${prenom}</label>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Email :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${email}</label>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Téléphone :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${telephone}</label>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Rue :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${rue}</label>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Code postal :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${code_postal}</label>
					</div>
				</div>
			</div>

			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Ville :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${ville}</label>
					</div>
				</div>
			</div>
			
			<div class="field is-horizontal">
				<div class="field-label is-normal">
					<label class="label">Crédits :</label>
				</div>
				<div class="field-body">
					<div class="field">
						<label class="label">${credits}</label>
					</div>
				</div>
			</div>
			<br>
			<a href="${pageContext.request.contextPath}/modifier_profil" class="button is-link">Modifier</a>

		</div>
	</section>
</body>
</html>