<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="./styles/connexion.css" />
</head>
<body>
<header>
    <div class="logo">
        <a href="">Enchères</a>
    </div>
    <div class="buttons">
        <a href="connexion.jsp">Se connecter</a>
        <a href="./inscription.jsp">S'inscrire</a>
    </div>
</header>
<main>
    <h1>Connectez-vous</h1>
    <h2>C'est un plaisir de vous revoir !</h2>
    <form action="">
        <label for="id">Identifiant</label>
        <input type="text" name="id" id="id" placeholder="Entrez votre identifiant">
        <label for="pwd">Mot de passe</label>
        <input type="text" name="pwd" id="pwd" placeholder="••••••••">
        <div class="remember">
            <input type="checkbox" name="remember" id="remember">
            <label for="remember">Se souvenir de moi</label>
            <a href="">Mot de passe oublié</a>
        </div>
        <button type="submit">Connexion</button>
    </form>
    <p>Vous n'avez pas de compte ? <a href="./inscription.jsp">Inscrivez-vous</a></p>
</main>
</body>
</html>