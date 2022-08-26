package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Categorie;

public interface CategorieDAO extends DAO<Categorie>{
    public Categorie selectByLibelle(String libelle) throws BusinessException;
}
