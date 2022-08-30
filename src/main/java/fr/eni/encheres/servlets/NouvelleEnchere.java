package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;
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

@WebServlet(value = "/NouvelleEnchere")
public class NouvelleEnchere extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/nouvelleEnchere.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Integer> listeCodesErreur=new ArrayList<>();

        try {
            creerEnchere(request, listeCodesErreur);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }

        if(listeCodesErreur.size() > 0) {
            request.getRequestDispatcher("/nouvelleEnchere.jsp").forward(request,response);
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private void creerEnchere(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        EnchereManager enchereManager = new EnchereManager();
        Enchere enchere = null;

        Utilisateur utilisateur = lireParametreUtilisateur(request, listeCodesErreur);
        Articles article = lireParametreArticle(request, listeCodesErreur);
        LocalDateTime date = lireParametreDate(request, listeCodesErreur);
        int montant = lireParametreMontant(request, listeCodesErreur);

        if (listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
        } else {
            enchere = new Enchere(utilisateur, article, date, montant);
            try {
                enchereManager.ajouterEnchere(enchere);
            } catch (BusinessException ex) {
                ex.printStackTrace();
                request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            }
        }
    }

    private Utilisateur lireParametreUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if(utilisateur == null) {
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_UTILISATEUR_OBLIGATOIRE);
        }
        return utilisateur;
    }

    private Articles lireParametreArticle(HttpServletRequest request, List<Integer> listeCodesErreur) {
        Articles article = new Articles();
        article.setNoArticle(Integer.valueOf(request.getParameter("noArticle")));
        article.setPrixVente(Integer.valueOf(request.getParameter("prixVente")));
        if (article == null) {
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_ARTICLE_OBLIGATOIRE);
        }
        return article;
    }

    private LocalDateTime lireParametreDate(HttpServletRequest request, List<Integer> listeCodesErreur) {
        LocalDateTime date = LocalDateTime.now();
        if(date == null) {
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_DATE_OBLIGATOIRE);
        }
        return date;
    }

    private int lireParametreMontant(HttpServletRequest request, List<Integer> listeCodesErreur) {
        Articles article = lireParametreArticle(request, listeCodesErreur);
        int montant = Integer.parseInt(request.getParameter("enchere"));
        if (montant <= article.getPrixVente()) {
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_MONTANT_OBLIGATOIRE);
        }
        return montant;
    }
}
