package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;

import java.util.List;

public class ArticleManager {

    private final ArticleDAO articleDAO;


    public ArticleManager() {
        this.articleDAO = DAOFactory.getArticleDAO();
    }



    public List<Articles> listeArticleNonConnecte(){
        return this.articleDAO.selectVentesEnCours();
    }

    public List<Articles> listeArticleParCategorie(Categorie categorie){
        return this.articleDAO.selectByCategorie(categorie);
    }

    public List<Articles> listeArticleByMotCle(String motCle){
        return this.articleDAO.selectByMotCle(motCle);
    }

    public List<Articles> listeVenteEnCoursParUtilisateur(Utilisateur utilisateur){
        return this.articleDAO.selectVentesEnCoursParUtilisateur(utilisateur);
    }

    public List<Articles> listeVenteTermineesParUtilisateur(Utilisateur utilisateur){
        return this.articleDAO.selectVentesTermineesParUtilisateur(utilisateur);
    }




}
