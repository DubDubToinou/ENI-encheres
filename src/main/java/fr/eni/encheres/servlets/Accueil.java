package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value="/accueil")
public class Accueil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //récupération et affichage catégories
        affichageCategories(request);

        //récupération et affichage articles
        affichageArticlesEnVente(request);

        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //récupération et affichage catégories
        affichageCategories(request);

        String formulaire = request.getParameter("form");

        if(formulaire == null) {
            this.doGet(request, response);
        }

        if(formulaire.equals("form1")) {

            String categorie = lireParametreCategorie(request);
            String motCle = lireParametreMotCle(request);

            if ((categorie == null || categorie.isBlank()) && motCle.isBlank()) {
                affichageArticlesEnVente(request);
            } else if (motCle.isBlank()) {
                affichageArticlesParCatégorie(request, categorie);
            } else if (categorie == null || categorie.isBlank()) {
                affichageArticlesParMotCle(request, motCle);
            } else {
                affichageArticlesParMotCleEtCatégorie(request, motCle, categorie);
            }
        } else if(formulaire.equals("form2")) {

            HttpSession session = request.getSession();
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            String check = request.getParameter("check");

            switch (check) {
                case "encheres_ouvertes" :
                    affichageArticlesEnVente(request);
                    break;
                case "mes_encheres_en_cours" :
                    affichageEncheresEnCours(request,utilisateur);
                    break;
                case "mes_encheres_remportees" :
                    affichageEncheresGagnees(request, utilisateur);
                    break;
                case "mes_ventes_en_cours" :
                    affichageVentesEnCours(request, utilisateur);
                    break;
                case "ventes_non_debutees" :
                    affichageVentesNonDebutees(request, utilisateur);
                    break;
                case "ventes_terminees" :
                    affichageVentesTerminees(request, utilisateur);
                    break;
            }

        }

        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

    }

    private void affichageCategories(HttpServletRequest request) {
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
            setEtatVente(articles);
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
            setEtatVente(articles);
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
            setEtatVente(articles);
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
            setEtatVente(articles);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }

    private void affichageEncheresEnCours(HttpServletRequest request, Utilisateur utilisateur) {
        List<Articles> articles = new ArrayList<>();
        EnchereManager enchereManager = new EnchereManager();
        try {
            articles = enchereManager.listeArticleEnchereEnCoursParUtilisateur(utilisateur);
            setEtatVente(articles);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }

    private void affichageEncheresGagnees(HttpServletRequest request, Utilisateur utilisateur) {
        List<Articles> articles = new ArrayList<>();
        EnchereManager enchereManager = new EnchereManager();
        try {
            articles = enchereManager.listeArticleEnchereGagneParUtilisateur(utilisateur);
            setEtatVente(articles);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }

    private void affichageVentesEnCours(HttpServletRequest request, Utilisateur utilisateur) {
        List<Articles> articles = new ArrayList<>();
        ArticleManager articleManager = new ArticleManager();
        try {
            articles = articleManager.listeVenteEnCoursParUtilisateur(utilisateur);
            setEtatVente(articles);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }

    private void affichageVentesNonDebutees(HttpServletRequest request, Utilisateur utilisateur) {
        List<Articles> articles = new ArrayList<>();
        ArticleManager articleManager = new ArticleManager();
        try {
            articles = articleManager.listeVenteNonDebuteesParUtilisateur(utilisateur);
            setEtatVente(articles);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }
    private void affichageVentesTerminees(HttpServletRequest request, Utilisateur utilisateur) {
        List<Articles> articles = new ArrayList<>();
        ArticleManager articleManager = new ArticleManager();
        try {
            articles = articleManager.listeVenteTermineesParUtilisateur(utilisateur);
            setEtatVente(articles);
        } catch(BusinessException ex) {
            ex.printStackTrace();
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
        }

        request.setAttribute("listeArticlesEnCours", articles);
    }

    private void setEtatVente(List<Articles> articles) {
        LocalDateTime now = LocalDateTime.now();


        for(Articles a : articles) {
            if(a.getDateDebutEncheres().isBefore(now) && a.getDateFinEncheres().isAfter(now)) {
                a.setEtatVente(true);
            }
        }

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
