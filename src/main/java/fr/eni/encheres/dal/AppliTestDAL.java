package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import java.time.LocalDate;

public class AppliTestDAL {
    public static void main(String[] args) {

        ArticleDAO articleDAO = DAOFactory.getArticleDAO();

        Utilisateur antoine = new Utilisateur("dubdub", "dubuc", "antoine", "dubdub@gmail.com", "0601020304",
                "15 rue du mirail", "33000", "bordeaux", "Pa$$w0rd1");


        Utilisateur francois = new Utilisateur("ff", "farlan", "francois", "fff@gmail.com", "0601021304",
                "15 allée des fleurs", "33950", "lege", "Pa$$w0rd2");
        

        Categorie informatique = new Categorie(1, "informatique");
        Categorie divers = new Categorie(2, "divers");

        Articles clavier = new Articles("clavier ordinateur", "un clavier d'ordinateur de la marque XXX", LocalDate.of(2022,7,12), LocalDate.of(2022,10,12), 5, 15, true, informatique );
        Articles lampe = new Articles("lampe de chevet", "une lampe de chevet en bois", LocalDate.of(2022,7,12), LocalDate.of(2022,10,12), 6, 10, true, divers );
        Articles souris = new Articles("souris ordinateur", "une souris d'ordinateur de la marque XXX", LocalDate.of(2022,10,12), LocalDate.of(2022,11,12), 10, 20, true, informatique );

        antoine.addArticleVendu(clavier);
        antoine.addArticleVendu(souris);
        francois.addArticleVendu(lampe);


    }
}
