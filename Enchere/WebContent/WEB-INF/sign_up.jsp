<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Se connecter</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.2/css/bulma.min.css">
    <script defer src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
  </head>
  <body>
  <nav class="navbar" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <a class="navbar-item" href="${pageContext.request.contextPath}/accueil">
      <h1 style="font-size: 25px">ENI - Enchères</h1>
    </a>
</nav>
  <section class="section">
    <div class="container">
    	<form method="POST" action="${pageContext.request.contextPath}/signUp">			
			<div class="field is-horizontal">
			  <div class="field-body">
			    <div class="field">
				  <label class="label">Pseudo</label>
				  <div class="control">
				    <input class="input" type="text" placeholder="Pseudo" name="pseudo">
				  </div>
				</div>
			    <div class="field">
				  <label class="label">Nom</label>
				  <div class="control">
				    <input class="input" type="text" placeholder="Nom" name="nom">
				  </div>
				</div>
			  </div>
			</div>
			
			<div class="field is-horizontal">
			  <div class="field-body">
			    <div class="field">
				  <label class="label">Prénom</label>
				  <div class="control">
				    <input class="input" type="text" placeholder="Prénom" name="prenom">
				  </div>
				</div>
			    <div class="field">
				  <label class="label">Email</label>
				  <div class="control">
				    <input class="input" type="text" placeholder="Email" name="email">
				  </div>
				</div>
			  </div>
			</div>
			
			<div class="field is-horizontal">
			  <div class="field-body">
			    <div class="field">
				  <label class="label">Téléphone</label>
				  <div class="control">
				    <input class="input" type="text" placeholder="Téléphone" name="telephone">
				  </div>
				</div>
			    <div class="field">
				  <label class="label">Rue</label>
				  <div class="control">
				    <input class="input" type="text" placeholder="Rue" name="rue">
				  </div>
				</div>
			  </div>
			</div>
			
			<div class="field is-horizontal">
			  <div class="field-body">
			    <div class="field">
				  <label class="label">Code postal</label>
				  <div class="control">
				    <input class="input" type="text" placeholder="Code postal" name="code_postal">
				  </div>
				</div>
			    <div class="field">
				  <label class="label">Ville</label>
				  <div class="control">
				    <input class="input" type="text" placeholder="Ville" name="ville">
				  </div>
				</div>
			  </div>
			</div>
			
			<div class="field is-horizontal">
			  <div class="field-body">
			    <div class="field">
				  <label class="label">Mot de passe</label>
				  <div class="control">
				    <input class="input" type="password" placeholder="Mot de passe" name="mdp">
				  </div>
				</div>
			    <div class="field">
				  <label class="label">Confirmation</label>
				  <div class="control">
				    <input class="input" type="password" placeholder="Confirmation" name="verif_mdp">
				  </div>
				</div>
			  </div>
			</div>
			
			<div class="field is-horizontal">
			    <div class="field">
			  		<div class="control">
			    		<button class="button is-link">Connexion</button>
		  			</div>
				</div>            
			    <div class="field">
			      <div class="control">
			        <a class="button" href="${pageContext.request.contextPath}/accueil">Annuler</a>
			      </div>
			    </div>
			 </div>
		</form>
    </div>
  </section>
  </body>
</html>