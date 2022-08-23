package fr.eni.encheres.bll;

import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class CategorieManager {

    private CategorieDAO categorieDAO;

    public CategorieManager() {
        this.categorieDAO = DAOFactory.getCategorieDAO();
    }
}
