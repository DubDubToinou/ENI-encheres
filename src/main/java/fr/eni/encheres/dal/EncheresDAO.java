package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface EncheresDAO {

    List<Enchere> selectByArticle(Articles article) throws BusinessException;

    void insert(Enchere elementEnchere) throws BusinessException;

    List<Articles> selectEncheresEnCoursByUtilisateurs(Utilisateur utilisateur) throws BusinessException;

    List<Articles> selectEncheresGagneByUtilisateur(Utilisateur utilisateur) throws BusinessException;
    public Enchere selectEnchereGagnanteByArticle(int noArticle) throws BusinessException;

}
