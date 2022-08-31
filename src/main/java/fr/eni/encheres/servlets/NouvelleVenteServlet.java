package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/NouvelleVente")
public class NouvelleVenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        affichageCategories(request);
        request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Integer> listeCodesErreur=new ArrayList<>();


        Articles article = null;
        try {
            article = creerVente(request, listeCodesErreur);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }


        if(listeCodesErreur.size() > 0) {
            affichageCategories(request);
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp").forward(request,response);
        } else {
            response.sendRedirect(request.getContextPath()+"/accueil");
        }
    }

    private Articles creerVente(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        ArticleManager articleManager = new ArticleManager();
        Articles article = null;

        String nom = lireParametreNom(request, listeCodesErreur);
        String description = lireParametreDescription(request, listeCodesErreur);
        LocalDateTime dateDebut = lireParametreDateDebut(request, listeCodesErreur);
        LocalDateTime dateFin = lireParametreDateFin(request, listeCodesErreur);
        int prixInitial = lireParametrePrixInitial(request, listeCodesErreur);
        Utilisateur utilisateur = lireUtilisateur(request, listeCodesErreur);
        Categorie categorie = lireParametreCategorie(request, listeCodesErreur);
        Retrait lieuRetrait = lireParametreRetrait(request, listeCodesErreur);

        if (listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
        } else {
            article = new Articles(nom, description, dateDebut, dateFin, prixInitial, prixInitial, categorie, lieuRetrait, utilisateur);
            try {
                articleManager.ajouterUnArticle(article);
            } catch (BusinessException ex) {
                ex.printStackTrace();
                request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            }
        }
        return article;
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

    private String lireParametreNom(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String nom = request.getParameter("nom_article");
        if (nom == null || nom.trim().isBlank()) {
            listeCodesErreur.add(CodesResultatServlets.NOM_ARTICLE_OBLIGATOIRE);
        }
        return nom;
    }

    private String lireParametreDescription(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String description = request.getParameter("description");
        if (description == null || description.trim().isBlank()) {
            listeCodesErreur.add(CodesResultatServlets.DESCRIPTION_OBLIGATOIRE);
        }
        return description;
    }

    private LocalDateTime lireParametreDateDebut(HttpServletRequest request, List<Integer> listeCodesErreur) {
        LocalDateTime dateDebut = LocalDateTime.parse(request.getParameter("date_debut_encheres"));
        if (dateDebut == null || dateDebut.isBefore(LocalDateTime.now())) {
            listeCodesErreur.add(CodesResultatServlets.DATE_DEBUT_OBLIGATOIRE);
        }
        return dateDebut;
    }

    private LocalDateTime lireParametreDateFin(HttpServletRequest request, List<Integer> listeCodesErreur) {
        LocalDateTime dateFin = LocalDateTime.parse(request.getParameter("date_fin_encheres"));
        if (dateFin == null || dateFin.isBefore(lireParametreDateDebut(request, listeCodesErreur)) || dateFin.isEqual(lireParametreDateDebut(request, listeCodesErreur))) {
            listeCodesErreur.add(CodesResultatServlets.DATE_FIN_OBLIGATOIRE);
        }
        return dateFin;
    }

    private int lireParametrePrixInitial(HttpServletRequest request, List<Integer> listeCodesErreur) {
        int prixInitial = Integer.parseInt(request.getParameter("prix_initial"));
        if (prixInitial <= 0) {
            listeCodesErreur.add(CodesResultatServlets.PRIX_INITIAL_OBLIGATOIRE);
        }
        return prixInitial;
    }

    private Utilisateur lireUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if(utilisateur == null) {
            listeCodesErreur.add(CodesResultatServlets.UTILISATEUR_OBLIGATOIRE);
        }
        return utilisateur;
    }

    private Categorie lireParametreCategorie(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        CategorieManager categorieManager = new CategorieManager();
        String libelleCategorie = request.getParameter("categorie");

        return categorieManager.selectCategorieByLibelle(libelleCategorie);

    }

    private Retrait lireParametreRetrait(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("code_postal");
        String ville = request.getParameter("ville");

        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (rue == null || rue.isBlank()) {
            rue = utilisateur.getRue();
        }

        if(codePostal == null || codePostal.isBlank()) {
            codePostal = utilisateur.getCodePostal();
        }

        if(ville == null || ville.isBlank()) {
            ville = utilisateur.getVille();
        }

        Retrait lieuRetrait = new Retrait(rue, codePostal, ville);
        return lieuRetrait;
    }
}

