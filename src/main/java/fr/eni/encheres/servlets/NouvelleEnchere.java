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
            encherir(request, response, listeCodesErreur);
        } catch (BusinessException ex) {
            listeCodesErreur = ex.getListeCodesErreur();
        }

        if(listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            this.doGet(request, response);
        } else {
            try {
                preleverCredit(request, response, listeCodesErreur);
            } catch (BusinessException ex) {
                listeCodesErreur = ex.getListeCodesErreur();
            }
        }

        if(listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            this.doGet(request, response);
        } else {
            try {
                updatePrixVenteArticle(request, response, listeCodesErreur);
            } catch (BusinessException ex) {
                listeCodesErreur = ex.getListeCodesErreur();
            }
        }

        if(listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            this.doGet(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("succes","Ench??re ajout??e avec succ??s");
            response.sendRedirect(request.getContextPath()+"/accueil");
        }
    }

    private void encherir(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException, BusinessException {
        Enchere enchere = creerEnchere(request, listeCodesErreur);
        isMeilleurEncherisseur(request, listeCodesErreur);

        EnchereManager enchereManager = new EnchereManager();

        //si la cr??ation d'ench??re et isMeilleurEnchrisseur ont renvoy?? des erreurs, on renvoi vers details article avec les erreurs
        if(listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            this.doGet(request, response);
        } else {
            //selectMeilleurEncherisseur si null 0 ench??res
            Utilisateur encherisseurPrecedent = enchereManager.selectMeilleurEncherisseur(lireParametreArticle(request));
            //si il n'y a eu aucune ench??re sur l'article on ins??re l'ench??re en base
            if(encherisseurPrecedent == null) {
                enchereManager.ajouterEnchere(enchere);
            } else {
                //s'il y a une autre ench??re sur l'article on rend les cr??dit ?? l'ench??risseur pr??c??dent avant d'ajouter notre ench??re
                rendreCredit(encherisseurPrecedent, lireParametrePrixVente(request));
                enchereManager.ajouterEnchere(enchere);
            }
        }
    }

    private Enchere creerEnchere(HttpServletRequest request, List<Integer> listeCodesErreur) {
        Utilisateur utilisateur = lireParametreUtilisateur(request, listeCodesErreur);
        Articles article = lireParametreArticle(request);
        LocalDateTime dateEnchere = lireParametreDate(request);
        Integer montant = lireParametreMontant(request, listeCodesErreur);

        Enchere enchere = new Enchere(utilisateur, article, dateEnchere, montant);

        return enchere;
    }

    private void isMeilleurEncherisseur(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        Utilisateur utilisateur = lireParametreUtilisateur(request, listeCodesErreur);
        Articles article = lireParametreArticle(request);

        EnchereManager enchereManager = new EnchereManager();

        //On v??rifie si l'utilisateur est d??j?? le meilleur encherisseur, si c'est le cas on ajoute une erreur
        boolean isMeilleurEncherisseur = enchereManager.isMeilleurEncherisseur(article.getNoArticle(), utilisateur.getNoUtilisateur());
        if(isMeilleurEncherisseur) {
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_UTILISATEUR_DOUBLE);
        }
    }

    private void preleverCredit (HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws BusinessException, ServletException, IOException {
        HttpSession session = request.getSession();

        //r??cup??rer utilisateur en session et soustraire ?? son cr??dit le montant de l'ench??re
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        utilisateur.setCredit(utilisateur.getCredit() - lireParametreMontant(request, listeCodesErreur));

        if(listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            this.doGet(request, response);
        } else {
            //update l'utilisateur
            UtilisateurManager utilisateurManager = new UtilisateurManager();
            utilisateurManager.updateUserWithoutCheck(utilisateur);

            //l'utilisateur est update en base, on le remet correctement dans la session
            session.setAttribute("utilisateur", utilisateur);
        }
    }

    private void rendreCredit (Utilisateur u, Integer prixVente) throws BusinessException {
        UtilisateurManager utilisateurManager = new UtilisateurManager();

        Utilisateur encherisseurPrecedent = u;
        encherisseurPrecedent.setCredit(encherisseurPrecedent.getCredit() + prixVente);
        utilisateurManager.updateUserWithoutCheck(encherisseurPrecedent);
    }

    private void updatePrixVenteArticle (HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws BusinessException, ServletException, IOException {
        Articles article = lireParametreArticle(request);
        //R??cup??rer l'article au complet
        ArticleManager articleManager = new ArticleManager();
        Articles fullArticle = articleManager.selectByNoArticle(article.getNoArticle());

        //Set nouveau prix de vente
        fullArticle.setPrixVente(lireParametreMontant(request, listeCodesErreur));

        if(listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            this.doGet(request, response);
        } else {
            //Update article en base
            articleManager.updateUnArticle(fullArticle);
        }
    }

    private Utilisateur lireParametreUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
        HttpSession session = request.getSession();
        Utilisateur utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateur");
        return utilisateurConnecte;
    }

    private LocalDateTime lireParametreDate(HttpServletRequest request) {
        LocalDateTime date = LocalDateTime.now();
        return date;
    }

    private Articles lireParametreArticle(HttpServletRequest request) {
        Integer prixVente = Integer.valueOf(request.getParameter("prixVente"));
        Integer noArticle = Integer.valueOf(request.getParameter("noArticle"));
        Articles article = new Articles();
        article.setNoArticle(noArticle);
        article.setPrixVente(prixVente);

        return article;
    }

    private Integer lireParametrePrixVente(HttpServletRequest request) {
        Integer prixVente = Integer.valueOf(request.getParameter("prixVente"));
        return prixVente;
    }
    private int lireParametreMontant(HttpServletRequest request, List<Integer> listeCodesErreur) {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        HttpSession session = request.getSession();

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        Integer prixVente = lireParametrePrixVente(request);
        int montant = Integer.parseInt(request.getParameter("montant"));
        if (montant <= prixVente)
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_MONTANT_ERREUR);
        if (montant > utilisateur.getCredit())
            listeCodesErreur.add(CodesResultatServlets.ENCHERE_CREDIT_INSUFFISANT);

        return montant;
    }
}
