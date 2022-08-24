package fr.eni.encheres.bll;

public abstract class CodesResultatBLL {

    // Code Erreur validateUtilisateur
    public static final int REGLE_USER_PSEUDO_ERREUR = 20000;
    public static final int REGLE_USER_PSEUDO_IN_BASE_ERREUR = 20001;
    public static final int REGLE_USER_NOM_ERREUR = 20010;
    public static final int REGLE_USER_PRENOM_ERREUR = 20020;
    public  static final int REGLE_USER_EMAIL_ERREUR = 20030;
    public static final int REGLE_USER_EMAIL_IN_BASE_ERREUR = 20040;
    public  static final int REGLE_USER_RUE_ERREUR = 20050;
    public  static final int REGLE_USER_CODEPOSTAL_ERREUR = 20060;
    public  static final int REGLE_USER_VILLE_ERREUR = 20070;
    public  static final int REGLE_USER_MOTDEPASSE_ERREUR = 20080;

    //Code Erreur validateConnexion

    public static final int REGLE_USER_TEST_MOTDEPASSE_CONNEXION_USER_LOGIN_ERREUR = 20100;
    public static final int REGLE_USER_TEST_MOTDEPASSE_CONNEXION_USER_MOTDEPASSE_ERREUR = 20101;





}
