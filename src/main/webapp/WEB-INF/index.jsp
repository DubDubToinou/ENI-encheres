<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Accueil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/modifierProfil.css"/>
</head>
<body>
<header>
    <div class="logo">
        <a href="${pageContext.request.contextPath}/accueil">COCOWIKI</a>
    </div>

    <c:if test="${connecte}">
        <div class="buttons">
            <a class="logout" href="">Mes Enchères</a>
            <a class="logout" href="${pageContext.request.contextPath}/NouvelleVente">Vendre un Article</a>
            <a class="logout" href="${pageContext.request.contextPath}/MonProfil">Mon Profil</a>
            <a class="account" href="${pageContext.request.contextPath}/deconnexion">Se Déconnecter</a>

        </div>
    </c:if>
    <c:if test="${!connecte}">
        <div class="buttons">
            <a class="logout" href="${pageContext.request.contextPath}/connexion">Se connecter</a>
            <a class="account" href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
        </div>
    </c:if>
</header>
<main>
    <div class="content">
        <h1>Liste des enchères</h1>
        <form class="searchBar" action="${pageContext.request.contextPath}/accueil" method="post">
            <input type="hidden" name="form" value="form1"/>
            <div class="search">
                <select name="categorie" id="categorie" class="input">
                    <option value="" disabled selected>Catégories</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.libelle}"><c:out value="${c.libelle}" /></option>
                    </c:forEach>
                </select>
                <input type="text" name="mot_cle" id="mot_cle" value= "" placeholder="Rechercher un article"/>
            </div>
            <button class="validate" type="submit">Rechercher</button>
        </form>

        <form action="${pageContext.request.contextPath}/accueil" method="post">
            <input type="hidden" name="form" value="form2"/>
            <fieldset id="check">
                <legend>Achats</legend>
                <div>
                    <input type="radio" id="encheres_ouvertes" name="check" value="encheres_ouvertes"
                           checked>
                    <label for="encheres_ouvertes">Enchères ouvertes</label>
                </div>

                <div>
                    <input type="radio" id="mes_encheres_en_cours" name="check" value="mes_encheres_en_cours">
                    <label for="mes_encheres_en_cours">Mes enchères en cours</label>
                </div>

                <div>
                    <input type="radio" id="mes_encheres_remportees" name="check" value="mes_encheres_remportees">
                    <label for="mes_encheres_remportees">Mes enchères remportées</label>
                </div>



                <legend>Mes ventes</legend>
                <div>
                    <input type="radio" id="mes_ventes_en_cours" name="check" value="mes_ventes_en_cours">
                    <label for="mes_ventes_en_cours">Mes ventes en cours</label>
                </div>

                <div>
                    <input type="radio" id="ventes_non_debutees" name="check" value="ventes_non_debutees">
                    <label for="ventes_non_debutees">Ventes non débutées</label>
                </div>

                <div>
                    <input type="radio" id="ventes_terminees" name="check" value="ventes_terminees">
                    <label for="ventes_terminees">Ventes terminées</label>
                </div>
            </fieldset>

            <button class="validate" type="submit">Rechercher</button>
        </form>

        <div class="items">

            <c:forEach var="article" items="${listeArticlesEnCours}">
                <div class="card">
                    <div class="product">
                        <a href=""><c:out value="${article.nomArticle}" /></a>
                        <p class="open">Ouverte</p>
                    </div>
                    <div class="info">
                        <div>
                            <h3>Prix</h3>
                            <p><c:out value="${article.prixVente}" /></p>
                        </div>
                        <div>
                            <h3>Date de fin</h3>
                                <fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd'T'HH:mm" var="dateTimeFinEncheres" type="both" />
                            <p> <fmt:formatDate pattern="dd/MM/yyyy à HH:mm" value="${ dateTimeFinEncheres }" /></p>
                        </div>
                    </div>
                    <div class="seller">
                        <h3>Par</h3>
                        <a href=""><c:out value="${article.utilisateurs.pseudo}" /></a>
                    </div>
                    <c:if test="${!empty article.encheres}">
                        <div class="enchere">

                            <c:forEach var="encheres" items="${article.encheres}">
                                <h3>Votre enchère</h3>
                                <p><c:out value="${encheres.montant_enchere}"/></p>
                                <h3>Date de l'enchère</h3>
                                <%--<fmt:parseDate  value="${encheres.dateEnchere}"  type="date" pattern="yyyy-MM-dd" var="dateDeLEnchere" />
                                <p><fmt:formatDate value="${dateDeLEnchere}" pattern="dd/MM/yyyy"/></p>--%>
                            </c:forEach>

                        </div>
                    </c:if>
                    <c:if test="${connecte}">
                        <a href="${pageContext.request.contextPath}/detailarticle?no_article=${article.noArticle}">Voir le détail</a>
                    </c:if>
                </div>
            </c:forEach>

        </div>
    </div>
</main>
</body>
</html>