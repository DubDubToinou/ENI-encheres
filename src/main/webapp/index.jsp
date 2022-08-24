<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Accueil</title>
    <link rel="stylesheet" href=""/>
</head>
<body>
<header>
    <div class="logo">
        <a href="">Enchères</a>
    </div>
    <div class="buttons">
        <button class="login">Se connecter</button>
        <button class="signup">S'inscrire</button>
    </div>
</header>
<main>
    <h1>Liste des enchères</h1>
    <form action="">
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
        <button type="submit">Rechercher</button>
    </form>
    <div class="items">
        <div class="card">
            <div class="seller">
                <p>Par</p>
                <a href="">U pseudo</a>
            </div>
            <div class="product">
                <a href="">A titre</a>
                <p class="tag">A etat</p>
            </div>
            <div class="info">
                <div>
                    <h3>Prix</h3>
                    <p>A prix</p>
                </div>
                <div>
                    <h3>Date de fin</h3>
                    <p>A date fin</p>
                </div>
            </div>
            <div class="address">
                <h3>Adresse</h3>
                <p>A retrait</p>
            </div>
            <a href="">Voir</a>
        </div>
    </div>
</main>
</body>
</html>