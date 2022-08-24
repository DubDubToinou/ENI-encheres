package fr.eni.encheres.bo;

/**
 * @author sheilaJava
 */

import java.time.LocalDate;

public class Enchere {
    private Utilisateur utilisateur;
    private Articles articleVendu;
    private LocalDate dateEnchere;
    private Integer montant_enchere;

    //Constructeur vide
    public Enchere() {
    }

    //Constructeur
    public Enchere(Utilisateur utilisateur, Articles articleVendu, LocalDate dateEnchere, Integer montant_enchere) {
        this.utilisateur = utilisateur;
        this.articleVendu = articleVendu;
        this.dateEnchere = dateEnchere;
        this.montant_enchere = montant_enchere;
    }

    //Constructeur avec Datenchere et Montant Enchere.
    public Enchere(LocalDate dateEnchere, Integer montant_enchere) {
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
        utilisateur.addEnchere(this);
    }


    //Article Vendu
    public Articles getArticleVendu() {
        return articleVendu;
    }

    public void setArticleVendu(Articles articleVendu) {
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
