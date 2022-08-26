<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Nouvelle vente</title>
    <link rel="stylesheet" href=""/>
</head>
<body>
<header>
    <div class="logo">
        <c:if test="${!empty listeCodesErreur}">
            <div>
                <strong>Erreur!</strong>
                <ul>
                    <c:forEach var="code" items="${listeCodesErreur}">
                        <li>${LecteurMessage.getMessageErreur(code)}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
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
    <h1>Mettre un article en vente</h1>
    <form action="${pageContext.request.contextPath}/NouvelleVente" method="post">
        <label for="titre">Titre</label>
        <input type="text" name="nom_article" id="titre" maxlength="30" required/>
        <label for="description">Description</label>
        <textarea name="description" id="description" cols="30" rows="10" maxlength="300" required></textarea>
        <label for="categorie">Catégorie</label>
        <select name="categorie" id="categorie">
            <option value="Informatique">Informatique</option>
            <option value="Ameublement">Ameublement</option>
            <option value="Vêtements">Vêtements</option>
            <option value="Sport et Loisirs">Sport et Loisirs</option>
        </select>
        <label for="prixInitial">Mise à prix</label>
        <input type="number" name="prix_initial" id="prixInitial" required/>
        <label for="dateDebut">Début de l'enchère</label>
        <input type="date" name="date_debut_encheres" id="dateDebut" required/>
        <label for="dateFin">Fin de l'enchère</label>
        <input type="date" name="date_fin_encheres" id="dateFin" required/>
        <fieldset>
            <legend>Retrait</legend>
            <label for="rue">Rue</label>
            <input type="text" name="rue" id="rue" maxlength="30" placeholder="${ sessionScope.utilisateur.rue }"/>
            <label for="codePostal">Code Postal</label>
            <input type="text" name="code_postal" id="codePostal" pattern="[A-Za-z0-9]{1,10}" placeholder="${sessionScope.utilisateur.codePostal}"/>
            <label for="ville">Ville</label>
            <input type="text" name="ville" id="ville" maxlength="30" placeholder="${ sessionScope.utilisateur.ville }"/>
        </fieldset>
        <div class="submit">
            <a href="">Annuler</a>
            <button type="submit">Enregistrer</button>
        </div>
    </form>
</main>
</body>
</html>