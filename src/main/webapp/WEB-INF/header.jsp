<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<html>
<head>
    <title>Header</title>
</head>
<body>
<header>
  <div class="logo">
    <a href="${pageContext.request.contextPath}/accueil">COCOWIKI</a>
  </div>

  <c:if test="${connecte}">
    <div class="buttons">
      <a class="logout" href="">Mes Enchères</a>
      <a class="logout" href="${pageContext.request.contextPath}/NouvelleVente">Vendre un Article</a>
      <a class="logout" href="${pageContext.request.contextPath}/MonProfil?pseudo=${sessionScope.utilisateur.pseudo}">Mon Profil</a>
      <a class="account" href="${pageContext.request.contextPath}/deconnexion">Se Déconnecter</a>

    </div>
  </c:if>
  <c:if test="${!connecte}">
    <div class="buttons">
      <a class="logout" href="${pageContext.request.contextPath}/connexion">Se connecter</a>
      <a class="account" href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
    </div>
  </c:if>
</header>
</body>
</html>
