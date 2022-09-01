<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Connexion</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body>

<jsp:include page="header.jsp"/>

<main>
  <h1>Bienvenue sur la boutique</h1>

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

  <h2> Vous pouvez acheter des crédits : </h2>
  <form action="${pageContext.request.contextPath}/Boutique" method="post">
    <label for="credit"></label>
    <input type="hidden" name="pseudo" value="${ sessionScope.utilisateur.pseudo }">
    <input type="hidden" name="noUtilisateur" value="${sessionScope.utilisateur.noUtilisateur}">
    <input type="text" name="newCredit" id="credit" placeholder="Combien de crédit?" minlength="1" maxlength="5" required />

    <button type="submit">Acheter</button>
  </form>
  <p>Retourner à l'accueil<a href="${pageContext.request.contextPath}/MonProfil">Retour à l'accueil</a></p>
</main>
</body>
</html>

</body>
</html>
