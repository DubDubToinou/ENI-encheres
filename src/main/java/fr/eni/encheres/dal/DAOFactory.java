package fr.eni.encheres.dal;

public abstract class DAOFactory {

    public static UtilisateurDAO getRepasDAO() {
        return new UtilisateurDAOJdbcImpl();
    }
    public static ArticleDAO getRepasDAO() {
        return new ArticleDAOJdbcImpl();
    }
}
