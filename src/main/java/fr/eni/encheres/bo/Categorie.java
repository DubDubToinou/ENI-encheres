package fr.eni.encheres.bo;

import java.util.List;

public class Categorie {
    private int noCategorie;
    private String libelle;
    private List<ArticleVendu> articles;

    //Constructeurs

    public Categorie() {
    }

    public Categorie(int noCategorie, String libelle) {
        this.noCategorie = noCategorie;
        this.libelle = libelle;
    }

    //Getters & Setters

    //noCategorie
    public int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    //libelle
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void addArticle(ArticleVendu article)
    {
        this.articles.add(article);
    }
}
