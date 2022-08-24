package fr.eni.encheres.bo;

/**
 * @author Antoine
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Articles {

    private Integer noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private Integer miseAPrix;
    private Integer prixVente;
    private boolean etatVente = false;
    private Categorie categorieArticle;
    private Retrait lieuRetrait;
    private List<Enchere> encheres = new ArrayList<>();
    private Utilisateur utilisateur;

    //Constructeur vide
    public Articles() {
    }

    public Articles(Integer noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, Integer miseAPrix, Integer prixVente, boolean etatVente, Categorie categorieArticle) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.categorieArticle = categorieArticle;
    }

    public Articles(Integer noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, Integer miseAPrix, boolean etatVente, Categorie categorieArticle) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.etatVente = etatVente;
        this.categorieArticle = categorieArticle;
    }

    //Constructeur sans ID.
    public Articles(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres,
                    Integer miseAPrix, Integer prixVente, boolean etatVente, Categorie categorieArticle) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.categorieArticle = categorieArticle;
    }

    //Constructeur sans catégorie de Catégorie.
    public Articles(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, Integer miseAPrix, Integer prixVente, boolean etatVente) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
    }

    public Articles(Integer noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, Integer miseAPrix, Integer prixVente) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
    }

    //Getter et Setter noArticle
    public Integer getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(Integer noArticle) {
        this.noArticle = noArticle;
    }

    //Getter et Setter nomArticle
    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    //Getter et Setter Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Getter et Setter Date Debut Encheres
    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    //Getter et Setter Date Fin Encheres
    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    //Getter et Setter Mise A Prix
    public Integer getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(Integer miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    //Getter et Setter Prix de Vente
    public Integer getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Integer prixVente) {
        this.prixVente = prixVente;
    }

    //Getter et Setter Bool Etat Vente
    public boolean isEtatVente() {
        return etatVente;
    }

    public void setEtatVente(boolean etatVente) {
        this.etatVente = etatVente;
    }

    //Getter et Setter Catégorie Article
    public Categorie getCategorieArticle() {
        return categorieArticle;
    }

    public void setCategorieArticle(Categorie categorieArticle) {
        this.categorieArticle = categorieArticle;
        categorieArticle.addArticle(this);
    }

    //Getter et Setter Lieu de retrait
    public Retrait getLieuRetrait() {
        return lieuRetrait;
    }

    public void setLieuRetrait(Retrait lieuRetrait) {
        this.lieuRetrait = lieuRetrait;
        lieuRetrait.setArticle(this);
    }

    //Getter et Setter List Encheres
    public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }


    //Getter et Setter Utilisateurs.
    public Utilisateur getUtilisateurs() {
        return utilisateur;
    }

    public void setUtilisateurs(Utilisateur utilisateurs) {
        this.utilisateur = utilisateurs;

        utilisateurs.addArticleVendu(this);
    }

    //Methode Ajouter une enchères a l'article
    public void addEnchere(Enchere enchere)
    {
        this.encheres.add(enchere);
    }


    //ToString
    @Override
    public String toString() {
        return "ArticlesVendu{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", miseAPrix=" + miseAPrix +
                ", prixVente=" + prixVente +
                ", etatVente=" + etatVente +
                ", categorieArticle=" + categorieArticle +
                '}';
    }
}
