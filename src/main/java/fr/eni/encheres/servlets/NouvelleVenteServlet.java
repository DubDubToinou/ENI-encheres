package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@WebServlet(value = "/NouvelleVente")
public class NouvelleVenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    private void creerVente(HttpServletRequest request, List<Integer> listeCodesErreur) {
        ArticleManager articleManager = new ArticleManager();

        String nom = lireParametreNom(request, listeCodesErreur);
        String description = lireParametreDescription(request, listeCodesErreur);
        LocalDate dateDebut = lireParametreDateDebut(request, listeCodesErreur);
        LocalDate telephone = lireParametreDateFin(request, listeCodesErreur);
        int prixInitial = lireParametrePrixInitial(request, listeCodesErreur);
        Utilisateur utilisateur = lireUtilisateur(request, listeCodesErreur);
        Categorie categorie = lireParametreCategorie(request, listeCodesErreur);
        Retrait lieuRetrait = lireParametreRetrait(request, listeCodesErreur);

        if (listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
        } else {
            Articles article = new Articles(nom, description, dateDebut, telephone, prixInitial, categorie, lieuRetrait, utilisateur);

            try {

                articleManager.ajouterUnArticle(article);

            } catch (BusinessException ex) {
                ex.printStackTrace();
                request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            }
        }
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

    private LocalDate lireParametreDateDebut(HttpServletRequest request, List<Integer> listeCodesErreur) {
        LocalDate dateDebut = LocalDate.parse(request.getParameter("date_debut_encheres"));
        if (dateDebut == null || dateDebut.isBefore(LocalDate.now())) {
            listeCodesErreur.add(CodesResultatServlets.DATE_DEBUT_OBLIGATOIRE);
        }
        return dateDebut;
    }

    private LocalDate lireParametreDateFin(HttpServletRequest request, List<Integer> listeCodesErreur) {
        LocalDate dateFin = LocalDate.parse(request.getParameter("date_debut_encheres"));
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
        //TODO Utilisateur connecté
    }

    private Categorie lireParametreCategorie(HttpServletRequest request, List<Integer> listeCodesErreur) {
        //TODO Catégorie
    }

    private Retrait lireParametreRetrait(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("code_postal");
        String ville = request.getParameter("ville");

        if (rue == null || rue.isBlank() || codePostal == null || codePostal.isBlank() || ville == null || ville.isBlank()) {
            //TODO Adresse Utilisateur connecté
        }

        Retrait lieuRetrait = new Retrait(rue, codePostal, ville);
        return lieuRetrait;
    }
}
