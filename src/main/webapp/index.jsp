<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <form class="searchBar" action="">
        <div class="search">
            <select name="category" id="category" class="input">
                <option value="" disabled selected>Catégories</option>
                <option value="tech">Informatique</option>
                <option value="furniture">Ameublement</option>
                <option value="clothe">Vêtement</option>
                <option value="sport">Sport et Loisirs</option>
            </select>
            <input type="text" name="search" id="search" placeholder="Rechercher un article"/>
        </div>
        <button class="validate" type="submit">Rechercher</button>
    </form>
    <div class="items">
        <div class="card">
            <div class="seller">
                <h3>Par</h3>
                <a href="">gentilVendeur123</a>
            </div>
            <div class="product">
                <a href="">Une jolie chaise</a>
                <p class="open">Ouverte</p>
                <p class="description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
            </div>
            <div class="info">
                <div>
                    <h3>Prix</h3>
                    <p>150 PTS</p>
                </div>
                <div>
                    <h3>Date de fin</h3>
                    <p>12 sept. 2022</p>
                </div>
            </div>
            <div class="address">
                <h3>Adresse</h3>
                <p>221 B BackerStreet, 77777, Londre</p>
            </div>
            <a href="">Voir</a>
        </div>
    </div>
    </div>
</main>
</body>
</html>