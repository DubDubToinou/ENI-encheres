<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Accueil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.svg"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<main>
    <div class="content">
        <h1>Liste des enchères</h1>

        <c:if test="${!empty succes}">
            <div class="success">
                <p class="successLabel">Bravo</p>
                <p class="successMessage">${succes}</p>
            </div>
            <c:remove var="succes" scope="session"/>
        </c:if>

        <form class="searchBar" action="${pageContext.request.contextPath}/accueil" method="post">
            <input type="hidden" name="form" value="form1"/>
            <div class="search">
                <select name="categorie" id="categorie" class="input">
                    <option value="" disabled selected>Catégories</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.libelle}"><c:out value="${c.libelle}"/></option>
                    </c:forEach>
                </select>
                <input type="text" name="mot_cle" id="mot_cle" value="" placeholder="Rechercher un article"/>
            </div>
            <button class="validate" type="submit">Rechercher</button>
        </form>

        <c:if test="${connecte}">
            <form class="filtreForm" action="${pageContext.request.contextPath}/accueil" method="post" id="formradio">
                <input type="hidden" name="form" value="form2"/>
                <fieldset id="check">
                    <legend>Enchères</legend>
                    <div>
                        <input type="radio" id="encheres_ouvertes" name="check" value="encheres_ouvertes"/>
                        <label for="encheres_ouvertes">Ouvertes</label>
                    </div>

                    <div>
                        <input type="radio" id="mes_encheres_en_cours" name="check" value="mes_encheres_en_cours"/>
                        <label for="mes_encheres_en_cours">En cours</label>
                    </div>

                    <div>
                        <input type="radio" id="mes_encheres_remportees" name="check" value="mes_encheres_remportees"/>
                        <label for="mes_encheres_remportees">Remportées</label>
                    </div>
                </fieldset>
                <fieldset>

                    <legend>Mes ventes</legend>
                    <div>
                        <input type="radio" id="mes_ventes_en_cours" name="check" value="mes_ventes_en_cours"/>
                        <label for="mes_ventes_en_cours">En cours</label>
                    </div>

                    <div>
                        <input type="radio" id="ventes_non_debutees" name="check" value="ventes_non_debutees"/>
                        <label for="ventes_non_debutees">Non débutées</label>
                    </div>

                    <div>
                        <input type="radio" id="ventes_terminees" name="check" value="ventes_terminees"/>
                        <label for="ventes_terminees">Terminées</label>
                    </div>
                </fieldset>
                    <%-- <button class="validate" type="submit">Rechercher</button>--%>
            </form>

        </c:if>
        <div class="items">
            <c:forEach var="article" items="${listeArticlesEnCours}">
                <div class="card">
                    <div class="seller">
                        <h3>Par</h3>
                        <a href="${pageContext.request.contextPath}/MonProfil?pseudo=${article.utilisateurs.pseudo}"><c:out
                                value="${article.utilisateurs.pseudo}"/></a>
                    </div>
                    <div class="product">
                        <a href=""><c:out value="${article.nomArticle}"/></a>
                        <c:if test="${article.etatVente}">
                            <p class="open">Ouverte</p>
                        </c:if>
                        <c:if test="${!article.etatVente}">
                            <p class="closed">Fermée</p>
                        </c:if>
                    </div>
                    <div class="info">
                        <div>
                            <h3>Prix</h3>
                            <p><c:out value="${article.prixVente}"/></p>
                        </div>
                        <div>
                            <h3>Date de fin</h3>
                            <fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd'T'HH:mm"
                                           var="dateTimeFinEncheres" type="both"/>
                            <p><fmt:formatDate pattern="dd/MM/yyyy à HH:mm" value="${ dateTimeFinEncheres }"/></p>
                        </div>
                    </div>
                    <c:if test="${!empty article.encheres}">
                        <div class="info">

                            <c:forEach var="encheres" items="${article.encheres}">
                                <h3>Votre enchère</h3>
                                <p><c:out value="${encheres.montant_enchere}"/></p>
                                <h3>Date de l'enchère</h3>
                                <fmt:parseDate value="${encheres.dateEnchere}" type="date" pattern="yyyy-MM-dd"
                                               var="dateDeLEnchere"/>
                                <p><fmt:formatDate value="${dateDeLEnchere}" pattern="dd/MM/yyyy"/></p>
                            </c:forEach>

                        </div>
                    </c:if>
                    <c:if test="${connecte}">
                        <a class="blue"
                           href="${pageContext.request.contextPath}/detailarticle?no_article=${article.noArticle}">Voir
                            le détail</a>
                    </c:if>
                </div>
            </c:forEach>

        </div>
    </div>
</main>
<script src="${pageContext.request.contextPath}/script/script.js"/>
"></script>
</body>
</html>