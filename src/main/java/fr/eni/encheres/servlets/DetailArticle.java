package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.Articles;

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
        Articles article = new Articles();

        try {
           article = articleManager.selectByNoArticle(noArticle);
        } catch (BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            request.getRequestDispatcher("/accueil").forward(request, response);
        }


        request.setAttribute("article", article);

        //Vérification si enchere est en cours
        LocalDateTime now = LocalDateTime.now();
        boolean enCours;

        if(article.getDateFinEncheres().isAfter(now)) {
            enCours = true;
        } else {
            enCours=false;
        }

        request.setAttribute("enCours", enCours);

        //Récupérer meilleur enchérisseur

        request.getRequestDispatcher("/WEB-INF/detailArticle.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
