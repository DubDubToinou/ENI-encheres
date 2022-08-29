package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value="/accueil")
public class Accueil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //récupération et affichage catégories
        affichageCatégories(request);

        //récupération et affichage articles
        affichageArticlesEnVente(request);

        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //récupération et affichage catégories
        affichageCatégories(request);

        String categorie = lireParametreCategorie(request);
        String motCle = lireParametreMotCle(request);

        if((categorie==null || categorie.isBlank()) && motCle.isBlank()) {
            affichageArticlesEnVente(request);
        } else if (motCle.isBlank()) {
            affichageArticlesParCatégorie(request, categorie);
        } else if (categorie==null || categorie.isBlank()) {
            affichageArticlesParMotCle(request, motCle);
        } else {
            affichageArticlesParMotCleEtCatégorie(request, motCle, categorie);
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }

    private void affichageCatégories(HttpServletRequest request) {
        List<Categorie> categories = new ArrayList<>();
        CategorieManager categorieManager = new CategorieManager();
        try {
            categories = categorieManager.selectAllCategories();

        } catch (BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("categories", categories);
    }

    private void affichageArticlesEnVente(HttpServletRequest request) {
        List<Articles> articles = new ArrayList<>();
        ArticleManager articleManager = new ArticleManager();
        try {
            articles = articleManager.listeArticleEnCours();
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }

    private void affichageArticlesParCatégorie(HttpServletRequest request, String libelle) {
        List<Articles> articles = new ArrayList<>();
        ArticleManager articleManager = new ArticleManager();
        try {
            articles = articleManager.listeArticleParCategorie(libelle);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }

    private void affichageArticlesParMotCle(HttpServletRequest request, String motCle) {
        List<Articles> articles = new ArrayList<>();
        ArticleManager articleManager = new ArticleManager();
        try {
            articles = articleManager.listeArticleByMotCle(motCle);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }

    private void affichageArticlesParMotCleEtCatégorie(HttpServletRequest request, String motCle, String categorie) {
        List<Articles> articles = new ArrayList<>();
        ArticleManager articleManager = new ArticleManager();
        try {
            articles = articleManager.listeArticleByMotCleAndCategorie(motCle, categorie);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }
    private String lireParametreCategorie(HttpServletRequest request) {
        String libelle = request.getParameter("categorie");
        return libelle;
    }

    private String lireParametreMotCle(HttpServletRequest request) {
        String motCle = request.getParameter("mot_cle");
        return motCle;
    }

}
