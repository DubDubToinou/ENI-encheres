<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>${ sessionScope.utilisateur.pseudo}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.svg" />
</head>
<body>

<jsp:include page="header.jsp"/>

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

<main>
    <div class="profileContent">

        <c:if test="${!empty succes}">
            <p>${succes}</p>
            <c:remove var="succes" scope="session"/>
        </c:if>

        <h1><c:out value="${utilisateurAAfficher.pseudo}"/></h1> <!-- Affichage du pseudo -->
        <div class="pFlex">
            <p class="midText"><c:out value="${utilisateurAAfficher.prenom}"/></p>     <!--Affichage du Prenom -->
            <p class="midText"><c:out value="${utilisateurAAfficher.nom}"/></p>        <!-- Affichage du Nom-->
        </div>
        <h3>Contact</h3>
        <div class="field">
            <img src="${pageContext.request.contextPath}/img/mail.svg"/>
            <p><c:out value="${utilisateurAAfficher.email}"/></p> <!--affichage de l'email -->
        </div>
        <div class="field">
            <img src="${pageContext.request.contextPath}/img/smartphone.svg"/>
            <p>Téléphone : <c:out value="${utilisateurAAfficher.telephone}"/></p> <!--Affichage du telephone -->
        </div>
        <div class="field">
            <img src="${pageContext.request.contextPath}/img/home.svg"/>
            <p><c:out value="${utilisateurAAfficher.rue}"/></p> <!-- Affichage de la rue-->
            <p><c:out value="${utilisateurAAfficher.codePostal}"/></p> <!-- Affichage du code postal-->
            <p><c:out value="${utilisateurAAfficher.ville}"/></p> <!-- Affichage de la ville-->
        </div>

        <c:if test="${sessionScope.utilisateur.pseudo == utilisateurAAfficher.pseudo}">
            <div class="pFlex">
                <div class="lightBlue">
                    <img src="../img/coin.svg">
                    <p><c:out value="${utilisateurAAfficher.credit}"/> CRT</p> <!-- Affichage des crédit -->
                </div>
                <a class="blue leftButton" href="${pageContext.request.contextPath}/ModifierProfil">Modifier mon
                    profil</a>
            </div>
        </c:if>

    </div>
</main>
</body>
</html>
