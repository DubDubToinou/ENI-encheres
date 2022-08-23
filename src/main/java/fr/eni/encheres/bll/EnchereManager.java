package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EncheresDAO;

public class EnchereManager {

    private EncheresDAO enchereDAO;

    public EnchereManager() {
        this.enchereDAO = DAOFactory.getEncheresDAO();
    }
}
