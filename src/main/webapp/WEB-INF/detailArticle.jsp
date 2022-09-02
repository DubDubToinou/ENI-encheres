<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title><c:out value="${article.nomArticle}"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.svg" />
</head>
<body>

<jsp:include page="header.jsp"/>

<main>
    <div class="content">
        <h1><c:out value="${article.nomArticle}"/></h1>

        <c:if test="${!empty listeCodesErreur}">
            <c:forEach var="code" items="${listeCodesErreur}">
                <div class="error">
                    <p class="errorLabel">Erreur</p>
                    <p class="errorMessage">${LecteurMessage.getMessageErreur(code)}</p>
                </div>
                <br>
            </c:forEach>
        </c:if>

        <c:if test="${connecte && !enCours && !venteNonDebutee}">
            <c:if test="${article.utilisateurs.pseudo == sessionScope.utilisateur.pseudo && enchereGagnante != null}">
                <p class="midText"><a
                        href="${pageContext.request.contextPath}/MonProfil?pseudo=${utilisateurGagnant}">${utilisateurGagnant}</a>
                    a remporté l'enchère</p>
            </c:if>

            <c:if test="${article.utilisateurs.pseudo != sessionScope.utilisateur.pseudo && enchereGagnante != null && utilisateurGagnant != sessionScope.utilisateur.pseudo}">
                <p class="midText"><a
                        href="${pageContext.request.contextPath}/MonProfil?pseudo=${utilisateurGagnant}">${utilisateurGagnant}</a>
                    a remporté l'enchère</p>
            </c:if>

            <c:if test="${article.utilisateurs.pseudo != sessionScope.utilisateur.pseudo && enchereGagnante != null && sessionScope.utilisateur.pseudo == utilisateurGagnant}">
                <p class="midText"> Vous avez remporté l'enchère</p>
            </c:if>

            <c:if test="${ enchereGagnante == null}">
                <p class="midText">Personne n'a enchéri sur cet objet</p>
            </c:if>
        </c:if>

        <div class="field">
            <div class="inputField">
                <h3>Description</h3>
                <p><c:out value="${article.description}"/></p>
            </div>
        </div>
        <div class="field">
            <div class="inputField">
                <h3>Catégorie</h3>
                <p><c:out value="${article.categorieArticle.libelle}"/></p>
            </div>
            <div class="inputField">
                <h3>Mise à prix</h3>
                <p><c:out value="${article.miseAPrix}"/> CRT</p>
            </div>
            <div class="inputField">
                <h3>Prix de vente (meilleure offre)</h3>
                <p><c:out value="${article.prixVente}"/></p>
            </div>
        </div>
        <fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd'T'HH:mm" var="dateFinEnchere"
                       type="both"/>
        <fmt:parseDate value="${article.dateDebutEncheres}" pattern="yyyy-MM-dd'T'HH:mm" var="dateDebutEnchere"
                       type="both"/>
        <div class="field">
            <div class="inputField">
                <h3>Début de l'enchère</h3>
                <p><fmt:formatDate pattern="dd/MM/yyyy à HH:mm" value="${ dateDebutEnchere }"/></p>
            </div>
            <div class="inputField">
                <h3>Fin de l'enchère</h3>
                <p><fmt:formatDate pattern="dd/MM/yyyy à HH:mm" value="${ dateFinEnchere }"/></p>
            </div>
        </div>
        <div class="field">
            <div class="inputField">
                <h3>Retrait</h3>
                <p><c:out value="${article.lieuRetrait.rue}"/> <c:out value="${article.lieuRetrait.codePostal}"/> <c:out
                        value="${article.lieuRetrait.ville}"/></p>
            </div>
            <div class="inputField">
                <h3>Vendeur</h3>
                <p><a href="${pageContext.request.contextPath}/MonProfil?pseudo=${article.utilisateurs.pseudo}"><c:out
                        value="${article.utilisateurs.pseudo}"/></a></p>
            </div>
            <c:if test="${connecte && !enCours && article.utilisateurs.pseudo != sessionScope.utilisateur.pseudo && enchereGagnante != null && sessionScope.utilisateur.pseudo == utilisateurGagnant}">
                <div class="inputField">
                    <h3>Téléphone</h3>
                    <p><c:out value="${article.utilisateurs.telephone}"/></p>
                </div>
            </c:if>
        </div>

        <c:if test="${connecte && article.utilisateurs.pseudo != sessionScope.utilisateur.pseudo && enCours}">
            <form method="post" action="${pageContext.request.contextPath}/NouvelleEnchere">
                <fieldset class="field">
                    <div class="inputField">
                        <label for="montant">Ma proposition </label>
                        <input name="noArticle" type="hidden" value="${article.noArticle}"/>
                        <input name="prixVente" type="hidden" value="${article.prixVente}"/>
                        <input type="number" id="montant" name="montant" min="${article.prixVente +1}"
                               placeholder="${article.prixVente +1}"/>
                    </div>
                    <div class="submit">
                        <button class="blue" type="submit">Enchérir</button>
                    </div>
                </fieldset>
            </form>
        </c:if>

        <c:if test="${connecte && article.utilisateurs.pseudo == sessionScope.utilisateur.pseudo && venteNonDebutee}">
            <div class="submit">
                <a class="blue"
                   href="${pageContext.request.contextPath}/modifiervente?no_article=${article.noArticle}">
                    Modifier cette vente
                </a>
            </div>
        </c:if>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
