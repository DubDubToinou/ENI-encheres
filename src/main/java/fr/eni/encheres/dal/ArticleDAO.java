package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface ArticleDAO extends DAO<Articles> {
    public List<Articles> selectVentesEnCours() throws BusinessException;
    public List<Articles> selectByCategorie(String libelle) throws BusinessException;
    public List<Articles> selectByMotCle(String motCle) throws BusinessException ;
    public List<Articles> selectByMotCleAndCategorie(String motCle, String libelle) throws BusinessException ;
    public List<Articles> selectVentesEnCoursParUtilisateur(Utilisateur utilisateur)throws BusinessException;
    public List<Articles> selectVentesTermineesParUtilisateur(Utilisateur utilisateur) throws BusinessException;
}
