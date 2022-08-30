<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Nouvelle vente</title>
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
        <a class="logout" href="">Se déconnecter</a>
        <a class="account" href="">Mon profil</a>
    </div>
</header>
<main>
    <h1>${sessionScope.article.nomArticle}</h1>
    <h3>Description</h3>
    <p>${sessionScope.article.description}</p>
    <h3>Catégorie</h3>
    <p>${sessionScope.article.categorieArticle}</p>
    <h3>Meilleure offre</h3>
    <p>${sessionScope.article.prixVente}</p>
    <h3>Mise à prix</h3>
    <p>${sessionScope.article.miseAPrix}</p>
    <h3>Fin de l'enchère</h3>
    <p>${sessionScope.article.dateFinEncheres}</p>
    <h3>Retrait</h3>
    <p>${sessionScope.article.lieuRetrait}</p>
    <h3>Vendeur</h3>
    <p>${sessionScope.article.utilisateur}</p>
    <form method="post" action="${pageContext.request.contextPath}/NouvelleEnchere">
        <label for="enchere">Ma proposition</label>
        <input type="number" name="enchere" id="enchere" required />
        <input name="noArticle" value="${sessionScope.article.noArticle}" hidden />
        <input name="prixVente" value="${sessionScope.article.prixVente}" hidden />
        <button type="submit">Enchérir</button>
    </form>
</main>
</body>
</html>
