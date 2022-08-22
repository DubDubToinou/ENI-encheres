package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;

import java.sql.*;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    private static final String INSERT_ARTICLE = "INSERT INTO Articles(nom_article, description, date_debut_encheres, date_fin_encheres, " +
            "prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS(no_article, rue, code_postal, ville VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ARTICLE = "UPDATE Articles SET nom_article = ?, description = ?, date_debut_encheres = ?, " +
            "date_fin_encheres = ?, prix_initial = ?, no_categorie = ? WHERE no_article = ?";
    private static final String DELETE_ARTICLE = "DELETE FROM Articles WHERE no_article = ?";

    @Override
    public void insert(Articles article) {
        if(article == null) {
            //TODO Gestion erreur
        }

        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement pstmtArticle = con.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement pstmtRetrait = con.prepareStatement(INSERT_RETRAIT);

            pstmtArticle.setString(1, article.getNomArticle());
            pstmtArticle.setString(2, article.getDescription());
            pstmtArticle.setDate(3, Date.valueOf(article.getDateDebutEncheres()));
            pstmtArticle.setDate(4, Date.valueOf(article.getDateFinEncheres()));
            pstmtArticle.setInt(5, article.getMiseAPrix());
            pstmtArticle.setInt(6, article.getPrixVente());
            pstmtArticle.setInt(7, article.getUtilisateurs().getNoUtilisateur());
            pstmtArticle.setInt(8, article.getCategorieArticle().getNoCategorie());

            pstmtArticle.executeUpdate();

            ResultSet rs = pstmtArticle.getGeneratedKeys();

            if(rs.next()) {
                article.setNoArticle(rs.getInt("no_article"));

                pstmtRetrait.setInt(1, article.getNoArticle());
                pstmtRetrait.setString(2, article.getUtilisateurs().getRue());
                pstmtRetrait.setString(3, article.getUtilisateurs().getCodePostal());
                pstmtRetrait.setString(4, article.getUtilisateurs().getVille());

                pstmtRetrait.executeUpdate();
            }

            rs.close();
            pstmtArticle.close();
            pstmtRetrait.close();
            con.close();


        } catch(SQLException ex) {

        }
    }

    @Override
    public void update(Articles articles) {

    }

    @Override
    public void delete(Articles articles) {

    }
    @Override
    public List<Articles> selectAll() {
        return null;
    }

    @Override
    public List<Articles> selectByCategorie(Categorie categorie) {
        return null;
    }

    @Override
    public List<Articles> selectByMotCle(String motCle) {
        return null;
    }

    @Override
    public List<Articles> selectVentesEnCours(Utilisateur utilisateur) {
        return null;
    }

    @Override
    public List<Articles> selectVentesTerminees(Utilisateur utilisateur) {
        return null;
    }
}
