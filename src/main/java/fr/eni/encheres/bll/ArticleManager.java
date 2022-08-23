package fr.eni.encheres.bll;

import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class ArticleManager {

    private ArticleDAO articleDAO;

    public ArticleManager() {
        this.articleDAO = DAOFactory.getArticleDAO();
    }
}
