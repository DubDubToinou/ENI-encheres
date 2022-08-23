package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppliTestDAL {
    public static void main() {

        ArticleDAO articleDAO = DAOFactory.getArticleDAO();
        UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
        EncheresDAO encheresDAO = DAOFactory.getEncheresDAO();

        Utilisateur antoine = new Utilisateur("dubdub", "dubuc", "antoine", "dubdub@gmail.com", "0601020304",
                "15 rue du mirail", "33000", "bordeaux", "Password1");


        Utilisateur francois = new Utilisateur("ff", "farlan", "francois", "fff@gmail.com", "0601021304",
                "15 allée des fleurs", "33950", "lege", "Pa$$w0rd2");

        Utilisateur sheila = new Utilisateur("sp", "rahmani", "sheila", "sheila@gmail.com", "0671021304",
                "1 bis rue de la paix", "29000", "brest", "Password2");

        Categorie informatique = new Categorie(1, "informatique");
        Categorie divers = new Categorie(2, "divers");

        Articles clavier = new Articles("clavier ordinateur", "un clavier d'ordinateur de la marque XXX", LocalDate.of(2022,7,12), LocalDate.of(2022,10,12), 5, 15, true, informatique );
        Articles lampe = new Articles("lampe de chevet", "une lampe de chevet en bois", LocalDate.of(2022,7,12), LocalDate.of(2022,10,12), 6, 10, true, divers );
        Articles souris = new Articles("souris ordinateur", "une souris d'ordinateur de la marque XXX", LocalDate.of(2022,10,12), LocalDate.of(2022,11,12), 10, 20, true, informatique );
        Articles couteauSuisse = new Articles("couteau suisse", "un couteau ustensile", LocalDate.of(2022,7,12), LocalDate.of(2022,7,13), 6, 10, true, divers );

        clavier.setUtilisateurs(antoine);
        souris.setUtilisateurs(antoine);
        lampe.setUtilisateurs(francois);
        couteauSuisse.setUtilisateurs(sheila);


        //UTILISATEURS /////////////////////////////////////////////////////////////////////////////////
        //insert

        try {
            utilisateurDAO.insert(antoine);
            utilisateurDAO.insert(francois);
            utilisateurDAO.insert(sheila);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Select By id");
        try {
            utilisateurDAO.selectByID("dubdub");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Select own profile");
        try {
            utilisateurDAO.selectOwnProfile("dubdub");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //update
        antoine.setPseudo("toinou");
        try {
            utilisateurDAO.update(antoine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //delete
        try {
            utilisateurDAO.delete(antoine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //ARTICLES/////////////////////////////////////////////////////////////////////////////////////
        //insert
        try {
            articleDAO.insert(clavier);
            articleDAO.insert(souris);
            articleDAO.insert(couteauSuisse);
            articleDAO.insert(lampe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        //update
        lampe.setCategorieArticle(informatique);
        try {
            articleDAO.update(lampe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // delete

        try {
            articleDAO.delete(lampe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //select ventes en cours
        List<Articles> listeArticles = articleDAO.selectVentesEnCours();

        for(Articles a : listeArticles) {
            System.out.println(a.toString());
        }

        //Select by categorie
        List<Articles> listeCategorie = articleDAO.selectByCategorie(informatique);

        for(Articles a : listeCategorie) {
            System.out.println(a.toString());
        }

        //Select by motcle
        List<Articles> listeKW = articleDAO.selectByMotCle("ordinateur");

        for(Articles a : listeKW) {
            System.out.println(a.toString());
        }

        //select ventes en cours par utilisateur
        List<Articles> listesEnCoursUtilisateur = articleDAO.selectVentesEnCoursParUtilisateur(francois);

        for(Articles a : listesEnCoursUtilisateur) {
            System.out.println(a.toString());
        }

        //select terminées par utilisateur
        List<Articles> listesTerminéesParUtilisateur = articleDAO.selectVentesTermineesParUtilisateur(sheila);
        for(Articles a : listesTerminéesParUtilisateur) {
            System.out.println(a.toString());
        }

    }
}
