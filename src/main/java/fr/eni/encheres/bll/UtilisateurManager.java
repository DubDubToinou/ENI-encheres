package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

import java.sql.SQLException;

public class UtilisateurManager {

    private UtilisateurDAO utilisateurDAO;

    public UtilisateurManager() {
        this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
    }


    public Utilisateur afficherSonProfil(String pseudo) throws SQLException{
        return this.utilisateurDAO.selectByID(pseudo);
    }

    public Utilisateur afficherUnProfil(String pseudo) throws SQLException{
        return this.utilisateurDAO.selectOwnProfile(pseudo);
    }

}
