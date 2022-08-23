package fr.eni.encheres.servlets;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "ServletTest", value = "/ServletTest")
public class ServletTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleDAO articleDAO = DAOFactory.getArticleDAO();
        UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
        EncheresDAO encheresDAO = DAOFactory.getEncheresDAO();

        Utilisateur antoine = new Utilisateur("dubdub", "dubuc", "antoine", "dubdub@gmail.com", "0601020304",
                "15 rue du mirail", "33000", "bordeaux", "Password1");


        Utilisateur francois = new Utilisateur("ff", "farlan", "francois", "fff@gmail.com", "0601021304",
                "15 allée des fleurs", "33950", "lege", "Passw0rd2");

        Utilisateur sheila = new Utilisateur("sp", "rahmani", "sheila", "sheila@gmail.com", "0671021304",
                "1 bis rue de la paix", "29000", "brest", "Password2");

        Categorie informatique = new Categorie("informatique");
        Categorie divers = new Categorie("divers");

        Articles clavier = new Articles("clavier ordinateur", "un clavier d'ordinateur de la marque XXX", LocalDate.of(2022,7,12), LocalDate.of(2022,10,12), 5, 15, true, informatique );
        Articles lampe = new Articles("lampe de chevet", "une lampe de chevet en bois", LocalDate.of(2022,7,12), LocalDate.of(2022,10,12), 6, 10, true, divers );
        Articles souris = new Articles("souris ordinateur", "une souris d'ordinateur de la marque XXX", LocalDate.of(2022,10,12), LocalDate.of(2022,11,12), 10, 20, true, informatique );
        Articles couteauSuisse = new Articles("couteau suisse", "un couteau ustensile", LocalDate.of(2022,7,12), LocalDate.of(2022,7,13), 6, 10, true, divers );

        clavier.setUtilisateurs(antoine);
        souris.setUtilisateurs(antoine);
        lampe.setUtilisateurs(francois);
        couteauSuisse.setUtilisateurs(sheila);


        //UTILISATEURS /////////////////////////////////////////////////////////////////////////////////


        try {
            utilisateurDAO.insert(antoine);
            utilisateurDAO.insert(francois);
            utilisateurDAO.insert(sheila);
        } catch (SQLException e) {
            e.printStackTrace();
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

        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
