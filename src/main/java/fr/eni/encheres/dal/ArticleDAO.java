package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface ArticleDAO extends DAO<Articles> {
    public List<Articles> selectAll();
    public List<Articles> selectByCategorie(Categorie categorie);
    public List<Articles> selectByMotCle(String motCle);
    public List<Articles> selectVentesEnCours(Utilisateur utilisateur);
    public List<Articles> selectVentesTerminees(Utilisateur utilisateur);
}
