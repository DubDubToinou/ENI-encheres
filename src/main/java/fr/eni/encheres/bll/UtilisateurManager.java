package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

import java.sql.SQLException;

public class UtilisateurManager {

    private UtilisateurDAO utilisateurDAO;

    public UtilisateurManager() {
        this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
    }


    public void removeUser(Utilisateur utilisateur) throws SQLException {

        utilisateurDAO.delete(utilisateur);

    }

    public Utilisateur afficherSonProfil(String pseudo) throws SQLException{
        return this.utilisateurDAO.selectByPseudo(pseudo);
    }

    public Utilisateur afficherUnProfil(String pseudo) throws SQLException{
        return this.utilisateurDAO.selectOwnProfile(pseudo);
    }

    public void validateUser(Utilisateur utilisateur, BusinessException businessException){

        if (utilisateur.getPseudo() == null || utilisateur.getPseudo().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_PSEUDO_ERREUR);
        }

        if(utilisateur.getNom() == null || utilisateur.getNom().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL);
        }

    }

}
