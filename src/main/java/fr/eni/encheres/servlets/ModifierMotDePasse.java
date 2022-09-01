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

@WebServlet(name = "ModifierMotDePasse", value = "/ModifierMotDePasse")
public class ModifierMotDePasse extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/modifierMotDePasse.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listeCodesErreur=new ArrayList<>();

        try {

            Utilisateur utilisateur = updateMotDePasse(request, listeCodesErreur);

            if(listeCodesErreur.size() > 0) {
                request.getRequestDispatcher("/WEB-INF/modifierMotDePasse.jsp").forward(request,response);
            } else {
                HttpSession session = request.getSession();
                Utilisateur utilisateurUpdate = (Utilisateur) session.getAttribute("utilisateur");
                utilisateurUpdate.setMotDePasse(utilisateur.getMotDePasse());
                session.removeAttribute("utilisateur");
                session.setAttribute("utilisateur", utilisateurUpdate);
                boolean connecte = true;
                session.setAttribute("connecte", connecte);
                session.setAttribute("succes", "Mot de Passe modifié avec succes");
                response.sendRedirect(request.getContextPath()+"/MonProfil?pseudo="+utilisateurUpdate.getPseudo());

            }

        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    private Utilisateur updateMotDePasse(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur = null;

        String pseudo = request.getParameter("pseudo");
        String motDePasseVerif = verifierMotDePasse(request, listeCodesErreur);
        Integer noUtilisateur = Integer.valueOf(request.getParameter("noUtilisateur"));

        if(listeCodesErreur.size()>0)
        {
            request.setAttribute("listeCodesErreur",listeCodesErreur);
        } else
        {

            utilisateur = new Utilisateur(pseudo, motDePasseVerif);
            utilisateur.setNoUtilisateur(noUtilisateur);
            try {

                utilisateurManager.updateMotDePasseUser(utilisateur);

            } catch (BusinessException ex) {
                ex.printStackTrace();
                request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            }
        }

        return utilisateur;

    }

    private String verifierMotDePasse(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String motDePasse = lireParametreMotDePasse(request, listeCodesErreur);
        String confirmation = lireParametreConfirmation(request, listeCodesErreur);

        if (!motDePasse.trim().equals(confirmation.trim())){
            listeCodesErreur.add(CodesResultatServlets.CONCORDANCE_MDP);
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
        confirmation = request.getParameter("confirm_mot_de_passe");
        if(confirmation==null || confirmation.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.MDP_OBLIGATOIRE);
        }
        return confirmation;
    }
}
