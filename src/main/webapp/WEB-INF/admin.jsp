<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <title>Administration</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.svg"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="adminContent">
        <h1>Administration</h1>

        <c:if test="${!empty listeCodesErreur}">
            <c:forEach var="code" items="${listeCodesErreur}">
                <div class="error">
                    <p class="errorLabel">Erreur</p>
                    <p class="errorMessage">${LecteurMessage.getMessageErreur(code)}</p>
                </div>
            </c:forEach>
        </c:if>

        <div class="buttons">
            <a class="white" href="${pageContext.request.contextPath}/AffichageCategorie">Gestion catégorie</a>
            <a class="white"
               href="${pageContext.request.contextPath}/MonProfil?pseudo=${sessionScope.utilisateur.pseudo}">Gestion
                Utilisateur</a>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>