package fr.eni.encheres.servlets;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ModifierCategorie", value = "/ModifierCategorie")
public class ModifierCategorie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String libelle = request.getParameter("libelle");
        CategorieManager categorieManager = new CategorieManager();

        try {
            Categorie categorieAAfficher = categorieManager.selectCategorieByLibelle(libelle);
            System.out.println(categorieAAfficher);
            request.setAttribute("categorieAAfficher", categorieAAfficher);

        } catch (BusinessException ex) {
            ex.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/modifierCategorie.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Integer> listeCodesErreur=new ArrayList<>();

       UpdateCategorie(request, listeCodesErreur);

        if(listeCodesErreur.size() > 0) {
            request.getRequestDispatcher("/WEB-INF/categorie.jsp").forward(request,response);
        } else {

            request.getRequestDispatcher("/AffichageCategorie").forward(request, response);
        }

    }

    private Categorie UpdateCategorie(HttpServletRequest request, List<Integer> listeCodesErreur) {

        CategorieManager categorieManager = new CategorieManager();
        Integer noCategorie = Integer.valueOf(request.getParameter("noCategorie"));
        String libelle = request.getParameter("newLibelle");

            Categorie categorieModifier = new Categorie(noCategorie, libelle);

            try {
                System.out.println(categorieModifier);
                    categorieManager.updateCategorie(categorieModifier);

                } catch (BusinessException e) {
                throw new RuntimeException(e);
            }

        return categorieModifier;
    }


}


