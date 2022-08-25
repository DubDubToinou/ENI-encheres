package fr.eni.encheres.bll;

public abstract class CodesResultatBLL {
    // #### CODE ERREUR UTILISATEUR MANAGER ####
    //  #### Code Erreur validateUtilisateur ####
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

    // #### Code Erreur validateConnexion ####
    public static final int REGLE_USER_TEST_MOTDEPASSE_CONNEXION_USER_LOGIN_ERREUR = 20100;
    public static final int REGLE_USER_TEST_MOTDEPASSE_CONNEXION_USER_MOTDEPASSE_ERREUR = 20101;


    //#### CODE ERREUR ARTICLE MANAGER ####
    // #### CODE ERREUR validateArticle ####

    public static final int REGLE_ARTICLE_NOM_ERREUR = 20200;
    public static final int REGLE_ARTICLE_DESCRIPTION_ERREUR = 20201;
    public static final int REGLE_ARTICLE_DATEDEBUTENCHERE_ERREUR = 20202;

    public static final int REGLE_ARTICLE_DATEFINENCHERE_ERREUR = 20203;
    public static final int REGLE_ARTICLE_MISEAPRIX_ERREUR = 20204;

    public static final int REGLE_ARTICLE_PRIXVENTE_ERREUR = 20205;
    public static final int REGLE_ARTICLE_NOUTILISATEUR_ERREUR = 20206;
    public static final int REGLE_ARTICLE_NOCATEGORIE_ERREUR = 20207;


    // #### CODE ERREUR validateRetraitArticle ####

    public static final int REGLE_RETRAIT_ARTICLE_NOARTICLE_ERREUR = 20251;
    public static final int REGLE_RETRAIT_ARTICLE_RUE_ERREUR = 20252;
    public static final int REGLE_RETRAIT_ARTICLE_CODEPOSTAL_ERREUR = 20253;
    public static final int REGLE_RETRAIT_ARTICLE_VILLE_ERREUR = 20254;

    // #### CODE ERREUR validate Categorie ####

    public static final int REGLE_CATEGORIE_LIBELLE_ERREUR = 20300;

    // #### CODE ERREUR validate Enchere ####

    public static final int REGLE_ENCHERE_UTILISATEUR_ERREUR = 20600;
    public static final int REGLE_ENCHERE_ARTICLE_ERREUR = 20601;
    public static final int REGLE_ENCHERE_DATE_ERREUR = 20602;
    public static final int REGLE_ENCHERE_MONTANT_ERREUR = 20603;

}
