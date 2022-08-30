<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Accueil</title>
    <link rel="stylesheet" href="styles/modifierProfil.css"/>
</head>
<body>
<header>
    <div class="logo">
        <a href="">COCOWIKI</a>
    </div>
    <div class="buttons">
        <a class="logout" href="">Se connecter</a>
        <a class="account" href="">S'inscrire</a>
    </div>
</header>
<main>
    <div class="content">
        <h1>Détails</h1>
        <h3><c:out value="${article.nomArticle}"/></h3>
        <p>Description : <c:out value="${article.description}"/></p>
        <p>Catégorie : <c:out value="${article.categorieArticle.libelle}"/></p>
        <p>Mise à prix : <c:out value="${article.miseAPrix}"/></p>
        <p>Prix de vente (meilleure offre) : <c:out value="${article.prixVente}"/></p>

        <fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd'T'HH:mm" var="dateFinEnchere" type="both" />

        <p>Fin de l'enchère : <fmt:formatDate pattern="dd/MM/yyyy à HH:mm" value="${ dateFinEnchere }" /></p>
        <p>Retrait : <c:out value="${article.lieuRetrait.rue}"/> <c:out value="${article.lieuRetrait.codePostal}"/> <c:out value="${article.lieuRetrait.ville}"/></p>
        <p>Vendeur : <c:out value="${article.utilisateurs.pseudo}"/></p>
        <c:if test="${connecte && article.utilisateurs.pseudo != sessionScope.utilisateur.pseudo && enCours}">
            <form method="post" action="${pageContext.request.contextPath}/NouvelleEnchere">
                <label for="montant">Ma proposition : </label>
                <input name="noArticle" type="hidden" value="${article.noArticle}"/>
                <input name="prixVente" type="hidden" value="${article.prixVente}"/>
                <input type="number" id="montant" name="montant" min="${article.prixVente}" placeholder="${article.prixVente}"/>
                <button type="submit">Enchérir</button>
            </form>
        </c:if>
        <c:if test="${connecte && article.utilisateurs.pseudo == sessionScope.utilisateur.pseudo && enCours}">
            <form method="post" action="${pageContext.request.contextPath}/modifierVente">
                <button type="submit">Modifier cette vente</button>
            </form>
        </c:if>





    </div>
</main>

</body>
</html>
