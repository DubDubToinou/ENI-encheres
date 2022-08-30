package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleManager {

    private final ArticleDAO articleDAO;

    public ArticleManager() {
        this.articleDAO = DAOFactory.getArticleDAO();
    }

    //Methode Ajout d'un article en vente

    public void ajouterUnArticle(Articles article) throws BusinessException {
        BusinessException businessException = new BusinessException();

        this.validateArticle(article, businessException);
        this.validateRetraitArticle(article, businessException);


        if(!businessException.hasErreurs()){
            this.articleDAO.insert(article);
        } else {
            throw businessException;
        }
    }

    //methode Update d'un Article
    public void updateUnArticle(Articles article) throws BusinessException {
        BusinessException businessException = new BusinessException();
        this.validateRetraitArticle(article, businessException);
        this.validateRetraitArticle(article, businessException);

        if(!businessException.hasErreurs()){
            this.articleDAO.update(article);
        }
    }

    //Suppression d'un article
    public void removeArticle(Articles article) throws BusinessException{
        this.articleDAO.delete(article);
    }

    public Articles selectByNoArticle(int noArticle) throws BusinessException {
        return this.articleDAO.selectByNoArticle(noArticle);
    }
    //Affichage liste article en mode non connecté
    public List<Articles> listeArticleEnCours() throws BusinessException{
        return this.articleDAO.selectVentesEnCours();
    }

    //Affichage list article par catégorie
    public List<Articles> listeArticleParCategorie(String libelle) throws BusinessException{
        return this.articleDAO.selectByCategorie(libelle);
    }

    //Affichage liste article par mot clé.
    public List<Articles> listeArticleByMotCle(String motCle) throws BusinessException{
        return this.articleDAO.selectByMotCle(motCle);
    }

    //Affichage liste article par mot clé et catégorie
    public List<Articles> listeArticleByMotCleAndCategorie(String motCle, String libelle) throws BusinessException{
        return this.articleDAO.selectByMotCleAndCategorie(motCle, libelle);
    }

    //Affichage liste vente en cours par utilisateur
    public List<Articles> listeVenteEnCoursParUtilisateur(Utilisateur utilisateur) throws BusinessException {
        return this.articleDAO.selectVentesEnCoursParUtilisateur(utilisateur);
    }

    //Affichage liste vente non débutées
    public List<Articles> listeVenteNonDebuteesParUtilisateur(Utilisateur utilisateur) throws BusinessException {
        return this.articleDAO.selectVentesNonDebuteesParUtilisateur(utilisateur);
    }

    // Affichage liste vente terminée par utilisateur.
    public List<Articles> listeVenteTermineesParUtilisateur(Utilisateur utilisateur) throws BusinessException{
        return this.articleDAO.selectVentesTermineesParUtilisateur(utilisateur);
    }

    //validation des données :
    public void validateArticle(Articles article, BusinessException businessException) throws BusinessException{

        if (article.getNomArticle() == null || article.getNomArticle().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_NOM_ERREUR);
        }

        if (article.getDescription() == null || article.getDescription().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DESCRIPTION_ERREUR);
        }

       if (article.getDateDebutEncheres() == null || article.getDateDebutEncheres().isBefore(LocalDateTime.now())){
           businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATEDEBUTENCHERE_ERREUR);
       }

       if (article.getDateFinEncheres() == null ||
               article.getDateFinEncheres().isBefore(article.getDateDebutEncheres()) ||
               article.getDateFinEncheres().isBefore(LocalDateTime.now())){
           businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATEFINENCHERE_ERREUR);
       }

        if (article.getMiseAPrix() == null || article.getMiseAPrix() <= 0 ){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_MISEAPRIX_ERREUR);
        }

       if(article.getPrixVente() == null){
           businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_PRIXVENTE_ERREUR);
       }

        if (article.getUtilisateurs().getNoUtilisateur() == null){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_NOUTILISATEUR_ERREUR);
        }

        if (article.getCategorieArticle().getNoCategorie() == null){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_NOCATEGORIE_ERREUR);
        }
    }

    //Validation des données pour le retrait article.
    public void validateRetraitArticle(Articles article, BusinessException businessException) throws BusinessException{

        if (article.getNoArticle() == null){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_RETRAIT_ARTICLE_NOARTICLE_ERREUR);
        }

        if (article.getLieuRetrait().getRue() == null || article.getLieuRetrait().getRue().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_RETRAIT_ARTICLE_RUE_ERREUR);
        }

        if (article.getLieuRetrait().getCodePostal() == null || article.getLieuRetrait().getCodePostal().isBlank() || article.getLieuRetrait().getCodePostal().length()>5){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_RETRAIT_ARTICLE_CODEPOSTAL_ERREUR);
        }

        if (article.getLieuRetrait().getVille() == null || article.getLieuRetrait().getVille().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_RETRAIT_ARTICLE_VILLE_ERREUR);
        }
    }
}
