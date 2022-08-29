<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Nouvelle vente</title>
    <link rel="stylesheet" href="style.css" />
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
        <a class="logout" href="">Se déconnecter</a>
        <a class="account" href="">Mon profil</a>
    </div>
</header>
<main>
    <h1>Titre de l'article</h1>
    <h3>Description</h3>
    <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim
        veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
        commodo consequat.
    </p>
    <h3>Catégorie</h3>
    <p>Informatique</p>
    <h3>Meilleure offre</h3>
    <h3>Mise à prix</h3>
    <h3>Fin de l'enchère</h3>
    <h3>Retrait</h3>
    <h3>Vendeur</h3>
    <form action="">
        <label for="enchere">Ma proposition</label>
        <input type="number" name="enchere" id="enchere" required />
        <button type="submit">Enchérir</button>
    </form>
</main>
</body>
</html>
