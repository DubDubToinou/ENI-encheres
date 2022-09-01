<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Inscription</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.svg" />
</head>
<body>

<jsp:include page="header.jsp"/>

<main>
    <div class="content">
        <h1>Créer un compte</h1>

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

        <%--<c:if test="${ !empty sessionScope.utilisateur}">
            <p>Vous êtes ${ utilisateur.prenom } ${ utilisateur.nom } !</p>
        </c:if>
        <c:if test="${connecte}">
            <p>Vous êtes ${ utilisateur.prenom } ${ utilisateur.nom } et vous êtes connecte !</p>
        </c:if>--%>


        <form action="${pageContext.request.contextPath}/inscription" method="post">
            <div class="fieldsets">
                <fieldset>
                    <legend>Vos informations</legend>
                    <div class="inputField">
                        <label for="pseudo">Pseudo</label>
                        <input type="text" name="pseudo" id="pseudo" pattern="[A-Za-z0-9]{1,30}"
                               value="${!empty listeCodesErreur?param.pseudo:""}" required/>
                    </div>
                    <div class="inputField">
                        <label for="prenom">Prénom</label>
                        <input type="text" name="prenom" id="prenom" maxlength="30"
                               value="${!empty listeCodesErreur?param.prenom:""}" required/>
                    </div>
                    <div class="inputField">
                        <label for="nom">Nom</label>
                        <input type="text" name="nom" id="nom" maxlength="30"
                               value="${!empty listeCodesErreur?param.nom:""}"
                               required/>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="inputField">
                        <label for="telephone">Téléphone</label>
                        <input type="tel" name="telephone" id="telephone"
                               value="${!empty listeCodesErreur?param.telephone:""}"/>
                    </div>
                    <div class="inputField">
                        <label for="email">Email</label>
                        <input type="email" name="email" id="email" value="${!empty listeCodesErreur?param.email:""}"
                               required/>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Votre adresse</legend>
                    <div class="inputField">
                        <label for="rue">Rue</label>
                        <input type="text" name="rue" id="rue" maxlength="30"
                               value="${!empty listeCodesErreur?param.rue:""}"
                               required/>
                    </div>
                    <div class="inputField">
                        <label for="code_postal">Code postal</label>
                        <input type="text" name="code_postal" id="code_postal" pattern="[A-Za-z0-9]{1,10}"
                               value="${!empty listeCodesErreur?param.code_postal:""}" required/>
                    </div>
                    <div class="inputField">
                        <label for="ville">Ville</label>
                        <input type="text" name="ville" id="ville" maxlength="30"
                               value="${!empty listeCodesErreur?param.ville:""}" required/>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Votre mot de passe</legend>
                    <div class="inputField">
                        <label for="mot_de_passe">Mot de passe</label>
                        <input type="password" name="mot_de_passe" id="mot_de_passe" minlength="8" maxlength="30"
                               value="${!empty listeCodesErreur?param.mot_de_passe:""}" required/>
                    </div>
                    <div class="inputField">
                        <label for="confirmation">Confirmation</label>
                        <input type="password" name="confirmation" id="confirmation" minlength="8" maxlength="30"
                               value="${!empty listeCodesErreur?param.confirmation:""}" required/>
                    </div>
                </fieldset>
            </div>
            <div class="submit">
                <a class="white" href="${pageContext.request.contextPath}/accueil">Annuler</a>
                <button class="blue" type="submit">S'inscrire</button>
            </div>
        </form>
        <p>Vous avez déjà un compte ? <a href="${pageContext.request.contextPath}/connexion">Connectez-vous</a></p>
    </div>
</main>
</body>
</html>
