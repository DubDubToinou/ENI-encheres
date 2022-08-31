package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

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

        Integer noArticle = Integer.valueOf(request.getParameter("noArticle"));

        request.getRequestDispatcher("/detailarticle?no_article="+noArticle).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Integer> listeCodesErreur=new ArrayList<>();

        try {
            creerEnchere(request, listeCodesErreur);
        } catch (BusinessException ex) {
            listeCodesErreur = ex.getListeCodesErreur();
        }

        if(listeCodesErreur.size() > 0) {
            this.doGet(request,response);
        } else {
            try {
                preleverCredit(request, listeCodesErreur);

            } catch (BusinessException ex) {
                listeCodesErreur = ex.getListeCodesErreur();
            }
        }
        if(listeCodesErreur.size() > 0) {
            this.doGet(request,response);
        } else {
            try {
                rendreCredit(request, listeCodesErreur);

            } catch (BusinessException ex) {
                listeCodesErreur = ex.getListeCodesErreur();
            }
        }
        if(listeCodesErreur.size() > 0) {
            this.doGet(request,response);
        } else {
            try {
                updatePrixVenteArticle(request, listeCodesErreur);
            } catch (BusinessException ex) {
                listeCodesErreur = ex.getListeCodesErreur();
            }

        }
        response.sendRedirect(request.getContextPath()+"/accueil");
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

    private void preleverCredit (HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur = lireParametreUtilisateur(request, listeCodesErreur);
        int montant = lireParametreMontant(request, listeCodesErreur);
        utilisateur.setCredit(utilisateur.getCredit()-montant);
        utilisateurManager.updateUserWithCheck(utilisateur);
    }

    private void rendreCredit (HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        EnchereManager enchereManager = new EnchereManager();
        ArticleManager articleManager = new ArticleManager();
        Articles article = lireParametreArticle(request, listeCodesErreur);
        Utilisateur utilisateur = null;

        Articles fullArticle = articleManager.selectByNoArticle(article.getNoArticle());
        List<Enchere> listeEnchere = enchereManager.listeEnchereEnCoursParArticle(fullArticle);

        for (Enchere e : listeEnchere) {
            if (e.getMontant_enchere().equals(fullArticle.getPrixVente())){
                utilisateur = utilisateurManager.recupererProfilParPseudo(e.getUtilisateur().getPseudo());
                utilisateur.setCredit(utilisateur.getCredit()+e.getMontant_enchere());
                utilisateurManager.updateUserWithCheck(utilisateur);
            }
        }
    }

    private void updatePrixVenteArticle (HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        ArticleManager articleManager = new ArticleManager();
        Articles article = lireParametreArticle(request, listeCodesErreur);
        Articles fullArticle = articleManager.selectByNoArticle(article.getNoArticle());
        int montant = lireParametreMontant(request, listeCodesErreur);
        fullArticle.setPrixVente(montant);

        try {

            articleManager.updateUnArticle(fullArticle);
        }catch ( BusinessException ex){
            ex.printStackTrace();
        }

    }

    private Utilisateur lireParametreUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        HttpSession session = request.getSession();
        Utilisateur utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateur");
        EnchereManager enchereManager = new EnchereManager();
        ArticleManager articleManager = new ArticleManager();
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Articles article = lireParametreArticle(request, listeCodesErreur);
        Utilisateur utilisateur = null;

        Articles fullArticle = articleManager.selectByNoArticle(article.getNoArticle());
        List<Enchere> listeEnchere = enchereManager.listeEnchereEnCoursParArticle(fullArticle);


            for (Enchere e : listeEnchere) {
                if (e.getMontant_enchere().equals(fullArticle.getPrixVente())) {
                    utilisateur = utilisateurManager.recupererProfilParPseudo(e.getUtilisateur().getPseudo());
                }
            }


            if (utilisateur.getPseudo().equals(utilisateurConnecte.getPseudo())) {
                listeCodesErreur.add(CodesResultatServlets.ENCHERE_UTILISATEUR_DOUBLE);
            }


        if(utilisateurConnecte == null) {
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_UTILISATEUR_OBLIGATOIRE);
        }
        return utilisateurConnecte;
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

    private int lireParametreMontant(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        HttpSession session = request.getSession();

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        utilisateur = utilisateurManager.recupererProfilParPseudo(utilisateur.getPseudo());


        Articles article = lireParametreArticle(request, listeCodesErreur);
        int montant = Integer.parseInt(request.getParameter("montant"));
        if (montant <= article.getPrixVente()) {
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_MONTANT_OBLIGATOIRE);
        } else if (montant > utilisateur.getCredit()) {
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_CREDIT_INSUFFISANT);
       }
        return montant;
    }
}
