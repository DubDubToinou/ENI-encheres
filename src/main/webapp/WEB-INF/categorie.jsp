<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Admin | Categorie</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/modifierProfil.css"/>

</head>
<body>
<main>

    <jsp:include page="header.jsp"/>



        <c:forEach var="listeCategorie" items="${listeCategorie}" >
    <div class="card">
          <div class="buttons">
             <p> <c:out value="${listeCategorie.libelle}"/></p>
              <a class="logout" href="" >Supprimer</a>
              <a class="account" href="${pageContext.request.contextPath}/ModifierCategorie?libelle=${listeCategorie.libelle}">Modifier</a>
          </div>
    </div>
        </c:forEach>
    <div class="buttons">
        <a class="account" href="">Ajouter une Categorie</a>
    </div>




</main>
</body>
</html>
