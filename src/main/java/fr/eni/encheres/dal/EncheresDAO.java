package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface EncheresDAO {

    List<Enchere> selectByNumArticle(int idArticle);

    void insert(Enchere elementEnchere);

    List<Enchere> selectEnCoursByNoUtilisateurs(Utilisateur utilisateur);

}
