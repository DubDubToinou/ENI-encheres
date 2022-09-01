<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Suppression du compte</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<main>
    <h1>Suppression du compte</h1>
    <h2>Merci de confirmer votre mot de passe pour supprimer votre compte.</h2>

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

    <form action="${pageContext.request.contextPath}/SupprimerProfil" method="post">
        <div class="inputField">
            <label for="mot_de_passe">Mot de passe</label>
            <input type="password" name="mot_de_passe" id="mot_de_passe" placeholder="Entrez votre mot de passe"
                   required/>
        </div>
        <div class="inputField">
            <label for="confirm_mot_de_passe">Confirmation du mot de passe</label>
            <input type="password" name="confirm_mot_de_passe" id="confirm_mot_de_passe"
                   placeholder="Confirmez votre mot de passe" required/>
        </div>
        <div class="submit">
            <button class="red" type="submit">Confirmer la suppression</button>
        </div>
    </form>

    <p>Je ne souhaite pas supprimer mon compte ! <a href="${pageContext.request.contextPath}/accueil">Retour à
        l'accueil</a></p>
</main>
</body>
</html>