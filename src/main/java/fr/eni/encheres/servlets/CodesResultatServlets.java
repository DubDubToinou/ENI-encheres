package fr.eni.encheres.servlets;

public abstract class CodesResultatServlets {

    //Inscription
    public static final int PRENOM_OBLIGATOIRE=30000;
    public static final int NOM_OBLIGATOIRE=30001;
    public static final int TELEPHONE_OBLIGATOIRE=30002;
    public static final int RUE_OBLIGATOIRE=30003;
    public static final int VILLE_OBLIGATOIRE=30004;
    public static final int MDP_OBLIGATOIRE=30005;
    public static final int CONCORDANCE_MDP = 30006;
    public static final int PSEUDO_OBLIGATOIRE = 30007;
    public static final int EMAIL_OBLIGATOIRE = 30008;
    public static final int CP_OBLIGATOIRE = 30009;
    public static final int LOGIN_OBLIGATOIRE = 30010;

    //Nouvelle vente
    public static final int NOM_ARTICLE_OBLIGATOIRE=40000;
    public static final int DESCRIPTION_OBLIGATOIRE=40001;
    public static final int DATE_DEBUT_OBLIGATOIRE=40002;
    public static final int DATE_FIN_OBLIGATOIRE=40003;
    public static final int PRIX_INITIAL_OBLIGATOIRE=40004;
    public static final int UTILISATEUR_OBLIGATOIRE=40005;

    //Nouvelle enchère
    public static final int ENCHERE_UTILISATEUR_OBLIGATOIRE=50001;
    public static final int ENCHERE_ARTICLE_OBLIGATOIRE=50002;
    public static final int ENCHERE_DATE_OBLIGATOIRE=50003;
    public static final int ENCHERE_MONTANT_ERREUR=50004;
    public static final int ENCHERE_CREDIT_INSUFFISANT=50005;

    public static final int ENCHERE_UTILISATEUR_DOUBLE=50006;

}
