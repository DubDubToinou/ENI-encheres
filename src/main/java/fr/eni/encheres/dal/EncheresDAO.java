package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface EncheresDAO {

    List<Enchere> selectByNoArticle(int noArticle) throws SQLException;

    void insert(Enchere elementEnchere) throws SQLException;

    List<Enchere> selectEnCoursByNoUtilisateurs(Utilisateur utilisateur) throws SQLException;

    List<Enchere> selectEncheresGagneByNoUtilisateur(Utilisateur utilisateur) throws SQLException;

}
