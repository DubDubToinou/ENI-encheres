<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<html>
<head>
    <title>Header</title>
</head>
<body>
<header>
    <div class="logo">
        <a href="${pageContext.request.contextPath}/accueil">Troc Enchère</a>
    </div>

    <c:if test="${connecte}">
        <nav>
            <a class="logout" href="${pageContext.request.contextPath}/NouvelleVente">Vendre un Article</a>
            <c:if test="${sessionScope.utilisateur.administrateur == 1}">
                <a href="${pageContext.request.contextPath}/Administration">Admin</a>
            </c:if>
        </nav>
        <div class="lightBlue">
            <img src="${pageContext.request.contextPath}/img/coin.svg"/>
            <p><c:out value="${sessionScope.utilisateur.credit}"/> CRT</p>
        </div>
        <div class="cart">
            <a href="${pageContext.request.contextPath}/Boutique"><img
                    src="${pageContext.request.contextPath}/img/shopping-cart.svg"></a>
        </div>
        <a class="white" href="${pageContext.request.contextPath}/deconnexion">Se Déconnecter</a>
        <a class="blue" href="${pageContext.request.contextPath}/MonProfil?pseudo=${sessionScope.utilisateur.pseudo}">Mon
            Profil</a>
    </c:if>

    <c:if test="${!connecte}">
        <div>
            <a class="white" href="${pageContext.request.contextPath}/connexion">Se connecter</a>
            <a class="blue" href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
        </div>
    </c:if>
</header>
</body>
</html>
