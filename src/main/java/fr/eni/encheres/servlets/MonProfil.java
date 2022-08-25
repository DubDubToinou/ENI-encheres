package fr.eni.encheres.servlets;

import fr.eni.encheres.bll.UtilisateurManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MonProfil", value = "/ServletMonProfil")
public class MonProfil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UtilisateurManager utilisateurManager = new UtilisateurManager();
        HttpSession session = request.getSession();
        request.getRequestDispatcher("/profile.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
