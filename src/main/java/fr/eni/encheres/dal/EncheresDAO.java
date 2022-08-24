package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface EncheresDAO {

    List<Enchere> selectByArticle(Articles article) throws SQLException;

    void insert(Enchere elementEnchere) throws SQLException;

    List<Enchere> selectEnCoursByUtilisateurs(Utilisateur utilisateur) throws SQLException;

    List<Enchere> selectEncheresGagneByUtilisateur(Utilisateur utilisateur) throws SQLException;

}
