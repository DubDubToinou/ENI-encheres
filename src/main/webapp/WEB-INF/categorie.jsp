<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Admin | Catégorie</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.svg"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <c:forEach var="listeCategorie" items="${listeCategorie}">
        <div class="field">
            <div class="pFlex">
                <p><c:out value="${listeCategorie.libelle}"/></p>
                <div class="leftDiv">
                    <a class="white" href="">Supprimer</a>
                    <a class="blue"
                       href="${pageContext.request.contextPath}/ModifierCategorie?libelle=${listeCategorie.libelle}">Modifier</a>
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="submit">
        <a class="blue" href="">Ajouter une Catégorie</a>
    </div>
</main>
</body>
</html>
