<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Accueil</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.2/css/bulma.min.css">
    <script defer src="https://use.fontawesome.com/releases/v5.3.1/js/all.js"></script>
  </head>
  <body>
  <nav class="navbar" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <a class="navbar-item" href="${pageContext.request.contextPath}">
      <h1 style="font-size: 25px">ENI - Ench�res</h1>
    </a>

    <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
    </a>
  </div>
  
<c:if test="${ !empty sessionScope.id}">
<div id="navbarBasicExample" class="navbar-menu">
    <div class="navbar-end">
      <div class="navbar-item">
      <div style="padding-right : 10px">
	       <a href="${pageContext.request.contextPath}/profil" >
	            <strong>${ sessionScope.prenom } ${ sessionScope.nom }</strong>
	          </a>
          </div>
        <div class="buttons">
          <a class="button is-light" href="${pageContext.request.contextPath}/logOut">
            D�connexion
          </a>
        </div>
      </div>
    </div>
  </div>
</c:if>




  <c:if test="${ empty sessionScope.id}">
  <div id="navbarBasicExample" class="navbar-menu">
    <div class="navbar-end">
      <div class="navbar-item">
        <div class="buttons">
          <a class="button is-primary" href="${pageContext.request.contextPath}/signUp">
            <strong>Cr�er un compte</strong>
          </a>
          <a class="button is-light" href="${pageContext.request.contextPath}/signIn">
            Se connecter
          </a>
        </div>
      </div>
    </div>
  </div>
  </c:if>
</nav>
  <section class="section">
    <div class="container">
      <h1 class="title">
        A faire
      </h1>
      <p class="subtitle">
        Afficher la liste des ench�res
      </p>
    </div>
  </section>
  </body>
</html>