<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Admin | Modifier Categorie</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/modifierProfil.css"/>

</head>
<body>
<main>

  <jsp:include page="header.jsp"/>



  <div class="card">
    <div class="">

      <p><c:out value="${categorieAAfficher.libelle}" /></p>
      <form action="${pageContext.request.contextPath}/ModifierCategorie" method="post">
        <input type="hidden" name="noCategorie" value="${categorieAAfficher.noCategorie}" />
        <label for="newLibelle">Nouveau Libelle:</label>
        <input type="text" name="newLibelle" id="newLibelle"/>
        <input type="submit" value="Modifier">
      </form>
    </div>
  </div>


</main>
</body>
</html>
