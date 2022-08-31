<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Modifier mon profil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/modifierProfil.css"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="content">
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

        <h2>Modifier mes informations personnels, mon adresse ou mon mot de passe.</h2>
        <form action="${pageContext.request.contextPath}/ModifierProfil" method="post">
            <div class="fieldsets">
                <fieldset>
                    <legend>Vos informations</legend>
                    <div class="label">
                        <label for="pseudo">Pseudo</label>
                        <input type="hidden" name="noUtilisateur" value="${sessionScope.utilisateur.noUtilisateur}">
                        <input type="text" name="pseudo" id="pseudo" pattern="[A-Za-z0-9]{1,30}" value="${sessionScope.utilisateur.pseudo}"
                               placeholder=${sessionScope.utilisateur.pseudo} required/>
                    </div>
                    <div class="label">
                        <label for="firstname">Prénom</label>
                        <input
                                type="text"
                                name="prenom"
                                id="firstname"
                                maxlength="30"
                                value="${sessionScope.utilisateur.prenom}"
                                placeholder=${sessionScope.utilisateur.prenom}
                                        required
                        />
                    </div>
                    <div class="label">
                        <label for="name">Nom</label>
                        <input
                                type="text"
                                name="nom"
                                id="name"
                                maxlength="30"
                                value="${sessionScope.utilisateur.nom}"
                                placeholder=${sessionScope.utilisateur.nom}
                                        required
                        />
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Vous contacter</legend>
                    <div class="label">
                        <label for="phone">Téléphone</label>
                        <input type="tel" name="telephone" id="phone" value="${sessionScope.utilisateur.telephone}"
                               placeholder=${sessionScope.utilisateur.telephone}/>
                    </div>
                    <div class="label">
                        <label for="email">Email</label>
                        <input type="email" name="email" id="email" value="${sessionScope.utilisateur.email}"
                               placeholder=${sessionScope.utilisateur.email} required>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Votre adresse</legend>
                    <div class="label">
                        <label for="street">Rue</label>
                        <input
                                type="text"
                                name="rue"
                                id="street"
                                maxlength="30"
                                value="${sessionScope.utilisateur.rue}"
                                placeholder=${sessionScope.utilisateur.rue}
                                        required
                        />
                    </div>
                    <div class="label">
                        <label for="city">Ville</label>
                        <input
                                type="text"
                                name="ville"
                                id="city"
                                maxlength="30"
                                value="${sessionScope.utilisateur.ville}"
                                placeholder=${sessionScope.utilisateur.ville}
                                        required
                        />
                    </div>
                    <div class="label">
                        <label for="zip">Code postal</label>
                        <input
                                type="text"
                                name="code_postal"
                                id="zip"
                                pattern="[A-Za-z0-9]{0,10}"
                                value="${sessionScope.utilisateur.codePostal}"
                                placeholder=${sessionScope.utilisateur.codePostal}
                                        required
                        />
                    </div>
                </fieldset>
                <div class="label">
                    <p class="label">Crédit</p>
                    <p>${sessionScope.utilisateur.credit} Crédits</p>
                </div>
            </div>
            <div class="submit">
                <a href="${pageContext.request.contextPath}/ModifierMotDePasse">Modifier mon mot de passe</a>
                <a href="${pageContext.request.contextPath}/SupprimerProfil">Supprimer mon compte</a>
                <button type="submit">Enregistrer</button>
            </div>
        </form>
    </div>
</main>
</body>
</html>