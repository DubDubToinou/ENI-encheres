package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns= {
        "/connexion",
        "/deconnexion"
})
public class Connexion extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getServletPath().equals("/connexion")) {
            request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
        } else if (request.getServletPath().equals("/deconnexion")) {
            HttpSession session = request.getSession();
            session.removeAttribute("connecte");
            session.removeAttribute("utilisateur");
            response.sendRedirect(request.getContextPath()+"/accueil");

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Integer> listeCodesErreur=new ArrayList<>();

        Utilisateur utilisateur = null;

            utilisateur = connecterUtilisateur(request, listeCodesErreur);

        if(listeCodesErreur.size()>0) {
            request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request,response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", utilisateur);
            boolean connecte = true;
            session.setAttribute("connecte", connecte);
            session.setAttribute("succes","Bienvenue " + utilisateur.getPseudo());
            response.sendRedirect(request.getContextPath()+"/accueil");
        }
    }

    private Utilisateur connecterUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
        Utilisateur utilisateur = null;
        UtilisateurManager utilisateurManager = new UtilisateurManager();

        String login = lireParametreLogin(request, listeCodesErreur);
        String motDePasse = lireParametreMotDePasse(request, listeCodesErreur);

        if(listeCodesErreur.size()>0)
        {
            request.setAttribute("listeCodesErreur",listeCodesErreur);
        } else
        {

            Utilisateur ConnexionATester = new Utilisateur(login, motDePasse);
            ConnexionATester.setEmail(login);
            try {
                utilisateur = utilisateurManager.RecupererProfil(ConnexionATester);
            } catch (BusinessException ex) {
                ex.printStackTrace();
                request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());

                for(Integer erreur : ex.getListeCodesErreur()) {
                    listeCodesErreur.add(erreur);
                }

            }

        }
        return utilisateur;
    }

    private String lireParametreLogin(HttpServletRequest request, List<Integer> listeCodesErreur) {
        String login;
        login = request.getParameter("login");
        if(login==null || login.trim().equals(""))
        {
            listeCodesErreur.add(CodesResultatServlets.LOGIN_OBLIGATOIRE);
        }
        return login;
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

}