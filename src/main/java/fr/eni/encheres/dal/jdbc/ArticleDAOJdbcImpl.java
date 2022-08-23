package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    private static final String INSERT_ARTICLE = "INSERT INTO Articles(nom_article, description, date_debut_encheres, date_fin_encheres, " +
            "prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS(no_article, rue, code_postal, ville VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ARTICLE = "UPDATE Articles SET nom_article = ?, description = ?, date_debut_encheres = ?, " +
            "date_fin_encheres = ?, prix_initial = ?, no_categorie = ? WHERE no_article = ?";
    private static final String UPDATE_RETRAIT = "UPDATE Retraits SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
    private static final String DELETE_ARTICLE = "DELETE FROM Articles WHERE no_article = ?";
    private static final String SELECT_VENTES_EN_COURS = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, u.pseudo, c.libelle FROM Articles a " +
            "LEFT JOIN Utilisateurs u " +
            "ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE()";
    private static final String SELECT_BY_CATEGORIE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, u.pseudo, c.libelle FROM Articles " +
            "LEFT JOIN Utilisateurs u " +
            "ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE() AND a.no_categorie = ?";

    private static final String SELECT_BY_MOT_CLE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, u.pseudo, c.libelle FROM Articles " +
            "LEFT JOIN Utilisateurs u " +


            "ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE() " +
            "AND (a.nom_article LIKE %?% OR a.description LIKE %?%)";

    private static final String SELECT_VENTES_EN_COURS_BY_UTILISATEUR = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, c.libelle FROM Articles " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE() " +
            "AND a.no_utilisateur = ?";

    private static final String SELECT_VENTES_TERMINEES_BY_UTILISATEUR = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, c.libelle FROM Articles " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_fin_encheres <= GETDATE() " +
            "AND a.no_utilisateur = ?";

    @Override
    public void insert(Articles article) {
        if(article == null) {
            //TODO Gestion erreur
        }

        try(Connection con = ConnectionProvider.getConnection()) {

            PreparedStatement pstmtArticle = con.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement pstmtRetrait = con.prepareStatement(INSERT_RETRAIT);

            //Ajout de l'article
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

                //ajout du lieu de retrait (par défaut adresse du vendeur)
                pstmtRetrait.setInt(1, article.getNoArticle());
                pstmtRetrait.setString(2, article.getUtilisateurs().getRue());
                pstmtRetrait.setString(3, article.getUtilisateurs().getCodePostal());
                pstmtRetrait.setString(4, article.getUtilisateurs().getVille());

                pstmtRetrait.executeUpdate();
            }

            rs.close();
            pstmtArticle.close();
            pstmtRetrait.close();


        } catch(SQLException ex) {
//TODO
        }
    }

    @Override
    public void update(Articles article) {

        try(Connection con = ConnectionProvider.getConnection()) {

            PreparedStatement pstmtArticle = con.prepareStatement(UPDATE_ARTICLE);
            PreparedStatement pstmtRetrait = con.prepareStatement(UPDATE_RETRAIT);

            pstmtArticle.setString(1, article.getNomArticle());
            pstmtArticle.setString(2, article.getDescription());
            pstmtArticle.setDate(3, Date.valueOf(article.getDateDebutEncheres()));
            pstmtArticle.setDate(4, Date.valueOf(article.getDateFinEncheres()));
            pstmtArticle.setInt(5, article.getMiseAPrix());
            pstmtArticle.setInt(6, article.getCategorieArticle().getNoCategorie());
            pstmtArticle.setInt(7, article.getNoArticle());

            pstmtArticle.executeUpdate();

            pstmtRetrait.setString(1, article.getLieuRetrait().getRue());
            pstmtRetrait.setString(2, article.getLieuRetrait().getCodePostal());
            pstmtRetrait.setString(3, article.getLieuRetrait().getVille());
            pstmtRetrait.setInt(4, article.getNoArticle());

            pstmtArticle.close();
            pstmtRetrait.close();

        } catch (SQLException ex) {
    //TODO
        }
    }

    @Override
    public void delete(Articles article) {

        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement pstmtArticle = con.prepareStatement(DELETE_ARTICLE);


            pstmtArticle.setInt(1, article.getNoArticle());
            pstmtArticle.executeUpdate();

            pstmtArticle.close();

        } catch(SQLException ex) {
//TODO
        }
    }
    @Override
    public List<Articles> selectAll() {
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){
            Statement selectall = con.createStatement();
            ResultSet rs = selectall.executeQuery(SELECT_VENTES_EN_COURS);

            while(rs.next()) {
              Articles article = new Articles(rs.getInt("a.no_article"), rs.getString("a.nom_article"), rs.getString("a.description"),
                      rs.getDate("a.date_debut_encheres").toLocalDate(), rs.getDate("a.date_fin_encheres").toLocalDate(),
                      rs.getInt("a.prix_initial"), rs.getInt("a.prix_vente"));

              Utilisateur utilisateur = new Utilisateur(rs.getString("u.pseudo"));
              Categorie categorie = new Categorie(rs.getString("c.libelle"));

              article.setUtilisateurs(utilisateur);
              article.setCategorieArticle(categorie);

              listeArticles.add(article);

            }

            rs.close();
            selectall.close();

        } catch(SQLException ex) {
            //TODO
        }

        return listeArticles;
    }

    @Override
    public List<Articles> selectByCategorie(Categorie categorie) {
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){
           PreparedStatement pstmt = con.prepareStatement(SELECT_BY_CATEGORIE);
           pstmt.setInt(1, categorie.getNoCategorie());

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt("a.no_article"), rs.getString("a.nom_article"), rs.getString("a.description"),
                        rs.getDate("a.date_debut_encheres").toLocalDate(), rs.getDate("a.date_fin_encheres").toLocalDate(),
                        rs.getInt("a.prix_initial"), rs.getInt("a.prix_vente"));

                Utilisateur utilisateur = new Utilisateur(rs.getString("u.pseudo"));
                Categorie categorieArticle = new Categorie(rs.getString("c.libelle"));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorieArticle);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();
        }catch(SQLException ex) {
            //TODO
        }

        return listeArticles;
    }

    @Override
    public List<Articles> selectByMotCle(String motCle) {
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pstmt = con.prepareStatement(SELECT_BY_MOT_CLE);
            pstmt.setString(1, motCle);
            pstmt.setString(2, motCle);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt("a.no_article"), rs.getString("a.nom_article"), rs.getString("a.description"),
                        rs.getDate("a.date_debut_encheres").toLocalDate(), rs.getDate("a.date_fin_encheres").toLocalDate(),
                        rs.getInt("a.prix_initial"), rs.getInt("a.prix_vente"));

                Utilisateur utilisateur = new Utilisateur(rs.getString("u.pseudo"));
                Categorie categorie = new Categorie(rs.getString("c.libelle"));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorie);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();

        }catch(SQLException ex) {
    //TODO
        }


        return listeArticles;
    }

    @Override
    public List<Articles> selectVentesEnCours(Utilisateur utilisateur) {
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_VENTES_EN_COURS_BY_UTILISATEUR);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt("a.no_article"), rs.getString("a.nom_article"), rs.getString("a.description"),
                        rs.getDate("a.date_debut_encheres").toLocalDate(), rs.getDate("a.date_fin_encheres").toLocalDate(),
                        rs.getInt("a.prix_initial"), rs.getInt("a.prix_vente"));

                Categorie categorie = new Categorie(rs.getString("c.libelle"));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorie);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();

        } catch(SQLException ex) {
            //TODO
        }

        return listeArticles;
    }

    @Override
    public List<Articles> selectVentesTerminees(Utilisateur utilisateur) {
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_VENTES_TERMINEES_BY_UTILISATEUR);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt("a.no_article"), rs.getString("a.nom_article"), rs.getString("a.description"),
                        rs.getDate("a.date_debut_encheres").toLocalDate(), rs.getDate("a.date_fin_encheres").toLocalDate(),
                        rs.getInt("a.prix_initial"), rs.getInt("a.prix_vente"));

                Categorie categorie = new Categorie(rs.getString("c.libelle"));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorie);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();

        } catch(SQLException ex) {
            //TODO
        }

        return listeArticles;
    }
}
