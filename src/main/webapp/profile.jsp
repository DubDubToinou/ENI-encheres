<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>U pseudo</title>
  <link rel="stylesheet" href="" />
</head>
<body>
<header>
  <div class="logo">
    <a href="">Enchères</a>
  </div>
  <nav>
    <a href="">Enchères</a>
    <a href="">Vendre</a>
  </nav>
  <div class="buttons">
    <a href="">Se déconnecter</a>
    <a href="">Mon profil</a>
  </div>
</header>
<main>
  <div class="profil">
    <div>
      <h1>${ sessionScope.utilisateur.pseudo }</h1> <!-- Affichage du pseudo -->
      <p>${sessionScope.utilisateur.prenom}</p>     <!--Affichage du Prenom -->
      <p>${sessionScope.utilisateur.nom}</p>        <!-- Affichage du Nom-->
    </div>
    <div>
      <h2>Contact</h2>
      <p>${ sessionScope.utilisateur.email  }</p>   <!--affichage de l'email -->
      <p>${ sessionScope.utilisateur.telephone }</p><!--Affichage du telephone -->
      <p>${ sessionScope.utilisateur.rue }</p> <!-- Affichage de la rue-->
      <p>${ sessionScope.utilisateur.ville }</p> <!-- Affichage de la ville-->
      <p>${ sessionScope.utilisateur.codePostal }</p> <!-- Affichage du code postal-->
    </div>
    <p>${ sessionScope.utililsateur.credit}</p>
    <a href="">Modifier mon profil</a>
  </div>
</main>
</body>
</html>
