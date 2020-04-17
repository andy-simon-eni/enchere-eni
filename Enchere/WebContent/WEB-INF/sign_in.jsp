<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<nav class="navbar" role="navigation" aria-label="main navigation">
		<div class="navbar-brand">
			<a class="navbar-item"
				href="${pageContext.request.contextPath}/accueil">
				<h1 style="font-size: 25px">ENI - Enchères</h1>
			</a>
	</nav>
	<section class="section">
		<div class="container" style="max-width: 600px !important">
			<form>
				<div class="field">
					<label class="label">Identifiant</label>
					<div class="control has-icons-left">
						<input class="input" type="text" placeholder="Identifiant"
							name="Identifiant"> <span class="icon is-small is-left">
							<i class="fas fa-user"></i>
						</span>
					</div>
				</div>

				<div class="field">
					<label class="label">Mot de passe</label>
					<div class="control">
						<input class="input" type="text" placeholder="Password"
							name="Password">
					</div>
				</div>

				<div class="field is-horizontal">
					<div class="field-body">
						<div class="field">
							<div class="control">
								<button class="button is-link">Connexion</button>
							</div>
						</div>
						<div class="field">
							<div class="control">
								<label class="checkbox"> <input type="checkbox">
									Se souvenir de moi
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
		</div>
	</section>
</body>
</html>