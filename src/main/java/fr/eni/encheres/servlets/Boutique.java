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

@WebServlet(name = "Boutique", value = "/Boutique")
public class Boutique extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/boutique.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listeCodesErreur=new ArrayList<>();

        try {

            Utilisateur utilisateur = updateCredit(request, listeCodesErreur);

            if(listeCodesErreur.size() > 0) {
                request.getRequestDispatcher("/WEB-INF/modifierMotDePasse.jsp").forward(request,response);
            } else {
                HttpSession session = request.getSession();
                Utilisateur utilisateurUpdate = (Utilisateur) session.getAttribute("utilisateur");
                utilisateurUpdate.setCredit(utilisateur.getCredit());
                session.removeAttribute("utilisateur");
                session.setAttribute("utilisateur", utilisateurUpdate);
                boolean connecte = true;
                session.setAttribute("connecte", connecte);
                request.setAttribute("succes", "Crédits ajoutés avec succes");
                request.getRequestDispatcher("/accueil").forward(request,response);
               // response.sendRedirect(request.getContextPath()+"/accueil");
            }

        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    private Utilisateur updateCredit(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur = null;

        String pseudo = request.getParameter("pseudo");
        Integer credit = Integer.valueOf(request.getParameter("newCredit"));
        Integer noUtilisateur = Integer.valueOf(request.getParameter("noUtilisateur"));

        if(listeCodesErreur.size()>0)
        {
            request.setAttribute("listeCodesErreur",listeCodesErreur);
        } else
        {
            HttpSession session = request.getSession();
            Utilisateur utilisateurUpdate = (Utilisateur) session.getAttribute("utilisateur");

            utilisateur = new Utilisateur(pseudo,credit);
            utilisateur.setCredit(credit + utilisateurUpdate.getCredit());
            utilisateur.setNoUtilisateur(noUtilisateur);
            try {

                utilisateurManager.updateCreditUser(utilisateur);

            } catch (BusinessException ex) {
                ex.printStackTrace();
                request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            }
        }

        return utilisateur;

    }
}
