<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.javaee.enchere.messages.LecteurMessage"%>
<!DOCTYPE html>
<html>
<head>
<title>Se connecter</title>
</head>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"></jsp:include>
	<section class="section">
		<div class="container petitContainer">
			<form method="POST"
				action="${pageContext.request.contextPath}/signIn">
				<div class="field">
					<label class="label">Identifiant</label>
					<div class="control has-icons-left">
						<input class="input" type="text" placeholder="Identifiant"
							name="identifiant" required value="${identifiant}"> <span
							class="icon is-small is-left"> <i class="fas fa-user"></i>
						</span>
					</div>
				</div>

				<div class="field">
					<label class="label">Mot de passe</label>
					<div class="control">
						<input class="input" type="password" placeholder="Password"
							name="mdp" required>
					</div>
				</div>

				<c:if test="${!empty listeCodesErreur}">
					<div role="alert">
						<c:forEach var="code" items="${listeCodesErreur}">
							<div class="notification is-danger is-light">
								<span>${LecteurMessage.getMessageErreur(code)}</span>
							</div>
						</c:forEach>
						<br> <br>
					</div>
				</c:if>
				<div class="field is-horizontal">
					<div class="field-body">
						<div class="field">
							<div class="control">
								<button class="button is-link">Connexion</button>
							</div>
						</div>
						<div class="field">
							<div class="control">
								<label class="checkbox"> <input type="checkbox"
									id="cbRemenber_id" name="cbRemenber_id"> Se souvenir de
									moi
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="field">
					<div class="control">
						<a href="">Mot de passe oublié</a>
					</div>
				</div>
			</form>
			</br>
			<div class="sous-titre">
				<a class="button is-link" style="width:100%"
					href="${pageContext.request.contextPath}/signUp"> <strong>Créer
						un compte</strong>
				</a>
			</div>

		</div>
	</section>
</body>
</html>