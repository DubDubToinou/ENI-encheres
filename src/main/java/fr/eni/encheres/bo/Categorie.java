/**
 * @author sheilaJava
 */

package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
    private int noCategorie;
    private String libelle;
    private List<Articles> articles = new ArrayList<>();

    //Constructeurs

    public Categorie() {
    }

    public Categorie(String libelle) {
        this.libelle = libelle;
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

    //articles
    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    //Fonction d'ajout d'un article
    public void addArticle(Articles article)
    {
        this.articles.add(article);
    }
}
