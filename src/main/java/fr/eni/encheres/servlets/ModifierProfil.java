package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value="/ModifierProfil")
public class ModifierProfil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Integer> listeCodesErreur=new ArrayList<>();

        Utilisateur utilisateur = ModifierUtilisateur(request, listeCodesErreur);

        if(listeCodesErreur.size() > 0) {
            request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request,response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", utilisateur);
            boolean connecte = true;
            session.setAttribute("connecte", connecte);
            session.setAttribute("succes","Profil modifié avec succès");
            response.sendRedirect(request.getContextPath()+"/accueil");
        }


    }

    private Utilisateur ModifierUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        HttpSession session = request.getSession();
        Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("utilisateur");

        Utilisateur utilisateur = null;

        Integer noUtilisateur = lireParametreNoUtilisateur(request);
        String pseudo = lireParametrePseudo(request, listeCodesErreur);
        String prenom = lireParametrePrenom(request, listeCodesErreur);
        String nom = lireParametreNom(request, listeCodesErreur);
        String telephone = lireParametreTelephone(request, listeCodesErreur);
        String email = lireParametreEmail(request, listeCodesErreur);
        String rue = lireParametreRue(request, listeCodesErreur);
        String ville = lireParametreVille(request, listeCodesErreur);
        String code_postal = lireParametreCP(request, listeCodesErreur);

        if(listeCodesErreur.size()>0)
        {
            request.setAttribute("listeCodesErreur",listeCodesErreur);
        } else
        {
            utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, code_postal, ville);
            utilisateur.setNoUtilisateur(noUtilisateur);
            utilisateur.setCredit(utilisateurSession.getCredit());

            try {
                if(!utilisateur.getPseudo().equals(utilisateurSession.getPseudo())){
                    utilisateurManager.updateUserWithNewPseudo(utilisateur);
                }else if(!utilisateur.getEmail().equals(utilisateurSession.getEmail())){
                    utilisateurManager.updateUserWithNewEmail(utilisateur);
                }else{
                    utilisateurManager.updateUserWithCheck(utilisateur);
                }

            } catch (BusinessException ex) {
                ex.printStackTrace();
                request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            }
        }
        return utilisateur;
    }

    private String verifierMotDePasse(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String motDePasse = lireParametreMotDePasse(request, listeCodesErreur);
        String motDePasseOld = lireParametreMotDePasseOld(request, listeCodesErreur);
        String confirmation = lireParametreConfirmation(request, listeCodesErreur);

        if (!motDePasseOld.trim().equals(confirmation.trim())){

            listeCodesErreur.add(CodesResultatServlets.CONCORDANCE_MDP);

        }else if (!motDePasse.trim().equals(confirmation.trim())){
            listeCodesErreur.add(CodesResultatServlets.CONCORDANCE_MDP);
        }

        return motDePasse;
    }

    private String lireParametrePseudo(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String pseudo;
        pseudo = request.getParameter("pseudo");
        if(pseudo==null || pseudo.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.PSEUDO_OBLIGATOIRE);
        }
        return pseudo;
    }

    private Integer lireParametreNoUtilisateur(HttpServletRequest request){
        Integer noUtilisateur;
        noUtilisateur = Integer.valueOf(request.getParameter("noUtilisateur"));

        return noUtilisateur;
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

    private String lireParametreEmail(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String email;
        email = request.getParameter("email");
        if(email==null || email.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.EMAIL_OBLIGATOIRE);
        }
        return email;
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

    private String lireParametreCP(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String cp;
        cp = request.getParameter("code_postal");
        if(cp==null || cp.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.CP_OBLIGATOIRE);
        }
        return cp;
    }

    private String lireParametreMotDePasseOld(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String motDePasse;
        motDePasse = request.getParameter("mot_de_passeold");
        if (motDePasse == null || motDePasse.trim().equals("")) {
            listeCodesErreur.add(CodesResultatServlets.MDP_OBLIGATOIRE);
        }
        return motDePasse;

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
