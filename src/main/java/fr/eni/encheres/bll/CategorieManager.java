package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class CategorieManager {

    private CategorieDAO categorieDAO;

    //Constructeur Categorie Manager
    public CategorieManager() {
        this.categorieDAO = DAOFactory.getCategorieDAO();
    }

    //Methode ajout d'une catégorie
    public void ajouterCategorie(Categorie categorie) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateCategorie(categorie, businessException);

        if (!businessException.hasErreurs()){
            Categorie newCategorie = new Categorie();
            this.categorieDAO.insert(categorie);
        }
    }

    //methode update categorie
    public void updateCategorie(Categorie categorie) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validateCategorie(categorie, businessException);

        if(!businessException.hasErreurs()){
            Categorie updateCategorie = new Categorie();
            this.categorieDAO.update(categorie);
        }
    }

    //methode supprimer une catégorie
    public void removeCategorie(Categorie categorie) throws BusinessException{
        categorieDAO.delete(categorie);
    }

    //Méthode sélectionner une catégorie par son libelle
    public Categorie selectCategorieByLibelle(String libelle) throws BusinessException {
        return this.categorieDAO.selectByLibelle(libelle);
    }

    //Validation de la donnée libelle pour la création de la catégorie.
    public void validateCategorie(Categorie categorie, BusinessException businessException) throws BusinessException{
        if (categorie.getLibelle() == null || categorie.getLibelle().isBlank()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_CATEGORIE_LIBELLE_ERREUR);
        }
    }

}
