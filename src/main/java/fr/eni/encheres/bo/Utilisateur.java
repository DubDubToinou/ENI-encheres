package fr.eni.encheres.bo;

public class Utilisateur {
    private int NoUtilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private int telephone;
    private String rue;
    private int codePostal;
    private String ville;
    private String motDePasse;
    private int credit = 0;
    private boolean administrateur = false;

    //Constructeur vide
    public Utilisateur() {
    }

    //Constructeur
    public Utilisateur(String pseudo, String nom, String prenom, String email, int telephone, String rue, int codePostal, String ville, String motDePasse) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.motDePasse = motDePasse;
    }

    //Getters & Setters

    //noUtilisateur
    public int getNoUtilisateur() {
        return this.NoUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.NoUtilisateur = noUtilisateur;
    }


    //pseudo
    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    //nom
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    //prenom
    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    //email
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //telephone
    public int getTelephone() {
        return this.telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }


    //rue
    public String getRue() {
        return this.rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }


    //cp
    public int getCodePostal() {
        return this.codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }


    //ville
    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    //adresse
    public String getAdresse() {
        StringBuilder bld = new StringBuilder();
        bld.append(this.rue + " ");
        bld.append(this.codePostal+ " ");
        bld.append(this.ville);
        return bld.toString();
    }


    //password
    public String getMotDePasse() {
        return this.motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }


    //credit
    public int getCredit() {
        return this.credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }


    //administrateur
    public boolean isAdministrateur() {
        return this.administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }


}
