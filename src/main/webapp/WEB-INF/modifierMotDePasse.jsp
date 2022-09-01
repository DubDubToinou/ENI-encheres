<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Mot de passe</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<main>
    <h1>Modifier votre mot de passe</h1>

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

    <form action="${pageContext.request.contextPath}/ModifierMotDePasse" method="post">
        <fieldset>
            <div class="inputField">
                <label for="NouveauMotDePasse">Nouveau mot de passe</label>
                <input type="hidden" name="pseudo" value="${ sessionScope.utilisateur.pseudo }">
            </div>
            <div class="inputField">
                <input type="hidden" name="noUtilisateur" value="${sessionScope.utilisateur.noUtilisateur}">
                <input type="password" name="mot_de_passe" id="NouveauMotDePasse"
                       placeholder="Entrez votre mot de passe"
                       minlength="8" maxlength="30" required/>
            </div>
            <div class="inputField">
                <label for="confirm_mot_de_passe">Confirmation</label>
                <input type="password" name="confirm_mot_de_passe" id="confirm_mot_de_passe"
                       placeholder="Confirmez votre mot de passe" minlength="8" maxlength="30" required/>
            </div>
        </fieldset>
        <div class="submit">
            <a class="white" href="${pageContext.request.contextPath}/MonProfil">Annuler</a>
            <button class="blue" type="submit">Enregistrer</button>
        </div>
    </form>

</main>
</body>
</html>