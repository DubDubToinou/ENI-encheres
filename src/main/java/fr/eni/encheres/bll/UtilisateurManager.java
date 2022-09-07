package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {

    private final UtilisateurDAO utilisateurDAO;

    // Constructeur du manager
    public UtilisateurManager() {
        this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
    }

    //Methode ajouter un Utilisateur
    public void ajouterUser(Utilisateur utilisateur) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateUser(utilisateur, businessException);


        if(!businessException.hasErreurs()){
                this.utilisateurDAO.insert(utilisateur);
        } else {
            throw businessException;
        }
    }

    //Methode mise à jour des utilisateurs
    public void updateUserWithCheck(Utilisateur utilisateur) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateUserForUpdate(utilisateur,businessException);
        if (!businessException.hasErreurs()) {
            this.utilisateurDAO.update(utilisateur);
        }
        else {
            throw businessException;
        }
    }

    public void updateUserWithoutCheck(Utilisateur utilisateur) throws BusinessException{
        this.utilisateurDAO.update(utilisateur);
    }

    public void updateUserWithNewPseudo(Utilisateur utilisateur) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateUserForUpdateNewPseudo(utilisateur, businessException);
        if (!businessException.hasErreurs()){
            this.utilisateurDAO.update(utilisateur);
        } else {
            throw businessException;
        }
    }

    public void updateUserWithNewEmail(Utilisateur utilisateur) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateUserForUpdateNewEmail(utilisateur,businessException);
        if (!businessException.hasErreurs()){
            this.utilisateurDAO.update(utilisateur);
        } else {
            throw businessException;
        }
    }

    public void updateMotDePasseUser(Utilisateur utilisateur) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateMotDePasseForUpdateMotDePasse(utilisateur, businessException);
        if (!businessException.hasErreurs()){
            this.utilisateurDAO.updateMotDePasse(utilisateur);
        } else {
            throw businessException;
        }
    }

    public void updateCreditUser(Utilisateur utilisateur) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateCreditForUser(utilisateur, businessException);
        if(!businessException.hasErreurs()){
            this.utilisateurDAO.updateCredit(utilisateur);
        } else {
            throw businessException;
        }
    }

    //Pouvoir se supprimer
    public void removeUser(Utilisateur utilisateur) throws BusinessException {
        utilisateurDAO.delete(utilisateur);
    }

    //afficher son profil
    public Utilisateur RecupererProfil(Utilisateur utilisateur) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateConnexion(utilisateur, businessException);
        Utilisateur utilisateurRetourne = null;

        if (!businessException.hasErreurs()) {
            utilisateurRetourne =  this.utilisateurDAO.selectOwnProfile(utilisateur);
        } else {
            throw businessException;
        }
        return utilisateurRetourne;
    }

    //Afficher un profil en cliquant sur le pseudo d'un utilisateur.
    public Utilisateur afficherUnProfil(String pseudo) throws BusinessException{
        BusinessException businessException = new BusinessException();
        Utilisateur utilisateurAAfficher;
        if(!businessException.hasErreurs()) {
            utilisateurAAfficher = utilisateurDAO.selectByPseudo(pseudo);
        }else{
            throw businessException;
        }
        return utilisateurAAfficher;
    }

    public Utilisateur recupererProfilParPseudo(String pseudo) throws BusinessException {
        return this.utilisateurDAO.selectProfileByPseudo(pseudo);
    }

    public void validateCreditForUser(Utilisateur utilisateur, BusinessException businessException) throws BusinessException{
        if (utilisateur.getCredit() > 15000 || utilisateur.getCredit() < 0){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_CREDIT_ERREUR);
        }
    }

    //Methode qui valide les données avec insert / update
    public void validateUser(Utilisateur utilisateur, BusinessException businessException) throws BusinessException{

        if (utilisateur.getPseudo() == null || utilisateur.getPseudo().isBlank() || !utilisateur.getPseudo().matches("^[a-zA-Z0-9]*$")){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_PSEUDO_ERREUR);
        }

        if(utilisateurDAO.pseudoIsInBase(utilisateur)) {
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

    public void validateMotDePasseForUpdateMotDePasse(Utilisateur utilisateur, BusinessException businessException) throws BusinessException{

        if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().isBlank() || utilisateur.getMotDePasse().length()<8){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_MOTDEPASSE_ERREUR);
        }
    }

    public void validateUserForUpdate(Utilisateur utilisateur, BusinessException businessException) throws BusinessException{

        if (utilisateur.getPseudo() == null || utilisateur.getPseudo().isBlank() || !utilisateur.getPseudo().matches("^[a-zA-Z0-9]*$")){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_PSEUDO_ERREUR);
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

        if (utilisateur.getRue() == null || utilisateur.getRue().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_RUE_ERREUR);
        }

        if (utilisateur.getCodePostal() == null || utilisateur.getCodePostal().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_CODEPOSTAL_ERREUR);
        }

        if (utilisateur.getVille() == null || utilisateur.getVille().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_VILLE_ERREUR);
        }
    }

    public void validateUserForUpdateNewPseudo(Utilisateur utilisateur, BusinessException businessException) throws BusinessException{

        if (utilisateur.getPseudo() == null || utilisateur.getPseudo().isBlank() || !utilisateur.getPseudo().matches("^[a-zA-Z0-9]*$")){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_PSEUDO_ERREUR);
        }

        if(utilisateurDAO.pseudoIsInBase(utilisateur)) {
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

        if (utilisateur.getRue() == null || utilisateur.getRue().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_RUE_ERREUR);
        }

        if (utilisateur.getCodePostal() == null || utilisateur.getCodePostal().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_CODEPOSTAL_ERREUR);
        }

        if (utilisateur.getVille() == null || utilisateur.getVille().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_VILLE_ERREUR);
        }
    }

    public void validateUserForUpdateNewEmail(Utilisateur utilisateur, BusinessException businessException) throws BusinessException{

        if (utilisateur.getPseudo() == null || utilisateur.getPseudo().isBlank() || !utilisateur.getPseudo().matches("^[a-zA-Z0-9]*$")){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_PSEUDO_ERREUR);
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
    }

    // Methode afin de valider la connexion.
    // On utilise la methode selectMotDePasse du DAO Utilisateur.
    // Si il n'y a pas de mot de passe retourné, alors il y a un probleme de login (pseudo ou mail)
    // sinon si il y a un mot de passe retourné par le selectMotDePasse alors on le compare à celui saisi par l'utilisateur.
    public void validateConnexion(Utilisateur utilisateur, BusinessException businessException) throws BusinessException {

        if(!utilisateurDAO.pseudoIsInBase(utilisateur) && !utilisateurDAO.emailIsInBase(utilisateur)) {

            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_TEST_MOTDEPASSE_CONNEXION_USER_LOGIN_ERREUR);

        } else if (!utilisateur.getMotDePasse().trim().equals(utilisateurDAO.selectMotDePasse(utilisateur.getPseudo()))) {

            businessException.ajouterErreur(CodesResultatBLL.REGLE_USER_TEST_MOTDEPASSE_CONNEXION_USER_MOTDEPASSE_ERREUR);
        }
    }
}
