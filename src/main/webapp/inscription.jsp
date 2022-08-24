<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Inscription</title>
</head>
<body>
<header>
    <div class="logo">
        <p>Enchères</p>
    </div>
    <div class="buttons">
        <button>Se connecter</button>
        <button>S'inscrire</button>
    </div>
</header>
<main>
    <h1>Créer un compte</h1>
    <form action="">
        <fieldset>
            <legend>Vos informations</legend>
            <label for="pseudo">Pseudo</label>
            <input type="text" name="pseudo" id="pseudo" maxlength="30" required/>
            <label for="firstname">Prénom</label>
            <input type="text" name="firstname" id="firstname" maxlength="30" required/>
            <label for="name">Nom</label>
            <input type="text" name="name" id="name" maxlength="30" required/>
            <label for="phone">Téléphone</label>
            <input type="tel" name="phone" id="phone"/>
            <label for="email">Email</label>
            <input type="email" name="email" id="email" required/>
        </fieldset>
        <fieldset>
            <legend>Votre adresse</legend>
            <label for="street">Rue</label>
            <input type="number" name="street" id="street" maxlength="30" required/>
            <label for="city">Ville</label>
            <input type="text" name="city" id="city" maxlength="30" required/>
            <label for="zip">Code postal</label>
            <input type="text" name="zip" id="zip" pattern="[A-Za-z0-9]{10}" required/>
        </fieldset>
        <fieldset>
            <legend>Créer un mot de passe</legend>
            <label for="pwd">Mot de passe</label>
            <input type="password" name="pwd" id="pwd" pattern="[A-Za-z0-9]{8,30}" required/>
            <label for="pwdv">Confirmation</label>
            <input type="password" name="pwdv" id="pwdv" pattern="[A-Za-z0-9]{8,30}" required/>
        </fieldset>
        <div class="buttons">
            <button type="submit">S'inscrire</button>
            <a href="">Annuler</a>
        </div>
    </form>
    <p>Vous avez déjà un compte ? <a href="">Connectez-vous</a></p>
</main>
</body>
</html>
