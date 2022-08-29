<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Accueil</title>
    <link rel="stylesheet" href="styles/modifierProfil.css"/>
</head>
<body>
<header>
    <div class="logo">
        <a href="">COCOWIKI</a>
    </div>
    <div class="buttons">
        <a class="logout" href="">Se connecter</a>
        <a class="account" href="">S'inscrire</a>
    </div>
</header>
<main>
    <div class="content">
        <h1>Liste des enchères</h1>
        <form class="searchBar" action="${pageContext.request.contextPath}/" method="post">
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
            <%--</fieldset>--%>

            <%--<fieldset id="check">--%>
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
                            <p><c:out value="${article.dateFinEncheres}" /></p>
                        </div>
                    </div>
                    <div class="seller">
                        <h3>Par</h3>
                        <a href=""><c:out value="${article.utilisateurs.pseudo}" /></a>
                    </div>
                    <c:if test="${connecte}">
                        <a href="">Voir le détail</a>
                    </c:if>
                </div>
            </c:forEach>

        </div>
    </div>
</main>
</body>
</html>