package fr.eni.encheres.servlets;

import com.microsoft.aad.msal4j.SystemBrowserOptions;
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

@WebServlet(name = "SupprimerProfil", value = "/SupprimerProfil")
public class SupprimerProfil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/supprimerProfil.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Integer> listeCodesErreur=new ArrayList<>();

        try {

            removeUser(request, listeCodesErreur);

            request.getRequestDispatcher("/index.jsp").forward(request,response);

        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }


        if(listeCodesErreur.size() > 0) {
            request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request,response);

        } else {
            HttpSession session = request.getSession();
            session.removeAttribute("connecte");
            session.removeAttribute("utilisateur");
            request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        }

    }

    private void removeUser(HttpServletRequest request, List<Integer> listeCodesErreur) throws BusinessException {
        UtilisateurManager utilisateurManager = new UtilisateurManager();
        HttpSession session = request.getSession();
        Utilisateur utilisateurSession = (Utilisateur) session.getAttribute("utilisateur");

        String motDePasse = request.getParameter("mot_de_passe");
        String confirm_mot_de_passe = request.getParameter("confirm_mot_de_passe");


        if (motDePasse.equals(confirm_mot_de_passe)) {
            if(confirm_mot_de_passe.equals(utilisateurSession.getMotDePasse())){
                utilisateurManager.removeUser(utilisateurSession);
            }
        }

    }

}
