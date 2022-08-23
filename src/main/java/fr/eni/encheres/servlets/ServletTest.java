package fr.eni.encheres.servlets;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "ServletTest", value = "/ServletTest")
public class ServletTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleDAO articleDAO = DAOFactory.getArticleDAO();
        UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
        EncheresDAO encheresDAO = DAOFactory.getEncheresDAO();
        CategorieDAO categorieDAO = DAOFactory.getCategorieDAO();

        Utilisateur antoine = new Utilisateur("dubdub", "dubuc", "antoine", "dubdub@gmail.com", "0601020304",
                "15 rue du mirail", "33000", "bordeaux", "Password1");


        Utilisateur francois = new Utilisateur("ff", "farlan", "francois", "fff@gmail.com", "0601021304",
                "15 allée des fleurs", "33950", "lege", "Passw0rd2");

        Utilisateur sheila = new Utilisateur("sp", "rahmani", "sheila", "sheila@gmail.com", "0671021304",
                "1 bis rue de la paix", "29000", "brest", "Password2");

        Categorie informatique = new Categorie("informatique");
        Categorie divers = new Categorie("divers");

        try {
            categorieDAO.insert(informatique);
            categorieDAO.insert(divers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Articles clavier = new Articles("clavier ordinateur", "un clavier d'ordinateur de la marque XXX", LocalDate.of(2022,7,12), LocalDate.of(2022,10,12), 5, 15, true);
        Articles lampe = new Articles("lampe de chevet", "une lampe de chevet en bois", LocalDate.of(2022,7,12), LocalDate.of(2022,10,12), 6, 10, true);
        Articles souris = new Articles("souris ordinateur", "une souris d'ordinateur de la marque XXX", LocalDate.of(2022,10,12), LocalDate.of(2022,11,12), 10, 20, true);
        Articles couteauSuisse = new Articles("couteau suisse", "un couteau ustensile", LocalDate.of(2022,7,12), LocalDate.of(2022,7,13), 6, 10, true);

        System.out.println(clavier.toString());

        clavier.setCategorieArticle(informatique);
        clavier.setUtilisateurs(antoine);
        clavier.setLieuRetrait(new Retrait(antoine.getRue(), antoine.getCodePostal(), antoine.getVille()));

        souris.setCategorieArticle(informatique);
        souris.setUtilisateurs(antoine);
        souris.setLieuRetrait(new Retrait(antoine.getRue(), antoine.getCodePostal(), antoine.getVille()));

        lampe.setCategorieArticle(divers);
        lampe.setUtilisateurs(francois);
        lampe.setLieuRetrait(new Retrait(francois.getRue(), francois.getCodePostal(), francois.getVille()));

        couteauSuisse.setCategorieArticle(divers);
        couteauSuisse.setUtilisateurs(sheila);
        couteauSuisse.setLieuRetrait(new Retrait(sheila.getRue(), sheila.getCodePostal(), sheila.getVille()));


        //UTILISATEURS /////////////////////////////////////////////////////////////////////////////////


        try {
            utilisateurDAO.insert(antoine);
            utilisateurDAO.insert(francois);
            utilisateurDAO.insert(sheila);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        //CATEGORIES  ////////////////////////////////////////////////////////////////////////////
        //insert


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

        lampe.setCategorieArticle(informatique);
        try {
            articleDAO.update(lampe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //select ventes en cours
        List<Articles> listeArticles = articleDAO.selectVentesEnCours();

        for(Articles a : listeArticles) {
            System.out.println(a.toString());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
