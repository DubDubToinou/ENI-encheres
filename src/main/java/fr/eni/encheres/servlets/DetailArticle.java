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

        try {
            Articles article = articleManager.selectByNoArticle(noArticle);
            request.setAttribute("article", article);
        } catch (BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            request.getRequestDispatcher("/accueil").forward(request, response);
        }

        request.getRequestDispatcher("/WEB-INF/detailArticle.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
