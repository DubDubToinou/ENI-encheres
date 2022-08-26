<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%><!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Modifier mon profil</title>
    <link rel="stylesheet" href=""/>
</head>
<body>
<header>
    <div class="logo">
        <a href="">Enchères</a>
    </div>
    <nav>
        <a href="">Accueil</a>
        <a href="">Mes ventes</a>
        <a href="">Mes enchères</a>
    </nav>
    <div class="buttons">
        <a href="">Se déconnecter</a>
        <a href="${pageContext.request.contextPath}/WEB-INF/profile.jsp">Mon profil</a>
    </div>
</header>
<main>
    <h1>Modifier mon compte</h1>

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

    <form action="${pageContext.request.contextPath}/ModifierProfil" method="post">
        <div class="fieldsets">
            <fieldset>
                <legend>Vos informations</legend>
                <label for="pseudo">Pseudo</label>
                <input
                        type="text"
                        name="pseudo"
                        id="pseudo"
                        pattern="[A-Za-z0-9]{1,30}"
                        value=${sessionScope.utilisateur.pseudo}
                        required
                />
                <label for="firstname">Prénom</label>
                <input
                        type="text"
                        name="prenom"
                        id="firstname"
                        maxlength="30"
                        value=${sessionScope.utilisateur.prenom}
                        required
                />
                <label for="name">Nom</label>
                <input type="text" name="nom" id="name" maxlength="30" value=${sessionScope.utilisateur.nom} required/>
                <label for="phone">Téléphone</label>
                <input type="tel" name="telephone" id="phone" value=${sessionScope.utilisateur.telephone} />
                <label for="email">Email</label>
                <input type="email" name="email" id="email" value=${sessionScope.utilisateur.email} required/>
            </fieldset>
            <fieldset>
                <legend>Votre adresse</legend>
                <label for="street">Rue</label>
                <input
                        type="text"
                        name="rue"
                        id="street"
                        maxlength="30"
                        value=${ sessionScope.utilisateur.rue }
                        required
                />
                <label for="city">Ville</label>
                <input type="text" name="ville" id="city" maxlength="30" value=${ sessionScope.utilisateur.ville } required/>
                <label for="zip">Code postal</label>
                <input
                        type="text"
                        name="code_postal"
                        id="zip"
                        pattern="[A-Za-z0-9]{0,10}"
                        value=${sessionScope.utilisateur.codePostal}
                        required
                />
            </fieldset>
            <fieldset>
                <legend>Modifier mon mot de passe</legend>
                <label for="pwd">Mot de passe actuel</label>
                <input
                        type="password"
                        name="mot_de_passeold"
                        id="pwd"
                        minlength="8"
                        maxlength="30"
                        value=${sessionScope.utilisateur.motDePasse}
                        required
                />
                <label for="pwdn">Nouveau mot de passe</label>
                <input
                        type="password"
                        name="mot_de_passe"
                        id="pwdn"
                        minlength="8"
                        maxlength="30"

                />
                <label for="pwdv">Confirmation</label>
                <input
                        type="password"
                        name="confirmation"
                        id="pwdv"
                        minlength="8"
                        maxlength="30"

                />
            </fieldset>
            <p>Crédit : ${sessionScope.utilisateur.credit}</p>
        </div>
        <div class="submit">
            <a href="">Supprimer mon compte</a>
            <button type="submit">Enregistrer</button>
        </div>
    </form>
</main>
</body>
</html>
