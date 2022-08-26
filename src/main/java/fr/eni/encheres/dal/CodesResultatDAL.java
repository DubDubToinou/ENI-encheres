package fr.eni.encheres.dal;

public abstract class CodesResultatDAL {

    //Général
    public static final int INSERT_OBJET_NULL=10000;
    public static final int INSERT_OBJET_ECHEC=10001;

    //Articles

    public static final int UPDATE_ARTICLE_ECHEC=10002;
    public static final int DELETE_ARTICLE_ECHEC=10003;
    public static final int SELECT_VENTES_ENCOURS_ECHEC=10004;
    public static final int SELECT_BY_CATEGORIE_ECHEC=10005;
    public static final int SELECT_BY_MOTCLE_ECHEC=10007;
    public static final int SELECT_VENTES_PAR_UTILISATEUR_ECHEC=10008;
    public static final int SELECT_VENTES_TERMINEES_PAR_UTILISATEUR_ECHEC=10010;
    public static final int ARTICLE_NULL_ECHEC = 10011;


    //Catégories
    public static final int CATEGORIE_NULL_ECHEC=10006;
    public static final int UPDATE_CATEGORIE_ECHEC=10012;
    public static final int DELETE_CATEGORIE_ECHEC=10013;
    public static final int SELECT_CATEGORIE_BY_LIBELLE_ECHEC=10050;

    //Encheres
    public static final int SELECT_ENCHERES_PAR_ARTICLE_ECHEC=10014;
    public static final int SELECT_ENCHERES_ENCOURS_PAR_UTILISATEUR_ECHEC=10015;
    public static final int SELECT_ENCHERES_GAGNEES_PAR_UTILISATEUR_ECHEC=10016;

    //Utilisateurs
    public static final int UTILISATEUR_INEXISTANT=10009;
    public static final int SELECT_UTILISATEUR_PAR_PSEUDO_ECHEC=10017;
    public static final int SELECT_OWN_PROFILE_ECHEC=10018;
    public static final int UPDATE_UTILISATEUR_ECHEC=10019;
    public static final int DELETE_UTILISATEUR_ECHEC=10020;
    public static final int PSEUDO_IN_BASE_ECHEC=10021;
    public static final int EMAIL_IN_BASE_ECHEC=10022;
    public static final int SELECT_PASSWORD_ECHEC=10023;
}
