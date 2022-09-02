<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Boutique</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.svg" />
</head>
<body>

<jsp:include page="header.jsp"/>

<main>
    <div class="content">
        <h1>Bienvenue sur la boutique</h1>

        <c:if test="${!empty listeCodesErreur}">
            <c:forEach var="code" items="${listeCodesErreur}">
                <div class="error">
                    <p class="errorLabel">Erreur</p>
                    <p class="errorMessage">${LecteurMessage.getMessageErreur(code)}</p>
                </div>
                <br>
            </c:forEach>
        </c:if>

        <h2>Vous pouvez acheter des crédits</h2>
        <form action="${pageContext.request.contextPath}/Boutique" method="post">
            <fieldset>
                <div class="inputField">
                    <label for="credit"></label>
                    <input type="hidden" name="pseudo" value="${ sessionScope.utilisateur.pseudo }">
                    <input type="hidden" name="noUtilisateur" value="${sessionScope.utilisateur.noUtilisateur}">
                    <input type="text" name="newCredit" id="credit" placeholder="Combien de crédit?" minlength="1"
                           maxlength="5"
                           required/>
                </div>
            </fieldset>
            <div class="submit">
                <button class="blue" type="submit">Acheter</button>
            </div>
        </form>
        <p>Retourner à l'accueil : <a href="${pageContext.request.contextPath}/MonProfil">retour à l'accueil</a></p>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
