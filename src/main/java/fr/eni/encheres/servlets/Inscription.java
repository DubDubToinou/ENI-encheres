package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value="/Inscription")
public class Inscription extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


            request.getRequestDispatcher("/inscription.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Integer> listeCodesErreur=new ArrayList<>();

        creerUtilisateur(request, listeCodesErreur);
        if(listeCodesErreur.size() > 0) {
            request.getRequestDispatcher("/inscription.jsp").forward(request,response);
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
                    //TODO SESSION

        }


    }

    private void creerUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
        UtilisateurManager utilisateurManager = new UtilisateurManager();


        String pseudo = request.getParameter("pseudo");
        String prenom = lireParametrePrenom(request, listeCodesErreur);
        String nom = lireParametreNom(request, listeCodesErreur);
        String telephone = lireParametreTelephone(request, listeCodesErreur);
        String email = request.getParameter("email");
        String rue = lireParametreRue(request, listeCodesErreur);
        String ville = lireParametreVille(request, listeCodesErreur);
        String code_postal = request.getParameter("code_postal");
        String motDePasse = verifierMotDePasse(request, listeCodesErreur);

        if(listeCodesErreur.size()>0)
        {
            request.setAttribute("listeCodesErreur",listeCodesErreur);
        } else
        {
            Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, motDePasse);

            try {

                utilisateurManager.ajouterUser(utilisateur);

            } catch (BusinessException ex) {
                ex.printStackTrace();
                request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            }
        }
    }
    private String verifierMotDePasse(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String motDePasse = lireParametreMotDePasse(request, listeCodesErreur);
        String confirmation = lireParametreConfirmation(request, listeCodesErreur);

        if (!motDePasse.trim().equals(confirmation.trim())){
            listeCodesErreur.add(CodesResultatServlets.CONCORDANCE_MDP);
        }

        return motDePasse;
    }

    private String lireParametrePrenom(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String prenom;
        prenom = request.getParameter("prenom");
        if(prenom==null || prenom.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.PRENOM_OBLIGATOIRE);
        }
        return prenom;
    }

    private String lireParametreNom(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String nom;
        nom = request.getParameter("nom");
        if(nom==null || nom.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.NOM_OBLIGATOIRE);
        }
        return nom;
    }

    private String lireParametreTelephone(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String telephone;
        telephone = request.getParameter("telephone");
        if(telephone==null || telephone.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.TELEPHONE_OBLIGATOIRE);
        }
        return telephone;
    }

    private String lireParametreRue(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String rue;
        rue = request.getParameter("rue");
        if(rue==null || rue.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.RUE_OBLIGATOIRE);
        }
        return rue;
    }

    private String lireParametreVille(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String ville;
        ville = request.getParameter("ville");
        if(ville==null || ville.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.VILLE_OBLIGATOIRE);
        }
        return ville;
    }

    private String lireParametreMotDePasse(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String motDePasse;
        motDePasse = request.getParameter("mot_de_passe");
        if(motDePasse==null || motDePasse.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.MDP_OBLIGATOIRE);
        }
        return motDePasse;
    }

    private String lireParametreConfirmation(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String confirmation;
        confirmation = request.getParameter("confirmation");
        if(confirmation==null || confirmation.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.MDP_OBLIGATOIRE);
        }
        return confirmation;
    }

}
