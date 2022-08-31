package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(value="/detailarticle")
public class DetailArticle extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer noArticle = Integer.valueOf(request.getParameter("no_article"));

        ArticleManager articleManager = new ArticleManager();
        EnchereManager enchereManager = new EnchereManager();
        Articles article = new Articles();

        //Récupération de l'article par le noArticle
        try {
           article = articleManager.selectByNoArticle(noArticle);
        } catch (BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            request.getRequestDispatcher("/accueil").forward(request, response);
        }


        request.setAttribute("article", article);

        //date du jour
        LocalDateTime now = LocalDateTime.now();


        //La vente a t'elle débuté ?
        boolean venteNonDebutee = false; //oui
        if(article.getDateDebutEncheres().isAfter(now)) {
            venteNonDebutee = true; //non
        }

        request.setAttribute("venteNonDebutee", venteNonDebutee);

        //Vérification si enchere est en cours
        boolean enCours;

        if(article.getDateFinEncheres().isAfter(now) && article.getDateDebutEncheres().isBefore(now)) {
            enCours = true;
        } else {
            enCours=false;
            //Récupérer meilleur enchérisseur
            try {
                Enchere enchere = enchereManager.enchereGagnanteParArticle(noArticle);
                request.setAttribute("enchereGagnante", enchere);

                if(enchere != null) {
                    request.setAttribute("utilisateurGagnant", enchere.getUtilisateur().getPseudo());
                }
            } catch (BusinessException ex) {
                ex.printStackTrace();
            }
        }

        request.setAttribute("enCours", enCours);



        request.getRequestDispatcher("/WEB-INF/detailArticle.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
