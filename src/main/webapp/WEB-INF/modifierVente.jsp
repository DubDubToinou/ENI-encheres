<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Nouvelle vente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.svg" />
</head>
<body>

<jsp:include page="header.jsp"/>

<c:if test="${!empty listeCodesErreur}">
    <c:forEach var="code" items="${listeCodesErreur}">
        <div class="error">
            <p class="errorLabel">Erreur</p>
            <p class="errorMessage">${LecteurMessage.getMessageErreur(code)}</p>
        </div>
    </c:forEach>
</c:if>

<main>
    <div class="content">
        <h1>Modifier l'article en vente</h1>
        <form action="${pageContext.request.contextPath}/modifiervente" method="post">
            <fieldset>
                <input type="hidden" name="noArticle" value="${article.noArticle}"/>
                <div class="inputField">
                    <label for="titre">Titre</label>
                    <input
                            type="text"
                            name="nom_article"
                            id="titre"
                            maxlength="30"
                            value="${article.nomArticle}"
                            placeholder="${article.nomArticle}"
                            required
                    />
                </div>

                <div class="inputField">
                    <label for="categorie">Catégorie</label>
                    <select name="categorie" id="categorie">
                        <c:forEach var="c" items="${categories}">
                            <option value="${c.libelle}"><c:out value="${c.libelle}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="inputField">
                    <label for="prixInitial">Mise à prix</label>
                    <input
                            type="number"
                            name="prix_initial"
                            id="prixInitial"
                            placeholder="${article.miseAPrix}"
                            value="${article.miseAPrix}"
                            required
                    />
                </div>
            </fieldset>
            <fieldset>
                <div class="inputField">
                    <label for="description">Description</label>
                    <textarea
                            name="description"
                            id="description"
                            cols="30"
                            rows="5"
                            maxlength="300"
                            placeholder="${article.description}"
                            required
                    >${article.description}</textarea>
                </div>
            </fieldset>
            <fieldset>
                <div class="inputField">
                    <label for="dateDebut">Début de l'enchère</label>
                    <input
                            type="datetime-local"
                            name="date_debut_encheres"
                            id="dateDebut"
                            value="${article.dateDebutEncheres}"
                            placeholder="${article.dateDebutEncheres}"
                            required
                    />
                </div>
                <div class="inputField">
                    <label for="dateFin">Fin de l'enchère</label>
                    <input type="datetime-local" name="date_fin_encheres" id="dateFin"
                           value="${article.dateFinEncheres}" placeholder="${article.dateFinEncheres}" required/>
                </div>
            </fieldset>
            <fieldset>
                <legend>Retrait</legend>
                <div class="inputField">
                    <label for="rue">Rue</label>
                    <input
                            type="text"
                            name="rue"
                            id="rue"
                            maxlength="30"
                            value="${article.lieuRetrait.rue}"
                            placeholder="${article.lieuRetrait.rue}"
                    />
                </div>
                <div class="inputField">
                    <label for="codePostal">Code Postal</label>
                    <input
                            type="text"
                            name="code_postal"
                            id="codePostal"
                            pattern="[A-Za-z0-9]{1,10}"
                            value="${article.lieuRetrait.codePostal}"
                            placeholder="${article.lieuRetrait.codePostal}"
                    />
                </div>
                <div class="inputField">
                    <label for="ville">Ville</label>
                    <input
                            type="text"
                            name="ville"
                            id="ville"
                            maxlength="30"
                            value="${article.lieuRetrait.ville}"
                            placeholder="${article.lieuRetrait.ville}"
                    />
                </div>
            </fieldset>
            <div class="submit">
                <a class="white"
                   href="${pageContext.request.contextPath}/detailarticle?no_article=${article.noArticle}">Annuler</a>
                <button class="blue" type="submit">Enregistrer</button>
            </div>
        </form>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
