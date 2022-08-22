package fr.eni.encheres.bo;

/**
 * @author Antoine
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticlesVendu {

    private Integer noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private float miseAPrix;
    private float prixVente;
    private boolean etatVente = false;
    private Categorie categorieArticle;
    private Retrait lieuRetrait;
    private List<Enchere> encheres = new ArrayList<>();
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    //Constructeur vide
    public ArticlesVendu() {
    }

    public ArticlesVendu(Integer noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, float miseAPrix, float prixVente, boolean etatVente, Categorie categorieArticle) {
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

    //Constructeur sans ID.
    public ArticlesVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres,
                         float miseAPrix, float prixVente, boolean etatVente, Categorie categorieArticle) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.categorieArticle = categorieArticle;
    }

    //Constrcteur sans catégorie de Catégorie.
    public ArticlesVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, float miseAPrix, float prixVente, boolean etatVente) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
    }

    public Integer getNoArticle() {
        return noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public float getMiseAPrix() {
        return miseAPrix;
    }

    public float getPrixVente() {
        return prixVente;
    }

    public boolean isEtatVente() {
        return etatVente;
    }

    public Categorie getCategorieArticle() {
        return categorieArticle;
    }

    public void addEnchere(Enchere enchere)
    {
        this.encheres.add(enchere);
    }

    public void addUtilisateur(Utilisateur utilisateur)
    {
        this.utilisateurs.add(utilisateur);
    }

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
