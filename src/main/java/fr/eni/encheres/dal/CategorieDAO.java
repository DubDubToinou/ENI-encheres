package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import java.sql.SQLException;

public interface CategorieDAO {

    public void insert(Categorie categorie) throws SQLException;

    public void update(Categorie categorie) throws SQLException;

    public void delete(Categorie categorie) throws SQLException;


}
