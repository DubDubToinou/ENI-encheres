package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private Integer NoUtilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String rue;
    private String codePostal;
    private String ville;
    private String motDePasse;
    private int credit = 0;
    private int administrateur = 0;
    private List<Articles> listeArticlesVendus = new ArrayList<>();
    private List<Enchere> enchere = new ArrayList<>();



    //Constructeur vide
    public Utilisateur() {
    }

    //Constructeur
    public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse) {
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

    //Constructeur avec enchere
    public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse, List<Enchere> enchere) {
        this(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse);
        this.enchere = enchere;
    }

    //Getters & Setters

    //noUtilisateur
    public Integer getNoUtilisateur() {
        return this.NoUtilisateur;
    }

    public void setNoUtilisateur(Integer noUtilisateur) {
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
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
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
    public String getCodePostal() {
        return this.codePostal;
    }

    public void setCodePostal(String codePostal) {
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
        bld.append(this.codePostal + " ");
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
    public int getAdministrateur() {
        return this.administrateur;
    }

    public void setAdministrateur(int administrateur) {
        this.administrateur = administrateur;
    }

    //articles vendus
    public List<ArticleVendu> getListeArticlesVendus() {
        return listeArticlesVendus;
    }

    public void setListeArticlesVendus(List<ArticleVendu> listeArticlesVendus) {
        this.listeArticlesVendus = listeArticlesVendus;
    }


    //enchere
    public List<Enchere> getEnchere() {
        return this.enchere;
    }

    public void setEnchere(List<Enchere> enchere) {
        this.enchere = enchere;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "NoUtilisateur=" + NoUtilisateur +
                ", pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", credit=" + credit +
                ", administrateur=" + administrateur +
                ", enchere=" + enchere +
                '}';
    }
}
