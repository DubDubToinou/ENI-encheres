package fr.eni.encheres.dal;

public abstract class DAOFactory {

    public static UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurDAOJdbcImpl();
    }
    public static ArticleDAO getArticleDAO() {
        return new ArticleDAOJdbcImpl();
    }
}
