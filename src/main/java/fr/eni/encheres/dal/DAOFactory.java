package fr.eni.encheres.dal;

import fr.eni.encheres.dal.jdbc.ArticleDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.EncheresDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

public abstract class DAOFactory {

    public static UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurDAOJdbcImpl();
    }
    public static ArticleDAO getArticleDAO() {
        return new ArticleDAOJdbcImpl();
    }
    public static EncheresDAO getEncheresDAO() {
        return new EncheresDAOJdbcImpl();
    }
}
