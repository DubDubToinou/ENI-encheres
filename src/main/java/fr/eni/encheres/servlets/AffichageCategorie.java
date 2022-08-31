package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Categorie;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AffichageCategorie", value = "/AffichageCategorie")
public class AffichageCategorie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategorieManager categorieManager = new CategorieManager();
        List<Categorie> listeCategorie;

            try {
                listeCategorie = categorieManager.selectAllCategories();
                request.setAttribute("listeCategorie", listeCategorie);
            } catch (BusinessException ex) {
                ex.printStackTrace();
            }

            request.getRequestDispatcher("/WEB-INF/categorie.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            this.doGet(request,response);
    }
}
