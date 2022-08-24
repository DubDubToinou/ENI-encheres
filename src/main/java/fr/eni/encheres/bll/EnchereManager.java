package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EncheresDAO;

import java.sql.SQLException;
import java.util.List;

public class EnchereManager {

    private final EncheresDAO enchereDAO;

    public EnchereManager() {
        this.enchereDAO = DAOFactory.getEncheresDAO();
    }

    public List<Enchere> listeEnchereEnCoursParArticle(Articles article) throws BusinessException {
        return this.enchereDAO.selectByArticle(article);
    }

    public List<Enchere> listeArticleEnchereEnCoursParUtilisateur(Utilisateur utilisateur) throws BusinessException {
        return this.enchereDAO.selectEnCoursByUtilisateurs(utilisateur);
    }

    public List<Enchere> listeArticleEnchereGagneParUtilisateur(Utilisateur utilisateur) throws BusinessException{
        return this.enchereDAO.selectEncheresGagneByUtilisateur(utilisateur);
    }



}
