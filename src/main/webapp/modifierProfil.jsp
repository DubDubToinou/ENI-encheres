<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Modifier mon profil</title>
  <link rel="stylesheet" href="" />
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
    <a href="">Mon profil</a>
  </div>
</header>
<main>
  <h1>Modifier mon compte</h1>
  <form action="">
    <div class="fieldsets">
      <fieldset>
        <legend>Vos informations</legend>
        <label for="pseudo">Pseudo</label>
        <input
                type="text"
                name="pseudo"
                id="pseudo"
                maxlength="30"
                required
        />
        <label for="firstname">Prénom</label>
        <input
                type="text"
                name="firstname"
                id="firstname"
                maxlength="30"
                required
        />
        <label for="name">Nom</label>
        <input type="text" name="name" id="name" maxlength="30" required />
        <label for="phone">Téléphone</label>
        <input type="tel" name="phone" id="phone" />
        <label for="email">Email</label>
        <input type="email" name="email" id="email" required />
      </fieldset>
      <fieldset>
        <legend>Votre adresse</legend>
        <label for="street">Rue</label>
        <input
                type="number"
                name="street"
                id="street"
                maxlength="30"
                required
        />
        <label for="city">Ville</label>
        <input type="text" name="city" id="city" maxlength="30" required />
        <label for="zip">Code postal</label>
        <input
                type="text"
                name="zip"
                id="zip"
                pattern="[A-Za-z0-9]{10}"
                required
        />
      </fieldset>
      <fieldset>
        <legend>Modifier mon mot de passe</legend>
        <label for="pwd">Mot de passe actuel</label>
        <input
                type="password"
                name="pwd"
                id="pwd"
                pattern="[A-Za-z0-9]{8,30}"
                required
        />
        <label for="pwdn">Nouveau mot de passe</label>
        <input
                type="password"
                name="pwdn"
                id="pwdn"
                pattern="[A-Za-z0-9]{8,30}"
                required
        />
        <label for="pwdv">Confirmation</label>
        <input
                type="password"
                name="pwdv"
                id="pwdv"
                pattern="[A-Za-z0-9]{8,30}"
                required
        />
      </fieldset>
      <p>Crédit U Crédit</p>
    </div>
    <div class="submit">
      <a href="">Supprimer mon compte</a>
      <button type="submit">Enregistrer</button>
    </div>
  </form>
</main>
</body>
</html>
