<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Bonjour : ${ sessionScope.utilisateur.pseudo}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/modifierProfil.css"/>
</head>
<body>
<jsp:include page="header.jsp"/>


<main>

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

  <div class="profil">

    <c:if test="${!empty succes}">
      <p>${succes}</p>
      <c:remove var="succes" scope="session" />
    </c:if>

    <div>
      <h1><c:out value="${utilisateurAAfficher.pseudo}" /> </h1> <!-- Affichage du pseudo -->
      <p>Prenom  : <c:out value="${utilisateurAAfficher.prenom}" /> </p>     <!--Affichage du Prenom -->
      <p>Nom : <c:out value="${utilisateurAAfficher.nom}" /> </p>        <!-- Affichage du Nom-->
    </div>
    <div>
      <h2>Contact</h2>
      <p>Email : <c:out value="${utilisateurAAfficher.email}" /></p>   <!--affichage de l'email -->
      <p>Téléphone : <c:out value="${utilisateurAAfficher.telephone}" /></p><!--Affichage du telephone -->
      <p>Rue : <c:out value="${utilisateurAAfficher.rue}" /></p> <!-- Affichage de la rue-->
      <p>Ville : <c:out value="${utilisateurAAfficher.ville}" /></p> <!-- Affichage de la ville-->
      <p>Code Postal :  <c:out value="${utilisateurAAfficher.codePostal}" /></p> <!-- Affichage du code postal-->

      <c:if test="${sessionScope.utilisateur.pseudo == utilisateurAAfficher.pseudo}">
        <p>Credit : <c:out value="${utilisateurAAfficher.credit}" /></p> <!-- Affichage des crédit -->
      </c:if>

    </div>

   <c:if test="${sessionScope.utilisateur.pseudo == utilisateurAAfficher.pseudo}">
        <button> <a class="logout" href="${pageContext.request.contextPath}/ModifierProfil">Modifier mon profil</a></button>
    </c:if>


  </div>
</main>
</body>
</html>
