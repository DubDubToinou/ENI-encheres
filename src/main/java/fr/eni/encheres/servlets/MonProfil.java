package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MonProfil", value = "/MonProfil")
public class MonProfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pseudo = request.getParameter("pseudo");

        UtilisateurManager utilisateurManager = new UtilisateurManager();

        try{
            System.out.println(pseudo);
            Utilisateur utilisateur = utilisateurManager.afficherUnProfil(pseudo);
            System.out.println(utilisateur);
        }catch (BusinessException ex){
            ex.printStackTrace();
        }


        request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
