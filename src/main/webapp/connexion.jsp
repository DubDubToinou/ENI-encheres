<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Connexion</title>
    <link rel="stylesheet" href="./styles/connexion.css" />
</head>
<body>
<header>
    <div class="logo">
        <a href="">Enchères</a>
    </div>
    <div class="buttons">
        <a class="login" href="${pageContext.request.contextPath}/connexion">Se connecter</a>
        <a class="signup" href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
    </div>
</header>
<main>
    <h1>Connectez-vous</h1>
    <h2>C'est un plaisir de vous revoir !</h2>

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

    <c:if test="${connecte}">
        <p>Vous êtes ${ utilisateur.prenom } ${ utilisateur.nom } et vous êtes connecte !</p>
    </c:if>
    <c:if test="${!connecte}">
        <p>Vous êtes ${ utilisateur.prenom } ${ utilisateur.nom } et vous êtes deconnecte !</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/connexion" method="post">
        <label for="login">Identifiant</label>
        <input type="text" name="login" id="login" placeholder="Entrez votre identifiant" value ="${!empty listeCodesErreur?param.login:""}" required />
        <label for="mot_de_passe">Mot de passe</label>
        <input type="password" name="mot_de_passe" id="mot_de_passe" placeholder="••••••••" value ="${!empty listeCodesErreur?param.prenom:""}" required/>
        <div class="remember">
            <input type="checkbox" name="remember" id="remember">
            <label for="remember">Se souvenir de moi</label>
            <a href="">Mot de passe oublié</a>
        </div>
        <button type="submit">Connexion</button>
    </form>
    <p>Vous n'avez pas de compte ? <a href="${pageContext.request.contextPath}/inscription">Inscrivez-vous</a></p>
</main>
</body>
</html>