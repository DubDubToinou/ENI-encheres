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

        if (utilisateur.getPseudo() == null || utilisateur.getPseudo().isBlank() && !utilisateur.getPseudo().matches("^[a-zA-Z0-9]*$") ){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_PSEUDO_ERREUR);
        }

        if(utilisateurDAO.pseudoIsInBase(utilisateur)){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_PSEUDO_IN_BASE_ERREUR);
        }

        if(utilisateur.getNom() == null || utilisateur.getNom().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_NOM_ERREUR);
        }

        if (utilisateur.getPrenom() == null || utilisateur.getPrenom().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_PRENOM_ERREUR);
        }

        if (utilisateur.getEmail() == null || utilisateur.getEmail().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_EMAIL_ERREUR);
        }

        if (utilisateurDAO.emailIsInBase(utilisateur)){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_EMAIL_IN_BASE_ERREUR);
        }

        if (utilisateur.getRue() == null || utilisateur.getRue().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_RUE_ERREUR);
        }

        if (utilisateur.getCodePostal() == null || utilisateur.getCodePostal().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_CODEPOSTAL_ERREUR);
        }

        if (utilisateur.getVille() == null || utilisateur.getVille().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_VILLE_ERREUR);
        }

        if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().isBlank() || utilisateur.getMotDePasse().length()<8){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_MOTDEPASSE_ERREUR);
        }

    }

}
