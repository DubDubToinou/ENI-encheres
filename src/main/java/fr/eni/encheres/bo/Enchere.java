package fr.eni.encheres.bo;


import java.time.LocalDate;

public class Enchere {
    private Utilisateur utilisateur;
    private ArticleVendu articleVendu;
    private LocalDate dateEnchere;
    private Integer montant_enchere;

    //Constructeur vide
    public Enchere() {
    }

    //Constructeur
    public Enchere(Utilisateur utilisateur, ArticleVendu articleVendu, LocalDate dateEnchere, Integer montant_enchere) {
        this.utilisateur = utilisateur;
        this.articleVendu = articleVendu;
        this.dateEnchere = dateEnchere;
        this.montant_enchere = montant_enchere;
    }

    //Getters & Setters

    //Utilisateur
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


    //Article Vendu
    public ArticleVendu getArticleVendu() {
        return articleVendu;
    }

    public void setArticleVendu(ArticleVendu articleVendu) {
        this.articleVendu = articleVendu;
    }


    //Date enchere
    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }


    //Montant enchere
    public Integer getMontant_enchere() {
        return montant_enchere;
    }

    public void setMontant_enchere(Integer montant_enchere) {
        this.montant_enchere = montant_enchere;
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "utilisateur=" + utilisateur +
                ", articleVendu=" + articleVendu +
                ", dateEnchere=" + dateEnchere +
                ", montant_enchere=" + montant_enchere +
                '}';
    }
}
