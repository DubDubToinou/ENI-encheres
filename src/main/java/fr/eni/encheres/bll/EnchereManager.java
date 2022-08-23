package fr.eni.encheres.bll;

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

    public List<Enchere> listeEnchereEnCoursParNoArticle(int noArticle) throws SQLException {
        return this.enchereDAO.selectByNoArticle(noArticle);
    }

    public List<Enchere> listeArticleEnchereEnCoursParUtilisateur(Utilisateur utilisateur) throws SQLException {
        return this.enchereDAO.selectEnCoursByNoUtilisateurs(utilisateur);
    }

    public List<Enchere> listeArticleEnchereGagneParUtilisateur(Utilisateur utilisateur) throws SQLException{
        return this.enchereDAO.selectEncheresGagneByNoUtilisateur(utilisateur);
    }


}
