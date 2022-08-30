<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Bonjour : ${ sessionScope.utilisateur.pseudo}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/modifierProfil.css"/>
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
      <a class="logout" href="${pageContext.request.contextPath}/MonProfil">Mon Profil</a>
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
<main>
  <div class="profil">
    <div>
      <h1>${ sessionScope.utilisateur.pseudo }</h1> <!-- Affichage du pseudo -->
      <p>Prenom  : ${sessionScope.utilisateur.prenom}</p>     <!--Affichage du Prenom -->
      <p>Nom : ${sessionScope.utilisateur.nom}</p>        <!-- Affichage du Nom-->
    </div>
    <div>
      <h2>Contact</h2>
      <p>Email : ${ sessionScope.utilisateur.email  }</p>   <!--affichage de l'email -->
      <p>Téléphone : ${ sessionScope.utilisateur.telephone }</p><!--Affichage du telephone -->
      <p>Rue : ${ sessionScope.utilisateur.rue }</p> <!-- Affichage de la rue-->
      <p>Ville : ${ sessionScope.utilisateur.ville }</p> <!-- Affichage de la ville-->
      <p>Code Postal :  ${ sessionScope.utilisateur.codePostal }</p> <!-- Affichage du code postal-->
      <p>Credit : ${ sessionScope.utilisateur.credit }</p> <!-- Affichage  des crédit -->
    </div>


    <button> <a class="logout" href="${pageContext.request.contextPath}/ModifierProfil">Modifier mon profil</a></button>


  </div>
</main>
</body>
</html>
