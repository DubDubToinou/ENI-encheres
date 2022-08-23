package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {

    private UtilisateurDAO utilisateurDAO;

    public UtilisateurManager() {
        this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
    }

}
