package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface EncheresDAO {

    List<Enchere> selectByNumArticle(int idArticle) throws SQLException;

    void insert(Enchere elementEnchere) throws SQLException;

    List<Enchere> selectEnCoursByNoUtilisateurs(Utilisateur utilisateur) throws SQLException;

}
