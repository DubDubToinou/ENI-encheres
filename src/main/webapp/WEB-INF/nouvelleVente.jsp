<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Nouvelle vente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/modifierProfil.css"/>
</head>
<body>

<jsp:include page="header.jsp"/>

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
<main>
    <div class="content">
        <h1>Mettre un article en vente</h1>
        <form
                action="${pageContext.request.contextPath}/NouvelleVente"
                method="post"
        >
            <fieldset>
                <div class="label">
                    <label for="titre">Titre</label>
                    <input
                            type="text"
                            name="nom_article"
                            id="titre"
                            maxlength="30"
                            required
                    />
                </div>
                <div class="label">
                    <label for="categorie">Catégorie</label>
                    <select name="categorie" id="categorie">
                        <c:forEach var="c" items="${categories}">
                            <option value="${c.libelle}"><c:out value="${c.libelle}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="label">
                    <label for="prixInitial">Mise à prix</label>
                    <input
                            type="number"
                            name="prix_initial"
                            id="prixInitial"
                            required
                    />
                </div>
            </fieldset>
            <fieldset>
                <div class="label">
                    <label for="description">Description</label>
                    <textarea
                            name="description"
                            id="description"
                            cols="30"
                            rows="5"
                            maxlength="300"
                            required
                    ></textarea>
                </div>
            </fieldset>
            <fieldset>
                <div class="label">
                    <label for="dateDebut">Début de l'enchère</label>
                    <input
                            type="datetime-local"
                            name="date_debut_encheres"
                            id="dateDebut"
                            required
                    />
                </div>
                <div class="label">
                    <label for="dateFin">Fin de l'enchère</label>
                    <input type="datetime-local" name="date_fin_encheres" id="dateFin" required/>
                </div>
            </fieldset>
            <fieldset>
                <legend>Retrait</legend>
                <div class="label">
                    <label for="rue">Rue</label>
                    <input
                            type="text"
                            name="rue"
                            id="rue"
                            maxlength="30"
                            placeholder="${ sessionScope.utilisateur.rue }"
                    />
                </div>
                <div class="label">
                    <label for="codePostal">Code Postal</label>
                    <input
                            type="text"
                            name="code_postal"
                            id="codePostal"
                            pattern="[A-Za-z0-9]{1,10}"
                            placeholder="${sessionScope.utilisateur.codePostal}"
                    />
                </div>
                <div class="label">
                    <label for="ville">Ville</label>
                    <input
                            type="text"
                            name="ville"
                            id="ville"
                            maxlength="30"
                            placeholder="${ sessionScope.utilisateur.ville }"
                    />
                </div>
                <p>* Si tous les champs ne sont pas remplis, votre adresse sera défini comme adresse de retrait.</p>
            </fieldset>
            <div class="submit">
                <a href="">Annuler</a>
                <button type="submit">Enregistrer</button>
            </div>
        </form>
    </div>
</main>
</body>
</html>
